package io.vertx.codegen.type;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class VoidTypeInfo extends TypeInfo {

  public static TypeInfo INSTANCE = new VoidTypeInfo() {
  };

  private VoidTypeInfo() {
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof VoidTypeInfo;
  }

  @Override
  public String format(boolean qualified) {
    return "void";
  }
}
