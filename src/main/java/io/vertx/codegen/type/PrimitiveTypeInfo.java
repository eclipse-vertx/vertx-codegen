package io.vertx.codegen.type;

import java.util.HashMap;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PrimitiveTypeInfo extends TypeInfo {

  static final HashMap<String, PrimitiveTypeInfo> PRIMITIVES = new HashMap<>();

  static {
    java.lang.Class<?>[] primitives = {boolean.class, byte.class, short.class, int.class, long.class,
        float.class, double.class, char.class};
    java.lang.Class<?>[] boxes = {Boolean.class, Byte.class, Short.class, Integer.class, Long.class,
        Float.class, Double.class, Character.class};
    for (int i = 0; i < primitives.length; i++) {
      java.lang.Class<?> primitive = primitives[i];
      String name = primitive.getName();
      PRIMITIVES.put(name, new PrimitiveTypeInfo(primitive.getName(), boxes[i].getName()));
    }
  }

  final String name;
  final String boxedName;

  private PrimitiveTypeInfo(String name, String boxedName) {
    this.name = name;
    this.boxedName = boxedName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PrimitiveTypeInfo) {
      return name.equals(((PrimitiveTypeInfo) obj).name);
    }
    return false;
  }

  /**
   * @return the boxed equivalent
   */
  public ClassTypeInfo getBoxed() {
    return ClassTypeInfo.PRIMITIVES.get(boxedName);
  }

  @Override
  public ClassKind getKind() {
    return ClassKind.PRIMITIVE;
  }

  @Override
  public String format(boolean qualified) {
    return name;
  }
}
