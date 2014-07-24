package io.vertx.codegen;

import io.vertx.codegen.annotations.*;
import org.mvel2.templates.TemplateRuntime;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@SupportedAnnotationTypes({"io.vertx.codegen.annotations.VertxGen","io.vertx.codegen.annotations.Options"})
@javax.annotation.processing.SupportedOptions({"templateFileName", "nameTemplate"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private Elements elementUtils;
  private Types typeUtils;
  private String templateFileName;
  private String nameTemplate;
  private Generator generator;

  @Override
  public synchronized void init(ProcessingEnvironment env) {
    super.init(env);
    templateFileName = env.getOptions().get("templateFileName");
    nameTemplate = env.getOptions().get("nameTemplate");
    elementUtils = env.getElementUtils();
    typeUtils = env.getTypeUtils();
    generator = new Generator();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (!roundEnv.errorRaised()) {
      if (!roundEnv.processingOver()) {
        Generator generator = new Generator();

        // Check options
        roundEnv.getElementsAnnotatedWith(Options.class).forEach(element -> {
          try {
            // Disabled until Vert.x has all options correct (VertxOptions cannot be migrated at the moment)
            // generator.checkOption(elementUtils, element);
          } catch (GenException e) {
            String msg = e.msg;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
          }
        });

        // Generate source code
        List<? extends Element> elements = roundEnv.
            getElementsAnnotatedWith(VertxGen.class).
            stream().
            filter(elt -> !elementUtils.getPackageOf(elt).getQualifiedName().toString().contains("impl")).
            collect(Collectors.toList());
        generator.addSources(elements);
        for (Element genElt : elements) {
          try {
            Model model = generator.resolve(elementUtils, typeUtils, genElt.toString());
            if (nameTemplate != null && templateFileName != null) {
              Map<String, Object> vars = new HashMap<>();
              vars.put("helper", new Helper());
              vars.put("fileSeparator", File.separator);
              vars.put("typeSimpleName", genElt.getSimpleName());
              vars.put("typeFQN", genElt.toString());
              String target = TemplateRuntime.eval(nameTemplate, vars).toString();
              model.applyTemplate(target, templateFileName);
              log.info("Generated model for class " + genElt);
            } else {
              log.info("Validated model for class " + genElt);
            }
          } catch (GenException e) {
            String msg = "Could not generate model for class " + e.element + ": " + e.msg;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
            break;
          } catch (Exception e) {
            String msg = "Could not generate element " + genElt;
            log.log(Level.SEVERE, msg, e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, genElt);
            break;
          }
        }
      }
    }
    return true;
  }
}
