package io.vertx.codegen.type;

import io.vertx.codegen.Case;
import io.vertx.codegen.Helper;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ClassTypeInfo extends TypeInfo {

  static final HashMap<String, io.vertx.codegen.type.ClassTypeInfo> PRIMITIVES = new HashMap<>();

  static {
    Class<?>[] boxes = {Boolean.class, Byte.class, Short.class, Integer.class, Long.class,
        Float.class, Double.class, Character.class};
    for (Class<?> box : boxes) {
      String name = box.getName();
      PRIMITIVES.put(name, new io.vertx.codegen.type.ClassTypeInfo(ClassKind.BOXED_PRIMITIVE, name, null, false, Collections.emptyList()));
    }
  }

  final ClassKind kind;
  final String name;
  final String simpleName;
  final String packageName;
  final ModuleInfo module;
  final boolean nullable;
  final List<TypeParamInfo.Class> params;

  public ClassTypeInfo(ClassKind kind, String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params) {
    this.kind = kind;
    this.name = name;
    this.simpleName = Helper.getSimpleName(name);
    this.packageName = Helper.getPackageName(name);
    this.module = module;
    this.nullable = nullable;
    this.params = params;
  }

  public List<TypeParamInfo.Class> getParams() {
    return params;
  }

  /**
   * @return the optional module name only present for {@link io.vertx.codegen.annotations.VertxGen} annotated types.
   */
  public String getModuleName() {
    return module != null ? module.getName() : null;
  }

  /**
   * @return the optional module name only present for {@link io.vertx.codegen.annotations.VertxGen} annotated types.
   */
  public ModuleInfo getModule() {
    return module;
  }

  public boolean isNullable() {
    return nullable;
  }

  public ClassKind getKind() {
    return kind;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getSimpleName(Case _case) {
    return _case.format(Case.CAMEL.parse(simpleName));
  }

  @Override
  public io.vertx.codegen.type.ClassTypeInfo getRaw() {
    return this;
  }

  @Override
  public void collectImports(Collection<io.vertx.codegen.type.ClassTypeInfo> imports) {
    imports.add(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof io.vertx.codegen.type.ClassTypeInfo) {
      return name.equals(((io.vertx.codegen.type.ClassTypeInfo) obj).name);
    }
    return false;
  }

  @Override
  public String format(boolean qualified) {
    return qualified ? name : simpleName;
  }

  public String translateName(String id) {
    return module == null ? name : module.translateQualifiedName(name, id);
  }

  public String translatePackageName(String id) {
    return module == null ? packageName : module.translateQualifiedName(packageName, id);
  }

  @Override
  public String translateName(TypeNameTranslator translator) {
    return module == null ? name : translator.translate(module, name);
  }

  public String translatePackageName(TypeNameTranslator translator) {
    return module == null ? packageName : translator.translate(module, packageName);
  }
}
