package io.vertx.codegen.processor.type;

import javax.lang.model.type.WildcardType;
import java.util.Objects;

public class WildcardTypeInfo extends TypeInfo {

  private final TypeInfo extendsBound;
  private final TypeInfo superBound;

  public WildcardTypeInfo(TypeInfo extendsBound, TypeInfo superBound) {
    this.extendsBound = extendsBound;
    this.superBound = superBound;
  }

  public TypeInfo getExtendsBound() {
    return extendsBound;
  }

  public TypeInfo getSuperBound() {
    return superBound;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof WildcardTypeInfo) {
      WildcardTypeInfo that = (WildcardTypeInfo) obj;
      return Objects.equals(extendsBound, that.extendsBound) && Objects.equals(superBound, that.superBound);
    }
    return false;
  }

  @Override
  public ClassKind getKind() {
    return ClassKind.OTHER;
  }

  @Override
  String format(boolean qualified) {
    if (extendsBound != null) {
      return "? extends " + extendsBound.format(qualified);
    } else if (superBound != null) {
      return "? super " + superBound.format(qualified);
    } else {
      return "?";
    }
  }
}
