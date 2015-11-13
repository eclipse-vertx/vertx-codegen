package io.vertx.codegen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeNameTranslator;
import org.mvel2.MVEL;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@javax.annotation.processing.SupportedOptions({"outputDirectory","codeGenerators"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private final static ObjectMapper mapper = new ObjectMapper();
  private static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private File outputDirectory;
  private Map<String, List<CodeGenerator>> codeGenerators;
  Map<File, GeneratedFile> generatedFiles = new HashMap<>();

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Arrays.asList(
        VertxGen.class,
        ProxyGen.class,
        DataObject.class,
        DataObject.class,
        ModuleGen.class
    ).stream().map(Class::getName).collect(Collectors.toSet());
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    generatedFiles.clear();
  }

  private Collection<CodeGenerator> getCodeGenerators() {
    if (codeGenerators == null) {
      Map<String, List<CodeGenerator>> codeGenerators = new LinkedHashMap<>();
      Enumeration<URL> descriptors = Collections.emptyEnumeration();
      try {
        descriptors = CodeGenProcessor.class.getClassLoader().getResources("codegen.json");
      } catch (IOException ignore) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not load code generator descriptors");
      }
      Set<String> templates = new HashSet<>();
      while (descriptors.hasMoreElements()) {
        URL descriptor = descriptors.nextElement();
        try (Scanner scanner = new Scanner(descriptor.openStream(), "UTF-8").useDelimiter("\\A")) {
          String s = scanner.next();
          ObjectNode obj = (ObjectNode) mapper.readTree(s);
          String name = obj.get("name").asText();
          ArrayNode generatorsCfg = (ArrayNode) obj.get("generators");
          for (JsonNode generator : generatorsCfg) {
            String kind = generator.get("kind").asText();
            String templateFileName = generator.get("templateFileName").asText();
            String fileName = generator.get("fileName").asText();
            boolean incremental = generator.has("incremental") && generator.get("incremental").asBoolean();
            if (!templates.contains(templateFileName)) {
              templates.add(templateFileName);
              Serializable fileNameExpression = MVEL.compileExpression(fileName);
              Template compiledTemplate = new Template(templateFileName);
              compiledTemplate.setOptions(processingEnv.getOptions());
              List<CodeGenerator> generators = codeGenerators.computeIfAbsent(name, abc -> new ArrayList<>());
              generators.add(new CodeGenerator(name, kind, incremental, fileNameExpression, compiledTemplate));
              log.info("Loaded " + name + " code generator");
            }
          }
        } catch (Exception e) {
          String msg = "Could not load code generator " + descriptor;
          log.log(Level.SEVERE, msg, e);
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
        }
      }
      String outputDirectoryOption = processingEnv.getOptions().get("outputDirectory");
      if (outputDirectoryOption != null) {
        outputDirectory = new File(outputDirectoryOption);
        if (!outputDirectory.exists()) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " does not exist");
        }
        if (!outputDirectory.isDirectory()) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " is not a directory");
        }
      }
      String codeGeneratorsOption = processingEnv.getOptions().get("codeGenerators");
      if (codeGeneratorsOption != null) {
        Set<String> wanted = Stream.of(codeGeneratorsOption.split(",")).map(String::trim).collect(Collectors.toSet());
        if (codeGenerators.keySet().containsAll(wanted)) {
          codeGenerators.keySet().retainAll(wanted);
        } else {
          codeGenerators.clear();
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Code generators " + wanted.removeAll(codeGenerators.keySet()) + " not found");
        }
      }
      this.codeGenerators = codeGenerators;
    }
    ArrayList<CodeGenerator> ret = new ArrayList<>();
    codeGenerators.values().stream().forEach(ret::addAll);
    return ret;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    if (!roundEnv.processingOver()) {
      Collection<CodeGenerator> codeGenerators = getCodeGenerators();

      if (!roundEnv.errorRaised()) {
        CodeGen codegen = new CodeGen(processingEnv, roundEnv);

        // Generate source code
        codegen.getModels().forEach(entry -> {
          try {
            Model model = entry.getValue();
            if (outputDirectory != null) {
              Map<String, Object> vars = new HashMap<>();
              vars.put("helper", new Helper());
              vars.put("options", processingEnv.getOptions());
              vars.put("fileSeparator", File.separator);
              vars.put("fqn", model.getFqn());
              vars.put("module", model.getModule());
              vars.put("model", model);
              vars.putAll(model.getVars());
              vars.putAll(ClassKind.vars());
              vars.putAll(MethodKind.vars());
              vars.putAll(Case.vars());
              for (CodeGenerator codeGenerator : codeGenerators) {
                Map<String, Object> translators = TypeNameTranslator.vars(codeGenerator.name);
                vars.putAll(translators);
                if (codeGenerator.kind.equals(model.getKind())) {
                  String relativeName = (String) MVEL.executeExpression(codeGenerator.filenameExpr, vars);
                  if (relativeName != null) {
                    if (relativeName.endsWith(".java") && !relativeName.contains("/")) {
                      // Special handling for .java
                      String fqn = relativeName.substring(0, relativeName.length() - ".java".length());
                      // Avoid to recreate the same file (this may happen as we unzip and recompile source trees)
                      if (processingEnv.getElementUtils().getTypeElement(fqn) != null) {
                        continue;
                      }
                      JavaFileObject target = processingEnv.getFiler().createSourceFile(fqn);
                      String output = codeGenerator.transformTemplate.render(model, translators);
                      try (Writer writer = target.openWriter()) {
                        writer.append(output);
                      }
                    } else {
                      File target = new File(outputDirectory, relativeName).getAbsoluteFile();
                      if (codeGenerator.incremental) {
                        List<ModelProcessing> processings = generatedFiles.computeIfAbsent(target, GeneratedFile::new);
                        processings.add(new ModelProcessing(model, codeGenerator));
                      } else {
                        codeGenerator.transformTemplate.apply(model, target, translators);
                      }
                    }
                    log.info("Generated model " + model.getFqn() + ": " + relativeName);
                  }
                }
              }
            } else {
              log.info("Validated model " + model.getFqn());
            }
          } catch (GenException e) {
            reportGenException(e);
          } catch (Exception e) {
            reportException(e, entry.getKey());
          }
        });
      }
    } else {

      // Incremental post processing
      generatedFiles.values().forEach(generated -> {
        File file = generated.file;
        Helper.ensureParentDir(file);
        try (FileWriter fileWriter = new FileWriter(file)) {
          for (int i = 0; i < generated.size(); i++) {
            ModelProcessing processing = generated.get(i);
            Map<String, Object> vars = new HashMap<>();
            vars.put("incrementalIndex", i);
            vars.put("incrementalSize", generated.size());
            vars.put("session", generated.session);
            try {
              String part = processing.generator.transformTemplate.render(processing.model, vars);
              fileWriter.append(part);
            } catch (GenException e) {
              reportGenException(e);
            } catch (Exception e) {
              reportException(e, processing.model.getElement());
            }
          }
        } catch (Exception e) {
          reportException(e, generated.get(0).model.getElement());
        }
      });
    }
    return true;
  }

  private void reportGenException(GenException e) {
    String msg = "Could not generate model for " + e.element + ": " + e.msg;
    log.log(Level.SEVERE, msg, e);
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
  }

  private void reportException(Exception e, Element elt) {
    String msg = "Could not generate element for " + elt + ": " + e.getMessage();
    log.log(Level.SEVERE, msg, e);
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, elt);
  }

  private static class CodeGenerator {
    final String name;
    final String kind;
    final boolean incremental;
    final Serializable filenameExpr;
    final Template transformTemplate;
    CodeGenerator(String name, String kind, boolean incremental, Serializable filenameExpr, Template transformTemplate) {
      this.name = name;
      this.kind = kind;
      this.filenameExpr = filenameExpr;
      this.transformTemplate = transformTemplate;
      this.incremental = incremental;
    }
  }

  private static class ModelProcessing {
    final Model model;
    final CodeGenerator generator;
    public ModelProcessing(Model model, CodeGenerator generator) {
      this.model = model;
      this.generator = generator;
    }
  }

  private static class GeneratedFile extends ArrayList<ModelProcessing> {
    final File file;
    final Map<String, Object> session = new HashMap<>();
    public GeneratedFile(File file) {
      super();
      this.file = file;
    }
  }
}
