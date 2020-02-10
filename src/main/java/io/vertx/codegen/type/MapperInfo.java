package io.vertx.codegen.type;

import io.vertx.codegen.MapperKind;

import java.util.List;

public class MapperInfo {

  private TypeInfo targetType;
  private String qualifiedName;
  private List<String> selectors;
  private MapperKind kind;

  public MapperInfo() {
  }

  public TypeInfo getTargetType() {
    return targetType;
  }

  public void setTargetType(TypeInfo targetType) {
    this.targetType = targetType;
  }

  /**
   * @return the mapper full qualified name
   */
  public String getQualifiedName() {
    return qualifiedName;
  }

  /**
   * Set the mapper full qualified name
   *
   * @param qualifiedName the name
   */
  public void setQualifiedName(String qualifiedName) {
    this.qualifiedName = qualifiedName;
  }

  public List<String> getSelectors() {
    return selectors;
  }

  public void setSelectors(List<String> selectors) {
    this.selectors = selectors;
  }

  public MapperKind getKind() {
    return kind;
  }

  public void setKind(MapperKind kind) {
    this.kind = kind;
  }
}
