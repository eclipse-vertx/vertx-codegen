package io.vertx.codegen.generators.mvel;

import io.vertx.codegen.*;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeNameTranslator;
import org.mvel2.MVEL;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class MvelCodeGenerator extends Generator<Model> {

  public String filename;
  public String templateFilename;
  private Template template;
  private Serializable filenameExpr;

  @Override
  public void load(ProcessingEnvironment processingEnv) {
    super.load(processingEnv);
    Template template = new Template(templateFilename);
    template.setOptions(processingEnv.getOptions());
    this.template = template;
    this.filenameExpr = MVEL.compileExpression(filename);
  }

  @Override
  public String filename(Model model) {

    Map<String, Object> vars = new HashMap<>();
    vars.put("helper", new Helper());
    vars.put("options", env.getOptions());
    vars.put("fileSeparator", File.separator);
    vars.put("fqn", model.getFqn());
    vars.put("module", model.getModule());
    vars.put("model", model);
    vars.putAll(model.getVars());
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    vars.putAll(Case.vars());

    vars.putAll(TypeNameTranslator.vars(name));

    return (String) MVEL.executeExpression(filenameExpr, vars);
  }

  @Override
  public String render(Model model, int index, int size, Map<String, Object> session) {
    Map<String, Object> vars = new HashMap<>();
    vars.putAll(TypeNameTranslator.vars(name));
    if (incremental) {
      vars.put("incrementalIndex", index);
      vars.put("incrementalSize", size);
      vars.put("session", session);
    }
    return template.render(model, vars);
  }
}
