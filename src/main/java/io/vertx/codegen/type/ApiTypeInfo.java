package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;

/**
 * A special subclass for {@link ClassKind#API} kinds.
 */
public class ApiTypeInfo extends ClassTypeInfo {

  final boolean concrete;
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
      boolean proxyGen) {
    super(ClassKind.API, fqcn, module, nullable, proxyGen, params);
    this.concrete = concrete;
    this.readStreamArg = readStreamArg;
    this.writeStreamArg = writeStreamArg;
    this.handlerArg = handlerArg;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public boolean isAbstract() {
    return !concrete;
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
