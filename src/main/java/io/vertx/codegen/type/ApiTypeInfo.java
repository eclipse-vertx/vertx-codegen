package io.vertx.codegen.type;

import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;

import java.util.List;
import java.util.Objects;

/**
 * A special subclass for {@link ClassKind#API} kinds.
 */
public class ApiTypeInfo extends ClassTypeInfo {

  final boolean proxyGen;
  final boolean concrete;
  final ApiTypeArgInfo argInfo;

  public ApiTypeInfo(
    String fqcn,
    boolean concrete,
    List<TypeParamInfo.Class> params,
    ModuleInfo module,
    boolean nullable,
    boolean proxyGen,
    ApiTypeArgInfo argInfo
  ) {
    super(ClassKind.API, fqcn, module, nullable, params);
    Objects.requireNonNull(argInfo, "argInfo");
    this.concrete = concrete;
    this.proxyGen = proxyGen;
    this.argInfo = argInfo;
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

  public TypeInfo getReadStreamArg() {
    return argInfo.readStreamArg;
  }

  public boolean isReadStream() {
    return argInfo.readStreamArg != null;
  }

  public TypeInfo getWriteStreamArg() {
    return argInfo.writeStreamArg;
  }

  public boolean isWriteStream() {
    return argInfo.writeStreamArg != null;
  }

  public TypeInfo getHandlerArg() {
    return argInfo.handlerArg;
  }

  public boolean isHandler() {
    return argInfo.handlerArg != null;
  }


  public TypeInfo getIterableArg() {
    return argInfo.iterableArg;
  }

  public boolean isIterable() {
    return argInfo.iterableArg != null;
  }

  public TypeInfo getIteratorArg() {
    return argInfo.iteratorArg;
  }

  public boolean isIterator() {
    return argInfo.iteratorArg != null;
  }

  public TypeInfo getFunctionArgIn() {
    return argInfo.functionArgIn;
  }

  public boolean isFunction() {
    return argInfo.functionArgIn != null;
  }

  public TypeInfo getFunctionArgOut() {
    return argInfo.functionArgOut;
  }

  public static class ApiTypeArgInfo {
    final TypeInfo readStreamArg;
    final TypeInfo writeStreamArg;
    final TypeInfo handlerArg;
    final TypeInfo iterableArg;
    final TypeInfo iteratorArg;
    final TypeInfo functionArgIn;
    final TypeInfo functionArgOut;

    public ApiTypeArgInfo(TypeInfo readStreamArg, TypeInfo writeStreamArg, TypeInfo handlerArg, TypeInfo iterableArg,
                          TypeInfo iteratorArg, TypeInfo functionArgIn, TypeInfo functionArgOut) {
      this.readStreamArg = readStreamArg;
      this.writeStreamArg = writeStreamArg;
      this.handlerArg = handlerArg;
      this.iterableArg = iterableArg;
      this.iteratorArg = iteratorArg;
      this.functionArgIn = functionArgIn;
      this.functionArgOut = functionArgOut;
    }
  }
}
