package io.vertx.codegen.processor.type;

import io.vertx.codegen.format.CamelCase;
import io.vertx.codegen.format.Case;
import io.vertx.codegen.processor.Helper;
import io.vertx.codegen.processor.ModuleInfo;
import io.vertx.codegen.processor.TypeParamInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ClassTypeInfo extends TypeInfo {

  static final HashMap<String, ClassTypeInfo> PRIMITIVES = new HashMap<>();

  static {
    Class<?>[] boxes = {Boolean.class, Byte.class, Short.class, Integer.class, Long.class,
        Float.class, Double.class, Character.class};
    for (Class<?> boxe : boxes) {
      String name = boxe.getName();
      PRIMITIVES.put(name, new ClassTypeInfo(ClassKind.BOXED_PRIMITIVE, name, null, false, Collections.emptyList(), false, null));
    }
  }

  final ClassKind kind;
  final String name;
  final String simpleName;
  final String packageName;
  final ModuleInfo module;
  final boolean nullable;
  final List<TypeParamInfo.Class> params;
  final DataObjectInfo dataObject;
  final boolean permitted;

  public ClassTypeInfo(ClassKind kind, String name, ModuleInfo module, boolean nullable, List<TypeParamInfo.Class> params, boolean permitted, DataObjectInfo dataObject) {
    this.kind = kind;
    this.name = name;
    this.simpleName = Helper.getSimpleName(name);
    this.packageName = Helper.getPackageName(name);
    this.module = module;
    this.nullable = nullable;
    this.params = params;
    this.permitted = permitted;
    this.dataObject = dataObject;
  }

  public boolean isPermitted() {
    return permitted;
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
    return _case.format(CamelCase.INSTANCE.parse(simpleName));
  }

  @Override
  public ClassTypeInfo getRaw() {
    return this;
  }

  @Override
  public void collectImports(Collection<ClassTypeInfo> imports) {
    imports.add(this);
  }

  public DataObjectInfo getDataObject() {
    return dataObject;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ClassTypeInfo) {
      return name.equals(((ClassTypeInfo) obj).name);
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
