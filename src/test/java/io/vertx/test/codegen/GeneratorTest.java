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
import io.vertx.test.codegen.testapi.InterfaceWithGenericMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithInstanceMethods;
import io.vertx.test.codegen.testapi.InterfaceExtendingGenericAbstractInterface;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverrideParameterRenamed;
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  static final TypeInfo.Class VertxGenInterfaceInfo = (TypeInfo.Class) TypeInfo.create(VertxGenInterface.class);

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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 9);
    List<ParamInfo> params = method.getParams();
    basicParamCheck(params.get(0), "b", "byte");
    basicParamCheck(params.get(1), "s", "short");
    basicParamCheck(params.get(2), "i", "int");
    basicParamCheck(params.get(3), "l", "long");
    basicParamCheck(params.get(4), "f", "float");
    basicParamCheck(params.get(5), "d", "double");
    basicParamCheck(params.get(6), "bool", "boolean");
    basicParamCheck(params.get(7), "ch", "char");
    new ParamCheck<String>(params.get(8), "str") {};
    assertEquals("java.lang.Byte", ((TypeInfo.Primitive)params.get(0).getType()).getBoxed().getName());
    assertEquals("java.lang.Short", ((TypeInfo.Primitive)params.get(1).getType()).getBoxed().getName());
    assertEquals("java.lang.Integer", ((TypeInfo.Primitive)params.get(2).getType()).getBoxed().getName());
    assertEquals("java.lang.Long", ((TypeInfo.Primitive)params.get(3).getType()).getBoxed().getName());
    assertEquals("java.lang.Float", ((TypeInfo.Primitive)params.get(4).getType()).getBoxed().getName());
    assertEquals("java.lang.Double", ((TypeInfo.Primitive)params.get(5).getType()).getBoxed().getName());
    assertEquals("java.lang.Boolean", ((TypeInfo.Primitive)params.get(6).getType()).getBoxed().getName());
    assertEquals("java.lang.Character", ((TypeInfo.Primitive)params.get(7).getType()).getBoxed().getName());
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
    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 9);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<Byte>(params.get(0), "b") {};
    new ParamCheck<Short>(params.get(1), "s") {};
    new ParamCheck<Integer>(params.get(2), "i") {};
    new ParamCheck<Long>(params.get(3), "l") {};
    new ParamCheck<Float>(params.get(4), "f") {};
    new ParamCheck<Double>(params.get(5), "d") {};
    new ParamCheck<Boolean>(params.get(6), "bool") {};
    new ParamCheck<Character>(params.get(7), "ch") {};
    new ParamCheck<String>(params.get(8), "str") {};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 6);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<List<String>>(params.get(0), "listString") {};
    new ParamCheck<List<Long>>(params.get(1), "listLong") {};
    new ParamCheck<List<JsonObject>>(params.get(2), "listJsonObject") {};
    new ParamCheck<List<JsonArray>>(params.get(3), "listJsonArray") {};
    new ParamCheck<List<VertxGenClass1>>(params.get(4), "listVertxGen") {};
    new ParamCheck<List<TestDataObject>>(params.get(5), "listDataObject") {};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 6);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<Set<String>>(params.get(0), "setString") {};
    new ParamCheck<Set<Long>>(params.get(1), "setLong") {};
    new ParamCheck<Set<JsonObject>>(params.get(2), "setJsonObject") {};
    new ParamCheck<Set<JsonArray>>(params.get(3), "setJsonArray") {};
    new ParamCheck<Set<VertxGenClass1>>(params.get(4), "setVertxGen") {};
    new ParamCheck<Set<TestDataObject>>(params.get(5), "setDataObject") {};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 5);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<Map<String, String>>(params.get(0), "mapString") {};
    new ParamCheck<Map<String, Long>>(params.get(1), "mapLong") {};
    new ParamCheck<Map<String, JsonObject>>(params.get(2), "mapJsonObject") {};
    new ParamCheck<Map<String, JsonArray>>(params.get(3), "mapJsonArray") {};
    new ParamCheck<Map<String, VertxGenClass1>>(params.get(4), "mapVertxGen") {};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.HANDLER, "void", false, false, false, 38);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<Handler<Byte>>(params.get(0), "byteHandler"){};
    new ParamCheck<Handler<Short>>(params.get(1), "shortHandler"){};
    new ParamCheck<Handler<Integer>>(params.get(2), "intHandler"){};
    new ParamCheck<Handler<Long>>(params.get(3), "longHandler"){};
    new ParamCheck<Handler<Float>>(params.get(4), "floatHandler"){};
    new ParamCheck<Handler<Double>>(params.get(5), "doubleHandler"){};
    new ParamCheck<Handler<Boolean>>(params.get(6), "booleanHandler"){};
    new ParamCheck<Handler<Character>>(params.get(7), "charHandler"){};
    new ParamCheck<Handler<String>>(params.get(8), "strHandler"){};
    new ParamCheck<Handler<VertxGenClass1>>(params.get(9), "gen1Handler"){};
    new ParamCheck<Handler<VertxGenClass2>>(params.get(10), "gen2Handler"){};
    new ParamCheck<Handler<List<Byte>>>(params.get(11), "listByteHandler"){};
    new ParamCheck<Handler<List<Short>>>(params.get(12), "listShortHandler"){};
    new ParamCheck<Handler<List<Integer>>>(params.get(13), "listIntHandler"){};
    new ParamCheck<Handler<List<Long>>>(params.get(14), "listLongHandler"){};
    new ParamCheck<Handler<List<Float>>>(params.get(15), "listFloatHandler"){};
    new ParamCheck<Handler<List<Double>>>(params.get(16), "listDoubleHandler"){};
    new ParamCheck<Handler<List<Boolean>>>(params.get(17), "listBooleanHandler"){};
    new ParamCheck<Handler<List<Character>>>(params.get(18), "listCharHandler"){};
    new ParamCheck<Handler<List<String>>>(params.get(19), "listStrHandler"){};
    new ParamCheck<Handler<List<VertxGenClass1>>>(params.get(20), "listVertxGenHandler"){};
    new ParamCheck<Handler<List<JsonObject>>>(params.get(21), "listJsonObjectHandler"){};
    new ParamCheck<Handler<List<JsonArray>>>(params.get(22), "listJsonArrayHandler"){};
    new ParamCheck<Handler<Set<Byte>>>(params.get(23), "setByteHandler"){};
    new ParamCheck<Handler<Set<Short>>>(params.get(24), "setShortHandler"){};
    new ParamCheck<Handler<Set<Integer>>>(params.get(25), "setIntHandler"){};
    new ParamCheck<Handler<Set<Long>>>(params.get(26), "setLongHandler"){};
    new ParamCheck<Handler<Set<Float>>>(params.get(27), "setFloatHandler"){};
    new ParamCheck<Handler<Set<Double>>>(params.get(28), "setDoubleHandler"){};
    new ParamCheck<Handler<Set<Boolean>>>(params.get(29), "setBooleanHandler"){};
    new ParamCheck<Handler<Set<Character>>>(params.get(30), "setCharHandler"){};
    new ParamCheck<Handler<Set<String>>>(params.get(31), "setStrHandler"){};
    new ParamCheck<Handler<Set<VertxGenClass1>>>(params.get(32), "setVertxGenHandler"){};
    new ParamCheck<Handler<Set<JsonObject>>>(params.get(33), "setJsonObjectHandler"){};
    new ParamCheck<Handler<Set<JsonArray>>>(params.get(34), "setJsonArrayHandler"){};
    new ParamCheck<Handler<Void>>(params.get(35), "voidHandler"){};
    new ParamCheck<Handler<Throwable>>(params.get(36), "throwableHandler"){};
    new ParamCheck<Handler<TestDataObject>>(params.get(37), "dataObjectHandler"){};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.FUTURE, "void", false, false, false, 39);
    List<ParamInfo> params = method.getParams();

    new ParamCheck<Handler<AsyncResult<Byte>>>(params.get(0), "byteHandler"){};
    new ParamCheck<Handler<AsyncResult<Short>>>(params.get(1), "shortHandler"){};
    new ParamCheck<Handler<AsyncResult<Integer>>>(params.get(2), "intHandler"){};
    new ParamCheck<Handler<AsyncResult<Long>>>(params.get(3), "longHandler"){};
    new ParamCheck<Handler<AsyncResult<Float>>>(params.get(4), "floatHandler"){};
    new ParamCheck<Handler<AsyncResult<Double>>>(params.get(5), "doubleHandler"){};
    new ParamCheck<Handler<AsyncResult<Boolean>>>(params.get(6), "booleanHandler"){};
    new ParamCheck<Handler<AsyncResult<Character>>>(params.get(7), "charHandler"){};
    new ParamCheck<Handler<AsyncResult<String>>>(params.get(8), "strHandler"){};
    new ParamCheck<Handler<AsyncResult<VertxGenClass1>>>(params.get(9), "gen1Handler"){};
    new ParamCheck<Handler<AsyncResult<VertxGenClass2>>>(params.get(10), "gen2Handler"){};
    new ParamCheck<Handler<AsyncResult<List<Byte>>>>(params.get(11), "listByteHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Short>>>>(params.get(12), "listShortHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Integer>>>>(params.get(13), "listIntHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Long>>>>(params.get(14), "listLongHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Float>>>>(params.get(15), "listFloatHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Double>>>>(params.get(16), "listDoubleHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Boolean>>>>(params.get(17), "listBooleanHandler"){};
    new ParamCheck<Handler<AsyncResult<List<Character>>>>(params.get(18), "listCharHandler"){};
    new ParamCheck<Handler<AsyncResult<List<String>>>>(params.get(19), "listStrHandler"){};
    new ParamCheck<Handler<AsyncResult<List<VertxGenClass1>>>>(params.get(20), "listVertxGenHandler"){};
    new ParamCheck<Handler<AsyncResult<List<JsonObject>>>>(params.get(21), "listJsonObjectHandler"){};
    new ParamCheck<Handler<AsyncResult<List<JsonArray>>>>(params.get(22), "listJsonArrayHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Byte>>>>(params.get(23), "setByteHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Short>>>>(params.get(24), "setShortHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Integer>>>>(params.get(25), "setIntHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Long>>>>(params.get(26), "setLongHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Float>>>>(params.get(27), "setFloatHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Double>>>>(params.get(28), "setDoubleHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Boolean>>>>(params.get(29), "setBooleanHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<Character>>>>(params.get(30), "setCharHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<String>>>>(params.get(31), "setStrHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<VertxGenClass1>>>>(params.get(32), "setVertxGenHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<JsonObject>>>>(params.get(33), "setJsonObjectHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<JsonArray>>>>(params.get(34), "setJsonArrayHandler"){};
    new ParamCheck<Handler<AsyncResult<Void>>>(params.get(35), "voidHandler"){};
    new ParamCheck<Handler<AsyncResult<TestDataObject>>>(params.get(36), "dataObjectHandler"){};
    new ParamCheck<Handler<AsyncResult<List<TestDataObject>>>>(params.get(37), "listDataObjectHandler"){};
    new ParamCheck<Handler<AsyncResult<Set<TestDataObject>>>>(params.get(38), "setDataObjectHandler"){};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 3);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<String>(params.get(0), "str"){};
    new ParamCheck<VertxGenClass1>(params.get(1), "myParam1"){};
    new ParamCheck<VertxGenClass2>(params.get(2), "myParam2"){};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<Object>(params.get(0), "obj"){};
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<TestEnum>(params.get(0), "weirdo"){};
    TypeInfo.Class.Enum enumType = (TypeInfo.Class.Enum) params.get(0).getType();
    assertEquals(Arrays.asList("TIM", "JULIEN", "NICK", "WESTON"), enumType.getValues());
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, TestEnum.class.getName(), false, false, false, 0);
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, Throwable.class.getName(), false, false, false, 0);
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

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, null, MethodKind.OTHER, "void", false, false, false, 1);
    List<ParamInfo> params = method.getParams();
    new ParamCheck<NetServerOptions>(params.get(0), "dataObject"){};
  }

  // Valid returns

  @Test
  public void testGenericInterface() throws Exception {
    ClassModel model = new Generator().generateClass(GenericInterface.class);
    assertEquals(GenericInterface.class.getName() + "<T>", model.getIfaceFQCN());
    assertEquals(GenericInterface.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue("Was expecting " + model.getReferencedTypes() + " to be empty", model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    TypeInfo.Variable t = (TypeInfo.Variable) ((TypeInfo.Parameterized) model.getType()).getArgs().get(0);
    checkMethod(methods.get(0), "methodWithClassTypeParam", null, MethodKind.OTHER, "T", false, false, false, 3);
    List<ParamInfo> params1 = methods.get(0).getParams();
    class Check1<T> extends ParamCheck<T> { public Check1() { super(params1.get(0), "t"); } }
    assertTrue(params1.get(0).getType() instanceof TypeInfo.Variable);
    assertEquals(t, params1.get(0).getType());
    class Check2<T> extends ParamCheck<Handler<T>> { public Check2() { super(params1.get(1), "handler"); } }
    class Check3<T> extends ParamCheck<Handler<AsyncResult<T>>> { public Check3() { super(params1.get(2), "asyncResultHandler"); } }
    checkMethod(methods.get(1), "someGenericMethod", null, MethodKind.OTHER, "io.vertx.test.codegen.testapi.GenericInterface<R>", false, false, false, 3);
    List<ParamInfo> params2 = methods.get(1).getParams();
    class Check4<R> extends ParamCheck<R> { public Check4() { super(params2.get(0), "r"); } }
    assertTrue(params2.get(0).getType() instanceof TypeInfo.Variable);
    assertEquals(methods.get(1).getTypeParams().get(0), ((TypeInfo.Variable) params2.get(0).getType()).getParam());
    class Check5<R> extends ParamCheck<Handler<R>> { public Check5() { super(params2.get(0), "handler"); } }
    class Check6<R> extends ParamCheck<Handler<AsyncResult<R>>> { public Check6() { super(params2.get(0), "asyncResultHandler"); } }
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(17, methods.size());
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(13, methods.size());
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(13, methods.size());
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
  }

  @Test
  public void testValidMapReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidMapReturn.class);
    assertEquals(MethodWithValidMapReturn.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidMapReturn.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(0, model.getReferencedTypes().size());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(11, methods.size());
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "methodWithVertxGen1Return", null, MethodKind.OTHER, VertxGenClass1.class.getName(), false, false, false, 0);
    checkMethod(methods.get(1), "methodWithVertxGen2Return", null, MethodKind.OTHER, VertxGenClass2.class.getName(), false, false, false, 0);
  }

  @Test
  public void testGenIgnore() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithIgnoredMethods.class);
    assertEquals(InterfaceWithIgnoredMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithIgnoredMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
  }

  @Test
  public void testFluentMethodOverrideFromConcrete() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithFluentMethodOverrideFromConcrete.class);
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethodOverrideFromConcrete.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(ConcreteInterfaceWithFluentMethods.class)), model.getReferencedTypes());
    assertEquals(Collections.singletonList(TypeInfo.create(ConcreteInterfaceWithFluentMethods.class)), model.getSuperTypes());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), false, true, false, 1);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromConcrete.class.getName(), false, true, false, 1);
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), false, true, false, 1);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, InterfaceWithFluentMethodOverrideFromAbstract.class.getName(), false, true, false, 1);
  }

  @Test
  public void testFluentMethods() throws Exception {
    ClassModel model = new Generator().generateClass(ConcreteInterfaceWithFluentMethods.class);
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(ConcreteInterfaceWithFluentMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, ConcreteInterfaceWithFluentMethods.class.getName(), false, true, false, 1);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, ConcreteInterfaceWithFluentMethods.class.getName(), false, true, false, 1);
  }

  @Test
  public void testCacheReturnMethods() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithCacheReturnMethods.class);
    assertEquals(InterfaceWithCacheReturnMethods.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithCacheReturnMethods.class.getSimpleName(), model.getIfaceSimpleName());
    assertEquals(1, model.getReferencedTypes().size());
    assertTrue(model.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, String.class.getName(), true, false, false, 1);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, VertxGenClass1.class.getName(), true, false, false, 1);
  }

  @Test
  public void testSupertypes() throws Exception {
    ClassModel gen = new Generator().generateClass(InterfaceWithSupertypes.class, VertxGenClass1.class, VertxGenInterface.class);
    assertEquals(InterfaceWithSupertypes.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithSupertypes.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1Info));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterfaceInfo));
    assertEquals(2, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(VertxGenClass1Info));
    assertTrue(gen.getSuperTypes().contains(VertxGenInterfaceInfo));
    assertEquals(1, gen.getConcreteSuperTypes().size());
    assertTrue(gen.getConcreteSuperTypes().contains(VertxGenClass1Info));
    assertEquals(1, gen.getAbstractSuperTypes().size());
    assertTrue(gen.getAbstractSuperTypes().contains(VertxGenInterfaceInfo));
    List<MethodInfo> methods = gen.getMethods();
    assertEquals(2, methods.size());
    Collections.sort(methods);
    checkMethod(methods.get(0), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    checkMethod(methods.get(1), "quux", null, MethodKind.OTHER, "void", false, false, false, 1);
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(8, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "void", false, false, false, 1);
    new ParamCheck<String>(methods.get(0).getParams().get(0), "str"){};

    checkMethod(methods.get(1), "foo", null, MethodKind.HANDLER, "void", false, false, false, 2);
    new ParamCheck<String>(methods.get(1).getParams().get(0), "str"){};
    new ParamCheck<Handler<VertxGenClass1>>(methods.get(1).getParams().get(1), "handler"){};

    checkMethod(methods.get(2), "foo", null, MethodKind.HANDLER, "void", false, false, false, 3);
    new ParamCheck<String>(methods.get(2).getParams().get(0), "str"){};

    basicParamCheck(methods.get(2).getParams().get(1), "time", "long");
    new ParamCheck<Handler<VertxGenClass1>>(methods.get(2).getParams().get(2), "handler"){};

    checkMethod(methods.get(3), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    new ParamCheck<VertxGenClass2>(methods.get(3).getParams().get(0), "obj1"){};

    checkMethod(methods.get(4), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    new ParamCheck<String>(methods.get(4).getParams().get(0), "obj1"){};

    checkMethod(methods.get(5), "juu", null, MethodKind.OTHER, "void", false, false, false, 1);
    new ParamCheck<String>(methods.get(5).getParams().get(0), "str"){};
    checkMethod(methods.get(6), "juu", null, MethodKind.OTHER, "void", false, false, false, 2);
    new ParamCheck<String>(methods.get(6).getParams().get(0), "str"){};
    basicParamCheck(methods.get(6).getParams().get(1), "time", "long");
    checkMethod(methods.get(7), "juu", null, MethodKind.HANDLER, "void", false, false, false, 3);
    new ParamCheck<String>(methods.get(7).getParams().get(0), "str"){};
    basicParamCheck(methods.get(7).getParams().get(1), "time", "long");
    class Check1<T> extends ParamCheck<Handler<T>> { public Check1() { super(methods.get(7).getParams().get(2), "handler"); } }
    assertEquals(3, model.getMethodMap().size());
    List<MethodInfo> meths1 = model.getMethodMap().get("foo");
    assertEquals(3, meths1.size());
    assertSame(methods.get(0), meths1.get(0));
    assertSame(methods.get(1), meths1.get(1));
    assertSame(methods.get(2), meths1.get(2));
    List<MethodInfo> meths2 = model.getMethodMap().get("bar");
    assertEquals(2, meths2.size());
    assertSame(methods.get(3), meths2.get(0));
    assertSame(methods.get(4), meths2.get(1));
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
    ClassModel model = new Generator().generateClass(InterfaceWithMethodOverride.class, VertxGenInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    assertEquals(set(
        TypeInfo.create(InterfaceWithMethodOverride.class),
        TypeInfo.create(VertxGenInterface.class)
    ), methods.get(0).getOwnerTypes());
    new ParamCheck<String>(methods.get(0).getParams().get(0), "str"){};}

  @Test
  public void testMethodOverrideParameterRenamed() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithMethodOverrideParameterRenamed.class, VertxGenInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "bar", null, MethodKind.OTHER, "void", false, false, false, 1);
    assertEquals(set(
        TypeInfo.create(InterfaceWithMethodOverrideParameterRenamed.class),
        TypeInfo.create(VertxGenInterface.class)
    ), methods.get(0).getOwnerTypes());
    new ParamCheck<String>(methods.get(0).getParams().get(0), "str_renamed"){};
  }

  @Test
  public void testInterfaceWithGenericMethodOverride() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithGenericMethodOverride.class, GenericAbstractInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(5, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "java.lang.String", false, false, false, 0);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, "java.util.List<java.lang.String>", false, false, false, 0);
    checkMethod(methods.get(2), "juu", null, MethodKind.FUTURE, "void", false, false, false, 1);
    checkMethod(methods.get(3), "daa", null, MethodKind.HANDLER, "void", false, false, false, 1);
    checkMethod(methods.get(4), "collargol", null, MethodKind.OTHER, "void", false, false, false, 1);
    for (int i = 0;i < 5;i++) {
      assertEquals(set(
          TypeInfo.create(InterfaceWithGenericMethodOverride.class),
          TypeInfo.create(GenericAbstractInterface.class)
      ), methods.get(i).getOwnerTypes());
    }
    new ParamCheck<Handler<AsyncResult<String>>>(methods.get(2).getParams().get(0), "handler"){};
    new ParamCheck<Handler<String>>(methods.get(3).getParams().get(0), "handler"){};
    new ParamCheck<String>(methods.get(4).getParams().get(0), "t"){};
  }

  @Test
  public void testInterfaceExtendingGenericAbstractInterface() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceExtendingGenericAbstractInterface.class, GenericAbstractInterface.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(5, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "java.lang.String", false, false, false, 0);
    checkMethod(methods.get(1), "bar", null, MethodKind.OTHER, "java.util.List<java.lang.String>", false, false, false, 0);
    checkMethod(methods.get(2), "juu", null, MethodKind.FUTURE, "void", false, false, false, 1);
    checkMethod(methods.get(3), "daa", null, MethodKind.HANDLER, "void", false, false, false, 1);
    checkMethod(methods.get(4), "collargol", null, MethodKind.OTHER, "void", false, false, false, 1);
    new ParamCheck<Handler<AsyncResult<String>>>(methods.get(2).getParams().get(0), "handler"){};
    new ParamCheck<Handler<String>>(methods.get(3).getParams().get(0), "handler"){};
    new ParamCheck<String>(methods.get(4).getParams().get(0), "t"){};
  }

  @Test
  public void testInterfaceWithTypeVariableArgument() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithTypeVariableArgument3.class, InterfaceWithTypeVariableArgument2.class, InterfaceWithTypeVariableArgument1.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "io.vertx.test.codegen.testapi.InterfaceWithTypeVariableArgument3", false, false, false, 0);
    assertEquals(set(
        TypeInfo.create(InterfaceWithTypeVariableArgument1.class),
        TypeInfo.create(InterfaceWithTypeVariableArgument2.class)
    ), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodWithSameSignatureInheritedFromDistinctInterfaces() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithSameSignatureInheritedFromDistinctInterfaces.class, SameSignatureMethod1.class, SameSignatureMethod2.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "U", false, false, false, 0);
    assertEquals(set(TypeInfo.create(SameSignatureMethod1.class), TypeInfo.create(SameSignatureMethod2.class)), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodWithDiamond() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithDiamond.class, DiamondMethod1.class, DiamondMethod2.class, DiamondMethod3.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "foo", null, MethodKind.OTHER, "U", false, false, false, 0);
    assertEquals(set(TypeInfo.create(DiamondMethod1.class), TypeInfo.create(DiamondMethod2.class), TypeInfo.create(DiamondMethod3.class)), methods.get(0).getOwnerTypes());
  }

  @Test
  public void testMethodComments() throws Exception {
    ClassModel model = new Generator().generateClass(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), model.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(2, methods.size());
    Doc comment1 = new Doc(" Comment 1 line 1\n Comment 1 line 2", null,
        Arrays.asList(new Tag("param", "str the_string"), new Tag("return", "the_return_value\n")));
    Doc comment2 = new Doc(" Comment 2 line 1\n Comment 2 line 2\n");
    checkMethod(methods.get(0), "foo", comment1, MethodKind.OTHER, "java.lang.String", false, false, false, 1);
    assertEquals("str", methods.get(0).getParams().get(0).getName());
    assertEquals("the_string", methods.get(0).getParams().get(0).getDescription().toString());
    checkMethod(methods.get(1), "bar", comment2, MethodKind.OTHER, "void", false, false, false, 1);
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
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithJsonParams", null, MethodKind.OTHER, "void", false, false, false, 2);
    new ParamCheck<JsonObject>(methods.get(0).getParams().get(0), "jsonObject"){};
    new ParamCheck<JsonArray>(methods.get(0).getParams().get(1), "jsonArray"){};
  }

  @Test
  public void testJsonHandlers() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerJSON.class);
    assertEquals(MethodWithValidHandlerJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodWithJsonHandlers", null, MethodKind.HANDLER, "void", false, false, false, 2);
    new ParamCheck<Handler<JsonObject>>(methods.get(0).getParams().get(0), "jsonObjectHandler"){};
    new ParamCheck<Handler<JsonArray>>(methods.get(0).getParams().get(1), "jsonArrayHandler"){};
  }

  @Test
  public void testJsonAsyncResultHandlers() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithValidHandlerAsyncResultJSON.class);
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getName(), model.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getReferencedTypes().isEmpty());
    assertTrue(model.getSuperTypes().isEmpty());
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    checkMethod(methods.get(0), "methodwithJsonHandlersAsyncResult", null, MethodKind.FUTURE, "void", false, false, false, 2);
    new ParamCheck<Handler<AsyncResult<JsonObject>>>(methods.get(0).getParams().get(0), "jsonObjectHandler"){};
    new ParamCheck<Handler<AsyncResult<JsonArray>>>(methods.get(0).getParams().get(1), "jsonArrayHandler"){};
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
    assertGenFail(MethodWithInvalidMapReturn1.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidMapReturn2() throws Exception {
    assertGenFail(MethodWithInvalidMapReturn2.class, "Invalid Map return should fail");
  }

  @Test
  public void testMethodInvalidListReturn1() throws Exception {
    assertGenFail(MethodWithInvalidListReturn1.class, "Invalid Map return should fail");
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

  private void basicParamCheck(ParamInfo param, String name, String type) {
    assertEquals(name, param.getName());
    assertEquals(type, param.getType().toString());
  }

  static abstract class ParamCheck<T> {
    public ParamCheck(ParamInfo param, String expectedName) {
      Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      assertEquals(TypeInfo.create(type), param.getType());
      assertEquals(expectedName, param.getName());
    }
  }

  private void assertGenInvalid(Class<?> c, Class<?>... rest) throws Exception {
    try {
      new Generator().generateClass(c, rest);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  private void assertGenFail(Class<?> type, String msg) throws Exception {
    try {
      new Generator().generateClass(type);
      fail(msg);
    } catch (GenException e) {
      // pass
    }
  }
}
