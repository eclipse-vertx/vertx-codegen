package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  final boolean _abstract;
  final boolean _interface;

  public DataObjectTypeInfo(ClassKind kind, String name, ModuleInfo module, boolean _abstract, boolean nullable, boolean _interface, List<TypeParamInfo.Class> params) {
    super(kind, name, module, nullable, params);

    this._abstract = _abstract;
    this._interface = _interface;
  }

  public boolean isAbstract() {
    return _abstract;
  }

  public boolean isInterface() {
    return _interface;
  }
}
