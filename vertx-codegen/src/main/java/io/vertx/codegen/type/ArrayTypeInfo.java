package io.vertx.codegen.type;

import java.util.Collection;

public class ArrayTypeInfo extends TypeInfo {

  private final TypeInfo componentType;
  private final boolean nullable;

  public ArrayTypeInfo(TypeInfo componentType, boolean nullable) {
    this.componentType = componentType;
    this.nullable = nullable;
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  @Override
  public void collectImports(Collection<ClassTypeInfo> imports) {
    componentType.collectImports(imports);
  }

  @Override
  public String translateName(TypeNameTranslator translator) {
    return componentType.translateName(translator) + "[]";
  }

  @Override
  public String getSimpleName() {
    return componentType.getSimpleName() + "[]";
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  @Override
  public ClassKind getKind() {
    return ClassKind.OTHER;
  }

  @Override
  String format(boolean qualified) {
    return componentType.format(qualified) + "[]";
  }
}
