package io.vertx.codegen;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleModel implements Model {

  private final PackageElement element;
  private final ModuleInfo info;
  private final Set<ClassModel> classes;

  public ModuleModel(PackageElement element, ModuleInfo info, Set<ClassModel> classes) {
    this.element = element;
    this.info = info;
    this.classes = classes;
  }

  public String getName() {
    return info.getName();
  }

  public String translateFqn(String name) {
    return info.translatePackageName(name);
  }

  @Override
  public String getKind() {
    return "module";
  }

  @Override
  public Element getElement() {
    return element;
  }

  @Override
  public String getFqn() {
    return info.getPackageName();
  }

  public Set<ClassModel> getClasses() {
    return classes;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = new HashMap<>();
    vars.put("fqn", info.getPackageName());
    vars.put("name", info.getName());
    vars.put("module", getModule());
    vars.put("classes", getClasses());
    vars.putAll(Case.vars());
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return info;
  }
}
