package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.Signature;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.codegen.type.ApiTypeInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.PrimitiveTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeReflectionFactory;
import io.vertx.codegen.type.TypeVariableInfo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.WriteStream;
import io.vertx.test.codegen.annotations.EmptyAnnotation;
import io.vertx.test.codegen.testapi.AbstractDataObjectWithToJson;
import io.vertx.test.codegen.testapi.AbstractInterfaceWithConcreteSuperInterface;
import io.vertx.test.codegen.testapi.AbstractInterfaceWithStaticMethod;
import io.vertx.test.codegen.testapi.Annotated;
import io.vertx.test.codegen.testapi.CacheReturnMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.ConcreteInterfaceWithTwoConcreteSuperInterfaces;
import io.vertx.test.codegen.testapi.DiamondMethod1;
import io.vertx.test.codegen.testapi.DiamondMethod2;
import io.vertx.test.codegen.testapi.DiamondMethod3;
import io.vertx.test.codegen.testapi.GenericInterface2;
import io.vertx.test.codegen.testapi.MethodWithTypeVarParamByGenericType;
import io.vertx.test.codegen.testapi.GenericAbstractInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.GenericInterfaceWithUpperBound;
import io.vertx.test.codegen.testapi.InterfaceDataObjectWithToJson;
import io.vertx.test.codegen.testapi.InterfaceExtendingGenericAbstractInterface;
import io.vertx.test.codegen.testapi.InterfaceWithCacheReturnMethods;
import io.vertx.test.codegen.testapi.InterfaceWithComments;
import io.vertx.test.codegen.testapi.InterfaceWithDefaultMethod;
import io.vertx.test.codegen.testapi.InterfaceWithGenericMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithIgnoredElements;
import io.vertx.test.codegen.testapi.InterfaceWithInstanceMethods;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverloadedFromParent;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverrideParameterRenamed;
import io.vertx.test.codegen.testapi.InterfaceWithNoMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNoNotIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNonGenSuperType;
import io.vertx.test.codegen.testapi.InterfaceWithOnlyDefaultMethod;
import io.vertx.test.codegen.testapi.InterfaceWithOverloadedFutureMethod;
import io.vertx.test.codegen.testapi.InterfaceWithOverloadedInstanceAndStaticMethod;
import io.vertx.test.codegen.testapi.InterfaceWithOverloadedMethods;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedArraySupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedDeclaredSupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedGenericArraySupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedVariableSupertype;
import io.vertx.test.codegen.testapi.InterfaceWithStaticMethods;
import io.vertx.test.codegen.testapi.InterfaceWithSuperStaticMethods;
import io.vertx.test.codegen.testapi.InterfaceWithSupertypes;
import io.vertx.test.codegen.testapi.InterfaceWithTypeVariableArgument1;
import io.vertx.test.codegen.testapi.InterfaceWithTypeVariableArgument2;
import io.vertx.test.codegen.testapi.InterfaceWithTypeVariableArgument3;
import io.vertx.test.codegen.testapi.MethodWithDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithDiamond;
import io.vertx.test.codegen.testapi.MethodWithEnumParam;
import io.vertx.test.codegen.testapi.MethodWithEnumReturn;
import io.vertx.test.codegen.testapi.MethodWithFunctionInHandler;
import io.vertx.test.codegen.testapi.MethodWithFunctionInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithHandlerAsyncResultParam;
import io.vertx.test.codegen.testapi.MethodWithHandlerAsyncResultReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerNonVertxGenReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerParam;
import io.vertx.test.codegen.testapi.MethodWithHandlerReturn;
import io.vertx.test.codegen.testapi.MethodWithInvalidAbstractDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidExceptionParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam1;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam2;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam3;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam4;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam5;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam6;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam7;
import io.vertx.test.codegen.testapi.MethodWithInvalidFunctionParam8;
import io.vertx.test.codegen.testapi.MethodWithInvalidHandlerAsyncResultDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidHandlerDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidInterfaceDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidListParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidListParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidListReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidListReturn2;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn2;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn3;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn4;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn5;
import io.vertx.test.codegen.testapi.MethodWithInvalidNestedEnumParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetReturn2;
import io.vertx.test.codegen.testapi.MethodWithInvalidTypeParamByObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithInvalidTypeParamByParameterizedReturn;
import io.vertx.test.codegen.testapi.MethodWithInvalidTypeParamByThrowableReturn;
import io.vertx.test.codegen.testapi.MethodWithInvalidWildcardTypeArg;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectParam;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithListNonBasicTypeReturn;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectParam;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithObjectParam;
import io.vertx.test.codegen.testapi.MethodWithObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithSameSignatureInheritedFromDistinctInterfaces;
import io.vertx.test.codegen.testapi.MethodWithSetNonBasicTypeReturn;
import io.vertx.test.codegen.testapi.MethodWithThrowableReturn;
import io.vertx.test.codegen.testapi.MethodWithTypeParameter;
import io.vertx.test.codegen.testapi.MethodWithTypeParameterUpperBound;
import io.vertx.test.codegen.testapi.MethodWithValidBasicBoxedParams;
import io.vertx.test.codegen.testapi.MethodWithValidBasicParams;
import io.vertx.test.codegen.testapi.MethodWithValidBasicReturn;
import io.vertx.test.codegen.testapi.MethodWithValidClassTypeParams;
import io.vertx.test.codegen.testapi.MethodWithValidDataObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithValidFunctionParams;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerAsyncResultJSON;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerAsyncResultParams;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerAsyncResultTypeParamByInterface;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerJSON;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerParams;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerTypeParamByInterface;
import io.vertx.test.codegen.testapi.MethodWithValidJSONParams;
import io.vertx.test.codegen.testapi.MethodWithValidJSONReturn;
import io.vertx.test.codegen.testapi.MethodWithValidListParams;
import io.vertx.test.codegen.testapi.MethodWithValidListReturn;
import io.vertx.test.codegen.testapi.MethodWithValidMapParams;
import io.vertx.test.codegen.testapi.MethodWithValidMapReturn;
import io.vertx.test.codegen.testapi.MethodWithValidSetParams;
import io.vertx.test.codegen.testapi.MethodWithValidSetReturn;
import io.vertx.test.codegen.testapi.MethodWithValidThrowableParam;
import io.vertx.test.codegen.testapi.MethodWithValidTypeParamByInterfaceReturn;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenReturn;
import io.vertx.test.codegen.testapi.MethodWithValidVoidReturn;
import io.vertx.test.codegen.testapi.MethodWithValidVoidTypeArg;
import io.vertx.test.codegen.testapi.MethodWithWildcardLowerBoundTypeArg;
import io.vertx.test.codegen.testapi.MethodWithWildcardUpperBoundTypeArg;
import io.vertx.test.codegen.testapi.NestedInterface;
import io.vertx.test.codegen.testapi.NoVertxGen;
import io.vertx.test.codegen.testapi.NotInterface;
import io.vertx.test.codegen.testapi.OverloadedMethodsWithDifferentReturnType;
import io.vertx.test.codegen.testapi.PlainDataObject;
import io.vertx.test.codegen.testapi.PlainDataObjectWithToJson;
import io.vertx.test.codegen.testapi.RecursiveFuture;
import io.vertx.test.codegen.testapi.SameSignatureMethod1;
import io.vertx.test.codegen.testapi.SameSignatureMethod2;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.VertxGenClass2;
import io.vertx.test.codegen.testapi.VertxGenInterface;
import io.vertx.test.codegen.testapi.fluent.AbstractInterfaceWithFluentMethods;
import io.vertx.test.codegen.testapi.fluent.ConcreteInterfaceWithFluentMethods;
import io.vertx.test.codegen.testapi.fluent.FluentMethodOverrideWithSuperType;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithGenericReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithIllegalParameterizedReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithIllegalReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.fluent.InterfaceWithFluentMethodOverrideFromAbstract;
import io.vertx.test.codegen.testapi.fluent.InterfaceWithFluentMethodOverrideFromConcrete;
import io.vertx.test.codegen.testapi.handler.FutureLike;
import io.vertx.test.codegen.testapi.handler.InterfaceExtendingHandlerStringSubtype;
import io.vertx.test.codegen.testapi.handler.InterfaceExtendingHandlerVertxGenSubtype;
import io.vertx.test.codegen.testapi.impl.InterfaceInImplPackage;
import io.vertx.test.codegen.testapi.impl.sub.InterfaceInImplParentPackage;
import io.vertx.test.codegen.testapi.simple.InterfaceInImplContainingPackage;
import io.vertx.test.codegen.testapi.streams.GenericInterfaceExtentingReadStream;
import io.vertx.test.codegen.testapi.streams.GenericInterfaceExtentingReadStreamAndWriteStream;
import io.vertx.test.codegen.testapi.streams.GenericInterfaceExtentingWriteStream;
import io.vertx.test.codegen.testapi.streams.InterfaceExtentingReadStream;
import io.vertx.test.codegen.testapi.streams.InterfaceExtentingReadStreamAndWriteStream;
import io.vertx.test.codegen.testapi.streams.InterfaceExtentingWriteStream;
import io.vertx.test.codegen.testapi.streams.InterfaceSubtypingReadStream;
import io.vertx.test.codegen.testapi.streams.ReadStreamWithParameterizedTypeArg;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ClassTest extends ClassTestBase {

  static final ClassTypeInfo GenericInterfaceInfo = (ClassTypeInfo) TypeReflectionFactory.create(GenericInterface.class);
  static final ClassTypeInfo VertxGenClass1Info = (ClassTypeInfo) TypeReflectionFactory.create(VertxGenClass1.class);
  static final ClassTypeInfo VertxGenClass2Info = (ClassTypeInfo) TypeReflectionFactory.create(VertxGenClass2.class);
  static final ClassTypeInfo VertxGenInterfaceInfo = (ClassTypeInfo) TypeReflectionFactory.create(VertxGenInterface.class);

  public ClassTest() {
  }

  // Test invalid stuff
  // ------------------

  // Invalid classes

  @Test
  public void testGenerateNotInterface() throws Exception {
    assertGenInvalid(NotInterface.class);
  }

  @Test
  public void testGenerateNoVertxGenAnnotation() throws Exception {
    try {
      new GeneratorHelper().generateClass(NoVertxGen.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateNestedInterfaces() throws Exception {
    assertGenInvalid(NestedInterface.class);
  }

  @Test
  public void testGenerateInterfaceWithNoMethods() throws Exception {
    assertGenInvalid(InterfaceWithNoMethods.class);
  }

  @Test
  public void testGenerateInterfaceWithNoNotIgnoredMethods() throws Exception {
    assertGenInvalid(InterfaceWithNoNotIgnoredMethods.class);
  }

  @Test
  public void testGenerateInterfaceWithOnlyDefaultMethod() throws Exception {
    assertGenInvalid(InterfaceWithOnlyDefaultMethod.class);
  }

  // Invalid params

  @Test
  public void testGenerateMethodWithJavaDotObjectParam() throws Exception {
    assertGenInvalid(MethodWithJavaDotObjectParam.class);
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandler() throws Exception {
    assertGenInvalid(MethodWithJavaDotObjectInHandler.class);
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandlerAsyncResult() throws Exception {
    assertGenInvalid(MethodWithJavaDotObjectInHandlerAsyncResult.class);
  }

  @Test
  public void testGenerateMethodWithNonVertxGenParam() throws Exception {
    Class<?> c = MethodWithNotVertxGenObjectParam.class;
    assertGenInvalid(c);
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandler() throws Exception {
    assertGenInvalid(MethodWithNotVertxGenObjectInHandler.class);
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandlerAsyncResult() throws Exception {
    assertGenInvalid(MethodWithNotVertxGenObjectInHandlerAsyncResult.class);
  }

  @Test
  public void testGenerateMethodWithFunctionInHandler() throws Exception {
    assertGenInvalid(MethodWithFunctionInHandler.class);
  }

  @Test
  public void testGenerateMethodWithFunctionInHandlerAsyncResult() throws Exception {
    assertGenInvalid(MethodWithFunctionInHandlerAsyncResult.class);
  }

  // TODO Handler and Handler<AsyncResult> of List/String with non basic types

  @Test
  public void testGenerateMethodWithWildcardUpperBoundTypeArg() throws Exception {
    assertGenInvalid(MethodWithWildcardUpperBoundTypeArg.class);
  }

  @Test
  public void testGenerateMethodWithWildcardLowerBoundTypeArg() throws Exception {
    assertGenInvalid(MethodWithWildcardLowerBoundTypeArg.class);
  }

  // Invalid returns

  @Test
  public void testGenerateMethodWithJavaDotObjectReturn() throws Exception {
    assertGenInvalid(MethodWithJavaDotObjectReturn.class);
  }

  @Test
  public void testGenerateMethodWithNonVertxGenReturn() throws Exception {
    assertGenInvalid(MethodWithNotVertxGenObjectReturn.class);
  }

  @Test
  public void testGenerateMethodWithReturnSetNonBasicType() throws Exception {
    assertGenInvalid(MethodWithSetNonBasicTypeReturn.class);
  }

  @Test
  public void testGenerateMethodWithReturnListNonBasicType() throws Exception {
    assertGenInvalid(MethodWithListNonBasicTypeReturn.class);
  }

  @Test
  public void testGenerateMethodWithReturnHandlerNonVertxGen() throws Exception {
    assertGenInvalid(MethodWithHandlerNonVertxGenReturn.class);
  }

  // Invalid methods

  @Test
  public void testOverloadedMethodsWithDifferentReturnType() throws Exception {
    assertGenInvalid(OverloadedMethodsWithDifferentReturnType.class);
  }

  @Test
  public void testFluentMethodWithVoidReturn() throws Exception {
    assertGenInvalid(FluentMethodWithVoidReturn.class);
  }

  @Test
  public void testFluentMethodWithIllegalReturn() throws Exception {
    assertGenInvalid(FluentMethodWithIllegalReturn.class);
  }

  @Test
  public void testFluentMethodWithGenericReturn() throws Exception {
    assertGenInvalid(FluentMethodWithGenericReturn.class);
  }

  @Test
  public void testFluentMethodWithIllegalParameterizedReturn() throws Exception {
    assertGenInvalid(FluentMethodWithIllegalParameterizedReturn.class);
  }

  @Test
  public void testFluentMethodOverrideWithSuperType() throws Exception {
    assertGenInvalid(FluentMethodOverrideWithSuperType.class);
  }

  @Test
  public void testCacheReturnMethodWithVoidReturn() throws Exception {
    assertGenInvalid(CacheReturnMethodWithVoidReturn.class);
  }

  @Test
  public void testMethodWithTypeParameterUpperBound() throws Exception {
    assertGenInvalid(MethodWithTypeParameterUpperBound.class);
  }

  @Test
  public void testMethodWithIllegalGenericsReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidTypeParamByObjectReturn.class);
    assertGenInvalid(MethodWithInvalidTypeParamByParameterizedReturn.class);
    assertGenInvalid(MethodWithInvalidTypeParamByThrowableReturn.class);
  }

  // Invalid abstract/concrete interfaces

  @Test
  public void testAbstractInterfaceCannotExtendConcreteInterface() throws Exception {
    assertGenInvalid(AbstractInterfaceWithConcreteSuperInterface.class);
  }

  @Test
  public void testAbstractInterfaceCannotHaveStaticMethod() throws Exception {
    assertGenInvalid(AbstractInterfaceWithStaticMethod.class);
  }

  @Test
  public void testConcreteInterfaceCannotExtendTwoConcreteInterfaces() throws Exception {
    assertGenInvalid(ConcreteInterfaceWithTwoConcreteSuperInterfaces.class);
  }

  // Various

  @Test
  public void testInvalidWildcardTypeArg() throws Exception {
    assertGenInvalid(MethodWithInvalidWildcardTypeArg.class);
  }

  // Test valid stuff
  // ----------------

  // Valid params

  @Test
  public void testValidBasicParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidBasicParams.class);
    assertEquals(MethodWithValidBasicParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithBasicParams";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 9, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "b", byte.class);
    checkParam(params.get(1), "s", short.class);
    checkParam(params.get(2), "i", int.class);
    checkParam(params.get(3), "l", long.class);
    checkParam(params.get(4), "f", float.class);
    checkParam(params.get(5), "d", double.class);
    checkParam(params.get(6), "bool", boolean.class);
    checkParam(params.get(7), "ch", char.class);
    checkParam(params.get(8), "str", String.class);
    assertEquals("java.lang.Byte", ((PrimitiveTypeInfo) params.get(0).getType()).getBoxed().getName());
    assertEquals("java.lang.Short", ((PrimitiveTypeInfo) params.get(1).getType()).getBoxed().getName());
    assertEquals("java.lang.Integer", ((PrimitiveTypeInfo) params.get(2).getType()).getBoxed().getName());
    assertEquals("java.lang.Long", ((PrimitiveTypeInfo) params.get(3).getType()).getBoxed().getName());
    assertEquals("java.lang.Float", ((PrimitiveTypeInfo) params.get(4).getType()).getBoxed().getName());
    assertEquals("java.lang.Double", ((PrimitiveTypeInfo) params.get(5).getType()).getBoxed().getName());
    assertEquals("java.lang.Boolean", ((PrimitiveTypeInfo) params.get(6).getType()).getBoxed().getName());
    assertEquals("java.lang.Character", ((PrimitiveTypeInfo) params.get(7).getType()).getBoxed().getName());
  }

  @Test
  public void testValidBasicBoxedParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidBasicBoxedParams.class);
    assertEquals(MethodWithValidBasicBoxedParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicBoxedParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithBasicParams";
    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 9, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "b", Byte.class);
    checkParam(params.get(1), "s", Short.class);
    checkParam(params.get(2), "i", Integer.class);
    checkParam(params.get(3), "l", Long.class);
    checkParam(params.get(4), "f", Float.class);
    checkParam(params.get(5), "d", Double.class);
    checkParam(params.get(6), "bool", Boolean.class);
    checkParam(params.get(7), "ch", Character.class);
    checkParam(params.get(8), "str", String.class);
  }

  @Test
  public void testValidTypeParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithTypeParameter.class);
    assertEquals(1, model.getMethods().size());
    MethodInfo mi = model.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(Arrays.asList("T"), mi.getTypeParams().stream().map(TypeParamInfo::getName).collect(Collectors.toList()));
  }

  @Test
  public void testValidVoidTypeArg() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidVoidTypeArg.class);
    assertEquals(1, model.getMethods().size());
    MethodInfo mi = model.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(new ParameterizedTypeInfo(new ClassTypeInfo(ClassKind.API, GenericInterface.class.getName(), null, false, Collections.emptyList()), false, Arrays.asList(TypeReflectionFactory.create(Void.class))), mi.getParams().get(0).getType());
    ParameterizedTypeInfo genericType = (ParameterizedTypeInfo) mi.getParams().get(0).getType();
    ClassTypeInfo voidType = (ClassTypeInfo) genericType.getArgs().get(0);
    assertEquals(ClassKind.VOID, voidType.getKind());
  }

  @Test
  public void testValidListParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidListParams.class);
    assertEquals(MethodWithValidListParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidListParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithListParams";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 7, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "listString", new TypeLiteral<List<String>>(){});
    checkParam(params.get(1), "listLong", new TypeLiteral<List<Long>>(){});
    checkParam(params.get(2), "listJsonObject", new TypeLiteral<List<JsonObject>>(){});
    checkParam(params.get(3), "listJsonArray", new TypeLiteral<List<JsonArray>>(){});
    checkParam(params.get(4), "listVertxGen", new TypeLiteral<List<VertxGenClass1>>(){});
    checkParam(params.get(5), "listDataObject", new TypeLiteral<List<TestDataObject>>(){});
    checkParam(params.get(6), "listEnum", new TypeLiteral<List<TestEnum>>(){});
  }

  @Test
  public void testValidSetParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidSetParams.class);
    assertEquals(MethodWithValidSetParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidSetParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithSetParams";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 7, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "setString", new TypeLiteral<Set<String>>(){});
    checkParam(params.get(1), "setLong", new TypeLiteral<Set<Long>>() {
    });
    checkParam(params.get(2), "setJsonObject", new TypeLiteral<Set<JsonObject>>() {});
    checkParam(params.get(3), "setJsonArray", new TypeLiteral<Set<JsonArray>>() {
    });
    checkParam(params.get(4), "setVertxGen", new TypeLiteral<Set<VertxGenClass1>>() {
    });
    checkParam(params.get(5), "setDataObject", new TypeLiteral<Set<TestDataObject>>(){});
    checkParam(params.get(6), "setEnum", new TypeLiteral<Set<TestEnum>>() {
    });
  }

  @Test
  public void testValidMapParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidMapParams.class);
    assertEquals(MethodWithValidMapParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidMapParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithMapParams";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 5, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "mapString", new TypeLiteral<Map<String, String>>(){});
    checkParam(params.get(1), "mapLong", new TypeLiteral<Map<String, Long>>(){});
    checkParam(params.get(2), "mapJsonObject", new TypeLiteral<Map<String, JsonObject>>(){});
    checkParam(params.get(3), "mapJsonArray", new TypeLiteral<Map<String, JsonArray>>(){});
    checkParam(params.get(4), "mapVertxGen", new TypeLiteral<Map<String, VertxGenClass1>>(){});
  }

  @Test
  public void testValidClassTypeParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidClassTypeParams.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    MethodInfo methodParam = methods.get(0);
    checkMethod(methodParam, "methodParam", 2, "void", MethodKind.OTHER);
    ParamInfo resolved = methodParam.resolveClassTypeParam((TypeVariableInfo) methodParam.getParam(0).getType());
    assertSame(resolved, methodParam.getParam(1));
    MethodInfo returnParam = methods.get(1);
    checkMethod(returnParam, "returnParam", 1, "T", MethodKind.OTHER);
    resolved = methodParam.resolveClassTypeParam((TypeVariableInfo) returnParam.getReturnType());
    assertSame(resolved, methodParam.getParam(1));
  }

  @Test
  public void testValidHandlerParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerParams.class);
    assertEquals(MethodWithValidHandlerParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(4, model.getMethods().size());

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, "methodWithHandlerParams", 15, "void", MethodKind.HANDLER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "byteHandler", new TypeLiteral<Handler<Byte>>() {});
    checkParam(params.get(1), "shortHandler", new TypeLiteral<Handler<Short>>() {});
    checkParam(params.get(2), "intHandler", new TypeLiteral<Handler<Integer>>() {});
    checkParam(params.get(3), "longHandler", new TypeLiteral<Handler<Long>>(){});
    checkParam(params.get(4), "floatHandler", new TypeLiteral<Handler<Float>>(){});
    checkParam(params.get(5), "doubleHandler", new TypeLiteral<Handler<Double>>(){});
    checkParam(params.get(6), "booleanHandler", new TypeLiteral<Handler<Boolean>>(){});
    checkParam(params.get(7), "charHandler", new TypeLiteral<Handler<Character>>(){});
    checkParam(params.get(8), "strHandler", new TypeLiteral<Handler<String>>(){});
    checkParam(params.get(9), "gen1Handler", new TypeLiteral<Handler<VertxGenClass1>>(){});
    checkParam(params.get(10), "gen2Handler", new TypeLiteral<Handler<VertxGenClass2>>(){});
    checkParam(params.get(11), "voidHandler",  new TypeLiteral<Handler<Void>>(){});
    checkParam(params.get(12), "throwableHandler",  new TypeLiteral<Handler<Throwable>>(){});
    checkParam(params.get(13), "dataObjectHandler", new TypeLiteral<Handler<TestDataObject>>(){});
    checkParam(params.get(14), "enumHandler", new TypeLiteral<Handler<TestEnum>>(){});

    method = model.getMethods().get(1);
    checkMethod(method, "methodWithListHandlerParams", 14, "void", MethodKind.HANDLER);
    params = method.getParams();
    checkParam(params.get(0), "listByteHandler", new TypeLiteral<Handler<List<Byte>>>(){});
    checkParam(params.get(1), "listShortHandler", new TypeLiteral<Handler<List<Short>>>() {});
    checkParam(params.get(2), "listIntHandler", new TypeLiteral<Handler<List<Integer>>>(){});
    checkParam(params.get(3), "listLongHandler", new TypeLiteral<Handler<List<Long>>>(){});
    checkParam(params.get(4), "listFloatHandler",  new TypeLiteral<Handler<List<Float>>>(){});
    checkParam(params.get(5), "listDoubleHandler", new TypeLiteral<Handler<List<Double>>>(){});
    checkParam(params.get(6), "listBooleanHandler", new TypeLiteral<Handler<List<Boolean>>>(){});
    checkParam(params.get(7), "listCharHandler", new TypeLiteral<Handler<List<Character>>>(){});
    checkParam(params.get(8), "listStrHandler", new TypeLiteral<Handler<List<String>>>(){});
    checkParam(params.get(9), "listVertxGenHandler", new TypeLiteral<Handler<List<VertxGenClass1>>>(){});
    checkParam(params.get(10), "listJsonObjectHandler", new TypeLiteral<Handler<List<JsonObject>>>(){});
    checkParam(params.get(11), "listJsonArrayHandler", new TypeLiteral<Handler<List<JsonArray>>>(){});
    checkParam(params.get(12), "listDataObjectHandler", new TypeLiteral<Handler<List<TestDataObject>>>(){});
    checkParam(params.get(13), "listEnumHandler", new TypeLiteral<Handler<List<TestEnum>>>(){});

    method = model.getMethods().get(2);
    checkMethod(method, "methodWithSetHandlerParams", 14, "void", MethodKind.HANDLER);
    params = method.getParams();
    checkParam(params.get(0), "setByteHandler", new TypeLiteral<Handler<Set<Byte>>>(){});
    checkParam(params.get(1), "setShortHandler", new TypeLiteral<Handler<Set<Short>>>(){});
    checkParam(params.get(2), "setIntHandler", new TypeLiteral<Handler<Set<Integer>>>(){});
    checkParam(params.get(3), "setLongHandler", new TypeLiteral<Handler<Set<Long>>>(){});
    checkParam(params.get(4), "setFloatHandler", new TypeLiteral<Handler<Set<Float>>>(){});
    checkParam(params.get(5), "setDoubleHandler", new TypeLiteral<Handler<Set<Double>>>(){});
    checkParam(params.get(6), "setBooleanHandler", new TypeLiteral<Handler<Set<Boolean>>>(){});
    checkParam(params.get(7), "setCharHandler", new TypeLiteral<Handler<Set<Character>>>(){});
    checkParam(params.get(8), "setStrHandler", new TypeLiteral<Handler<Set<String>>>(){});
    checkParam(params.get(9), "setVertxGenHandler", new TypeLiteral<Handler<Set<VertxGenClass1>>>(){});
    checkParam(params.get(10), "setJsonObjectHandler", new TypeLiteral<Handler<Set<JsonObject>>>(){});
    checkParam(params.get(11), "setJsonArrayHandler",  new TypeLiteral<Handler<Set<JsonArray>>>(){});
    checkParam(params.get(12), "setDataObjectHandler",  new TypeLiteral<Handler<Set<TestDataObject>>>(){});
    checkParam(params.get(13), "setEnumHandler",  new TypeLiteral<Handler<Set<TestEnum>>>(){});

    method = model.getMethods().get(3);
    checkMethod(method, "methodWithMapHandlerParams", 11, "void", MethodKind.HANDLER);
    params = method.getParams();
    checkParam(params.get(0), "mapByteHandler", new TypeLiteral<Handler<Map<String, Byte>>>(){});
    checkParam(params.get(1), "mapShortHandler", new TypeLiteral<Handler<Map<String, Short>>>(){});
    checkParam(params.get(2), "mapIntHandler", new TypeLiteral<Handler<Map<String, Integer>>>(){});
    checkParam(params.get(3), "mapLongHandler", new TypeLiteral<Handler<Map<String, Long>>>(){});
    checkParam(params.get(4), "mapFloatHandler", new TypeLiteral<Handler<Map<String, Float>>>(){});
    checkParam(params.get(5), "mapDoubleHandler", new TypeLiteral<Handler<Map<String, Double>>>(){});
    checkParam(params.get(6), "mapBooleanHandler", new TypeLiteral<Handler<Map<String, Boolean>>>(){});
    checkParam(params.get(7), "mapCharHandler", new TypeLiteral<Handler<Map<String, Character>>>(){});
    checkParam(params.get(8), "mapStrHandler", new TypeLiteral<Handler<Map<String, String>>>(){});
    checkParam(params.get(9), "mapJsonObjectHandler", new TypeLiteral<Handler<Map<String, JsonObject>>>(){});
    checkParam(params.get(10), "mapJsonArrayHandler",  new TypeLiteral<Handler<Map<String, JsonArray>>>(){});
  }

  @Test
  public <T> void testValidFunctionParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidFunctionParams.class);

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, "methodWithFunctionParams", 18, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "byteFunction", new TypeLiteral<Function<Byte, Byte>>() {});
    checkParam(params.get(1), "shortFunction", new TypeLiteral<Function<Short, Short>>() {});
    checkParam(params.get(2), "intFunction", new TypeLiteral<Function<Integer, Integer>>() {});
    checkParam(params.get(3), "longFunction", new TypeLiteral<Function<Long, Long>>(){});
    checkParam(params.get(4), "floatFunction", new TypeLiteral<Function<Float, Float>>(){});
    checkParam(params.get(5), "doubleFunction", new TypeLiteral<Function<Double, Double>>(){});
    checkParam(params.get(6), "booleanFunction", new TypeLiteral<Function<Boolean, Boolean>>(){});
    checkParam(params.get(7), "charFunction", new TypeLiteral<Function<Character, Character>>(){});
    checkParam(params.get(8), "strFunction", new TypeLiteral<Function<String, String>>(){});
    checkParam(params.get(9), "gen1Function", new TypeLiteral<Function<VertxGenClass1, VertxGenClass1>>(){});
    checkParam(params.get(10), "gen2Function", new TypeLiteral<Function<VertxGenClass2, VertxGenClass2>>(){});
    checkParam(params.get(11), "voidFunction",  new TypeLiteral<Function<Void, String>>(){});
    checkParam(params.get(12), "throwableFunction",  new TypeLiteral<Function<Throwable, Throwable>>(){});
    checkParam(params.get(13), "dataObjectFunction", new TypeLiteral<Function<TestDataObject, TestDataObject>>(){});
    checkParam(params.get(14), "enumFunction", new TypeLiteral<Function<TestEnum, TestEnum>>(){});
    checkParam(params.get(15), "objectFunction", new TypeLiteral<Function<Object, Object>>(){});
    checkParam(params.get(16), "genericFunction", new TypeLiteral<Function<T, T>>(){});
    checkParam(params.get(17), "genericUserTypeFunction", new TypeLiteral<Function<GenericInterface<T>, GenericInterface<T>>>(){});

    method = model.getMethods().get(1);
    checkMethod(method, "methodWithListFunctionParams", 14, "void", MethodKind.OTHER);
    params = method.getParams();
    checkParam(params.get(0), "listByteFunction", new TypeLiteral<Function<List<Byte>, List<Byte>>>(){});
    checkParam(params.get(1), "listShortFunction", new TypeLiteral<Function<List<Short>, List<Short>>>() {});
    checkParam(params.get(2), "listIntFunction", new TypeLiteral<Function<List<Integer>, List<Integer>>>(){});
    checkParam(params.get(3), "listLongFunction", new TypeLiteral<Function<List<Long>, List<Long>>>(){});
    checkParam(params.get(4), "listFloatFunction",  new TypeLiteral<Function<List<Float>, List<Float>>>(){});
    checkParam(params.get(5), "listDoubleFunction", new TypeLiteral<Function<List<Double>, List<Double>>>(){});
    checkParam(params.get(6), "listBooleanFunction", new TypeLiteral<Function<List<Boolean>, List<Boolean>>>(){});
    checkParam(params.get(7), "listCharFunction", new TypeLiteral<Function<List<Character>, List<Character>>>(){});
    checkParam(params.get(8), "listStrFunction", new TypeLiteral<Function<List<String>, List<String>>>(){});
    checkParam(params.get(9), "listVertxGenFunction", new TypeLiteral<Function<List<VertxGenClass1>, List<VertxGenClass1>>>(){});
    checkParam(params.get(10), "listJsonObjectFunction", new TypeLiteral<Function<List<JsonObject>, List<JsonObject>>>(){});
    checkParam(params.get(11), "listJsonArrayFunction", new TypeLiteral<Function<List<JsonArray>, List<JsonArray>>>(){});
    checkParam(params.get(12), "listDataObjectFunction", new TypeLiteral<Function<List<TestDataObject>, List<TestDataObject>>>(){});
    checkParam(params.get(13), "listEnumFunction", new TypeLiteral<Function<List<TestEnum>, List<TestEnum>>>(){});

    method = model.getMethods().get(2);
    checkMethod(method, "methodWithSetFunctionParams", 14, "void", MethodKind.OTHER);
    params = method.getParams();
    checkParam(params.get(0), "setByteFunction", new TypeLiteral<Function<Set<Byte>, Set<Byte>>>(){});
    checkParam(params.get(1), "setShortFunction", new TypeLiteral<Function<Set<Short>, Set<Short>>>(){});
    checkParam(params.get(2), "setIntFunction", new TypeLiteral<Function<Set<Integer>, Set<Integer>>>(){});
    checkParam(params.get(3), "setLongFunction", new TypeLiteral<Function<Set<Long>, Set<Long>>>(){});
    checkParam(params.get(4), "setFloatFunction", new TypeLiteral<Function<Set<Float>, Set<Float>>>(){});
    checkParam(params.get(5), "setDoubleFunction", new TypeLiteral<Function<Set<Double>, Set<Double>>>(){});
    checkParam(params.get(6), "setBooleanFunction", new TypeLiteral<Function<Set<Boolean>, Set<Boolean>>>(){});
    checkParam(params.get(7), "setCharFunction", new TypeLiteral<Function<Set<Character>, Set<Character>>>(){});
    checkParam(params.get(8), "setStrFunction", new TypeLiteral<Function<Set<String>, Set<String>>>(){});
    checkParam(params.get(9), "setVertxGenFunction", new TypeLiteral<Function<Set<VertxGenClass1>, Set<VertxGenClass1>>>(){});
    checkParam(params.get(10), "setJsonObjectFunction", new TypeLiteral<Function<Set<JsonObject>, Set<JsonObject>>>(){});
    checkParam(params.get(11), "setJsonArrayFunction",  new TypeLiteral<Function<Set<JsonArray>, Set<JsonArray>>>(){});
    checkParam(params.get(12), "setDataObjectFunction",  new TypeLiteral<Function<Set<TestDataObject>, Set<TestDataObject>>>(){});
    checkParam(params.get(13), "setEnumFunction",  new TypeLiteral<Function<Set<TestEnum>, Set<TestEnum>>>(){});

    method = model.getMethods().get(3);
    checkMethod(method, "methodWithMapFunctionParams", 11, "void", MethodKind.OTHER);
    params = method.getParams();
    checkParam(params.get(0), "mapByteFunction", new TypeLiteral<Function<Map<String, Byte>, Map<String, Byte>>>(){});
    checkParam(params.get(1), "mapShortFunction", new TypeLiteral<Function<Map<String, Short>, Map<String, Short>>>(){});
    checkParam(params.get(2), "mapIntFunction", new TypeLiteral<Function<Map<String, Integer>, Map<String, Integer>>>(){});
    checkParam(params.get(3), "mapLongFunction", new TypeLiteral<Function<Map<String, Long>, Map<String, Long>>>(){});
    checkParam(params.get(4), "mapFloatFunction", new TypeLiteral<Function<Map<String, Float>, Map<String, Float>>>(){});
    checkParam(params.get(5), "mapDoubleFunction", new TypeLiteral<Function<Map<String, Double>, Map<String, Double>>>(){});
    checkParam(params.get(6), "mapBooleanFunction", new TypeLiteral<Function<Map<String, Boolean>, Map<String, Boolean>>>(){});
    checkParam(params.get(7), "mapCharFunction", new TypeLiteral<Function<Map<String, Character>, Map<String, Character>>>(){});
    checkParam(params.get(8), "mapStrFunction", new TypeLiteral<Function<Map<String, String>, Map<String, String>>>(){});
    checkParam(params.get(9), "mapJsonObjectFunction", new TypeLiteral<Function<Map<String, JsonObject>, Map<String, JsonObject>>>(){});
    checkParam(params.get(10), "mapJsonArrayFunction",  new TypeLiteral<Function<Map<String, JsonArray>, Map<String, JsonArray>>>(){});
  }

  @Test
  public void testMethodWithInvalidFunctionParams() throws Exception {
    assertGenInvalid(MethodWithInvalidFunctionParam1.class);
    assertGenInvalid(MethodWithInvalidFunctionParam2.class);
    assertGenInvalid(MethodWithInvalidFunctionParam3.class);
    assertGenInvalid(MethodWithInvalidFunctionParam4.class);
    assertGenInvalid(MethodWithInvalidFunctionParam5.class);
    assertGenInvalid(MethodWithInvalidFunctionParam6.class);
    assertGenInvalid(MethodWithInvalidFunctionParam7.class);
    assertGenInvalid(MethodWithInvalidFunctionParam8.class);
  }

  @Test
  public void testValidHandlerAsyncResultParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerAsyncResultParams.class);
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(4, model.getMethods().size());

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, "methodWithHandlerParams", 16, "void", MethodKind.FUTURE);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "byteHandler", new TypeLiteral<Handler<AsyncResult<Byte>>>() {});
    checkParam(params.get(1), "shortHandler", new TypeLiteral<Handler<AsyncResult<Short>>>() {});
    checkParam(params.get(2), "intHandler", new TypeLiteral<Handler<AsyncResult<Integer>>>() {});
    checkParam(params.get(3), "longHandler", new TypeLiteral<Handler<AsyncResult<Long>>>(){});
    checkParam(params.get(4), "floatHandler", new TypeLiteral<Handler<AsyncResult<Float>>>(){});
    checkParam(params.get(5), "doubleHandler", new TypeLiteral<Handler<AsyncResult<Double>>>(){});
    checkParam(params.get(6), "booleanHandler", new TypeLiteral<Handler<AsyncResult<Boolean>>>(){});
    checkParam(params.get(7), "charHandler", new TypeLiteral<Handler<AsyncResult<Character>>>(){});
    checkParam(params.get(8), "strHandler", new TypeLiteral<Handler<AsyncResult<String>>>(){});
    checkParam(params.get(9), "gen1Handler", new TypeLiteral<Handler<AsyncResult<VertxGenClass1>>>(){});
    checkParam(params.get(10), "gen2Handler", new TypeLiteral<Handler<AsyncResult<VertxGenClass2>>>(){});
    checkParam(params.get(11), "jsonObjectHandler", new TypeLiteral<Handler<AsyncResult<JsonObject>>>(){});
    checkParam(params.get(12), "jsonArrayHandler", new TypeLiteral<Handler<AsyncResult<JsonArray>>>(){});
    checkParam(params.get(13), "voidHandler",  new TypeLiteral<Handler<AsyncResult<Void>>>(){});
    checkParam(params.get(14), "dataObjectHandler", new TypeLiteral<Handler<AsyncResult<TestDataObject>>>(){});
    checkParam(params.get(15), "enumHandler", new TypeLiteral<Handler<AsyncResult<TestEnum>>>(){});

    method = model.getMethods().get(1);
    checkMethod(method, "methodWithListHandlerParams", 14, "void", MethodKind.FUTURE);
    params = method.getParams();
    checkParam(params.get(0), "listByteHandler", new TypeLiteral<Handler<AsyncResult<List<Byte>>>>(){});
    checkParam(params.get(1), "listShortHandler", new TypeLiteral<Handler<AsyncResult<List<Short>>>>() {});
    checkParam(params.get(2), "listIntHandler", new TypeLiteral<Handler<AsyncResult<List<Integer>>>>(){});
    checkParam(params.get(3), "listLongHandler", new TypeLiteral<Handler<AsyncResult<List<Long>>>>(){});
    checkParam(params.get(4), "listFloatHandler", new TypeLiteral<Handler<AsyncResult<List<Float>>>>(){});
    checkParam(params.get(5), "listDoubleHandler", new TypeLiteral<Handler<AsyncResult<List<Double>>>>(){});
    checkParam(params.get(6), "listBooleanHandler", new TypeLiteral<Handler<AsyncResult<List<Boolean>>>>(){});
    checkParam(params.get(7), "listCharHandler", new TypeLiteral<Handler<AsyncResult<List<Character>>>>(){});
    checkParam(params.get(8), "listStrHandler", new TypeLiteral<Handler<AsyncResult<List<String>>>>(){});
    checkParam(params.get(9), "listVertxGenHandler", new TypeLiteral<Handler<AsyncResult<List<VertxGenClass1>>>>(){});
    checkParam(params.get(10), "listJsonObjectHandler", new TypeLiteral<Handler<AsyncResult<List<JsonObject>>>>(){});
    checkParam(params.get(11), "listJsonArrayHandler", new TypeLiteral<Handler<AsyncResult<List<JsonArray>>>>(){});
    checkParam(params.get(12), "listDataObjectHandler", new TypeLiteral<Handler<AsyncResult<List<TestDataObject>>>>(){});
    checkParam(params.get(13), "listEnumHandler", new TypeLiteral<Handler<AsyncResult<List<TestEnum>>>>(){});

    method = model.getMethods().get(2);
    checkMethod(method, "methodWithSetHandlerParams", 14, "void", MethodKind.FUTURE);
    params = method.getParams();
    checkParam(params.get(0), "setByteHandler", new TypeLiteral<Handler<AsyncResult<Set<Byte>>>>(){});
    checkParam(params.get(1), "setShortHandler", new TypeLiteral<Handler<AsyncResult<Set<Short>>>>(){});
    checkParam(params.get(2), "setIntHandler", new TypeLiteral<Handler<AsyncResult<Set<Integer>>>>(){});
    checkParam(params.get(3), "setLongHandler", new TypeLiteral<Handler<AsyncResult<Set<Long>>>>(){});
    checkParam(params.get(4), "setFloatHandler", new TypeLiteral<Handler<AsyncResult<Set<Float>>>>(){});
    checkParam(params.get(5), "setDoubleHandler", new TypeLiteral<Handler<AsyncResult<Set<Double>>>>(){});
    checkParam(params.get(6), "setBooleanHandler", new TypeLiteral<Handler<AsyncResult<Set<Boolean>>>>(){});
    checkParam(params.get(7), "setCharHandler", new TypeLiteral<Handler<AsyncResult<Set<Character>>>>(){});
    checkParam(params.get(8), "setStrHandler", new TypeLiteral<Handler<AsyncResult<Set<String>>>>(){});
    checkParam(params.get(9), "setVertxGenHandler", new TypeLiteral<Handler<AsyncResult<Set<VertxGenClass1>>>>(){});
    checkParam(params.get(10), "setJsonObjectHandler", new TypeLiteral<Handler<AsyncResult<Set<JsonObject>>>>(){});
    checkParam(params.get(11), "setJsonArrayHandler", new TypeLiteral<Handler<AsyncResult<Set<JsonArray>>>>(){});
    checkParam(params.get(12), "setDataObjectHandler", new TypeLiteral<Handler<AsyncResult<Set<TestDataObject>>>>(){});
    checkParam(params.get(13), "setEnumHandler", new TypeLiteral<Handler<AsyncResult<Set<TestEnum>>>>(){});

    method = model.getMethods().get(3);
    checkMethod(method, "methodWithMapHandlerParams", 11, "void", MethodKind.FUTURE);
    params = method.getParams();
    checkParam(params.get(0), "mapByteHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Byte>>>>(){});
    checkParam(params.get(1), "mapShortHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Short>>>>(){});
    checkParam(params.get(2), "mapIntHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Integer>>>>(){});
    checkParam(params.get(3), "mapLongHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Long>>>>(){});
    checkParam(params.get(4), "mapFloatHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Float>>>>(){});
    checkParam(params.get(5), "mapDoubleHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Double>>>>(){});
    checkParam(params.get(6), "mapBooleanHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Boolean>>>>(){});
    checkParam(params.get(7), "mapCharHandler", new TypeLiteral<Handler<AsyncResult<Map<String,Character>>>>(){});
    checkParam(params.get(8), "mapStrHandler", new TypeLiteral<Handler<AsyncResult<Map<String,String>>>>(){});
    checkParam(params.get(9), "mapJsonObjectHandler", new TypeLiteral<Handler<AsyncResult<Map<String,JsonObject>>>>(){});
    checkParam(params.get(10), "mapJsonArrayHandler", new TypeLiteral<Handler<AsyncResult<Map<String,JsonArray>>>>(){});
  }

  @Test
  public void testValidVertxGenParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidVertxGenParams.class);
    assertEquals(MethodWithValidVertxGenParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithVertxGenParams";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 3, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "str", String.class);
    checkParam(params.get(1), "myParam1", VertxGenClass1.class);
    checkParam(params.get(2), "myParam2", VertxGenClass2.class);
  }

  @Test
  public void testValidExceptionParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidThrowableParam.class);
    assertEquals(0, model.getReferencedTypes().size());
    assertEquals(1, model.getImportedTypes().size());
    assertEquals(ClassKind.THROWABLE, model.getImportedTypes().iterator().next().getKind());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithThrowableParam";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "t", Throwable.class);
  }

  @Test
  public void testValidObjectParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithObjectParam.class);
    assertEquals(MethodWithObjectParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithObjectParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithObjectParam";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "obj", Object.class);
  }

  @Test
  public void testValidEnumParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithEnumParam.class);
    assertEquals(MethodWithEnumParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithEnumParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getImportedTypes().contains(TypeReflectionFactory.create(TestEnum.class)));
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, "methodWithEnumParam", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "weirdo", TestEnum.class);
    EnumTypeInfo enumType = (EnumTypeInfo) params.get(0).getType();
    assertFalse(enumType.isGen());
    assertEquals(Arrays.asList("TIM", "JULIEN", "NICK", "WESTON"), enumType.getValues());

    method = model.getMethods().get(1);
    checkMethod(method, "methodWithGenEnumParam", 1, "void", MethodKind.OTHER);
    params = method.getParams();
    checkParam(params.get(0), "weirdo", TestGenEnum.class);
    enumType = (EnumTypeInfo) params.get(0).getType();
    assertTrue(enumType.isGen());
    assertEquals(Arrays.asList("LAURA", "BOB", "MIKE", "LELAND"), enumType.getValues());
  }

  @Test
  public void testValidHandlerReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithHandlerReturn.class);
    assertEquals(MethodWithHandlerReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithHandlerReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());

    checkMethod(model.getMethods().get(0), "methodWithHandlerStringReturn", 0, new TypeLiteral<Handler<String>>() {}, MethodKind.OTHER);
    TypeInfo returnType = model.getMethods().get(0).getReturnType();
    assertEquals(ClassKind.HANDLER, returnType.getKind());
    assertEquals(ClassKind.STRING, ((ParameterizedTypeInfo)returnType).getArg(0).getKind());
  }

  @Test
  public void testValidHandlerAsyncResultReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithHandlerAsyncResultReturn.class);
    assertEquals(MethodWithHandlerAsyncResultReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithHandlerAsyncResultReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());

    checkMethod(model.getMethods().get(0), "methodWithHandlerAsyncResultStringReturn", 0, new TypeLiteral<Handler<AsyncResult<String>>>() {}, MethodKind.OTHER);
    TypeInfo returnType = model.getMethods().get(0).getReturnType();
    assertEquals(ClassKind.HANDLER, returnType.getKind());
    assertEquals(ClassKind.ASYNC_RESULT, ((ParameterizedTypeInfo)returnType).getArg(0).getKind());
    assertEquals(ClassKind.STRING, ((ParameterizedTypeInfo)((ParameterizedTypeInfo)returnType).getArg(0)).getArg(0).getKind());
  }

  @Test
  public void testValidEnumReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithEnumReturn.class);
    assertEquals(MethodWithEnumReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithEnumReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getImportedTypes().contains(TypeReflectionFactory.create(TestEnum.class)));
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());

    checkMethod(model.getMethods().get(0), "methodWithEnumReturn", 0, TestEnum.class, MethodKind.OTHER);
    checkMethod(model.getMethods().get(1), "methodWithGenEnumReturn", 0, TestGenEnum.class, MethodKind.OTHER);
  }

  @Test
  public void testValidThrowableReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithThrowableReturn.class);
    assertEquals(MethodWithThrowableReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithThrowableReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithThrowableReturn";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 0, Throwable.class, MethodKind.OTHER);
  }

  @Test
  public void testValidDataObjectsParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithDataObjectParam.class);
    assertEquals(MethodWithDataObjectParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithDataObjectParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithDataObjectParam";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    checkParam(params.get(0), "dataObject", PlainDataObject.class);
  }

  @Test
  public void testInvalidDataObjectsParam() throws Exception {
    assertGenInvalid(MethodWithInvalidAbstractDataObjectParam.class);
    assertGenInvalid(MethodWithInvalidInterfaceDataObjectParam.class);
  }

  // Valid returns

  @Test
  public void testFoo() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithTypeVarParamByGenericType.class);
  }

  @Test
  public <T, R> void testGenericInterface() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(GenericInterface.class);
    assertEquals(GenericInterface.class.getName() + "<T>", model.getIfaceFQCN());
    assertEquals(GenericInterface.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getReferencedTypes());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    TypeParamInfo t = model.getType().getParams().get(0);
    checkMethod(methods.get(0), "methodWithClassTypeParam", 3, "T", MethodKind.OTHER);
    List<ParamInfo> params1 = methods.get(0).getParams();
    checkParam(params1.get(0), "t", new TypeLiteral<T>(){});
    assertTrue(params1.get(0).getType() instanceof TypeVariableInfo);
    assertEquals(t, ((TypeVariableInfo)params1.get(0).getType()).getParam());
    assertTrue(((TypeVariableInfo)params1.get(0).getType()).isClassParam());
    assertFalse(((TypeVariableInfo)params1.get(0).getType()).isMethodParam());
    assertNull(methods.get(0).resolveClassTypeParam((TypeVariableInfo) params1.get(0).getType()));
    checkParam(params1.get(1), "handler", new TypeLiteral<Handler<T>>(){});
    checkParam(params1.get(2), "asyncResultHandler", new TypeLiteral<Handler<AsyncResult<T>>>(){});
    checkMethod(methods.get(1), "someGenericMethod", 3, new TypeLiteral<GenericInterface<R>>(){}, MethodKind.OTHER);
    List<ParamInfo> params2 = methods.get(1).getParams();
    checkParam(params2.get(0), "r", new TypeLiteral<R>(){});
    assertTrue(params2.get(0).getType() instanceof TypeVariableInfo);
    assertEquals(methods.get(1).getTypeParams().get(0), ((TypeVariableInfo) params2.get(0).getType()).getParam());
    assertFalse(((TypeVariableInfo) params2.get(0).getType()).isClassParam());
    assertTrue(((TypeVariableInfo) params2.get(0).getType()).isMethodParam());
    assertNull(methods.get(1).resolveClassTypeParam((TypeVariableInfo) params2.get(0).getType()));
    checkParam(params2.get(1), "handler", new TypeLiteral<Handler<R>>(){});
    checkParam(params2.get(2), "asyncResultHandler", new TypeLiteral<Handler<AsyncResult<R>>>(){});
  }


  @Test
  public void testGenericInterfaceWithUpperBound() throws Exception {
    try {
      new GeneratorHelper().generateClass(GenericInterfaceWithUpperBound.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testValidBasicReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidBasicReturn.class);
    assertEquals(MethodWithValidBasicReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(17, methods.size());
    checkMethod(methods.get(0), "methodWithByteReturn", 0, "byte", MethodKind.OTHER);
    checkMethod(methods.get(1), "methodWithShortReturn", 0, "short", MethodKind.OTHER);
    checkMethod(methods.get(2), "methodWithIntReturn", 0, "int", MethodKind.OTHER);
    checkMethod(methods.get(3), "methodWithLongReturn", 0, "long", MethodKind.OTHER);
    checkMethod(methods.get(4), "methodWithFloatReturn", 0, "float", MethodKind.OTHER);
    checkMethod(methods.get(5), "methodWithDoubleReturn", 0, "double", MethodKind.OTHER);
    checkMethod(methods.get(6), "methodWithBooleanReturn", 0, "boolean", MethodKind.OTHER);
    checkMethod(methods.get(7), "methodWithCharReturn", 0, "char", MethodKind.OTHER);
    checkMethod(methods.get(8), "methodWithStringReturn", 0, String.class, MethodKind.OTHER);

    checkMethod(methods.get(9), "methodWithByteObjectReturn", 0, Byte.class, MethodKind.OTHER);
    checkMethod(methods.get(10), "methodWithShortObjectReturn", 0, Short.class, MethodKind.OTHER);
    checkMethod(methods.get(11), "methodWithIntObjectReturn", 0, Integer.class, MethodKind.OTHER);
    checkMethod(methods.get(12), "methodWithLongObjectReturn", 0, Long.class, MethodKind.OTHER);
    checkMethod(methods.get(13), "methodWithFloatObjectReturn", 0, Float.class, MethodKind.OTHER);
    checkMethod(methods.get(14), "methodWithDoubleObjectReturn", 0, Double.class, MethodKind.OTHER);
    checkMethod(methods.get(15), "methodWithBooleanObjectReturn", 0, Boolean.class, MethodKind.OTHER);
    checkMethod(methods.get(16), "methodWithCharObjectReturn", 0, Character.class, MethodKind.OTHER);
  }

  @Test
  public void testValidVoidReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidVoidReturn.class);
    assertEquals(MethodWithValidVoidReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVoidReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithVoidReturn";
    checkMethod(model.getMethods().get(0), methodName, 0, "void", MethodKind.OTHER);
  }

  @Test
  public void testValidDataObjectReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidDataObjectReturn.class);
    assertEquals(MethodWithValidDataObjectReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidDataObjectReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(3, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "methodWithDataObjectReturn", 0, PlainDataObjectWithToJson.class.getName(), MethodKind.OTHER);
    checkMethod(model.getMethods().get(1), "methodWithAbstractDataObjectReturn", 0, AbstractDataObjectWithToJson.class.getName(), MethodKind.OTHER);
    checkMethod(model.getMethods().get(2), "methodWithInterfaceDataObjectReturn", 0, InterfaceDataObjectWithToJson.class.getName(), MethodKind.OTHER);
  }

  @Test
  public void testValidObjectReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithObjectReturn.class);
    assertEquals(MethodWithObjectReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithObjectReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "foo", 0, "java.lang.Object", MethodKind.OTHER);
  }

  @Test
  public void testValidListReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidListReturn.class);
    assertEquals(MethodWithValidListReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidListReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(15, methods.size());
    checkMethod(methods.get(0), "byteList", 0, new TypeLiteral<List<Byte>>() {
    }, MethodKind.OTHER);
    checkMethod(methods.get(1), "shortList", 0, new TypeLiteral<List<Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(2), "intList", 0, new TypeLiteral<List<Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(3), "longList", 0, new TypeLiteral<List<Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(4), "floatList", 0, new TypeLiteral<List<Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(5), "doubleList", 0, new TypeLiteral<List<Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(6), "booleanList", 0, new TypeLiteral<List<Boolean>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(7), "charList", 0, new TypeLiteral<List<Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(8), "stringList", 0, new TypeLiteral<List<String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(9), "vertxGen1List", 0, new TypeLiteral<List<VertxGenClass1>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(10), "vertxGen2List", 0, new TypeLiteral<List<VertxGenClass2>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(11), "jsonArrayList", 0, new TypeLiteral<List<JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(12), "jsonObjectList", 0, new TypeLiteral<List<JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(13), "dataObjectList", 0, new TypeLiteral<List<TestDataObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(14), "enumList", 0, new TypeLiteral<List<TestEnum>>() {}, MethodKind.OTHER);
  }

  @Test
  public void testValidSetReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidSetReturn.class);
    assertEquals(MethodWithValidSetReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidSetReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(15, methods.size());
    checkMethod(methods.get(0), "byteSet", 0, new TypeLiteral<Set<Byte>>() {
    }, MethodKind.OTHER);
    checkMethod(methods.get(1), "shortSet", 0, new TypeLiteral<Set<Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(2), "intSet", 0, new TypeLiteral<Set<Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(3), "longSet", 0, new TypeLiteral<Set<Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(4), "floatSet", 0, new TypeLiteral<Set<Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(5), "doubleSet", 0, new TypeLiteral<Set<Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(6), "booleanSet", 0, new TypeLiteral<Set<Boolean>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(7), "charSet", 0, new TypeLiteral<Set<Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(8), "stringSet", 0, new TypeLiteral<Set<String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(9), "vertxGen1Set", 0, new TypeLiteral<Set<VertxGenClass1>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(10), "vertxGen2Set", 0, new TypeLiteral<Set<VertxGenClass2>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(11), "jsonArraySet", 0, new TypeLiteral<Set<JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(12), "jsonObjectSet", 0, new TypeLiteral<Set<JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(13), "dataObjectSet", 0, new TypeLiteral<Set<TestDataObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(14), "enumSet", 0, new TypeLiteral<Set<TestEnum>>() {}, MethodKind.OTHER);
  }

  @Test
  public void testValidMapReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidMapReturn.class);
    assertEquals(MethodWithValidMapReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidMapReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(0, model.getReferencedTypes().size());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(11, methods.size());
    checkMethod(methods.get(0), "byteMap", 0, new TypeLiteral<Map<String, Byte>>() {
    }, MethodKind.OTHER);
    checkMethod(methods.get(1), "shortMap", 0, new TypeLiteral<Map<String, Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(2), "intMap", 0, new TypeLiteral<Map<String, Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(3), "longMap", 0, new TypeLiteral<Map<String, Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(4), "floatMap", 0, new TypeLiteral<Map<String, Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(5), "doubleMap", 0, new TypeLiteral<Map<String, Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(6), "booleanMap", 0, new TypeLiteral<Map<String, Boolean>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(7), "charMap", 0, new TypeLiteral<Map<String, Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(8), "stringMap", 0, new TypeLiteral<Map<String, String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(9), "jsonArrayMap", 0, new TypeLiteral<Map<String, JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(10), "jsonObjectMap", 0, new TypeLiteral<Map<String, JsonObject>>() {}, MethodKind.OTHER);
  }

  @Test
  public void testValidVertxGenReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidVertxGenReturn.class);
    assertEquals(MethodWithValidVertxGenReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "methodWithVertxGen1Return", 0, VertxGenClass1.class, MethodKind.OTHER);
    checkMethod(methods.get(1), "methodWithVertxGen2Return", 0, VertxGenClass2.class, MethodKind.OTHER);
  }

  @Test
  public void testIgnore() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithIgnoredElements.class);
    assertEquals(InterfaceWithIgnoredElements.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithIgnoredElements.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 1, "void", MethodKind.OTHER);
    checkMethod(methods.get(1), "bar", 1, "void", MethodKind.OTHER);
  }

  @Test
  public void testFluentMethodOverrideFromConcrete() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithFluentMethodOverrideFromConcrete.class);
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(ConcreteInterfaceWithFluentMethods.class)), model.getReferencedTypes());
    assertEquals(Collections.singletonList(TypeReflectionFactory.create(ConcreteInterfaceWithFluentMethods.class)), model.getSuperTypes());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 1, InterfaceWithFluentMethodOverrideFromConcrete.class, MethodKind.OTHER, MethodCheck.FLUENT);
    checkMethod(methods.get(1), "bar", 1, InterfaceWithFluentMethodOverrideFromConcrete.class, MethodKind.OTHER, MethodCheck.FLUENT);
  }

  @Test
  public void testFluentMethodOverrideFromAbstract() throws Exception {
    GeneratorHelper gen = new GeneratorHelper();
    ClassModel model = gen.generateClass(InterfaceWithFluentMethodOverrideFromAbstract.class);
    assertEquals(InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethodOverrideFromAbstract.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(AbstractInterfaceWithFluentMethods.class)), model.getReferencedTypes());
    assertEquals(Collections.singletonList(TypeReflectionFactory.create(AbstractInterfaceWithFluentMethods.class)), model.getSuperTypes());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 1, InterfaceWithFluentMethodOverrideFromAbstract.class, MethodKind.OTHER, MethodCheck.FLUENT);
    checkMethod(methods.get(1), "bar", 1, InterfaceWithFluentMethodOverrideFromAbstract.class, MethodKind.OTHER, MethodCheck.FLUENT);
  }

  @Test
  public void testFluentMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(ConcreteInterfaceWithFluentMethods.class);
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 1, ConcreteInterfaceWithFluentMethods.class, MethodKind.OTHER, MethodCheck.FLUENT);
    checkMethod(methods.get(1), "bar", 1, ConcreteInterfaceWithFluentMethods.class, MethodKind.OTHER, MethodCheck.FLUENT);
  }

  @Test
  public void testCacheReturnMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithCacheReturnMethods.class);
    assertEquals(InterfaceWithCacheReturnMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithCacheReturnMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 1, String.class, MethodKind.OTHER, MethodCheck.CACHE_RETURN);
    checkMethod(methods.get(1), "bar", 1, VertxGenClass1.class, MethodKind.OTHER, MethodCheck.CACHE_RETURN);
  }

  @Test
  public void testSupertypes() throws Exception {
    ClassModel gen = new GeneratorHelper().generateClass(InterfaceWithSupertypes.class, VertxGenClass1.class, VertxGenInterface.class);
    assertEquals(InterfaceWithSupertypes.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithSupertypes.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterfaceInfo));
    assertEquals(2, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(VertxGenClass1Info));
    assertTrue(gen.getSuperTypes().contains(VertxGenInterfaceInfo));
    assertEquals(VertxGenClass1Info, gen.getConcreteSuperType());
    assertEquals(1, gen.getAbstractSuperTypes().size());
    assertTrue(gen.getAbstractSuperTypes().contains(VertxGenInterfaceInfo));
    List<MethodInfo> methods = gen.getMethods();
    assertEquals(2, methods.size());
    Collections.sort(methods);
    checkMethod(methods.get(0), "bar", 1, "void", MethodKind.OTHER);
    checkMethod(methods.get(1), "quux", 1, "void", MethodKind.OTHER);
  }

  @Test
  public <T> void testParameterizedClassSuperType() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithParameterizedDeclaredSupertype.class, GenericInterface.class);
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(GenericInterfaceInfo));
    assertEquals(1, model.getSuperTypes().size());
    assertTrue(model.getSuperTypes().contains(TypeReflectionFactory.create(InterfaceWithParameterizedDeclaredSupertype.class.getGenericInterfaces()[0])));
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithClassTypeParam", 3, "java.lang.String", MethodKind.OTHER);
    checkParam(methods.get(0).getParam(0), "t", new TypeLiteral<String>() {}, new TypeLiteral<T>() {});
    checkParam(methods.get(0).getParam(1), "handler", new TypeLiteral<Handler<String>>() {}, new TypeLiteral<Handler<T>>() {});
    checkParam(methods.get(0).getParam(2), "asyncResultHandler", new TypeLiteral<Handler<AsyncResult<String>>>() {}, new TypeLiteral<Handler<AsyncResult<T>>>() {});
  }

  @Test
  public void testParameterizedVariableSuperType() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithParameterizedVariableSupertype.class);
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getName() + "<T>", model.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(GenericInterfaceInfo));
    assertEquals(1, model.getSuperTypes().size());
    assertTrue(model.getSuperTypes().contains(TypeReflectionFactory.create(InterfaceWithParameterizedVariableSupertype.class.getGenericInterfaces()[0])));
    List<TypeInfo> superTypeArgs = model.getSuperTypeArguments();
    assertEquals(1, superTypeArgs.size());
    TypeVariableInfo superTypeArg = (TypeVariableInfo) superTypeArgs.get(0);
    assertEquals("T", superTypeArg.getName());
    assertTrue(superTypeArg.isClassParam());
  }

  @Test
  public void testNonGenSuperType() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithNonGenSuperType.class);
    assertEquals(InterfaceWithNonGenSuperType.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithNonGenSuperType.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(0, model.getReferencedTypes().size());
    assertEquals(0, model.getSuperTypes().size());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", 1, "void", MethodKind.OTHER);
  }

  @Test
  public void testMethodWithTypeVarParamByGenericType() throws Exception {
    Runnable test = () -> {
      try {
        ClassModel model = new GeneratorHelper().generateClass(MethodWithTypeVarParamByGenericType.class);
        MethodInfo meth = model.getMethods().get(0);
        ParamInfo param = meth.getParam(0);
        ParameterizedTypeInfo handler = (ParameterizedTypeInfo) param.getType();
        assertEquals(Handler.class.getName(), handler.getRaw().getName());
        ParameterizedTypeInfo genericInt2 = (ParameterizedTypeInfo) handler.getArg(0);
        assertEquals(GenericInterface2.class.getName(), genericInt2.getRaw().getName());
        TypeVariableInfo k = (TypeVariableInfo) genericInt2.getArg(0);
        assertEquals("K", k.getName());
        TypeVariableInfo v = (TypeVariableInfo) genericInt2.getArg(1);
        assertEquals("V", v.getName());
      } catch (Exception e) {
        throw new AssertionError(e);
      }
    };
    blacklist(test, Stream.of(WriteStream.class));
    test.run();
  }

  @Test
  public void testParameterizedForbiddenSuperType() throws Exception {
    Class<?>[] forbidenTypes = {
        InterfaceWithParameterizedArraySupertype.class,
        InterfaceWithParameterizedGenericArraySupertype.class
    };
    for (Class<?> forbidenType : forbidenTypes) {
      try {
        new GeneratorHelper().generateClass(forbidenType);
        fail();
      } catch (GenException e) {
      }
    }
  }

  @Test
  public void testGenerateInterfaceWithDefaultMethod() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithDefaultMethod.class);
    assertEquals(2, model.getMethods().size());
  }

  @Test
  public <T> void testOverloadedMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithOverloadedMethods.class);
    assertEquals(InterfaceWithOverloadedMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithOverloadedMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());

    List<MethodInfo> methods = model.getMethods();
    assertEquals(8, methods.size());
    checkMethod(methods.get(0), "foo", 1, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(0).getParams().get(0), "str", String.class);

    checkMethod(methods.get(1), "foo", 2, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(1).getParams().get(0), "str", String.class);
    checkParam(model.getMethods().get(1).getParams().get(1), "handler", new TypeLiteral<Handler<VertxGenClass1>>(){});

    checkMethod(methods.get(2), "foo", 3, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(2).getParams().get(0), "str", String.class);
    checkParam(model.getMethods().get(2).getParams().get(1), "time", long.class);
    checkParam(model.getMethods().get(2).getParams().get(2), "handler", new TypeLiteral<Handler<VertxGenClass1>>(){});

    checkMethod(methods.get(3), "bar", 1, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(3).getParams().get(0), "obj1", VertxGenClass2.class);

    checkMethod(methods.get(4), "bar", 1, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(4).getParams().get(0), "obj1", String.class);

    checkMethod(methods.get(5), "juu", 1, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(0).getParams().get(0), "str", String.class);
    checkMethod(methods.get(6), "juu", 2, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(6).getParams().get(0), "str", String.class);
    checkParam(model.getMethods().get(6).getParams().get(1), "time", long.class);
    checkMethod(methods.get(7), "juu", 3, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(7).getParams().get(0), "str", String.class);
    checkParam(model.getMethods().get(7).getParams().get(1), "time", long.class);
    checkParam(model.getMethods().get(7).getParams().get(2), "handler", new TypeLiteral<Handler<T>>(){});
    assertEquals(3, model.getMethodMap().size());
    List<MethodInfo> meths1 = model.getMethodMap().get("foo");
    assertEquals(3, meths1.size());
    assertSame(model.getMethods().get(0), meths1.get(0));
    assertSame(model.getMethods().get(1), meths1.get(1));
    assertSame(model.getMethods().get(2), meths1.get(2));
    List<MethodInfo> meths2 = model.getMethodMap().get("bar");
    assertEquals(2, meths2.size());
    assertSame(model.getMethods().get(3), meths2.get(0));
    assertSame(model.getMethods().get(4), meths2.get(1));
  }

  @Test
  public void testStaticMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithStaticMethods.class);
    assertEquals(InterfaceWithStaticMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithStaticMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", 1, VertxGenClass1.class, MethodKind.OTHER, MethodCheck.STATIC);
      checkMethod(methods.get(1), "bar", 1, VertxGenClass2.class, MethodKind.OTHER, MethodCheck.STATIC);
    };
    checker.accept(model.getMethods());
    checker.accept(model.getStaticMethods());
    assertEquals(Collections.<MethodInfo>emptyList(), model.getInstanceMethods());
  }

  @Test
  public void testStaticSuperStaticMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithSuperStaticMethods.class);
    assertEquals(InterfaceWithSuperStaticMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithSuperStaticMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singletonList(TypeReflectionFactory.create(InterfaceWithStaticMethods.class)), model.getSuperTypes());
    assertEquals(0, model.getMethods().size());
  }

  @Test
  public void testInstanceMethods() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithInstanceMethods.class);
    assertEquals(InterfaceWithInstanceMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithInstanceMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", 1, VertxGenClass1.class, MethodKind.OTHER);
      checkMethod(methods.get(1), "bar", 1, VertxGenClass2.class, MethodKind.OTHER);
    };
    checker.accept(model.getMethods());
    checker.accept(model.getInstanceMethods());
    assertEquals(Collections.<MethodInfo>emptyList(), model.getStaticMethods());
  }

  @Test
  public void testMethodOverride() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithMethodOverride.class, VertxGenInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "bar", 1, "void", MethodKind.OTHER);
    assertEquals(set(
        TypeReflectionFactory.create(InterfaceWithMethodOverride.class),
        TypeReflectionFactory.create(VertxGenInterface.class)
    ), methods.get(0).getOwnerTypes());
    checkParam(methods.get(0).getParams().get(0), "str", String.class);
  }

  @Test
  public void testMethodOverrideParameterRenamed() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithMethodOverrideParameterRenamed.class, VertxGenInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "bar", 1, "void", MethodKind.OTHER);
    assertEquals(set(
        TypeReflectionFactory.create(InterfaceWithMethodOverrideParameterRenamed.class),
        TypeReflectionFactory.create(VertxGenInterface.class)
    ), methods.get(0).getOwnerTypes());
    checkParam(methods.get(0).getParams().get(0), "str_renamed", String.class);
  }

  @Test
  public void testInterfaceWithGenericMethodOverride() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithGenericMethodOverride.class, GenericAbstractInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(5, methods.size());
    checkMethod(methods.get(0), "foo", 0, String.class, MethodKind.OTHER);
    checkMethod(methods.get(1), "bar", 0, new TypeLiteral<List<String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(2), "juu", 1, "void", MethodKind.FUTURE);
    checkMethod(methods.get(3), "daa", 1, "void", MethodKind.HANDLER);
    checkMethod(methods.get(4), "collargol", 1, "void", MethodKind.OTHER);
    for (int i = 0;i < 5;i++) {
      assertEquals(set(
          TypeReflectionFactory.create(InterfaceWithGenericMethodOverride.class),
          TypeReflectionFactory.create(GenericAbstractInterface.class)
      ), methods.get(i).getOwnerTypes());
    }
    checkParam(methods.get(2).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<String>>>() {
    });
    checkParam(methods.get(3).getParams().get(0), "handler", new TypeLiteral<Handler<String>>() {
    });
    checkParam(methods.get(4).getParams().get(0), "t", String.class);
  }

  @Test
  public void testInterfaceExtendingGenericAbstractInterface() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtendingGenericAbstractInterface.class, GenericAbstractInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(5, methods.size());
    checkMethod(methods.get(0), "foo", 0, String.class, MethodKind.OTHER);
    checkMethod(methods.get(1), "bar", 0, new TypeLiteral<List<String>>(){}, MethodKind.OTHER);
    checkMethod(methods.get(2), "juu", 1, "void", MethodKind.FUTURE);
    checkMethod(methods.get(3), "daa", 1, "void", MethodKind.HANDLER);
    checkMethod(methods.get(4), "collargol", 1, "void", MethodKind.OTHER);
    checkParam(methods.get(2).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<String>>>() {});
    checkParam(methods.get(3).getParams().get(0), "handler", new TypeLiteral<Handler<String>>() {});
    checkParam(methods.get(4).getParams().get(0), "t", String.class);
  }

  @Test
  public <R> void testInterfaceExtendingGenericInterface() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithParameterizedDeclaredSupertype.class, GenericInterface.class);
    List<TypeInfo> superTypeArgs = model.getSuperTypeArguments();
    assertEquals(1, superTypeArgs.size());
    ClassTypeInfo superTypeArg = (ClassTypeInfo) superTypeArgs.get(0);
    assertEquals(ClassKind.STRING, superTypeArg.getKind());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithClassTypeParam", 3, String.class, MethodKind.OTHER);
    checkParam(methods.get(0).getParam(0), "t", String.class);
    checkParam(methods.get(0).getParam(1), "handler", new TypeLiteral<Handler<String>>() {});
    checkParam(methods.get(0).getParam(2), "asyncResultHandler", new TypeLiteral<Handler<AsyncResult<String>>>() {});
  }

  @Test
  public void testInterfaceWithTypeVariableArgument() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithTypeVariableArgument3.class, InterfaceWithTypeVariableArgument2.class, InterfaceWithTypeVariableArgument1.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", 0, InterfaceWithTypeVariableArgument3.class, MethodKind.OTHER);
    assertEquals(set(
        TypeReflectionFactory.create(InterfaceWithTypeVariableArgument1.class),
        TypeReflectionFactory.create(InterfaceWithTypeVariableArgument2.class)
    ), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodWithSameSignatureInheritedFromDistinctInterfaces() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithSameSignatureInheritedFromDistinctInterfaces.class, SameSignatureMethod1.class, SameSignatureMethod2.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", 0, "U", MethodKind.OTHER);
    assertEquals(set(TypeReflectionFactory.create(SameSignatureMethod1.class), TypeReflectionFactory.create(SameSignatureMethod2.class)), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodWithDiamond() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithDiamond.class, DiamondMethod1.class, DiamondMethod2.class, DiamondMethod3.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", 0, "U", MethodKind.OTHER);
    assertEquals(set(TypeReflectionFactory.create(DiamondMethod1.class), TypeReflectionFactory.create(DiamondMethod2.class), TypeReflectionFactory.create(DiamondMethod3.class)), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodComments() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    Doc comment1 = new Doc(" Comment 1 line 1\n Comment 1 line 2", null,
        Arrays.asList(new Tag("param", "str the_string"), new Tag("return", "the_return_value\n")));
    Doc comment2 = new Doc(" Comment 2 line 1\n Comment 2 line 2\n");
    checkMethod(methods.get(0), "foo", 1, String.class, MethodKind.OTHER, comment1);
    assertEquals("str", methods.get(0).getParams().get(0).getName());
    assertEquals("the_string", methods.get(0).getParams().get(0).getDescription().toString());
    checkMethod(methods.get(1), "bar", 1, "void", MethodKind.OTHER, comment2);
  }

  @Test
  public void testInterfaceComments() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    String firstSentence =
      " Interface comment line 1\n" +
      " Interface comment line 2\n" +
      " Interface comment line 3";
    assertEquals(firstSentence, model.getDoc().getFirstSentence().getValue());
  }

  @Test
  public void testSignature() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidBasicParams.class);
    assertEquals(1, model.getMethods().size());
    MethodInfo mi = model.getMethods().get(0);
    Signature s1 = mi.getSignature();
    Signature s2 = mi.getSignature();
    assertNotSame(s1, s2);
    assertEquals(s1, s2);
    assertEquals(s1.hashCode(), s2.hashCode());
    assertEquals("methodWithBasicParams", s1.getName());
    assertEquals(9, s1.getParams().size());
    s1.getParams().remove(8);
    assertEquals(8, s1.getParams().size());
    assertEquals(9, mi.getParams().size());
  }

  @Test
  public void testOverloadedMethodFuture() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithOverloadedFutureMethod.class);
    assertEquals(4, model.getMethods().size());
    assertEquals(2, model.getMethodMap().size());
    List<MethodInfo> closes = model.getMethodMap().get("close");
    assertEquals(0, closes.get(0).getParams().size());
    assertEquals(1, closes.get(1).getParams().size());
    Signature closeSignature = closes.get(1).getSignature();
    closeSignature.getParams().remove(0);
    assertEquals(closes.get(0).getSignature(), closeSignature);
    List<MethodInfo> foos = model.getMethodMap().get("foo");
    assertEquals(1, foos.get(0).getParams().size());
    assertEquals(2, foos.get(1).getParams().size());
    Signature fooSignature = foos.get(1).getSignature();
    fooSignature.getParams().remove(1);
    assertEquals(foos.get(0).getSignature(), fooSignature);
  }

  @Test
  public void testOverloadedFromParent() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceWithMethodOverloadedFromParent.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", 1, "void", MethodKind.OTHER);
    checkParam(methods.get(0).getParam(0), "str", String.class);
    methods = model.getMethodMap().get("foo");
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", 0, "void", MethodKind.OTHER);
    checkMethod(methods.get(1), "foo", 1, "void", MethodKind.OTHER);
  }

  @Test
  public void testOverloadedInstanceAndStaticMethod() throws Exception {
    assertGenInvalid(InterfaceWithOverloadedInstanceAndStaticMethod.class);
  }

  @Test
  public void testJsonParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidJSONParams.class);
    assertEquals(MethodWithValidJSONParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidJSONParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithJsonParams", 2, "void", MethodKind.OTHER);
    checkParam(model.getMethods().get(0).getParams().get(0), "jsonObject", JsonObject.class);
    checkParam(model.getMethods().get(0).getParams().get(1), "jsonArray", JsonArray.class);
  }

  @Test
  public void testJsonHandlers() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerJSON.class);
    assertEquals(MethodWithValidHandlerJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithJsonHandlers", 2, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(0).getParams().get(0), "jsonObjectHandler", new TypeLiteral<Handler<JsonObject>>(){});
    checkParam(model.getMethods().get(0).getParams().get(1), "jsonArrayHandler", new TypeLiteral<Handler<JsonArray>>() {
    });
  }

  @Test
  public void testJsonAsyncResultHandlers() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerAsyncResultJSON.class);
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodwithJsonHandlersAsyncResult", 2, "void", MethodKind.FUTURE);
    checkMethod(model.getMethods().get(0), "methodwithJsonHandlersAsyncResult", 2, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(0).getParams().get(0), "jsonObjectHandler", new TypeLiteral<Handler<AsyncResult<JsonObject>>>() {
    });
    checkParam(model.getMethods().get(0).getParams().get(1), "jsonArrayHandler", new TypeLiteral<Handler<AsyncResult<JsonArray>>>() {
    });
  }

  @Test
  public void testJsonReturns() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidJSONReturn.class);
    assertEquals(MethodWithValidJSONReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidJSONReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "foo", 0, JsonObject.class, MethodKind.OTHER);
    checkMethod(model.getMethods().get(1), "bar", 0, JsonArray.class, MethodKind.OTHER);
  }

  @Test
  public void testMethodHandlerParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithHandlerParam.class);
    checkMethod(model.getMethods().get(0), "foo_1", 1, "void", MethodKind.HANDLER);
    checkMethod(model.getMethods().get(1), "foo_2", 2, "void", MethodKind.HANDLER);
    checkMethod(model.getMethods().get(2), "foo_3", 2, "void", MethodKind.HANDLER);
    checkMethod(model.getMethods().get(3), "foo_4", 1, MethodWithHandlerParam.class, MethodKind.HANDLER, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(4), "foo_5", 2, MethodWithHandlerParam.class, MethodKind.HANDLER, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(5), "foo_6", 2, MethodWithHandlerParam.class, MethodKind.HANDLER, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(6), "foo_7", 1, String.class, MethodKind.OTHER);
    checkMethod(model.getMethods().get(7), "foo_8", 2, "void", MethodKind.OTHER);
  }

  @Test
  public void testMethodHandlerAsyncResultParam() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithHandlerAsyncResultParam.class);
    checkMethod(model.getMethods().get(0), "foo_1", 1, "void", MethodKind.FUTURE);
    checkMethod(model.getMethods().get(1), "foo_2", 2, "void", MethodKind.FUTURE);
    checkMethod(model.getMethods().get(2), "foo_3", 2, "void", MethodKind.FUTURE);
    checkMethod(model.getMethods().get(3), "foo_4", 1, MethodWithHandlerAsyncResultParam.class, MethodKind.FUTURE, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(4), "foo_5", 2, MethodWithHandlerAsyncResultParam.class, MethodKind.FUTURE, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(5), "foo_6", 2, MethodWithHandlerAsyncResultParam.class, MethodKind.FUTURE, MethodCheck.FLUENT);
    checkMethod(model.getMethods().get(6), "foo_7", 1, String.class.getName(), MethodKind.OTHER);
    checkMethod(model.getMethods().get(7), "foo_8", 2, "void", MethodKind.OTHER);
  }

  @Test
  public <T> void testValidTypeParamByInterfaceReturn() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidTypeParamByInterfaceReturn.class);
    assertEquals(MethodWithValidTypeParamByInterfaceReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidTypeParamByInterfaceReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(model.getReferencedTypes(), set(GenericInterfaceInfo, VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(18, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "withByte", 0, new TypeLiteral<GenericInterface<Byte>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(1), "withShort", 0, new TypeLiteral<GenericInterface<Short>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(2), "withInteger", 0, new TypeLiteral<GenericInterface<Integer>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(3), "withLong", 0, new TypeLiteral<GenericInterface<Long>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(4), "withFloat", 0, new TypeLiteral<GenericInterface<Float>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(5), "withDouble", 0, new TypeLiteral<GenericInterface<Double>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(6), "withBoolean", 0, new TypeLiteral<GenericInterface<Boolean>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(7), "withCharacter", 0, new TypeLiteral<GenericInterface<Character>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(8), "withString", 0, new TypeLiteral<GenericInterface<String>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(9), "withJsonObject", 0, new TypeLiteral<GenericInterface<JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(10), "withJsonArray", 0, new TypeLiteral<GenericInterface<JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(11), "withDataObject", 0, new TypeLiteral<GenericInterface<PlainDataObjectWithToJson>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(12), "withEnum", 0, new TypeLiteral<GenericInterface<TestEnum>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(13), "withGenEnum", 0, new TypeLiteral<GenericInterface<TestGenEnum>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(14), "withUserType", 0, new TypeLiteral<GenericInterface<VertxGenClass1>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(15), "withVoid", 0, new TypeLiteral<GenericInterface<Void>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(16), "withGeneric", 0, new TypeLiteral<GenericInterface<T>>() {}, MethodKind.OTHER);
    checkMethod(model.getMethods().get(17), "withClassType", 1, new TypeLiteral<GenericInterface<T>>() {}, MethodKind.OTHER);
    checkParam(model.getMethods().get(17).getParams().get(0), "classType", new TypeLiteral<Class<T>>(){});
  }

  @Test
  public <T> void testValidHandlerTypeParamByInterface() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerTypeParamByInterface.class);
    assertEquals(MethodWithValidHandlerTypeParamByInterface.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerTypeParamByInterface.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(model.getReferencedTypes(), set(GenericInterfaceInfo, VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(16, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "withByte", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(0).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Byte>>>(){});
    checkMethod(model.getMethods().get(1), "withShort", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(1).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Short>>>(){});
    checkMethod(model.getMethods().get(2), "withInteger", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(2).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Integer>>>(){});
    checkMethod(model.getMethods().get(3), "withLong", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(3).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Long>>>(){});
    checkMethod(model.getMethods().get(4), "withFloat", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(4).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Float>>>(){});
    checkMethod(model.getMethods().get(5), "withDouble", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(5).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Double>>>(){});
    checkMethod(model.getMethods().get(6), "withBoolean", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(6).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Boolean>>>(){});
    checkMethod(model.getMethods().get(7), "withCharacter", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(7).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<Character>>>(){});
    checkMethod(model.getMethods().get(8), "withString", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(8).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<String>>>(){});
    checkMethod(model.getMethods().get(9), "withJsonObject", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(9).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<JsonObject>>>(){});
    checkMethod(model.getMethods().get(10), "withJsonArray", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(10).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<JsonArray>>>(){});
    checkMethod(model.getMethods().get(11), "withDataObject", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(11).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<PlainDataObjectWithToJson>>>(){});
    checkMethod(model.getMethods().get(12), "withEnum", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(12).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<TestEnum>>>(){});
    checkMethod(model.getMethods().get(13), "withGenEnum", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(13).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<TestGenEnum>>>(){});
    checkMethod(model.getMethods().get(14), "withUserType", 1, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(14).getParams().get(0), "handler", new TypeLiteral<Handler<GenericInterface<VertxGenClass1>>>(){});
    checkMethod(model.getMethods().get(15), "withClassType", 2, "void", MethodKind.HANDLER);
    checkParam(model.getMethods().get(15).getParams().get(0), "classType", new TypeLiteral<Class<T>>(){});
    checkParam(model.getMethods().get(15).getParams().get(1), "handler", new TypeLiteral<Handler<GenericInterface<T>>>(){});
  }

  @Test
  public <T> void testValidHandlerAsyncResultTypeParamByInterface() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(MethodWithValidHandlerAsyncResultTypeParamByInterface.class);
    assertEquals(MethodWithValidHandlerAsyncResultTypeParamByInterface.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultTypeParamByInterface.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(model.getReferencedTypes(), set(GenericInterfaceInfo, VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(16, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "withByte", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(0).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Byte>>>>(){});
    checkMethod(model.getMethods().get(1), "withShort", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(1).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Short>>>>(){});
    checkMethod(model.getMethods().get(2), "withInteger", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(2).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Integer>>>>(){});
    checkMethod(model.getMethods().get(3), "withLong", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(3).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Long>>>>(){});
    checkMethod(model.getMethods().get(4), "withFloat", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(4).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Float>>>>(){});
    checkMethod(model.getMethods().get(5), "withDouble", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(5).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Double>>>>(){});
    checkMethod(model.getMethods().get(6), "withBoolean", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(6).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Boolean>>>>(){});
    checkMethod(model.getMethods().get(7), "withCharacter", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(7).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<Character>>>>(){});
    checkMethod(model.getMethods().get(8), "withString", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(8).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<String>>>>(){});
    checkMethod(model.getMethods().get(9), "withJsonObject", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(9).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<JsonObject>>>>(){});
    checkMethod(model.getMethods().get(10), "withJsonArray", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(10).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<JsonArray>>>>(){});
    checkMethod(model.getMethods().get(11), "withDataObject", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(11).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<PlainDataObjectWithToJson>>>>(){});
    checkMethod(model.getMethods().get(12), "withEnum", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(12).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<TestEnum>>>>(){});
    checkMethod(model.getMethods().get(13), "withGenEnum", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(13).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<TestGenEnum>>>>(){});
    checkMethod(model.getMethods().get(14), "withUserType", 1, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(14).getParams().get(0), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<VertxGenClass1>>>>(){});
    checkMethod(model.getMethods().get(15), "withClassType", 2, "void", MethodKind.FUTURE);
    checkParam(model.getMethods().get(15).getParams().get(0), "classType", new TypeLiteral<Class<T>>(){});
    checkParam(model.getMethods().get(15).getParams().get(1), "handler", new TypeLiteral<Handler<AsyncResult<GenericInterface<T>>>>(){});
  }

  @Test
  public void testMethodInvalidMapReturn1() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapReturn2() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapReturn3() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn3.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapReturn4() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn4.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapReturn5() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn5.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidListReturn1() throws Exception {
    assertGenFail(MethodWithInvalidListReturn1.class, "Invalid Map return should fail");
  }

  @Test
  public void testInterfaceExtendingReadStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtentingReadStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getReadStreamArg());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingReadStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(GenericInterfaceExtentingReadStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    TypeVariableInfo readStreamArg = (TypeVariableInfo) apiType.getReadStreamArg();
    assertEquals("U", readStreamArg.getName());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingWriteStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtentingWriteStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertFalse(apiType.isReadStream());
    assertNull(apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingWriteStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(GenericInterfaceExtentingWriteStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType().getRaw();
    assertFalse(apiType.isReadStream());
    assertNull(apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    TypeVariableInfo writeStreamArg = (TypeVariableInfo) apiType.getWriteStreamArg();
    assertEquals("U", writeStreamArg.getName());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingReadStreamAndWriteStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtentingReadStreamAndWriteStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingReadStreamAndWriteStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(GenericInterfaceExtentingReadStreamAndWriteStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    TypeVariableInfo readStreamArg = (TypeVariableInfo) apiType.getReadStreamArg();
    assertEquals("U", readStreamArg.getName());
    assertTrue(apiType.isWriteStream());
    TypeVariableInfo writeStreamArg = (TypeVariableInfo) apiType.getWriteStreamArg();
    assertEquals("U", writeStreamArg.getName());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceSubtypingReadStream() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceSubtypingReadStream.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getReadStreamArg());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testReadStreamWithParameterizedTypeArg() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(ReadStreamWithParameterizedTypeArg.class);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    ParameterizedTypeInfo readStreamArg = (ParameterizedTypeInfo) apiType.getReadStreamArg();
    assertEquals(TypeReflectionFactory.create(List.class), readStreamArg.getRaw());
    assertEquals(1, readStreamArg.getArgs().size());
    assertEquals("T", readStreamArg.getArgs().get(0).getName());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingHandlerStringSubtype() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtendingHandlerStringSubtype.class);
    TypeInfo handlerSuperType = model.getHandlerType();
    assertEquals(TypeReflectionFactory.create(String.class), handlerSuperType);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertTrue(apiType.isHandler());
    assertEquals(TypeReflectionFactory.create(String.class), apiType.getHandlerArg());
    assertFalse(apiType.isReadStream());
    assertFalse(apiType.isWriteStream());
    assertEquals(1, model.getMethodMap().size());
    assertEquals(1, model.getMethodMap().get("handle").size());
    checkMethod(model.getMethodMap().get("handle").get(0), "handle", 1, "void", MethodKind.OTHER);
  }

  @Test
  public void testInterfaceExtendingHandlerVertxGenSubtype() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(InterfaceExtendingHandlerVertxGenSubtype.class, VertxGenClass1.class);
    TypeInfo handlerSuperType = model.getHandlerType();
    assertEquals(TypeReflectionFactory.create(VertxGenClass1.class), handlerSuperType);
    ApiTypeInfo apiType = (ApiTypeInfo) model.getType();
    assertTrue(apiType.isHandler());
    assertEquals(TypeReflectionFactory.create(VertxGenClass1.class), apiType.getHandlerArg());
    assertFalse(apiType.isReadStream());
    assertFalse(apiType.isWriteStream());
    assertEquals(1, model.getMethodMap().size());
    assertEquals(1, model.getMethodMap().get("handle").size());
    checkMethod(model.getMethodMap().get("handle").get(0), "handle", 1, "void", MethodKind.OTHER);
  }

  @Test
  public void testRecursiveFuture() throws Exception {
    // Check we can build this type
    ClassModel model = new GeneratorHelper().generateClass(RecursiveFuture.class);
    assertNull(model.getHandlerType());
  }

  @Test
  public void testFutureLike() throws Exception {
    // Check we can build this type
    ClassModel model = new GeneratorHelper().generateClass(FutureLike.class);
    assertNull(model.getHandlerType());
  }

  @Test
  public void testMethodInvalidListReturn2() throws Exception {
    assertGenFail(MethodWithInvalidListReturn2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidSetReturn1() throws Exception {
    assertGenFail(MethodWithInvalidSetReturn1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidSetReturn2() throws Exception {
    assertGenFail(MethodWithInvalidSetReturn2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapParams1() throws Exception {
    assertGenFail(MethodWithInvalidMapParams1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapParams2() throws Exception {
    assertGenFail(MethodWithInvalidMapParams2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidListParams1() throws Exception {
    assertGenFail(MethodWithInvalidListParams1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidListParams2() throws Exception {
    assertGenFail(MethodWithInvalidListParams2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidSetParams1() throws Exception {
    assertGenFail(MethodWithInvalidSetParams1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidSetParams2() throws Exception {
    assertGenFail(MethodWithInvalidSetParams2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidHandlerDataObjectsParam() throws Exception {
    assertGenFail(MethodWithInvalidHandlerDataObjectParam.class, "Option without toJson() in Handler param should fail");
  }

  @Test
  public void testMethodInvalidHandlerAsyncResultDataObjectsParam() throws Exception {
    assertGenFail(MethodWithInvalidHandlerAsyncResultDataObjectParam.class, "Option without toJson() in AsyncResult param should fail");
  }

  @Test
  public void testMethodInvalidThrowableParam() throws Exception {
    assertGenInvalid(MethodWithInvalidExceptionParam.class);
  }

  @Test
  public void testMethodInvalidNestedEnumParam() throws Exception {
    assertGenInvalid(MethodWithInvalidNestedEnumParam.class);
  }

  @Test
  public void testStringAnnotated() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(Annotated.class);
    assertFalse(model.getAnnotations().isEmpty());
    assertEquals(2, model.getAnnotations().size());
    assertEquals(VertxGen.class.getName(), model.getAnnotations().get(0).getName());
    assertEquals(EmptyAnnotation.class.getName(), model.getAnnotations().get(1).getName());
    assertFalse(model.getMethodAnnotations().values().isEmpty());
    assertEquals(1, model.getMethodAnnotations().get("stringAnnotated").size());
    assertEquals(3, model.getMethodAnnotations().get("stringAnnotated").get(0).getMembersNames().size());
    assertNotNull(model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("value"));
    assertNotNull(model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("array"));
    assertNotNull(model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("defaultValue"));
    assertEquals("aString", model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("value"));
    assertArrayEquals(new String[]{"one", "two"}, ((List) model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("array")).toArray());
    assertEquals("defaultString", model.getMethodAnnotations().get("stringAnnotated").get(0).getMember("defaultValue"));
  }

  @Test
  public void testImplPackage() throws Exception {
    try {
      new GeneratorHelper().generateClass(InterfaceInImplParentPackage.class);
      fail();
    } catch (IllegalArgumentException expected) {
    }
    try {
      new GeneratorHelper().generateClass(InterfaceInImplPackage.class);
      fail();
    } catch (IllegalArgumentException expected) {
    }
    ClassModel model = new GeneratorHelper().generateClass(InterfaceInImplContainingPackage.class);
    assertEquals(InterfaceInImplContainingPackage.class.getName(), model.getFqn());
  }

}
