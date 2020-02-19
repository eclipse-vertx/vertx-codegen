package io.vertx.codegen.type;

import io.vertx.codegen.ClassModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum ClassKind {

  // Basic types
  STRING(true, false, false),
  BOXED_PRIMITIVE(true, false, false),
  PRIMITIVE(true, false, false),

  // Enum
  ENUM(false, false, false),

  // Json types
  JSON_OBJECT(false, true, false),
  JSON_ARRAY(false, true, false),

  // Various stuff
  THROWABLE(false, false, false),  // java.lang.Throwable
  VOID(false, false, false),       // java.lang.Void or void
  OBJECT(false, false, false),     // java.lang.Object or an unbounded type variable

  // Collection types
  LIST(false, false, true),        // java.util.List
  SET(false, false, true),         // java.util.Set
  MAP(false, false, true),

  // API types
  API(false, false, false),

  // Handler
  HANDLER(false, false, false),

  // Function
  FUNCTION(false, false, false),

  // AsyncResult
  ASYNC_RESULT(false, false, false),

  // Class type, e.g Class<T>
  CLASS_TYPE(false, false, false),

  // Anything else
  OTHER(false, false, false);

  // True when basic
  public final boolean basic;
  // True when json
  public final boolean json;
  // True when a java collection type
  public final boolean collection;

  ClassKind(boolean basic, boolean json, boolean collection) {
    this.basic = basic;
    this.json = json;
    this.collection = collection;
  }

  /**
   * Useful for testing the type class kind, allows to do type.kind == CLASS_API instead of type.kind.name() == "API"
   */
  public static Map<String, ClassKind> vars() {
    HashMap<String, ClassKind> vars = new HashMap<>();
    for (ClassKind classKind : ClassKind.values()) {
      vars.put("CLASS_" + classKind.name(), classKind);
    }
    return vars;
  }

  public static ClassKind getKind(String fqcn, boolean isVertxGenAnnotated) {
    if (isVertxGenAnnotated) {
      return API;
    } else if (fqcn.equals("java.lang.Class")) {
      return CLASS_TYPE;
    } else if (fqcn.equals(ClassModel.VERTX_HANDLER)) {
      return HANDLER;
    } else if (fqcn.equals(ClassModel.VERTX_ASYNC_RESULT)) {
      return ASYNC_RESULT;
    } else if (fqcn.equals(ClassModel.JSON_ARRAY)) {
      return JSON_ARRAY;
    } else if (fqcn.equals(ClassModel.JSON_OBJECT)) {
      return JSON_OBJECT;
    } else if (fqcn.equals(Object.class.getName())) {
      return OBJECT;
    } else if (fqcn.equals(String.class.getName())) {
      return STRING;
    } else if (fqcn.equals(List.class.getName())) {
      return LIST;
    } else if (fqcn.equals(Set.class.getName())) {
      return SET;
    } else if (fqcn.equals(Map.class.getName())) {
      return MAP;
    } else if (fqcn.equals(Throwable.class.getName())) {
      return THROWABLE;
    } else if (fqcn.equals(Void.class.getName())) {
      return VOID;
    } else if (fqcn.equals(Function.class.getName())) {
      return FUNCTION;
    } else if (fqcn.equals(Integer.class.getName()) ||
        fqcn.equals(Long.class.getName()) ||
        fqcn.equals(Boolean.class.getName()) ||
        fqcn.equals(Double.class.getName()) ||
        fqcn.equals(Float.class.getName()) ||
        fqcn.equals(Short.class.getName()) ||
        fqcn.equals(Character.class.getName()) ||
        fqcn.equals(Byte.class.getName())) {
      return BOXED_PRIMITIVE;
    } else {
      return OTHER;
    }
  }
}
