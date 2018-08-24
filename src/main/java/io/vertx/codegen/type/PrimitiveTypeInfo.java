package io.vertx.codegen.type;

import java.util.HashMap;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PrimitiveTypeInfo extends TypeInfo {

  public static final PrimitiveTypeInfo BOOLEAN = new PrimitiveTypeInfo(boolean.class.getName(), Boolean.class.getName());
  public static final PrimitiveTypeInfo BYTE = new PrimitiveTypeInfo(byte.class.getName(), Byte.class.getName());
  public static final PrimitiveTypeInfo SHORT = new PrimitiveTypeInfo(short.class.getName(), Short.class.getName());
  public static final PrimitiveTypeInfo INT = new PrimitiveTypeInfo(int.class.getName(), Integer.class.getName());
  public static final PrimitiveTypeInfo LONG = new PrimitiveTypeInfo(long.class.getName(), Long.class.getName());
  public static final PrimitiveTypeInfo FLOAT = new PrimitiveTypeInfo(float.class.getName(), Float.class.getName());
  public static final PrimitiveTypeInfo DOUBLE = new PrimitiveTypeInfo(double.class.getName(), Double.class.getName());
  public static final PrimitiveTypeInfo CHAR = new PrimitiveTypeInfo(char.class.getName(), Character.class.getName());

  static final HashMap<String, PrimitiveTypeInfo> PRIMITIVES = new HashMap<>();

  static {
    PrimitiveTypeInfo[] primitives = {
      BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR
    };
    for (PrimitiveTypeInfo primitive : primitives) {
      PRIMITIVES.put(primitive.getName(), primitive);
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
