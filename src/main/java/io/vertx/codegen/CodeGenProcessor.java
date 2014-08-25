package io.vertx.codegen;

import io.vertx.codegen.annotations.*;
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
          String templateFileName = obj.getString("templateFileName");
          String nameTemplate = obj.getString("nameTemplate");
          Template compiledTemplate = new Template(templateFileName);
          compiledTemplate.setOptions(env.getOptions());
          codeGenerators.add(new CodeGenerator(nameTemplate, compiledTemplate));
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

        // Check options
        roundEnv.getElementsAnnotatedWith(Options.class).forEach(element -> {
          try {
            codegen.validateOption(element);
          } catch (GenException e) {
            String msg = e.msg;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
          }
        });

        // Generate source code
        for (Model model : codegen.getModels()) {
          try {
            if (outputDirectory != null) {
              Map<String, Object> vars = new HashMap<>();
              vars.put("helper", new Helper());
              vars.put("options", processingEnv.getOptions());
              vars.put("fileSeparator", File.separator);
              vars.put("type", model.getType().getRaw());
              for (CodeGenerator codeGenerator : codeGenerators) {
                String relativeName = TemplateRuntime.eval(codeGenerator.nameTemplate, vars).toString();
                if (relativeName.endsWith(".java")) {
                  // Special handling for .java
                  JavaFileObject target = processingEnv.getFiler().createSourceFile(relativeName.substring(0, relativeName.length() - ".java".length()));
                  String output = codeGenerator.modelTemplate.render(model);
                  try (Writer writer = target.openWriter()) {
                    writer.append(output);
                  }
                } else {
                  File target = new File(outputDirectory, relativeName);
                  codeGenerator.modelTemplate.apply(model, target);
                }
                log.info("Generated model for class " + model.getIfaceFQCN() + ": " + relativeName);
              }
            } else {
              log.info("Validated model for class " + model.getIfaceFQCN());
            }
          } catch (GenException e) {
            String msg = "Could not generate model for class " + e.element + ": " + e.msg;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
            break;
          } catch (Exception e) {
            String msg = "Could not generate element " + model.getIfaceFQCN();
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, model.getElement());
            break;
          }
        }
      }
    }
    return true;
  }

  static class CodeGenerator {
    final String nameTemplate;
    final Template modelTemplate;
    CodeGenerator(String nameTemplate, Template modelTemplate) {
      this.nameTemplate = nameTemplate;
      this.modelTemplate = modelTemplate;
    }
  }
}
