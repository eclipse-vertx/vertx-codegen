package io.vertx.test.codegen;

import io.vertx.codegen.ClassKind;
import io.vertx.codegen.ClassModel;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.Signature;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.TypeInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServerOptions;
import io.vertx.test.codegen.testapi.AbstractInterfaceWithConcreteSuperInterface;
import io.vertx.test.codegen.testapi.AbstractInterfaceWithStaticMethod;
import io.vertx.test.codegen.testapi.CacheReturnMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.ConcreteInterfaceWithTwoConcreteSuperInterfaces;
import io.vertx.test.codegen.testapi.DiamondMethod1;
import io.vertx.test.codegen.testapi.DiamondMethod2;
import io.vertx.test.codegen.testapi.DiamondMethod3;
import io.vertx.test.codegen.testapi.GenericAbstractInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.GenericInterfaceWithUpperBound;
import io.vertx.test.codegen.testapi.InterfaceWithCacheReturnMethods;
import io.vertx.test.codegen.testapi.InterfaceWithComments;
import io.vertx.test.codegen.testapi.InterfaceWithDefaultMethod;
import io.vertx.test.codegen.testapi.InterfaceWithGetterMethods;
import io.vertx.test.codegen.testapi.InterfaceWithIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithInstanceMethods;
import io.vertx.test.codegen.testapi.InterfaceWithMethodHavingGenericOverride;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithNoMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNoNotIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNonGenSuperType;
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
import io.vertx.test.codegen.testapi.MethodWithDiamond;
import io.vertx.test.codegen.testapi.MethodWithEnumParam;
import io.vertx.test.codegen.testapi.MethodWithEnumReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerAsyncResultParam;
import io.vertx.test.codegen.testapi.MethodWithHandlerAsyncResultReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerNonVertxGenReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidHandlerAsyncResultDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidHandlerDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithInvalidListParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidListParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidListReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidListReturn2;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidMapReturn2;
import io.vertx.test.codegen.testapi.MethodWithInvalidParameterized;
import io.vertx.test.codegen.testapi.MethodWithInvalidParameterizedReturn;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetParams1;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetParams2;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetReturn1;
import io.vertx.test.codegen.testapi.MethodWithInvalidSetReturn2;
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
import io.vertx.test.codegen.testapi.MethodWithDataObjectParam;
import io.vertx.test.codegen.testapi.MethodWithSameSignatureInheritedFromDistinctInterfaces;
import io.vertx.test.codegen.testapi.MethodWithSetNonBasicTypeReturn;
import io.vertx.test.codegen.testapi.MethodWithThrowableReturn;
import io.vertx.test.codegen.testapi.MethodWithTypeParameter;
import io.vertx.test.codegen.testapi.MethodWithTypeParameterUpperBound;
import io.vertx.test.codegen.testapi.MethodWithValidBasicBoxedParams;
import io.vertx.test.codegen.testapi.MethodWithValidBasicParams;
import io.vertx.test.codegen.testapi.MethodWithValidBasicReturn;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerAsyncResultJSON;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerAsyncResultParams;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerJSON;
import io.vertx.test.codegen.testapi.MethodWithValidHandlerParams;
import io.vertx.test.codegen.testapi.MethodWithValidJSONParams;
import io.vertx.test.codegen.testapi.MethodWithValidJSONReturn;
import io.vertx.test.codegen.testapi.MethodWithValidListParams;
import io.vertx.test.codegen.testapi.MethodWithValidListReturn;
import io.vertx.test.codegen.testapi.MethodWithValidMapParams;
import io.vertx.test.codegen.testapi.MethodWithValidMapReturn;
import io.vertx.test.codegen.testapi.MethodWithValidSetParams;
import io.vertx.test.codegen.testapi.MethodWithValidSetReturn;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenReturn;
import io.vertx.test.codegen.testapi.MethodWithValidVoidReturn;
import io.vertx.test.codegen.testapi.MethodWithWildcardLowerBoundTypeArg;
import io.vertx.test.codegen.testapi.MethodWithWildcardTypeArg;
import io.vertx.test.codegen.testapi.MethodWithWildcardUpperBoundTypeArg;
import io.vertx.test.codegen.testapi.NestedInterface;
import io.vertx.test.codegen.testapi.NoVertxGen;
import io.vertx.test.codegen.testapi.NotInterface;
import io.vertx.test.codegen.testapi.OverloadedMethodsWithDifferentReturnType;
import io.vertx.test.codegen.testapi.SameSignatureMethod1;
import io.vertx.test.codegen.testapi.SameSignatureMethod2;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.VertxGenClass2;
import io.vertx.test.codegen.testapi.VertxGenInterface1;
import io.vertx.test.codegen.testapi.VertxGenInterface2;
import io.vertx.test.codegen.testapi.fluent.AbstractInterfaceWithFluentMethods;
import io.vertx.test.codegen.testapi.fluent.ConcreteInterfaceWithFluentMethods;
import io.vertx.test.codegen.testapi.fluent.FluentMethodOverrideWithSuperType;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithGenericReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithIllegalParameterizedReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithIllegalReturn;
import io.vertx.test.codegen.testapi.fluent.FluentMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.fluent.InterfaceWithFluentMethodOverrideFromAbstract;
import io.vertx.test.codegen.testapi.fluent.InterfaceWithFluentMethodOverrideFromConcrete;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.vertx.test.codegen.Utils.*;
import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GeneratorTest {

  static final TypeInfo.Class GenericInterfaceInfo = (TypeInfo.Class) TypeInfo.create(GenericInterface.class);
  static final TypeInfo.Class VertxGenClass1Info = (TypeInfo.Class) TypeInfo.create(VertxGenClass1.class);
  static final TypeInfo.Class VertxGenClass2Info = (TypeInfo.Class) TypeInfo.create(VertxGenClass2.class);
  static final TypeInfo.Class VertxGenInterface1Info = (TypeInfo.Class) TypeInfo.create(VertxGenInterface1.class);
  static final TypeInfo.Class VertxGenInterface2Info = (TypeInfo.Class) TypeInfo.create(VertxGenInterface2.class);

  public GeneratorTest() {
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
      new Generator().generateClass(NoVertxGen.class);
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
  public void testGenerateInterfaceWithDefaultMethod() throws Exception {
    assertGenInvalid(InterfaceWithDefaultMethod.class);
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

  // TODO Handler and Handler<AsyncResult> of List/String with non basic types

  @Test
  public void testGenerateMethodWithWildcardUpperBoundTypeArg() throws Exception {
    assertGenInvalid(MethodWithWildcardUpperBoundTypeArg.class);
  }

  @Test
  public void testGenerateMethodWithWildcardLowerBoundTypeArg() throws Exception {
    assertGenInvalid(MethodWithWildcardLowerBoundTypeArg.class);
  }

  @Test
  public void testGenerateMethodWithInvalidParameterized() throws Exception {
    assertGenInvalid(MethodWithInvalidParameterized.class);
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
  public void testGenerateMethodWithObjectReturn() throws Exception {
    assertGenInvalid(MethodWithObjectReturn.class);
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

  @Test
  public void testGenerateMethodWithReturnAsyncResultHandler() throws Exception {
    assertGenInvalid(MethodWithHandlerAsyncResultReturn.class);
  }

  @Test
  public void testGenerateMethodWithInvalidParameterizedReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidParameterizedReturn.class, VertxGenClass1.class);
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

  // Test valid stuff
  // ----------------

  // Valid params

  @Test
  public void testValidBasicParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidBasicParams.class);
    assertEquals(MethodWithValidBasicParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithBasicParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 9);
      List<ParamInfo> params = method.getParams();
      checkParam(params.get(0), "b", "byte");
      checkParam(params.get(1), "s", "short");
      checkParam(params.get(2), "i", "int");
      checkParam(params.get(3), "l", "long");
      checkParam(params.get(4), "f", "float");
      checkParam(params.get(5), "d", "double");
      checkParam(params.get(6), "bool", "boolean");
      checkParam(params.get(7), "ch", "char");
      checkClassParam(params.get(8), "str", "java.lang.String", ClassKind.STRING);
      assertEquals("java.lang.Byte", ((TypeInfo.Primitive)params.get(0).getType()).getBoxed().getName());
      assertEquals("java.lang.Short", ((TypeInfo.Primitive)params.get(1).getType()).getBoxed().getName());
      assertEquals("java.lang.Integer", ((TypeInfo.Primitive)params.get(2).getType()).getBoxed().getName());
      assertEquals("java.lang.Long", ((TypeInfo.Primitive)params.get(3).getType()).getBoxed().getName());
      assertEquals("java.lang.Float", ((TypeInfo.Primitive)params.get(4).getType()).getBoxed().getName());
      assertEquals("java.lang.Double", ((TypeInfo.Primitive)params.get(5).getType()).getBoxed().getName());
      assertEquals("java.lang.Boolean", ((TypeInfo.Primitive)params.get(6).getType()).getBoxed().getName());
      assertEquals("java.lang.Character", ((TypeInfo.Primitive)params.get(7).getType()).getBoxed().getName());
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidBasicBoxedParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidBasicBoxedParams.class);
    assertEquals(MethodWithValidBasicBoxedParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicBoxedParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithBasicParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 9);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "b", "java.lang.Byte", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(1), "s", "java.lang.Short", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(2), "i", "java.lang.Integer", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(3), "l", "java.lang.Long", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(4), "f", "java.lang.Float", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(5), "d", "java.lang.Double", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(6), "bool", "java.lang.Boolean", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(7), "ch", "java.lang.Character", ClassKind.BOXED_PRIMITIVE);
      checkClassParam(params.get(8), "str", "java.lang.String", ClassKind.STRING);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidTypeParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithTypeParameter.class);
    assertEquals(1, model.getMethods().size());
    MethodInfo mi = model.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(Arrays.asList("T"), mi.getTypeParams().stream().map(TypeParamInfo::getName).collect(Collectors.toList()));
  }

  @Test
  public void testValidWildcardTypeArg() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithWildcardTypeArg.class);
    assertEquals(1, model.getMethods().size());
    MethodInfo mi = model.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(new TypeInfo.Parameterized(new TypeInfo.Class(ClassKind.API, GenericInterface.class.getName(), null, false), Arrays.asList(new TypeInfo.Wildcard())), mi.getParams().get(0).getType());
    TypeInfo.Parameterized genericType = (TypeInfo.Parameterized) mi.getParams().get(0).getType();
    TypeInfo.Wildcard wildcard = (TypeInfo.Wildcard) genericType.getArgs().get(0);
    assertEquals(ClassKind.OBJECT, wildcard.getKind());
  }

  @Test
  public void testValidListParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidListParams.class);
    assertEquals(MethodWithValidListParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidListParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithListParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 6);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "listString", "java.util.List<java.lang.String>", ClassKind.LIST);
      checkClassParam(params.get(1), "listLong", "java.util.List<java.lang.Long>", ClassKind.LIST);
      checkClassParam(params.get(2), "listJsonObject", "java.util.List<io.vertx.core.json.JsonObject>", ClassKind.LIST);
      checkClassParam(params.get(3), "listJsonArray", "java.util.List<io.vertx.core.json.JsonArray>", ClassKind.LIST);
      checkClassParam(params.get(4), "listVertxGen", "java.util.List<" + VertxGenClass1.class.getName() + ">", ClassKind.LIST);
      checkClassParam(params.get(5), "listDataObject", "java.util.List<" + TestDataObject.class.getName() + ">", ClassKind.LIST);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidSetParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidSetParams.class);
    assertEquals(MethodWithValidSetParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidSetParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithSetParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 6);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "setString", "java.util.Set<java.lang.String>", ClassKind.SET);
      checkClassParam(params.get(1), "setLong", "java.util.Set<java.lang.Long>", ClassKind.SET);
      checkClassParam(params.get(2), "setJsonObject", "java.util.Set<io.vertx.core.json.JsonObject>", ClassKind.SET);
      checkClassParam(params.get(3), "setJsonArray", "java.util.Set<io.vertx.core.json.JsonArray>", ClassKind.SET);
      checkClassParam(params.get(4), "setVertxGen", "java.util.Set<" + VertxGenClass1.class.getName() + ">", ClassKind.SET);
      checkClassParam(params.get(5), "setDataObject", "java.util.Set<" + TestDataObject.class.getName() + ">", ClassKind.SET);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidMapParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidMapParams.class);
    assertEquals(MethodWithValidMapParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidMapParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithMapParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 5);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "mapString", "java.util.Map<java.lang.String,java.lang.String>", ClassKind.MAP);
      checkClassParam(params.get(1), "mapLong", "java.util.Map<java.lang.String,java.lang.Long>", ClassKind.MAP);
      checkClassParam(params.get(2), "mapJsonObject", "java.util.Map<java.lang.String,io.vertx.core.json.JsonObject>", ClassKind.MAP);
      checkClassParam(params.get(3), "mapJsonArray", "java.util.Map<java.lang.String,io.vertx.core.json.JsonArray>", ClassKind.MAP);
      checkClassParam(params.get(4), "mapVertxGen", "java.util.Map<java.lang.String," + VertxGenClass1.class.getName() + ">", ClassKind.MAP);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidHandlerParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerParams.class);
    assertEquals(MethodWithValidHandlerParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithHandlerParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.HANDLER, "void", false, false, false, 38);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "byteHandler", "io.vertx.core.Handler<java.lang.Byte>", ClassKind.HANDLER);
      checkClassParam(params.get(1), "shortHandler", "io.vertx.core.Handler<java.lang.Short>", ClassKind.HANDLER);
      checkClassParam(params.get(2), "intHandler", "io.vertx.core.Handler<java.lang.Integer>", ClassKind.HANDLER);
      checkClassParam(params.get(3), "longHandler", "io.vertx.core.Handler<java.lang.Long>", ClassKind.HANDLER);
      checkClassParam(params.get(4), "floatHandler", "io.vertx.core.Handler<java.lang.Float>", ClassKind.HANDLER);
      checkClassParam(params.get(5), "doubleHandler", "io.vertx.core.Handler<java.lang.Double>", ClassKind.HANDLER);
      checkClassParam(params.get(6), "booleanHandler", "io.vertx.core.Handler<java.lang.Boolean>", ClassKind.HANDLER);
      checkClassParam(params.get(7), "charHandler", "io.vertx.core.Handler<java.lang.Character>", ClassKind.HANDLER);
      checkClassParam(params.get(8), "strHandler", "io.vertx.core.Handler<java.lang.String>", ClassKind.HANDLER);
      checkClassParam(params.get(9), "gen1Handler", "io.vertx.core.Handler<" + VertxGenClass1Info + ">", ClassKind.HANDLER);
      checkClassParam(params.get(10), "gen2Handler", "io.vertx.core.Handler<" + VertxGenClass2.class.getName() + ">", ClassKind.HANDLER);
      checkClassParam(params.get(11), "listByteHandler", "io.vertx.core.Handler<java.util.List<java.lang.Byte>>", ClassKind.HANDLER);
      checkClassParam(params.get(12), "listShortHandler", "io.vertx.core.Handler<java.util.List<java.lang.Short>>", ClassKind.HANDLER);
      checkClassParam(params.get(13), "listIntHandler", "io.vertx.core.Handler<java.util.List<java.lang.Integer>>", ClassKind.HANDLER);
      checkClassParam(params.get(14), "listLongHandler", "io.vertx.core.Handler<java.util.List<java.lang.Long>>", ClassKind.HANDLER);
      checkClassParam(params.get(15), "listFloatHandler", "io.vertx.core.Handler<java.util.List<java.lang.Float>>", ClassKind.HANDLER);
      checkClassParam(params.get(16), "listDoubleHandler", "io.vertx.core.Handler<java.util.List<java.lang.Double>>", ClassKind.HANDLER);
      checkClassParam(params.get(17), "listBooleanHandler", "io.vertx.core.Handler<java.util.List<java.lang.Boolean>>", ClassKind.HANDLER);
      checkClassParam(params.get(18), "listCharHandler", "io.vertx.core.Handler<java.util.List<java.lang.Character>>", ClassKind.HANDLER);
      checkClassParam(params.get(19), "listStrHandler", "io.vertx.core.Handler<java.util.List<java.lang.String>>", ClassKind.HANDLER);
      checkClassParam(params.get(20), "listVertxGenHandler", "io.vertx.core.Handler<java.util.List<" + VertxGenClass1Info + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(21), "listJsonObjectHandler", "io.vertx.core.Handler<java.util.List<" + JsonObject.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(22), "listJsonArrayHandler", "io.vertx.core.Handler<java.util.List<" + JsonArray.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(23), "setByteHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Byte>>", ClassKind.HANDLER);
      checkClassParam(params.get(24), "setShortHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Short>>", ClassKind.HANDLER);
      checkClassParam(params.get(25), "setIntHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Integer>>", ClassKind.HANDLER);
      checkClassParam(params.get(26), "setLongHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Long>>", ClassKind.HANDLER);
      checkClassParam(params.get(27), "setFloatHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Float>>", ClassKind.HANDLER);
      checkClassParam(params.get(28), "setDoubleHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Double>>", ClassKind.HANDLER);
      checkClassParam(params.get(29), "setBooleanHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Boolean>>", ClassKind.HANDLER);
      checkClassParam(params.get(30), "setCharHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Character>>", ClassKind.HANDLER);
      checkClassParam(params.get(31), "setStrHandler", "io.vertx.core.Handler<java.util.Set<java.lang.String>>", ClassKind.HANDLER);
      checkClassParam(params.get(32), "setVertxGenHandler", "io.vertx.core.Handler<java.util.Set<" + VertxGenClass1Info + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(33), "setJsonObjectHandler", "io.vertx.core.Handler<java.util.Set<" + JsonObject.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(34), "setJsonArrayHandler", "io.vertx.core.Handler<java.util.Set<" + JsonArray.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(35), "voidHandler", "io.vertx.core.Handler<java.lang.Void>", ClassKind.HANDLER);
      checkClassParam(params.get(36), "throwableHandler", "io.vertx.core.Handler<java.lang.Throwable>", ClassKind.HANDLER);
      checkClassParam(params.get(37), "dataObjectHandler", "io.vertx.core.Handler<" + TestDataObject.class.getName() + ">", ClassKind.HANDLER);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidHandlerAsyncResultParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerAsyncResultParams.class);
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithHandlerParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.FUTURE, "void", false, false, false, 39);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "byteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Byte>>", ClassKind.HANDLER);
      checkClassParam(params.get(1), "shortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Short>>", ClassKind.HANDLER);
      checkClassParam(params.get(2), "intHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>>", ClassKind.HANDLER);
      checkClassParam(params.get(3), "longHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>>", ClassKind.HANDLER);
      checkClassParam(params.get(4), "floatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Float>>", ClassKind.HANDLER);
      checkClassParam(params.get(5), "doubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Double>>", ClassKind.HANDLER);
      checkClassParam(params.get(6), "booleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Boolean>>", ClassKind.HANDLER);
      checkClassParam(params.get(7), "charHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Character>>", ClassKind.HANDLER);
      checkClassParam(params.get(8), "strHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>", ClassKind.HANDLER);
      checkClassParam(params.get(9), "gen1Handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<" + VertxGenClass1.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(10), "gen2Handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<" + VertxGenClass2.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(11), "listByteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Byte>>>", ClassKind.HANDLER);
      checkClassParam(params.get(12), "listShortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Short>>>", ClassKind.HANDLER);
      checkClassParam(params.get(13), "listIntHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Integer>>>", ClassKind.HANDLER);
      checkClassParam(params.get(14), "listLongHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Long>>>", ClassKind.HANDLER);
      checkClassParam(params.get(15), "listFloatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Float>>>", ClassKind.HANDLER);
      checkClassParam(params.get(16), "listDoubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Double>>>", ClassKind.HANDLER);
      checkClassParam(params.get(17), "listBooleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Boolean>>>", ClassKind.HANDLER);
      checkClassParam(params.get(18), "listCharHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Character>>>", ClassKind.HANDLER);
      checkClassParam(params.get(19), "listStrHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.String>>>", ClassKind.HANDLER);
      checkClassParam(params.get(20), "listVertxGenHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<" + VertxGenClass1.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(21), "listJsonObjectHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<" + JsonObject.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(22), "listJsonArrayHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<" + JsonArray.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(23), "setByteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Byte>>>", ClassKind.HANDLER);
      checkClassParam(params.get(24), "setShortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Short>>>", ClassKind.HANDLER);
      checkClassParam(params.get(25), "setIntHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Integer>>>", ClassKind.HANDLER);
      checkClassParam(params.get(26), "setLongHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Long>>>", ClassKind.HANDLER);
      checkClassParam(params.get(27), "setFloatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Float>>>", ClassKind.HANDLER);
      checkClassParam(params.get(28), "setDoubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Double>>>", ClassKind.HANDLER);
      checkClassParam(params.get(29), "setBooleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Boolean>>>", ClassKind.HANDLER);
      checkClassParam(params.get(30), "setCharHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Character>>>", ClassKind.HANDLER);
      checkClassParam(params.get(31), "setStrHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.String>>>", ClassKind.HANDLER);
      checkClassParam(params.get(32), "setVertxGenHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<" + VertxGenClass1.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(33), "setJsonObjectHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<" + JsonObject.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(34), "setJsonArrayHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<" + JsonArray.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(35), "voidHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>>", ClassKind.HANDLER);
      checkClassParam(params.get(36), "dataObjectHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<" + TestDataObject.class.getName() + ">>", ClassKind.HANDLER);
      checkClassParam(params.get(37), "listDataObjectHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<" + TestDataObject.class.getName() + ">>>", ClassKind.HANDLER);
      checkClassParam(params.get(38), "setDataObjectHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<" + TestDataObject.class.getName() + ">>>", ClassKind.HANDLER);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidVertxGenParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidVertxGenParams.class);
    assertEquals(MethodWithValidVertxGenParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithVertxGenParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 3);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "str", "java.lang.String", ClassKind.STRING);
      checkClassParam(params.get(1), "myParam1", VertxGenClass1.class.getName(), ClassKind.API);
      checkClassParam(params.get(2), "myParam2", VertxGenClass2.class.getName(), ClassKind.API);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidObjectParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithObjectParam.class);
    assertEquals(MethodWithObjectParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithObjectParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithObjectParam";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "obj", "java.lang.Object", ClassKind.OBJECT);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidEnumParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithEnumParam.class);
    assertEquals(MethodWithEnumParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithEnumParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithEnumParam";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "weirdo", TestEnum.class.getName(), ClassKind.ENUM);
      TypeInfo.Class.Enum enumType = (TypeInfo.Class.Enum) params.get(0).getType();
      assertEquals(Arrays.asList("TIM", "JULIEN", "NICK", "WESTON"), enumType.getValues());
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidEnumReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithEnumReturn.class);
    assertEquals(MethodWithEnumReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithEnumReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithEnumReturn";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, TestEnum.class.getName(), false, false, false, 0);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidThrowableReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithThrowableReturn.class);
    assertEquals(MethodWithThrowableReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithThrowableReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithThrowableReturn";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, Throwable.class.getName(), false, false, false, 0);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  @Test
  public void testValidDataObjectsParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithDataObjectParam.class);
    assertEquals(MethodWithDataObjectParam.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithDataObjectParam.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithDataObjectParam";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "dataObject", NetServerOptions.class.getName(), ClassKind.DATA_OBJECT);
    };

    MethodInfo method = model.getMethods().get(0);
    checker.accept(method);
  }

  // Valid returns

  @Test
  public void testGenericInterface() throws Exception {
    ClassModel model = new Generator().generateClass(GenericInterface.class);
    assertEquals(GenericInterface.class.getName() + "<T>", model.getIfaceFQCN());
    assertEquals(GenericInterface.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue("Was expecting " + model.getReferencedTypes() + " to be empty", model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    TypeInfo.Variable t = (TypeInfo.Variable) ((TypeInfo.Parameterized) model.getType()).getArgs().get(0);
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "methodWithClassTypeParam", null, MethodKind.OTHER, "T", false, false, false, 3);
      List<ParamInfo> params = methods.get(0).getParams();
      checkClassParam(params.get(0), "t", "T", ClassKind.OBJECT);
      assertTrue(params.get(0).getType() instanceof TypeInfo.Variable);
      assertEquals(t, params.get(0).getType());
      checkClassParam(params.get(1), "handler", "io.vertx.core.Handler<T>", ClassKind.HANDLER);
      checkClassParam(params.get(2), "asyncResultHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<T>>", ClassKind.HANDLER);
      checkMethod(methods.get(1), "someGenericMethod", null, MethodKind.OTHER, "io.vertx.test.codegen.testapi.GenericInterface<R>", false, false, false, 3);
      params = methods.get(1).getParams();
      checkClassParam(params.get(0), "r", "R", ClassKind.OBJECT);
      assertTrue(params.get(0).getType() instanceof TypeInfo.Variable);
      assertEquals(methods.get(1).getTypeParams().get(0), ((TypeInfo.Variable) params.get(0).getType()).getParam());
      checkClassParam(params.get(1), "handler", "io.vertx.core.Handler<R>", ClassKind.HANDLER);
      checkClassParam(params.get(2), "asyncResultHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<R>>", ClassKind.HANDLER);
    };
    checker.accept(model.getMethods());
  }


  @Test
  public void testGenericInterfaceWithUpperBound() throws Exception {
    try {
      new Generator().generateClass(GenericInterfaceWithUpperBound.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testValidBasicReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidBasicReturn.class);
    assertEquals(MethodWithValidBasicReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidBasicReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(17, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "methodWithByteReturn", null, MethodKind.OTHER, "byte", false, false, false, 0);
      checkMethod(methods.get(1), "methodWithShortReturn", null, MethodKind.OTHER, "short", false, false, false, 0);
      checkMethod(methods.get(2), "methodWithIntReturn", null, MethodKind.OTHER, "int", false, false, false, 0);
      checkMethod(methods.get(3), "methodWithLongReturn", null, MethodKind.OTHER, "long", false, false, false, 0);
      checkMethod(methods.get(4), "methodWithFloatReturn", null, MethodKind.OTHER, "float", false, false, false, 0);
      checkMethod(methods.get(5), "methodWithDoubleReturn", null, MethodKind.OTHER, "double", false, false, false, 0);
      checkMethod(methods.get(6), "methodWithBooleanReturn", null, MethodKind.OTHER, "boolean", false, false, false, 0);
      checkMethod(methods.get(7), "methodWithCharReturn", null, MethodKind.OTHER, "char", false, false, false, 0);
      checkMethod(methods.get(8), "methodWithStringReturn", null, MethodKind.OTHER, "java.lang.String", false, false, false, 0);

      checkMethod(methods.get(9), "methodWithByteObjectReturn", null, MethodKind.OTHER, "java.lang.Byte", false, false, false, 0);
      checkMethod(methods.get(10), "methodWithShortObjectReturn", null, MethodKind.OTHER, "java.lang.Short", false, false, false, 0);
      checkMethod(methods.get(11), "methodWithIntObjectReturn", null, MethodKind.OTHER, "java.lang.Integer", false, false, false, 0);
      checkMethod(methods.get(12), "methodWithLongObjectReturn", null, MethodKind.OTHER, "java.lang.Long", false, false, false, 0);
      checkMethod(methods.get(13), "methodWithFloatObjectReturn", null, MethodKind.OTHER, "java.lang.Float", false, false, false, 0);
      checkMethod(methods.get(14), "methodWithDoubleObjectReturn", null, MethodKind.OTHER, "java.lang.Double", false, false, false, 0);
      checkMethod(methods.get(15), "methodWithBooleanObjectReturn", null, MethodKind.OTHER, "java.lang.Boolean", false, false, false, 0);
      checkMethod(methods.get(16), "methodWithCharObjectReturn", null, MethodKind.OTHER, "java.lang.Character", false, false, false, 0);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testValidVoidReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidVoidReturn.class);
    assertEquals(MethodWithValidVoidReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVoidReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "methodWithVoidReturn";
    checkMethod(model.getMethods().get(0), methodName, null, MethodKind.OTHER, "void", false, false, false, 0);
  }

  @Test
  public void testValidListReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidListReturn.class);
    assertEquals(MethodWithValidListReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidListReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(13, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "byteList", null, MethodKind.OTHER, "java.util.List<java.lang.Byte>", false, false, false, 0);
      checkMethod(methods.get(1), "shortList", null, MethodKind.OTHER, "java.util.List<java.lang.Short>", false, false, false, 0);
      checkMethod(methods.get(2), "intList", null, MethodKind.OTHER, "java.util.List<java.lang.Integer>", false, false, false, 0);
      checkMethod(methods.get(3), "longList", null, MethodKind.OTHER, "java.util.List<java.lang.Long>", false, false, false, 0);
      checkMethod(methods.get(4), "floatList", null, MethodKind.OTHER, "java.util.List<java.lang.Float>", false, false, false, 0);
      checkMethod(methods.get(5), "doubleList", null, MethodKind.OTHER, "java.util.List<java.lang.Double>", false, false, false, 0);
      checkMethod(methods.get(6), "booleanList", null, MethodKind.OTHER, "java.util.List<java.lang.Boolean>", false, false, false, 0);
      checkMethod(methods.get(7), "charList", null, MethodKind.OTHER, "java.util.List<java.lang.Character>", false, false, false, 0);
      checkMethod(methods.get(8), "stringList", null, MethodKind.OTHER, "java.util.List<java.lang.String>", false, false, false, 0);
      checkMethod(methods.get(9), "vertxGen1List", null, MethodKind.OTHER, "java.util.List<" + VertxGenClass1Info + ">", false, false, false, 0);
      checkMethod(methods.get(10), "vertxGen2List", null, MethodKind.OTHER, "java.util.List<" + VertxGenClass2.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(11), "jsonArrayList", null, MethodKind.OTHER, "java.util.List<" + JsonArray.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(12), "jsonObjectList", null, MethodKind.OTHER, "java.util.List<" + JsonObject.class.getName() + ">", false, false, false, 0);
    };
    checker.accept(model.getMethods());
  }
  
  @Test
  public void testValidSetReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidSetReturn.class);
    assertEquals(MethodWithValidSetReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidSetReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(13, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "byteSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Byte>", false, false, false, 0);
      checkMethod(methods.get(1), "shortSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Short>", false, false, false, 0);
      checkMethod(methods.get(2), "intSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Integer>", false, false, false, 0);
      checkMethod(methods.get(3), "longSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Long>", false, false, false, 0);
      checkMethod(methods.get(4), "floatSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Float>", false, false, false, 0);
      checkMethod(methods.get(5), "doubleSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Double>", false, false, false, 0);
      checkMethod(methods.get(6), "booleanSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Boolean>", false, false, false, 0);
      checkMethod(methods.get(7), "charSet", null, MethodKind.OTHER, "java.util.Set<java.lang.Character>", false, false, false, 0);
      checkMethod(methods.get(8), "stringSet", null, MethodKind.OTHER, "java.util.Set<java.lang.String>", false, false, false, 0);
      checkMethod(methods.get(9), "vertxGen1Set", null, MethodKind.OTHER, "java.util.Set<" + VertxGenClass1.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(10), "vertxGen2Set", null, MethodKind.OTHER, "java.util.Set<" + VertxGenClass2.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(11), "jsonArraySet", null, MethodKind.OTHER, "java.util.Set<" + JsonArray.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(12), "jsonObjectSet", null, MethodKind.OTHER, "java.util.Set<" + JsonObject.class.getName() + ">", false, false, false, 0);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testValidMapReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidMapReturn.class);
    assertEquals(MethodWithValidMapReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidMapReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(0, model.getReferencedTypes().size());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(11, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "byteMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Byte>", false, false, false, 0);
      checkMethod(methods.get(1), "shortMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Short>", false, false, false, 0);
      checkMethod(methods.get(2), "intMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Integer>", false, false, false, 0);
      checkMethod(methods.get(3), "longMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Long>", false, false, false, 0);
      checkMethod(methods.get(4), "floatMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Float>", false, false, false, 0);
      checkMethod(methods.get(5), "doubleMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Double>", false, false, false, 0);
      checkMethod(methods.get(6), "booleanMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Boolean>", false, false, false, 0);
      checkMethod(methods.get(7), "charMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.Character>", false, false, false, 0);
      checkMethod(methods.get(8), "stringMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String,java.lang.String>", false, false, false, 0);
      checkMethod(methods.get(9), "jsonArrayMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String," + JsonArray.class.getName() + ">", false, false, false, 0);
      checkMethod(methods.get(10), "jsonObjectMap", null, MethodKind.OTHER, "java.util.Map<java.lang.String," + JsonObject.class.getName() + ">", false, false, false, 0);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testValidVertxGenReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidVertxGenReturn.class);
    assertEquals(MethodWithValidVertxGenReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "methodWithVertxGen1Return", null, MethodKind.OTHER, VertxGenClass1.class.getName(), false, false, false, 0);
      checkMethod(methods.get(1), "methodWithVertxGen2Return", null, MethodKind.OTHER, VertxGenClass2.class.getName(), false, false, false, 0);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testGenIgnore() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithIgnoredMethods.class);
    assertEquals(InterfaceWithIgnoredMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithIgnoredMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testPropertyGetters() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithGetterMethods.class);
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "isA", null, MethodKind.GETTER, "boolean", false, false, false, 0);
      assertEquals("a", methods.get(0).getPropertyName());
      checkMethod(methods.get(1), "isAb", null, MethodKind.GETTER, "boolean", false, false, false, 0);
      assertEquals("ab", methods.get(1).getPropertyName());
      checkMethod(methods.get(2), "isABC", null, MethodKind.GETTER, "boolean", false, false, false, 0);
      assertEquals("abc", methods.get(2).getPropertyName());
      checkMethod(methods.get(3), "isABCd", null, MethodKind.GETTER, "boolean", false, false, false, 0);
      assertEquals("abCd", methods.get(3).getPropertyName());
      checkMethod(methods.get(4), "isAbCde", null, MethodKind.GETTER, "boolean", false, false, false, 0);
      assertEquals("abCde", methods.get(4).getPropertyName());

      checkMethod(methods.get(5), "getB", null, MethodKind.GETTER, "java.lang.String", false, false, false, 0);
      assertEquals("b", methods.get(5).getPropertyName());
      checkMethod(methods.get(6), "getBc", null, MethodKind.GETTER, "java.lang.String", false, false, false, 0);
      assertEquals("bc", methods.get(6).getPropertyName());
      checkMethod(methods.get(7), "getBCD", null, MethodKind.GETTER, "java.lang.String", false, false, false, 0);
      assertEquals("bcd", methods.get(7).getPropertyName());
      checkMethod(methods.get(8), "getBCDe", null, MethodKind.GETTER, "java.lang.String", false, false, false, 0);
      assertEquals("bcDe", methods.get(8).getPropertyName());
      checkMethod(methods.get(9), "getBcDef", null, MethodKind.GETTER, "java.lang.String", false, false, false, 0);
      assertEquals("bcDef", methods.get(9).getPropertyName());

      checkMethod(methods.get(10), "isC", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(10).getPropertyName());
      checkMethod(methods.get(11), "isCd", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(11).getPropertyName());
      checkMethod(methods.get(12), "isCDE", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(12).getPropertyName());
      checkMethod(methods.get(13), "isCDEf", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(13).getPropertyName());
      checkMethod(methods.get(14), "isCdEfg", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(14).getPropertyName());

      checkMethod(methods.get(15), "getD", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(15).getPropertyName());
      checkMethod(methods.get(16), "getDe", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(16).getPropertyName());
      checkMethod(methods.get(17), "getDEF", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(17).getPropertyName());
      checkMethod(methods.get(18), "getDEFg", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(18).getPropertyName());
      checkMethod(methods.get(19), "getDeFgh", null, MethodKind.OTHER, "void", false, false, false, 0);
      assertEquals(null, methods.get(19).getPropertyName());
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testFluentMethodOverrideFromConcrete() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithFluentMethodOverrideFromConcrete.class);
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(ConcreteInterfaceWithFluentMethods.class)), model.getReferencedTypes());
    assertEquals(Collections.singletonList(TypeInfo.create(ConcreteInterfaceWithFluentMethods.class)), model.getSuperTypes());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), false, true, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), false, true, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testFluentMethodOverrideFromAbstract() throws Exception {
    Generator gen = new Generator();
    ClassModel model = gen.generateClass(InterfaceWithFluentMethodOverrideFromAbstract.class);
    assertEquals(0, gen.getDiagnostics().size());
    assertEquals(InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethodOverrideFromAbstract.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(AbstractInterfaceWithFluentMethods.class)), model.getReferencedTypes());
    assertEquals(Collections.singletonList(TypeInfo.create(AbstractInterfaceWithFluentMethods.class)), model.getSuperTypes());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), false, true, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), false, true, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testFluentMethods() throws Exception {
    ClassModel model = new Generator().generateClass(ConcreteInterfaceWithFluentMethods.class);
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, ConcreteInterfaceWithFluentMethods.class.getName(), false, true, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, ConcreteInterfaceWithFluentMethods.class.getName(), false, true, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testCacheReturnMethods() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithCacheReturnMethods.class);
    assertEquals(InterfaceWithCacheReturnMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithCacheReturnMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, String.class.getName(), true, false, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, VertxGenClass1.class.getName(), true, false, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testSupertypes() throws Exception {
    ClassModel gen = new Generator().generateClass(InterfaceWithSupertypes.class, VertxGenInterface1.class, VertxGenInterface2.class);
    assertEquals(InterfaceWithSupertypes.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithSupertypes.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(3, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterface1Info));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterface2Info));
    assertEquals(3, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenClass1.class)));
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenInterface1.class)));
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenInterface2.class)));
    assertEquals(1, gen.getConcreteSuperTypes().size());
    assertTrue(gen.getConcreteSuperTypes().contains(TypeInfo.create(VertxGenClass1.class)));
    assertEquals(2, gen.getAbstractSuperTypes().size());
    assertTrue(gen.getAbstractSuperTypes().contains(TypeInfo.create(VertxGenInterface1.class)));
    assertTrue(gen.getAbstractSuperTypes().contains(TypeInfo.create(VertxGenInterface2.class)));
    List<MethodInfo> methods = gen.getMethods();
    assertEquals(3, methods.size());
    Collections.sort(methods);
    Consumer<List<MethodInfo>> checker = (m) -> {
      checkMethod(methods.get(0), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
      checkMethod(methods.get(1), "juu", null, MethodKind.OTHER, "void", false, false, false, 1);
      checkMethod(methods.get(2), "quux", null, MethodKind.OTHER, "void", false, false, false, 1);
    };
    checker.accept(methods);
  }

  @Test
  public void testParameterizedClassSuperType() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithParameterizedDeclaredSupertype.class);
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(GenericInterfaceInfo));
    assertEquals(1, model.getSuperTypes().size());
    assertTrue(model.getSuperTypes().contains(TypeInfo.create(InterfaceWithParameterizedDeclaredSupertype.class.getGenericInterfaces()[0])));
  }

  @Test
  public void testParameterizedVariableSuperType() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithParameterizedVariableSupertype.class);
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getName() + "<T>", model.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(GenericInterfaceInfo));
    assertEquals(1, model.getSuperTypes().size());
    assertTrue(model.getSuperTypes().contains(TypeInfo.create(InterfaceWithParameterizedVariableSupertype.class.getGenericInterfaces()[0])));
  }

  @Test
  public void testNonGenSuperType() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithNonGenSuperType.class);
    assertEquals(InterfaceWithNonGenSuperType.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithNonGenSuperType.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(0, model.getReferencedTypes().size());
    assertEquals(0, model.getSuperTypes().size());
    assertEquals(1, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testParameterizedForbiddenSuperType() throws Exception {
    Class<?>[] forbidenTypes = {
        InterfaceWithParameterizedArraySupertype.class,
        InterfaceWithParameterizedGenericArraySupertype.class
    };
    for (Class<?> forbidenType : forbidenTypes) {
      try {
        new Generator().generateClass(forbidenType);
        fail();
      } catch (GenException e) {
      }
    }
  }

  @Test
  public void testOverloadedMethods() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithOverloadedMethods.class);
    assertEquals(InterfaceWithOverloadedMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithOverloadedMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(8, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkClassParam(model.getMethods().get(0).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);

    checkMethod(model.getMethods().get(1), "foo", null, MethodKind.HANDLER, "void", false, false, false, 2);
    checkClassParam(model.getMethods().get(1).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkClassParam(model.getMethods().get(1).getParams().get(1), "handler", "io.vertx.core.Handler<" + VertxGenClass1Info + ">", ClassKind.HANDLER);

    checkMethod(model.getMethods().get(2), "foo", null, MethodKind.HANDLER, "void", false, false, false, 3);
    checkClassParam(model.getMethods().get(2).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkParam(model.getMethods().get(2).getParams().get(1), "time", "long");
    checkClassParam(model.getMethods().get(2).getParams().get(2), "handler", "io.vertx.core.Handler<" + VertxGenClass1Info + ">", ClassKind.HANDLER);
    checkMethod(model.getMethods().get(3), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkClassParam(model.getMethods().get(3).getParams().get(0), "obj1", VertxGenClass2.class.getName(), ClassKind.API);
    checkMethod(model.getMethods().get(4), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkClassParam(model.getMethods().get(4).getParams().get(0), "obj1", String.class.getName(), ClassKind.STRING);
    checkMethod(model.getMethods().get(5), "juu", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkClassParam(model.getMethods().get(0).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkMethod(model.getMethods().get(6), "juu", null, MethodKind.OTHER, "void", false, false, false, 2);
    checkClassParam(model.getMethods().get(6).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkParam(model.getMethods().get(6).getParams().get(1), "time", "long");
    checkMethod(model.getMethods().get(7), "juu", null, MethodKind.HANDLER, "void", false, false, false, 3);
    checkClassParam(model.getMethods().get(7).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkParam(model.getMethods().get(7).getParams().get(1), "time", "long");
    checkClassParam(model.getMethods().get(7).getParams().get(2), "handler", "io.vertx.core.Handler<T>", ClassKind.HANDLER);
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
    ClassModel model = new Generator().generateClass(InterfaceWithStaticMethods.class);
    assertEquals(InterfaceWithStaticMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithStaticMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, VertxGenClass1.class.getName(), false, false, true, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, VertxGenClass2.class.getName(), false, false, true, 1);
    };
    checker.accept(model.getMethods());
    checker.accept(model.getStaticMethods());
    assertEquals(Collections.<MethodInfo>emptyList(), model.getInstanceMethods());
  }

  @Test
  public void testStaticSuperStaticMethods() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithSuperStaticMethods.class);
    assertEquals(InterfaceWithSuperStaticMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithSuperStaticMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singletonList(TypeInfo.create(InterfaceWithStaticMethods.class)), model.getSuperTypes());
    assertEquals(0, model.getMethods().size());
  }

  @Test
  public void testInstanceMethods() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithInstanceMethods.class);
    assertEquals(InterfaceWithInstanceMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithInstanceMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(2, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getReferencedTypes().contains(VertxGenClass2Info));
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, VertxGenClass1.class.getName(), false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, VertxGenClass2.class.getName(), false, false, false, 1);
    };
    checker.accept(model.getMethods());
    checker.accept(model.getInstanceMethods());
    assertEquals(Collections.<MethodInfo>emptyList(), model.getStaticMethods());
  }

  @Test
  public void testMethodOverride() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithMethodOverride.class, VertxGenInterface1.class, VertxGenInterface2.class);
    assertEquals(2, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
      assertEquals(set(
          TypeInfo.create(InterfaceWithMethodOverride.class),
          TypeInfo.create(VertxGenInterface1.class)
      ), methods.get(0).getOwnerTypes());
      checkMethod(methods.get(1), "juu", null, MethodKind.OTHER, "void", false, false, false, 1);
      assertEquals(set(
          TypeInfo.create(InterfaceWithMethodOverride.class),
          TypeInfo.create(VertxGenInterface2.class)
      ), methods.get(1).getOwnerTypes());
    };
    checker.accept(model.getMethods());
    checkClassParam(model.getMethods().get(0).getParams().get(0), "str", String.class.getName(), ClassKind.STRING);
    checkClassParam(model.getMethods().get(1).getParams().get(0), "str_renamed", String.class.getName(), ClassKind.STRING);
  }

  @Test
  public void testInterfaceWithMethodHavingGenericOverride() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithMethodHavingGenericOverride.class, GenericAbstractInterface.class);
    assertEquals(4, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "java.lang.String", false, false, false, 0);
      checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, "java.util.List<java.lang.String>", false, false, false, 0);
      checkMethod(methods.get(2), "juu", null, MethodKind.FUTURE, "void", false, false, false, 1);
      checkMethod(methods.get(3), "daa", null, MethodKind.HANDLER, "void", false, false, false, 1);
    };
    checker.accept(model.getMethods());
    checkClassParam(model.getMethods().get(2).getParams().get(0), "handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>", ClassKind.HANDLER);
    checkClassParam(model.getMethods().get(3).getParams().get(0), "handler", "io.vertx.core.Handler<java.lang.String>", ClassKind.HANDLER);
  }

  @Test
  public void testInterfaceWithTypeVariableArgument() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithTypeVariableArgument3.class, InterfaceWithTypeVariableArgument2.class, InterfaceWithTypeVariableArgument1.class);
    assertEquals(1, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "io.vertx.test.codegen.testapi.InterfaceWithTypeVariableArgument3", false, false, false, 0);
      assertEquals(set(
          TypeInfo.create(InterfaceWithTypeVariableArgument1.class),
          TypeInfo.create(InterfaceWithTypeVariableArgument2.class)
      ), methods.get(0).getOwnerTypes());
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testMethodWithSameSignatureInheritedFromDistinctInterfaces() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithSameSignatureInheritedFromDistinctInterfaces.class, SameSignatureMethod1.class, SameSignatureMethod2.class);
    assertEquals(1, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "U", false, false, false, 0);
      assertEquals(set(TypeInfo.create(SameSignatureMethod1.class), TypeInfo.create(SameSignatureMethod2.class)), methods.get(0).getOwnerTypes());
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testMethodWithDiamond() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithDiamond.class, DiamondMethod1.class, DiamondMethod2.class, DiamondMethod3.class);
    assertEquals(1, model.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "U", false, false, false, 0);
      assertEquals(set(TypeInfo.create(DiamondMethod1.class), TypeInfo.create(DiamondMethod2.class), TypeInfo.create(DiamondMethod3.class)), methods.get(0).getOwnerTypes());
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testMethodComments() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    Doc comment1 = new Doc(" Comment 1 line 1\n Comment 1 line 2", null,
        Arrays.asList(new Tag("param", "str the_string"), new Tag("return", "the_return_value\n")));
    Doc comment2 = new Doc(" Comment 2 line 1\n Comment 2 line 2\n");
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", comment1, MethodKind.OTHER, "java.lang.String", false, false, false, 1);
      assertEquals("str", methods.get(0).getParams().get(0).getName());
      assertEquals("the_string", methods.get(0).getParams().get(0).getDescription().toString());
      checkMethod(methods.get(1), "bar", comment2, MethodKind.OTHER, "void", false, false, false, 1);
    };
    checker.accept(model.getMethods());
  }

  @Test
  public void testInterfaceComments() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithComments.class);
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
    ClassModel model = new Generator().generateClass(MethodWithValidBasicParams.class);
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
    ClassModel model = new Generator().generateClass(InterfaceWithOverloadedFutureMethod.class);
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
  public void testOverloadedInstanceAndStaticMethod() throws Exception {
    assertGenInvalid(InterfaceWithOverloadedInstanceAndStaticMethod.class);
  }

  @Test
  public void testJsonParams() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidJSONParams.class);
    assertEquals(MethodWithValidJSONParams.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidJSONParams.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "methodWithJsonParams", null, MethodKind.OTHER, "void", false, false, false, 2);
    checkClassParam(model.getMethods().get(0).getParams().get(0), "jsonObject", JsonObject.class.getName(), ClassKind.JSON_OBJECT);
    checkClassParam(model.getMethods().get(0).getParams().get(1), "jsonArray", JsonArray.class.getName(), ClassKind.JSON_ARRAY);
  }

  @Test
  public void testJsonHandlers() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerJSON.class);
    assertEquals(MethodWithValidHandlerJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "methodWithJsonHandlers", null, MethodKind.HANDLER, "void", false, false, false, 2);
    checkClassParam(model.getMethods().get(0).getParams().get(0), "jsonObjectHandler", Handler.class.getName() + "<" + JsonObject.class.getName() + ">", ClassKind.HANDLER);
    checkClassParam(model.getMethods().get(0).getParams().get(1), "jsonArrayHandler", Handler.class.getName() + "<" + JsonArray.class.getName() + ">", ClassKind.HANDLER);
  }

  @Test
  public void testJsonAsyncResultHandlers() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerAsyncResultJSON.class);
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "methodwithJsonHandlersAsyncResult", null, MethodKind.FUTURE, "void", false, false, false, 2);
    checkClassParam(model.getMethods().get(0).getParams().get(0), "jsonObjectHandler", Handler.class.getName() + "<" + AsyncResult.class.getName() + "<" + JsonObject.class.getName() + ">>", ClassKind.HANDLER);
    checkClassParam(model.getMethods().get(0).getParams().get(1), "jsonArrayHandler", Handler.class.getName() + "<" + AsyncResult.class.getName() + "<" + JsonArray.class.getName() + ">>", ClassKind.HANDLER);
  }

  @Test
  public void testJsonReturns() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidJSONReturn.class);
    assertEquals(MethodWithValidJSONReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidJSONReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());
    checkMethod(model.getMethods().get(0), "foo", null, MethodKind.OTHER, JsonObject.class.getName(), false, false, false, 0);
    checkMethod(model.getMethods().get(1), "bar", null, MethodKind.OTHER, JsonArray.class.getName(), false, false, false, 0);
  }

  @Test
  public void testMethodHandlerParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithHandlerParam.class);
    checkMethod(model.getMethods().get(0), "foo_1", null, MethodKind.HANDLER, "void", false, false, false, 1);
    checkMethod(model.getMethods().get(1), "foo_2", null, MethodKind.HANDLER, "void", false, false, false, 2);
    checkMethod(model.getMethods().get(2), "foo_3", null, MethodKind.HANDLER, "void", false, false, false, 2);
    checkMethod(model.getMethods().get(3), "foo_4", null, MethodKind.HANDLER, MethodWithHandlerParam.class.getName(), false, true, false, 1);
    checkMethod(model.getMethods().get(4), "foo_5", null, MethodKind.HANDLER, MethodWithHandlerParam.class.getName(), false, true, false, 2);
    checkMethod(model.getMethods().get(5), "foo_6", null, MethodKind.HANDLER, MethodWithHandlerParam.class.getName(), false, true, false, 2);
    checkMethod(model.getMethods().get(6), "foo_7", null, MethodKind.OTHER, String.class.getName(), false, false, false, 1);
    checkMethod(model.getMethods().get(7), "foo_8", null, MethodKind.OTHER, "void", false, false, false, 2);
  }

  @Test
  public void testMethodHandlerAsyncResultParam() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithHandlerAsyncResultParam.class);
    checkMethod(model.getMethods().get(0), "foo_1", null, MethodKind.FUTURE, "void", false, false, false, 1);
    checkMethod(model.getMethods().get(1), "foo_2", null, MethodKind.FUTURE, "void", false, false, false, 2);
    checkMethod(model.getMethods().get(2), "foo_3", null, MethodKind.FUTURE, "void", false, false, false, 2);
    checkMethod(model.getMethods().get(3), "foo_4", null, MethodKind.FUTURE, MethodWithHandlerAsyncResultParam.class.getName(), false, true, false, 1);
    checkMethod(model.getMethods().get(4), "foo_5", null, MethodKind.FUTURE, MethodWithHandlerAsyncResultParam.class.getName(), false, true, false, 2);
    checkMethod(model.getMethods().get(5), "foo_6", null, MethodKind.FUTURE, MethodWithHandlerAsyncResultParam.class.getName(), false, true, false, 2);
    checkMethod(model.getMethods().get(6), "foo_7", null, MethodKind.OTHER, String.class.getName(), false, false, false, 1);
    checkMethod(model.getMethods().get(7), "foo_8", null, MethodKind.OTHER, "void", false, false, false, 2);
  }

  @Test
  public void testMethodInvalidMapReturn1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidMapReturn1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidMapReturn2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidMapReturn2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidListReturn1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidListReturn1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testInterfaceExtendingReadStream() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtentingReadStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeInfo.create(String.class), apiType.getReadStreamArg());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingReadStream() throws Exception {
    ClassModel model = new Generator().generateClass(GenericInterfaceExtentingReadStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    TypeInfo.Variable readStreamArg = (TypeInfo.Variable) apiType.getReadStreamArg();
    assertEquals("U", readStreamArg.getName());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingWriteStream() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtentingWriteStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertFalse(apiType.isReadStream());
    assertNull(apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    assertEquals(TypeInfo.create(String.class), apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingWriteStream() throws Exception {
    ClassModel model = new Generator().generateClass(GenericInterfaceExtentingWriteStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType().getRaw();
    assertFalse(apiType.isReadStream());
    assertNull(apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    TypeInfo.Variable writeStreamArg = (TypeInfo.Variable) apiType.getWriteStreamArg();
    assertEquals("U", writeStreamArg.getName());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingReadStreamAndWriteStream() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtentingReadStreamAndWriteStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeInfo.create(String.class), apiType.getReadStreamArg());
    assertTrue(apiType.isWriteStream());
    assertEquals(TypeInfo.create(String.class), apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testGenericInterfaceExtendingReadStreamAndWriteStream() throws Exception {
    ClassModel model = new Generator().generateClass(GenericInterfaceExtentingReadStreamAndWriteStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    TypeInfo.Variable readStreamArg = (TypeInfo.Variable) apiType.getReadStreamArg();
    assertEquals("U", readStreamArg.getName());
    assertTrue(apiType.isWriteStream());
    TypeInfo.Variable writeStreamArg = (TypeInfo.Variable) apiType.getWriteStreamArg();
    assertEquals("U", writeStreamArg.getName());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceSubtypingReadStream() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceSubtypingReadStream.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertTrue(apiType.isReadStream());
    assertEquals(TypeInfo.create(String.class), apiType.getReadStreamArg());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testReadStreamWithParameterizedTypeArg() throws Exception {
    ClassModel model = new Generator().generateClass(ReadStreamWithParameterizedTypeArg.class);
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType().getRaw();
    assertTrue(apiType.isReadStream());
    TypeInfo.Parameterized readStreamArg = (TypeInfo.Parameterized) apiType.getReadStreamArg();
    assertEquals(TypeInfo.create(List.class), readStreamArg.getRaw());
    assertEquals(1, readStreamArg.getArgs().size());
    assertEquals("T", readStreamArg.getArgs().get(0).getName());
    assertFalse(apiType.isWriteStream());
    assertNull(apiType.getWriteStreamArg());
    assertFalse(apiType.isHandler());
  }

  @Test
  public void testInterfaceExtendingHandlerStringSubtype() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtendingHandlerStringSubtype.class);
    TypeInfo.Parameterized handlerSuperType = (TypeInfo.Parameterized) model.getHandlerSuperType();
    assertEquals(1, handlerSuperType.getArgs().size());
    assertEquals(TypeInfo.create(String.class), handlerSuperType.getArgs().get(0));
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertTrue(apiType.isHandler());
    assertEquals(TypeInfo.create(String.class), apiType.getHandlerArg());
    assertFalse(apiType.isReadStream());
    assertFalse(apiType.isWriteStream());
    assertEquals(1, model.getMethodMap().size());
    assertEquals(1, model.getMethodMap().get("handle").size());
    checkMethod(model.getMethodMap().get("handle").get(0), "handle", null, MethodKind.OTHER, "void", false, false, false, 1);
  }

  @Test
  public void testInterfaceExtendingHandlerVertxGenSubtype() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtendingHandlerVertxGenSubtype.class, VertxGenClass1.class);
    TypeInfo.Parameterized handlerSuperType = (TypeInfo.Parameterized) model.getHandlerSuperType();
    assertEquals(1, handlerSuperType.getArgs().size());
    assertEquals(TypeInfo.create(VertxGenClass1.class), handlerSuperType.getArgs().get(0));
    TypeInfo.Class.Api apiType = (TypeInfo.Class.Api) model.getType();
    assertTrue(apiType.isHandler());
    assertEquals(TypeInfo.create(VertxGenClass1.class), apiType.getHandlerArg());
    assertFalse(apiType.isReadStream());
    assertFalse(apiType.isWriteStream());
    assertEquals(1, model.getMethodMap().size());
    assertEquals(1, model.getMethodMap().get("handle").size());
    checkMethod(model.getMethodMap().get("handle").get(0), "handle", null, MethodKind.OTHER, "void", false, false, false, 1);
  }

  @Test
  public void testMethodInvalidListReturn2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidListReturn2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidSetReturn1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidSetReturn1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidSetReturn2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidSetReturn2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidMapParams1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidMapParams1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidMapParams2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidMapParams2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidListParams1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidListParams1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidListParams2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidListParams2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidSetParams1() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidSetParams1.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidSetParams2() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidSetParams2.class);
      fail("Invalid Map return should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidHandlerDataObjectsParam() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidHandlerDataObjectParam.class);
      fail("Option without toJson() in Handler param should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testMethodInvalidHandlerAsyncResultDataObjectsParam() throws Exception {
    try {
      new Generator().generateClass(MethodWithInvalidHandlerAsyncResultDataObjectParam.class);
      fail("Option without toJson() in AsyncResult param should fail");
    } catch (GenException e) {
      // pass
    }
  }

  @Test
  public void testImplPackage() throws Exception {
    try {
      new Generator().generateClass(InterfaceInImplParentPackage.class);
      fail();
    } catch (IllegalArgumentException expected) {
    }
    try {
      new Generator().generateClass(InterfaceInImplPackage.class);
      fail();
    } catch (IllegalArgumentException expected) {
    }
    ClassModel model = new Generator().generateClass(InterfaceInImplContainingPackage.class);
    assertEquals(InterfaceInImplContainingPackage.class.getName(), model.getFqn());
  }

  private void checkMethod(MethodInfo meth, String name, Doc comment, MethodKind kind, String returnType, boolean cacheReturn,
                           boolean fluent, boolean staticMethod, int numParams) {

    assertEquals(name, meth.getName());
    if (comment != null) {
      assertNotNull(meth.getComment());
      assertEquals(comment.getFirstSentence(), meth.getDoc().getFirstSentence());
      assertEquals(comment.getBody(), meth.getDoc().getBody());
      assertEquals(comment.getBlockTags(), meth.getDoc().getBlockTags());
    } else {
      assertNull(meth.getComment());
    }
    assertEquals(kind, meth.getKind());
    assertEquals(returnType, meth.getReturnType().toString());
    assertEquals(cacheReturn, meth.isCacheReturn());
    assertEquals(fluent, meth.isFluent());
    assertEquals(staticMethod, meth.isStaticMethod());
    assertEquals(numParams, meth.getParams().size());
  }

  private void checkParam(ParamInfo param, String name, String type) {
    assertEquals(name, param.getName());
    assertEquals(type, param.getType().toString());
  }

  private void checkClassParam(ParamInfo param, String name, String type, ClassKind kind) {
    checkParam(param, name, type);
    TypeInfo paramType;
    if (param.getType() instanceof TypeInfo.Parameterized) {
      paramType = param.getType().getRaw();
    } else {
      paramType = param.getType();
    }
    assertEquals(kind, paramType.getKind());
  }

  private void assertGenInvalid(Class<?> c, Class<?>... rest) throws Exception {
    try {
      new Generator().generateClass(c, rest);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }
}
