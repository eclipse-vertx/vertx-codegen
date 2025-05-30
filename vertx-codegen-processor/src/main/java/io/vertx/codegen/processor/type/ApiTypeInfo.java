package io.vertx.codegen.processor.type;

import io.vertx.codegen.processor.ModuleInfo;
import io.vertx.codegen.processor.TypeParamInfo;

import java.util.List;

/**
 * A special subclass for {@link ClassKind#API} kinds.
 */
public class ApiTypeInfo extends ClassTypeInfo {

  final boolean proxyGen;
  final boolean concrete;
  final TypeInfo handlerArg;

  public ApiTypeInfo(
      String fqcn,
      boolean concrete,
      List<TypeParamInfo.Class> params,
      TypeInfo handlerArg,
      ModuleInfo module,
      boolean nullable,
      boolean proxyGen,
      DataObjectInfo dataObject) {
    super(ClassKind.API, fqcn, module, nullable, params, false, dataObject);
    this.concrete = concrete;
    this.proxyGen = proxyGen;
    this.handlerArg = handlerArg;
  }

  public boolean isProxyGen() {
    return proxyGen;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public boolean isAbstract() {
    return !concrete;
  }

  public TypeInfo getHandlerArg() {
    return handlerArg;
  }

  public boolean isHandler() {
    return handlerArg != null;
  }

}
