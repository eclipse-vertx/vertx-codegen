package io.vertx.codegen;

import io.vertx.codegen.annotations.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@SupportedAnnotationTypes({"io.vertx.codegen.annotations.VertxGen"})
@javax.annotation.processing.SupportedOptions({"fileExtension", "outputLocation", "templateFileName"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private Elements elementUtils;
  private Types typeUtils;
  private String fileExtension;
  private String outputLocation;
  private String templateFileName;

  @Override
  public synchronized void init(ProcessingEnvironment env) {
    super.init(env);
    fileExtension = env.getOptions().get("fileExtension");
    outputLocation = env.getOptions().get("outputLocation");
    templateFileName = env.getOptions().get("templateFileName");
    elementUtils = env.getElementUtils();
    typeUtils = env.getTypeUtils();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (!roundEnv.errorRaised()) {
      if (!roundEnv.processingOver()) {
        for (Element genElt : roundEnv.getElementsAnnotatedWith(VertxGen.class)) {
          String pkgName = elementUtils.getPackageOf(genElt).getQualifiedName().toString();
          if (!pkgName.contains("impl")) {
            try {
              Generator generator = new Generator();
              generator.traverseElem(elementUtils, typeUtils, genElt);
              if (outputLocation != null && templateFileName != null) {
                String target = outputLocation + File.separator + Helper.convertCamelCaseToUnderscores(genElt.getSimpleName().toString()) + fileExtension;
                generator.applyTemplate(target, templateFileName);
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
    }
    return true;
  }
}
