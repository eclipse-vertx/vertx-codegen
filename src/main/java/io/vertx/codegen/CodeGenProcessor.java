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
@javax.annotation.processing.SupportedOptions({"codegen.output","codegen.generators"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private final static ObjectMapper mapper = new ObjectMapper();
  private static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private File outputDirectory;
  private List<CodeGenerator> codeGenerators;
  Map<String, GeneratedFile> generatedFiles = new HashMap<>();
  private Map<String, String> relocations = new HashMap<>();

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
      List<CodeGenerator> generators = new ArrayList<>();
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
              generators.add(new CodeGenerator(name, kind, incremental, fileNameExpression, compiledTemplate));
            }
          }
          log.info("Loaded " + name + " code generator");
        } catch (Exception e) {
          String msg = "Could not load code generator " + descriptor;
          log.log(Level.SEVERE, msg, e);
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
        }
      }
      String outputDirectoryOption = processingEnv.getOptions().get("codegen.output");
      if (outputDirectoryOption == null) {
        outputDirectoryOption = processingEnv.getOptions().get("outputDirectory");
        if (outputDirectoryOption != null) {
          log.warning("Please use 'codegen.output' option instead of 'outputDirectory' option");
        }
      }
      if (outputDirectoryOption != null) {
        outputDirectory = new File(outputDirectoryOption);
        if (!outputDirectory.exists()) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " does not exist");
        }
        if (!outputDirectory.isDirectory()) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " is not a directory");
        }
      }
      String codeGeneratorsOption = processingEnv.getOptions().get("codegen.generators");
      if (codeGeneratorsOption == null) {
        codeGeneratorsOption = processingEnv.getOptions().get("codeGenerators");
        if (codeGeneratorsOption != null) {
          log.warning("Please use 'codegen.generators' option instead of 'codeGenerators' option");
        }
      }
      if (codeGeneratorsOption != null) {
        Set<String> wanted = Stream.of(codeGeneratorsOption.split(",")).map(String::trim).collect(Collectors.toSet());
        generators = generators.stream().filter(cg -> wanted.contains(cg.name)).collect(Collectors.toList());
      }

      relocations = processingEnv.getOptions()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().startsWith("codegen.output."))
        .collect(Collectors.toMap(
          e -> e.getKey().substring("codegen.output.".length()),
          Map.Entry::getValue)
        );

      codeGenerators = generators;
    }
    return codeGenerators;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    if (!roundEnv.processingOver()) {
      Collection<CodeGenerator> codeGenerators = getCodeGenerators();

      if (!roundEnv.errorRaised()) {
        CodeGen codegen = new CodeGen(processingEnv, roundEnv);
        Map<String, GeneratedFile> generatedClasses = new HashMap<>();

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
                      String relocation = relocations.get(codeGenerator.name);
                      if (relocation != null) {
                        relativeName = relocation + '/' +
                          relativeName.substring(0, relativeName.length() - ".java".length()).replace('.', '/') + ".java";
                      }
                    }

                    if (relativeName.endsWith(".java") && !relativeName.contains("/")) {

                      // Special handling for .java
                      String fqn = relativeName.substring(0, relativeName.length() - ".java".length());
                      // Avoid to recreate the same file (this may happen as we unzip and recompile source trees)
                      if (processingEnv.getElementUtils().getTypeElement(fqn) != null) {
                        continue;
                      }

                      if (codeGenerator.incremental) {
                        List<ModelProcessing> processings = generatedClasses.computeIfAbsent(fqn, GeneratedFile::new);
                        processings.add(new ModelProcessing(model, codeGenerator));
                      } else {
                        String result = codeGenerator.transformTemplate.render(model);
                        if (result != null) {
                          JavaFileObject target = processingEnv.getFiler().createSourceFile(fqn);
                          try (Writer w = target.openWriter()) {
                            w.write(result);
                          }
                        }
                      }
                    } else {
                      String target = new File(outputDirectory, relativeName).getAbsoluteFile().getAbsolutePath();
                      if (codeGenerator.incremental) {
                        List<ModelProcessing> processings = generatedFiles.computeIfAbsent(target, GeneratedFile::new);
                        processings.add(new ModelProcessing(model, codeGenerator));
                      } else {
                        codeGenerator.transformTemplate.apply(model, new File(target), translators);
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
        generatedClasses.values().forEach(generated -> {
          try {
            JavaFileObject target = processingEnv.getFiler().createSourceFile(generated.uri);
            try (Writer writer = target.openWriter()) {
              generated.writeTo(writer);
            }
          } catch (GenException e) {
            reportGenException(e);
          } catch (Exception e) {
            reportException(e, generated.get(0).model.getElement());
          }
        });
      }
    } else {

      // Incremental post processing
      generatedFiles.values().forEach(generated -> {
        File file = new File(generated.uri);
        Helper.ensureParentDir(file);
        try (FileWriter fileWriter = new FileWriter(file)) {
          generated.writeTo(fileWriter);
        } catch (GenException e) {
          reportGenException(e);
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
    final String uri;
    final Map<String, Object> session = new HashMap<>();
    public GeneratedFile(String uri) {
      super();
      this.uri = uri;
    }

    void writeTo(Writer writer) throws IOException {
      Collections.sort(this, (o1, o2) ->
        o1.model.getElement().getSimpleName().toString().compareTo(
          o2.model.getElement().getSimpleName().toString()));
      for (int i = 0; i < size(); i++) {
        ModelProcessing processing = get(i);
        Map<String, Object> vars = new HashMap<>();
        vars.put("incrementalIndex", i);
        vars.put("incrementalSize", size());
        vars.put("session", session);
        try {
          String part = processing.generator.transformTemplate.render(processing.model, vars);
          if (part != null) {
            writer.append(part);
          }
        } catch (GenException e) {
          throw e;
        } catch (Exception e) {
          throw new GenException(processing.model.getElement(), e.getMessage());
        }
      }
    }
  }
}
