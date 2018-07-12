package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTypeInfo extends ClassTypeInfo {

  final boolean _abstract;

  public DataObjectTypeInfo(ClassKind kind, String name, ModuleInfo module, boolean _abstract, boolean nullable, List<TypeParamInfo.Class> params) {
    super(kind, name, module, nullable, params);

    this._abstract = _abstract;
  }

  public boolean isAbstract() {
    return _abstract;
  }
}
