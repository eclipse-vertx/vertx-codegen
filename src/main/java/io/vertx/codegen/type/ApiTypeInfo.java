package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * A special subclass for {@link ClassKind#API} kinds.
 */
public class ApiTypeInfo extends ClassTypeInfo {

  final boolean proxyGen;
  final boolean concrete;
  final boolean autoCloseable;
  final TypeInfo readStreamArg;
  final TypeInfo writeStreamArg;
  final TypeInfo handlerArg;

  public ApiTypeInfo(
      String fqcn,
      boolean concrete,
      List<TypeParamInfo.Class> params,
      TypeInfo readStreamArg,
      TypeInfo writeStreamArg,
      TypeInfo handlerArg,
      ModuleInfo module,
      boolean nullable,
      boolean proxyGen,
      boolean autoCloseable) {
    super(ClassKind.API, fqcn, module, nullable, params);
    this.concrete = concrete;
    this.proxyGen = proxyGen;
    this.autoCloseable = autoCloseable;
    this.readStreamArg = readStreamArg;
    this.writeStreamArg = writeStreamArg;
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

  public boolean isAutoCloseable() {
    return autoCloseable;
  }

  public TypeInfo getReadStreamArg() {
    return readStreamArg;
  }

  public boolean isReadStream() {
    return readStreamArg != null;
  }

  public TypeInfo getWriteStreamArg() {
    return writeStreamArg;
  }

  public boolean isWriteStream() {
    return writeStreamArg != null;
  }

  public TypeInfo getHandlerArg() {
    return handlerArg;
  }

  public boolean isHandler() {
    return handlerArg != null;
  }
}
