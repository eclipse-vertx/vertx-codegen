package io.vertx.codegen;

import io.vertx.codegen.type.AnnotationValueInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleModel implements Model {

  private final PackageElement element;
  private final ModuleInfo info;
  private final List<AnnotationValueInfo> annotationValueInfos;

  public ModuleModel(PackageElement element, ModuleInfo info, List<AnnotationValueInfo> annotationValueInfos) {
    this.element = element;
    this.info = info;
    this.annotationValueInfos = annotationValueInfos != null ? annotationValueInfos : Collections.emptyList();
  }

  public String getName() {
    return info.getName();
  }

  public String translateFqn(String name) {
    return info.translatePackageName(name);
  }

  @Override
  public boolean process() {
    return false;
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

  public List<AnnotationValueInfo> getAnnotations() {
    return annotationValueInfos;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("fqn", info.getPackageName());
    vars.put("name", info.getName());
    vars.put("module", getModule());
    vars.put("annotations", getAnnotations());
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return info;
  }
}
