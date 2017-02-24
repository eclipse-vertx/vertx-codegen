package io.vertx.codegen;

import io.vertx.codegen.type.AnnotationTypeInfo;

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
  private final List<AnnotationTypeInfo> annotationTypeInfos;

  public ModuleModel(PackageElement element, ModuleInfo info, List<AnnotationTypeInfo> annotationTypeInfos) {
    this.element = element;
    this.info = info;
    this.annotationTypeInfos = annotationTypeInfos != null ? annotationTypeInfos : Collections.emptyList();
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

  public List<AnnotationTypeInfo> getAnnotationTypeInfos() {
    return annotationTypeInfos;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("fqn", info.getPackageName());
    vars.put("name", info.getName());
    vars.put("module", getModule());
    vars.put("annotations", getAnnotationTypeInfos());
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return info;
  }
}
