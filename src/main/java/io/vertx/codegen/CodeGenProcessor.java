package io.vertx.codegen;

import io.vertx.core.json.JsonObject;
import org.mvel2.templates.TemplateRuntime;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@SupportedAnnotationTypes({"io.vertx.codegen.annotations.VertxGen","io.vertx.codegen.annotations.Options",
  "io.vertx.codegen.annotations.GenModule"})
@javax.annotation.processing.SupportedOptions({"outputDirectory"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private File outputDirectory;
  private List<CodeGenerator> codeGenerators;

  @Override
  public synchronized void init(ProcessingEnvironment env) {
    super.init(env);
    codeGenerators = new ArrayList<>();
    Enumeration<URL> descriptors = Collections.emptyEnumeration();
    try {
      descriptors = CodeGenProcessor.class.getClassLoader().getResources("codegen.json");
    } catch (IOException ignore) {
      env.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not load code generator descriptors");
    }
    while (descriptors.hasMoreElements()) {
        URL descriptor = descriptors.nextElement();
        try (Scanner scanner = new Scanner(descriptor.openStream(), "UTF-8").useDelimiter("\\A")) {
          String s = scanner.next();
          JsonObject obj = new JsonObject(s);
          String name = obj.getString("name");
          for (String modelKind : Arrays.asList("options", "class", "package", "module")) {
            JsonObject model = obj.getObject(modelKind);
            if (model != null) {
              String templateFileName = model.getString("templateFileName");
              String nameTemplate = model.getString("nameTemplate");
              Template compiledTemplate = new Template(templateFileName);
              compiledTemplate.setOptions(env.getOptions());
              codeGenerators.add(new CodeGenerator(modelKind, nameTemplate, compiledTemplate));
            }
          }
          log.info("Loaded " + name + " code generator");
        } catch (Exception e) {
          String msg = "Could not load code generator " + descriptor;
          log.log(Level.SEVERE, msg, e);
          env.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
        }
      }
    String outputDirectoryOption = env.getOptions().get("outputDirectory");
    if (outputDirectoryOption != null) {
      outputDirectory = new File(outputDirectoryOption);
      if (!outputDirectory.exists()) {
        env.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " does not exist");
      }
      if (!outputDirectory.isDirectory()) {
        env.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " is not a directory");
      }
    }
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (!roundEnv.errorRaised()) {
      if (!roundEnv.processingOver()) {

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
              vars.put("type", model.getFqn());
              for (CodeGenerator codeGenerator : codeGenerators) {
                if (codeGenerator.kind.equals(model.getKind())) {
                  String relativeName = TemplateRuntime.eval(codeGenerator.nameTemplate, vars).toString();
                  if (relativeName.endsWith(".java")) {
                    // Special handling for .java
                    JavaFileObject target = processingEnv.getFiler().createSourceFile(relativeName.substring(0, relativeName.length() - ".java".length()));
                    String output = codeGenerator.transformTemplate.render(model);
                    try (Writer writer = target.openWriter()) {
                      writer.append(output);
                    }
                  } else {
                    File target = new File(outputDirectory, relativeName);
                    codeGenerator.transformTemplate.apply(model, target);
                  }
                  log.info("Generated model " + model.getFqn() + ": " + relativeName);
                }
              }
            } else {
              log.info("Validated model " + model.getFqn());
            }
          } catch (GenException e) {
            String msg = "Could not generate model for " + e.element + ": " + e.msg;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
          } catch (Exception e) {
            String msg = "Could not generate element for " + entry.getKey() + ": " + e.getMessage();
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, entry.getKey());
          }
        });
      }
    }
    return true;
  }

  static class CodeGenerator {
    final String kind;
    final String nameTemplate;
    final Template transformTemplate;
    CodeGenerator(String kind, String nameTemplate, Template transformTemplate) {
      this.kind = kind;
      this.nameTemplate = nameTemplate;
      this.transformTemplate = transformTemplate;
    }
  }
}
