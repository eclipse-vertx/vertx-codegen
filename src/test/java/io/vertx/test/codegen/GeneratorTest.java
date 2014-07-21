package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.Source;
import io.vertx.codegen.TypeInfo;
import io.vertx.codegen.TypeKind;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServerOptions;
import io.vertx.test.codegen.testapi.AbstractInterfaceWithConcreteSuperInterface;
import io.vertx.test.codegen.testapi.CacheReturnMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.ConcreteInterfaceWithTwoConcreteSuperInterfaces;
import io.vertx.test.codegen.testapi.FluentMethodWithVoidReturn;
import io.vertx.test.codegen.testapi.GenericAbstractInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.GenericInterfaceWithUpperBound;
import io.vertx.test.codegen.testapi.InterfaceWithMethodHavingGenericOverride;
import io.vertx.test.codegen.testapi.InterfaceWithMethodOverride;
import io.vertx.test.codegen.testapi.InterfaceWithNonGenSuperType;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedArraySupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedGenericArraySupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedVariableSupertype;
import io.vertx.test.codegen.testapi.GenericMethod;
import io.vertx.test.codegen.testapi.InterfaceWithCacheReturnMethods;
import io.vertx.test.codegen.testapi.InterfaceWithComments;
import io.vertx.test.codegen.testapi.InterfaceWithDefaultMethod;
import io.vertx.test.codegen.testapi.InterfaceWithFluentMethods;
import io.vertx.test.codegen.testapi.InterfaceWithIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithIndexSetterGetterMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNoMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNoNotIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithOverloadedMethods;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedDeclaredSupertype;
import io.vertx.test.codegen.testapi.InterfaceWithStaticMethods;
import io.vertx.test.codegen.testapi.InterfaceWithSupertypes;
import io.vertx.test.codegen.testapi.MethodWithHandlerAsyncResultReturn;
import io.vertx.test.codegen.testapi.MethodWithHandlerNonVertxGenReturn;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectParam;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithListNonBasicTypeReturn;
import io.vertx.test.codegen.testapi.MethodWithListParam;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectParam;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithObjectParam;
import io.vertx.test.codegen.testapi.MethodWithObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithOptionsParam;
import io.vertx.test.codegen.testapi.MethodWithSetNonBasicTypeReturn;
import io.vertx.test.codegen.testapi.MethodWithSetParam;
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
import io.vertx.test.codegen.testapi.MethodWithValidListReturn;
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
import io.vertx.test.codegen.testapi.OverloadedMethodsInWrongOrder;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.VertxGenClass2;
import io.vertx.test.codegen.testapi.VertxGenInterface1;
import io.vertx.test.codegen.testapi.VertxGenInterface2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GeneratorTest {

  private Source gen;

  public GeneratorTest() {
  }

  // Test invalid stuff
  // ------------------

  // Invalid classes

  @Test
  public void testGenerateNotInterface() throws Exception {
    try {
      gen = new Generator().generateModel(NotInterface.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateNoVertxGenAnnotation() throws Exception {
    try {
      gen = new Generator().generateModel(NoVertxGen.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateNestedInterfaces() throws Exception {
    try {
      gen = new Generator().generateModel(NestedInterface.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateInterfaceWithNoMethods() throws Exception {
    try {
      gen = new Generator().generateModel(InterfaceWithNoMethods.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateInterfaceWithNoNotIgnoredMethods() throws Exception {
    try {
      gen = new Generator().generateModel(InterfaceWithNoNotIgnoredMethods.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateInterfaceWithDefaultMethod() throws Exception {
    try {
      gen = new Generator().generateModel(InterfaceWithDefaultMethod.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  // Invalid params

  @Test
  public void testGenerateMethodWithJavaDotObjectParam() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithJavaDotObjectParam.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandler() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithJavaDotObjectInHandler.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandlerAsyncResult() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithJavaDotObjectInHandlerAsyncResult.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenParam() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithNotVertxGenObjectParam.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandler() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithNotVertxGenObjectInHandler.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandlerAsyncResult() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithNotVertxGenObjectInHandlerAsyncResult.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  // TODO Handler and Handler<AsyncResult> of List/String with non basic types

  @Test
  public void testGenerateMethodWithListParam() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithListParam.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithSetParam() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithSetParam.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithWildcardUpperBoundTypeArg() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithWildcardUpperBoundTypeArg.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithWildcardLowerBoundTypeArg() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithWildcardLowerBoundTypeArg.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  // Invalid returns

  @Test
  public void testGenerateMethodWithJavaDotObjectReturn() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithJavaDotObjectReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenReturn() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithNotVertxGenObjectReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithObjectReturn() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithObjectReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithReturnSetNonBasicType() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithSetNonBasicTypeReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithReturnListNonBasicType() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithListNonBasicTypeReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithReturnHandlerNonVertxGen() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithHandlerNonVertxGenReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithReturnAsyncResultHandler() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithHandlerAsyncResultReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testGenericMethod() throws Exception {
    try {
      gen = new Generator().generateModel(GenericMethod.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }


  // Invalid methods


  @Test
  public void testOverloadedMethodsInWrongOrder() throws Exception {
    try {
      gen = new Generator().generateModel(OverloadedMethodsInWrongOrder.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testFluentMethodWithVoidReturn() throws Exception {
    try {
      gen = new Generator().generateModel(FluentMethodWithVoidReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testCacheReturnMethodWithVoidReturn() throws Exception {
    try {
      gen = new Generator().generateModel(CacheReturnMethodWithVoidReturn.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testMethodWithTypeParameterUpperBound() throws Exception {
    try {
      gen = new Generator().generateModel(MethodWithTypeParameterUpperBound.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  // Invalid abstract/concrete interfaces

  @Test
  public void testAbstractInterfaceCannotExtendConcreteInterface() throws Exception {
    try {
      gen = new Generator().generateModel(AbstractInterfaceWithConcreteSuperInterface.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testConcreteInterfaceCannotExtendTwoConcreteInterfaces() throws Exception {
    try {
      gen = new Generator().generateModel(ConcreteInterfaceWithTwoConcreteSuperInterfaces.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  // Test valid stuff
  // ----------------

  // Valid params

  @Test
  public void testValidBasicParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidBasicParams.class);
    assertEquals(MethodWithValidBasicParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidBasicParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithBasicParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 9);
      List<ParamInfo> params = method.getParams();
      checkParam(params.get(0), "b", "byte");
      checkParam(params.get(1), "s", "short");
      checkParam(params.get(2), "i", "int");
      checkParam(params.get(3), "l", "long");
      checkParam(params.get(4), "f", "float");
      checkParam(params.get(5), "d", "double");
      checkParam(params.get(6), "bool", "boolean");
      checkParam(params.get(7), "ch", "char");
      checkClassParam(params.get(8), "str", "java.lang.String", TypeKind.NONE);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidBasicBoxedParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidBasicBoxedParams.class);
    assertEquals(MethodWithValidBasicBoxedParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidBasicBoxedParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithBasicParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 9);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "b", "java.lang.Byte", TypeKind.NONE);
      checkClassParam(params.get(1), "s", "java.lang.Short", TypeKind.NONE);
      checkClassParam(params.get(2), "i", "java.lang.Integer", TypeKind.NONE);
      checkClassParam(params.get(3), "l", "java.lang.Long", TypeKind.NONE);
      checkClassParam(params.get(4), "f", "java.lang.Float", TypeKind.NONE);
      checkClassParam(params.get(5), "d", "java.lang.Double", TypeKind.NONE);
      checkClassParam(params.get(6), "bool", "java.lang.Boolean", TypeKind.NONE);
      checkClassParam(params.get(7), "ch", "java.lang.Character", TypeKind.NONE);
      checkClassParam(params.get(8), "str", "java.lang.String", TypeKind.NONE);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidTypeParam() throws Exception {
    gen = new Generator().generateModel(MethodWithTypeParameter.class);
    assertEquals(1, gen.getMethods().size());
    MethodInfo mi = gen.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(Arrays.asList("T"), mi.getTypeParams());
  }

  @Test
  public void testValidWildcardTypeArg() throws Exception {
    gen = new Generator().generateModel(MethodWithWildcardTypeArg.class);
    assertEquals(1, gen.getMethods().size());
    MethodInfo mi = gen.getMethods().get(0);
    assertEquals("foo", mi.getName());
    assertEquals(new TypeInfo.Parameterized(new TypeInfo.Class(TypeKind.GEN, GenericInterface.class.getName()), Arrays.asList(new TypeInfo.Wildcard())), mi.getParams().get(0).getType());
  }

  @Test
  public void testValidHandlerParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidHandlerParams.class);
    assertEquals(MethodWithValidHandlerParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithHandlerParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 31);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "byteHandler", "io.vertx.core.Handler<java.lang.Byte>", TypeKind.HANDLER);
      checkClassParam(params.get(1), "shortHandler", "io.vertx.core.Handler<java.lang.Short>", TypeKind.HANDLER);
      checkClassParam(params.get(2), "intHandler", "io.vertx.core.Handler<java.lang.Integer>", TypeKind.HANDLER);
      checkClassParam(params.get(3), "longHandler", "io.vertx.core.Handler<java.lang.Long>", TypeKind.HANDLER);
      checkClassParam(params.get(4), "floatHandler", "io.vertx.core.Handler<java.lang.Float>", TypeKind.HANDLER);
      checkClassParam(params.get(5), "doubleHandler", "io.vertx.core.Handler<java.lang.Double>", TypeKind.HANDLER);
      checkClassParam(params.get(6), "booleanHandler", "io.vertx.core.Handler<java.lang.Boolean>", TypeKind.HANDLER);
      checkClassParam(params.get(7), "charHandler", "io.vertx.core.Handler<java.lang.Character>", TypeKind.HANDLER);
      checkClassParam(params.get(8), "strHandler", "io.vertx.core.Handler<java.lang.String>", TypeKind.HANDLER);
      checkClassParam(params.get(9), "gen1Handler", "io.vertx.core.Handler<" + VertxGenClass1.class.getName() + ">", TypeKind.HANDLER);
      checkClassParam(params.get(10), "gen2Handler", "io.vertx.core.Handler<" + VertxGenClass2.class.getName() + ">", TypeKind.HANDLER);
      checkClassParam(params.get(11), "listByteHandler", "io.vertx.core.Handler<java.util.List<java.lang.Byte>>", TypeKind.HANDLER);
      checkClassParam(params.get(12), "listShortHandler", "io.vertx.core.Handler<java.util.List<java.lang.Short>>", TypeKind.HANDLER);
      checkClassParam(params.get(13), "listIntHandler", "io.vertx.core.Handler<java.util.List<java.lang.Integer>>", TypeKind.HANDLER);
      checkClassParam(params.get(14), "listLongHandler", "io.vertx.core.Handler<java.util.List<java.lang.Long>>", TypeKind.HANDLER);
      checkClassParam(params.get(15), "listFloatHandler", "io.vertx.core.Handler<java.util.List<java.lang.Float>>", TypeKind.HANDLER);
      checkClassParam(params.get(16), "listDoubleHandler", "io.vertx.core.Handler<java.util.List<java.lang.Double>>", TypeKind.HANDLER);
      checkClassParam(params.get(17), "listBooleanHandler", "io.vertx.core.Handler<java.util.List<java.lang.Boolean>>", TypeKind.HANDLER);
      checkClassParam(params.get(18), "listCharHandler", "io.vertx.core.Handler<java.util.List<java.lang.Character>>", TypeKind.HANDLER);
      checkClassParam(params.get(19), "listStrHandler", "io.vertx.core.Handler<java.util.List<java.lang.String>>", TypeKind.HANDLER);
      checkClassParam(params.get(20), "setByteHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Byte>>", TypeKind.HANDLER);
      checkClassParam(params.get(21), "setShortHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Short>>", TypeKind.HANDLER);
      checkClassParam(params.get(22), "setIntHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Integer>>", TypeKind.HANDLER);
      checkClassParam(params.get(23), "setLongHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Long>>", TypeKind.HANDLER);
      checkClassParam(params.get(24), "setFloatHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Float>>", TypeKind.HANDLER);
      checkClassParam(params.get(25), "setDoubleHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Double>>", TypeKind.HANDLER);
      checkClassParam(params.get(26), "setBooleanHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Boolean>>", TypeKind.HANDLER);
      checkClassParam(params.get(27), "setCharHandler", "io.vertx.core.Handler<java.util.Set<java.lang.Character>>", TypeKind.HANDLER);
      checkClassParam(params.get(28), "setStrHandler", "io.vertx.core.Handler<java.util.Set<java.lang.String>>", TypeKind.HANDLER);
      checkClassParam(params.get(29), "voidHandler", "io.vertx.core.Handler<java.lang.Void>", TypeKind.HANDLER);
      checkClassParam(params.get(30), "throwableHandler", "io.vertx.core.Handler<java.lang.Throwable>", TypeKind.HANDLER);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidHandlerAsyncResultParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidHandlerAsyncResultParams.class);
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithHandlerParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 30);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "byteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Byte>>", TypeKind.HANDLER);
      checkClassParam(params.get(1), "shortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Short>>", TypeKind.HANDLER);
      checkClassParam(params.get(2), "intHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Integer>>", TypeKind.HANDLER);
      checkClassParam(params.get(3), "longHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Long>>", TypeKind.HANDLER);
      checkClassParam(params.get(4), "floatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Float>>", TypeKind.HANDLER);
      checkClassParam(params.get(5), "doubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Double>>", TypeKind.HANDLER);
      checkClassParam(params.get(6), "booleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Boolean>>", TypeKind.HANDLER);
      checkClassParam(params.get(7), "charHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Character>>", TypeKind.HANDLER);
      checkClassParam(params.get(8), "strHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>", TypeKind.HANDLER);
      checkClassParam(params.get(9), "gen1Handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<" + VertxGenClass1.class.getName() + ">>", TypeKind.HANDLER);
      checkClassParam(params.get(10), "gen2Handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<" + VertxGenClass2.class.getName() + ">>", TypeKind.HANDLER);
      checkClassParam(params.get(11), "listByteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Byte>>>", TypeKind.HANDLER);
      checkClassParam(params.get(12), "listShortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Short>>>", TypeKind.HANDLER);
      checkClassParam(params.get(13), "listIntHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Integer>>>", TypeKind.HANDLER);
      checkClassParam(params.get(14), "listLongHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Long>>>", TypeKind.HANDLER);
      checkClassParam(params.get(15), "listFloatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Float>>>", TypeKind.HANDLER);
      checkClassParam(params.get(16), "listDoubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Double>>>", TypeKind.HANDLER);
      checkClassParam(params.get(17), "listBooleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Boolean>>>", TypeKind.HANDLER);
      checkClassParam(params.get(18), "listCharHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.Character>>>", TypeKind.HANDLER);
      checkClassParam(params.get(19), "listStrHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.List<java.lang.String>>>", TypeKind.HANDLER);
      checkClassParam(params.get(20), "setByteHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Byte>>>", TypeKind.HANDLER);
      checkClassParam(params.get(21), "setShortHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Short>>>", TypeKind.HANDLER);
      checkClassParam(params.get(22), "setIntHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Integer>>>", TypeKind.HANDLER);
      checkClassParam(params.get(23), "setLongHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Long>>>", TypeKind.HANDLER);
      checkClassParam(params.get(24), "setFloatHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Float>>>", TypeKind.HANDLER);
      checkClassParam(params.get(25), "setDoubleHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Double>>>", TypeKind.HANDLER);
      checkClassParam(params.get(26), "setBooleanHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Boolean>>>", TypeKind.HANDLER);
      checkClassParam(params.get(27), "setCharHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.Character>>>", TypeKind.HANDLER);
      checkClassParam(params.get(28), "setStrHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.util.Set<java.lang.String>>>", TypeKind.HANDLER);
      checkClassParam(params.get(29), "voidHandler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.Void>>", TypeKind.HANDLER);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidVertxGenParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidVertxGenParams.class);
    assertEquals(MethodWithValidVertxGenParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithVertxGenParams";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 3);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "str", "java.lang.String", TypeKind.NONE);
      checkClassParam(params.get(1), "myParam1", VertxGenClass1.class.getName(), TypeKind.GEN);
      checkClassParam(params.get(2), "myParam2", VertxGenClass2.class.getName(), TypeKind.GEN);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidObjectParam() throws Exception {
    gen = new Generator().generateModel(MethodWithObjectParam.class);
    assertEquals(MethodWithObjectParam.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithObjectParam.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithObjectParam";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 1);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "obj", "java.lang.Object", TypeKind.NONE);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  @Test
  public void testValidOptionsParam() throws Exception {
    gen = new Generator().generateModel(MethodWithOptionsParam.class);
    assertEquals(MethodWithOptionsParam.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithOptionsParam.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithOptionsParam";

    Consumer<MethodInfo> checker = (method) -> {
      checkMethod(method, methodName, null, "void", false, false, false, false, false, false, 1);
      List<ParamInfo> params = method.getParams();
      checkClassParam(params.get(0), "options", NetServerOptions.class.getName(), TypeKind.OPTIONS);
    };

    MethodInfo method = gen.getMethods().get(0);
    checker.accept(method);

    assertEquals(1, gen.getSquashedMethods().size());
    MethodInfo squashed = gen.getSquashedMethods().get(methodName);
    assertNotNull(squashed);
    checker.accept(squashed);
  }

  // Valid returns

  @Test
  public void testGenericInterface() throws Exception {
    gen = new Generator().generateModel(GenericInterface.class);
    assertEquals(GenericInterface.class.getName() + "<T>", gen.getIfaceFQCN());
    assertEquals(GenericInterface.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());

    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, "T", false, false, false, false, false, false, 1);
      List<ParamInfo> params = methods.get(0).getParams();
      checkClassParam(params.get(0), "str", "java.lang.String", TypeKind.NONE);
      checkMethod(methods.get(1), "someGenericMethod", null, "io.vertx.test.codegen.testapi.GenericInterface<R>", false, false, false, false, false, false, 0);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testGenericInterfaceWithUpperBound() throws Exception {
    try {
      gen = new Generator().generateModel(GenericInterfaceWithUpperBound.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testValidBasicReturn() throws Exception {
    gen = new Generator().generateModel(MethodWithValidBasicReturn.class);
    assertEquals(MethodWithValidBasicReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidBasicReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(17, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "methodWithByteReturn", null, "byte", false, false, false, false, false, false, 0);
      checkMethod(methods.get(1), "methodWithShortReturn", null, "short", false, false, false, false, false, false, 0);
      checkMethod(methods.get(2), "methodWithIntReturn", null, "int", false, false, false, false, false, false, 0);
      checkMethod(methods.get(3), "methodWithLongReturn", null, "long", false, false, false, false, false, false, 0);
      checkMethod(methods.get(4), "methodWithFloatReturn", null, "float", false, false, false, false, false, false, 0);
      checkMethod(methods.get(5), "methodWithDoubleReturn", null, "double", false, false, false, false, false, false, 0);
      checkMethod(methods.get(6), "methodWithBooleanReturn", null, "boolean", false, false, false, false, false, false, 0);
      checkMethod(methods.get(7), "methodWithCharReturn", null, "char", false, false, false, false, false, false, 0);
      checkMethod(methods.get(8), "methodWithStringReturn", null, "java.lang.String", false, false, false, false, false, false, 0);

      checkMethod(methods.get(9), "methodWithByteObjectReturn", null, "java.lang.Byte", false, false, false, false, false, false, 0);
      checkMethod(methods.get(10), "methodWithShortObjectReturn", null, "java.lang.Short", false, false, false, false, false, false, 0);
      checkMethod(methods.get(11), "methodWithIntObjectReturn", null, "java.lang.Integer", false, false, false, false, false, false, 0);
      checkMethod(methods.get(12), "methodWithLongObjectReturn", null, "java.lang.Long", false, false, false, false, false, false, 0);
      checkMethod(methods.get(13), "methodWithFloatObjectReturn", null, "java.lang.Float", false, false, false, false, false, false, 0);
      checkMethod(methods.get(14), "methodWithDoubleObjectReturn", null, "java.lang.Double", false, false, false, false, false, false, 0);
      checkMethod(methods.get(15), "methodWithBooleanObjectReturn", null, "java.lang.Boolean", false, false, false, false, false, false, 0);
      checkMethod(methods.get(16), "methodWithCharObjectReturn", null, "java.lang.Character", false, false, false, false, false, false, 0);
    };
    checker.accept(gen.getMethods());
    assertEquals(17, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testValidVoidReturn() throws Exception {
    gen = new Generator().generateModel(MethodWithValidVoidReturn.class);
    assertEquals(MethodWithValidVoidReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidVoidReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    String methodName = "methodWithVoidReturn";
    checkMethod(gen.getMethods().get(0), methodName, null, "void", false, false, false, false, false, false, 0);
    assertEquals(1, gen.getSquashedMethods().size());
    checkMethod(gen.getSquashedMethods().get(methodName), methodName, null, "void", false, false, false, false, false, false, 0);
  }

  @Test
  public void testValidListReturn() throws Exception {
    gen = new Generator().generateModel(MethodWithValidListReturn.class);
    assertEquals(MethodWithValidListReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidListReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(11, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "byteList", null, "java.util.List<java.lang.Byte>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(1), "shortList", null, "java.util.List<java.lang.Short>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(2), "intList", null, "java.util.List<java.lang.Integer>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(3), "longList", null, "java.util.List<java.lang.Long>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(4), "floatList", null, "java.util.List<java.lang.Float>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(5), "doubleList", null, "java.util.List<java.lang.Double>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(6), "booleanList", null, "java.util.List<java.lang.Boolean>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(7), "charList", null, "java.util.List<java.lang.Character>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(8), "stringList", null, "java.util.List<java.lang.String>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(9), "vertxGen1List", null, "java.util.List<" + VertxGenClass1.class.getName() + ">", false, false, false, false, false, false, 0);
      checkMethod(methods.get(10), "vertxGen2List", null, "java.util.List<" + VertxGenClass2.class.getName() + ">", false, false, false, false, false, false, 0);
    };
    checker.accept(gen.getMethods());
    assertEquals(11, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testValidSetReturn() throws Exception {
    gen = new Generator().generateModel(MethodWithValidSetReturn.class);
    assertEquals(MethodWithValidSetReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidSetReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(11, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "byteSet", null, "java.util.Set<java.lang.Byte>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(1), "shortSet", null, "java.util.Set<java.lang.Short>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(2), "intSet", null, "java.util.Set<java.lang.Integer>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(3), "longSet", null, "java.util.Set<java.lang.Long>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(4), "floatSet", null, "java.util.Set<java.lang.Float>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(5), "doubleSet", null, "java.util.Set<java.lang.Double>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(6), "booleanSet", null, "java.util.Set<java.lang.Boolean>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(7), "charSet", null, "java.util.Set<java.lang.Character>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(8), "stringSet", null, "java.util.Set<java.lang.String>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(9), "vertxGen1Set", null, "java.util.Set<" + VertxGenClass1.class.getName() + ">", false, false, false, false, false, false, 0);
      checkMethod(methods.get(10), "vertxGen2Set", null, "java.util.Set<" + VertxGenClass2.class.getName() + ">", false, false, false, false, false, false, 0);
    };
    checker.accept(gen.getMethods());
    assertEquals(11, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testValidVertxGenReturn() throws Exception {
    gen = new Generator().generateModel(MethodWithValidVertxGenReturn.class);
    assertEquals(MethodWithValidVertxGenReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidVertxGenReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "methodWithVertxGen1Return", null, VertxGenClass1.class.getName(), false, false, false, false, false, false, 0);
      checkMethod(methods.get(1), "methodWithVertxGen2Return", null, VertxGenClass2.class.getName(), false, false, false, false, false, false, 0);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testGenIgnore() throws Exception {
    gen = new Generator().generateModel(InterfaceWithIgnoredMethods.class);
    assertEquals(InterfaceWithIgnoredMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithIgnoredMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testFluentMethods() throws Exception {
    gen = new Generator().generateModel(InterfaceWithFluentMethods.class);
    assertEquals(InterfaceWithFluentMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithFluentMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, InterfaceWithFluentMethods.class.getName(), false, true, false, false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, InterfaceWithFluentMethods.class.getName(), false, true, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testCacheReturnMethods() throws Exception {
    gen = new Generator().generateModel(InterfaceWithCacheReturnMethods.class);
    assertEquals(InterfaceWithCacheReturnMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithCacheReturnMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(1, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, String.class.getName(), true, false, false, false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, VertxGenClass1.class.getName(), true, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testIndexGetterSetterMethods() throws Exception {
    gen = new Generator().generateModel(InterfaceWithIndexSetterGetterMethods.class);
    assertEquals(InterfaceWithIndexSetterGetterMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithIndexSetterGetterMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "getAt", null, "byte", false, false, true, false, false, false, 1);
      checkMethod(methods.get(1), "setAt", null, "void", false, false, false, true, false, false, 2);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testSupertypes() throws Exception {
    Source gen = new Generator().generateModel(InterfaceWithSupertypes.class, VertxGenInterface1.class, VertxGenInterface2.class);
    assertEquals(InterfaceWithSupertypes.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithSupertypes.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(3, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterface1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenInterface2.class.getName()));
    assertEquals(3, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenClass1.class)));
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenInterface1.class)));
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(VertxGenInterface2.class)));
    assertEquals(1, gen.getConcreteSuperTypes().size());
    assertTrue(gen.getConcreteSuperTypes().contains(TypeInfo.create(VertxGenClass1.class)));
    assertEquals(2, gen.getAbstractSuperTypes().size());
    assertTrue(gen.getAbstractSuperTypes().contains(TypeInfo.create(VertxGenInterface1.class)));
    assertTrue(gen.getAbstractSuperTypes().contains(TypeInfo.create(VertxGenInterface2.class)));
    assertEquals(3, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "quux", null, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(1), "bar", null, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(2), "juu", null, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(3, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testParameterizedClassSuperType() throws Exception {
    gen = new Generator().generateModel(InterfaceWithParameterizedDeclaredSupertype.class);
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedDeclaredSupertype.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(1, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(GenericInterface.class.getName()));
    assertEquals(1, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(InterfaceWithParameterizedDeclaredSupertype.class.getGenericInterfaces()[0])));
  }

  @Test
  public void testParameterizedVariableSuperType() throws Exception {
    gen = new Generator().generateModel(InterfaceWithParameterizedVariableSupertype.class);
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getName() + "<T>", gen.getIfaceFQCN());
    assertEquals(InterfaceWithParameterizedVariableSupertype.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(1, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(GenericInterface.class.getName()));
    assertEquals(1, gen.getSuperTypes().size());
    assertTrue(gen.getSuperTypes().contains(TypeInfo.create(InterfaceWithParameterizedVariableSupertype.class.getGenericInterfaces()[0])));
  }

  @Test
  public void testNonGenSuperType() throws Exception {
    gen = new Generator().generateModel(InterfaceWithNonGenSuperType.class);
    assertEquals(InterfaceWithNonGenSuperType.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithNonGenSuperType.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(0, gen.getReferencedTypes().size());
    assertEquals(0, gen.getSuperTypes().size());
    assertEquals(1, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(1, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testParameterizedForbiddenSuperType() throws Exception {
    Class<?>[] forbidenTypes = {
        InterfaceWithParameterizedArraySupertype.class,
        InterfaceWithParameterizedGenericArraySupertype.class
    };
    for (Class<?> forbidenType : forbidenTypes) {
      try {
        gen = new Generator().generateModel(forbidenType);
        fail();
      } catch (GenException e) {
      }
    }
  }

  @Test
  public void testOverloadedMethods() throws Exception {
    gen = new Generator().generateModel(InterfaceWithOverloadedMethods.class);
    assertEquals(InterfaceWithOverloadedMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithOverloadedMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(5, gen.getMethods().size());
    checkMethod(gen.getMethods().get(0), "foo", null, "void", false, false, false, false, false, false, 1);
    checkClassParam(gen.getMethods().get(0).getParams().get(0), "str", String.class.getName(), TypeKind.NONE);
    checkMethod(gen.getMethods().get(1), "foo", null, "void", false, false, false, false, false, false, 2);
    checkClassParam(gen.getMethods().get(1).getParams().get(0), "str", String.class.getName(), TypeKind.NONE);
    checkParam(gen.getMethods().get(1).getParams().get(1), "time", "long");
    checkMethod(gen.getMethods().get(2), "foo", null, "void", false, false, false, false, false, false, 3);
    checkClassParam(gen.getMethods().get(2).getParams().get(0), "str", String.class.getName(), TypeKind.NONE);
    checkParam(gen.getMethods().get(2).getParams().get(1), "time", "long");
    checkClassParam(gen.getMethods().get(2).getParams().get(2), "handler", "io.vertx.core.Handler<" + VertxGenClass1.class.getName() + ">", TypeKind.HANDLER);
    checkMethod(gen.getMethods().get(3), "bar", null, "void", false, false, false, false, false, false, 1);
    checkClassParam(gen.getMethods().get(3).getParams().get(0), "obj1", VertxGenClass2.class.getName(), TypeKind.GEN);
    checkMethod(gen.getMethods().get(4), "bar", null, "void", false, false, false, false, false, false, 2);
    checkClassParam(gen.getMethods().get(4).getParams().get(0), "obj1", VertxGenClass2.class.getName(), TypeKind.GEN);
    checkClassParam(gen.getMethods().get(4).getParams().get(1), "str", String.class.getName(), TypeKind.NONE);

    assertEquals(2, gen.getSquashedMethods().size());
    MethodInfo squashed1 = gen.getSquashedMethods().get("foo");
    checkMethod(squashed1, "foo", null, "void", false, false, false, false, false, true, 3);
    checkClassParam(squashed1.getParams().get(0), "str", String.class.getName(), TypeKind.NONE);
    checkParam(squashed1.getParams().get(1), "time", "long");
    checkClassParam(squashed1.getParams().get(2), "handler", "io.vertx.core.Handler<" + VertxGenClass1.class.getName() + ">", TypeKind.HANDLER);

    MethodInfo squashed2 = gen.getSquashedMethods().get("bar");
    checkMethod(squashed2, "bar", null, "void", false, false, false, false, false, true, 2);
    checkClassParam(squashed2.getParams().get(0), "obj1", VertxGenClass2.class.getName(), TypeKind.GEN);
    checkClassParam(squashed2.getParams().get(1), "str", String.class.getName(), TypeKind.NONE);

    assertEquals(2, gen.getMethodMap().size());
    List<MethodInfo> meths1 = gen.getMethodMap().get("foo");
    assertEquals(3, meths1.size());
    assertSame(gen.getMethods().get(0), meths1.get(0));
    assertSame(gen.getMethods().get(1), meths1.get(1));
    assertSame(gen.getMethods().get(2), meths1.get(2));
    List<MethodInfo> meths2 = gen.getMethodMap().get("bar");
    assertEquals(2, meths2.size());
    assertSame(gen.getMethods().get(3), meths2.get(0));
    assertSame(gen.getMethods().get(4), meths2.get(1));
  }

  @Test
  public void testStaticMethods() throws Exception {
    gen = new Generator().generateModel(InterfaceWithStaticMethods.class);
    assertEquals(InterfaceWithStaticMethods.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithStaticMethods.class.getSimpleName(), gen.getIfaceSimpleName());
    assertEquals(2, gen.getReferencedTypes().size());
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass1.class.getName()));
    assertTrue(gen.getReferencedTypes().contains(VertxGenClass2.class.getName()));
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, VertxGenClass1.class.getName(), false, false, false, false, true, false, 1);
      checkMethod(methods.get(1), "bar", null, VertxGenClass2.class.getName(), false, false, false, false, true, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testMethodOverride() throws Exception {
    gen = new Generator().generateModel(InterfaceWithMethodOverride.class, VertxGenInterface1.class, VertxGenInterface2.class);
    assertEquals(2, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "bar", null, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(1), "juu", null, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
    checkClassParam(gen.getMethods().get(0).getParams().get(0), "str", String.class.getName(), TypeKind.NONE);
    checkClassParam(gen.getMethods().get(1).getParams().get(0), "str_renamed", String.class.getName(), TypeKind.NONE);
  }

  @Test
  public void testInterfaceWithMethodHavingGenericOverride() throws Exception {
    gen = new Generator().generateModel(InterfaceWithMethodHavingGenericOverride.class, GenericAbstractInterface.class);
    assertEquals(4, gen.getMethods().size());
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", null, "java.lang.String", false, false, false, false, false, false, 0);
      checkMethod(methods.get(1), "bar", null, "java.util.List<java.lang.String>", false, false, false, false, false, false, 0);
      checkMethod(methods.get(2), "juu", null, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(3), "daa", null, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(4, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
    checkClassParam(gen.getMethods().get(2).getParams().get(0), "handler", "io.vertx.core.Handler<io.vertx.core.AsyncResult<java.lang.String>>", TypeKind.HANDLER);
    checkClassParam(gen.getMethods().get(3).getParams().get(0), "handler", "io.vertx.core.Handler<java.lang.String>", TypeKind.HANDLER);
  }

  @Test
  public void testMethodComments() throws Exception {
    gen = new Generator().generateModel(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    String comment1 = " Comment 1 line 1\n Comment 1 line 2\n";
    String comment2 = " Comment 2 line 1\n Comment 2 line 2\n";
    Consumer<List<MethodInfo>> checker = (methods) -> {
      checkMethod(methods.get(0), "foo", comment1, "void", false, false, false, false, false, false, 1);
      checkMethod(methods.get(1), "bar", comment2, "void", false, false, false, false, false, false, 1);
    };
    checker.accept(gen.getMethods());
    assertEquals(2, gen.getSquashedMethods().size());
    checker.accept(new ArrayList<>(gen.getSquashedMethods().values()));
  }

  @Test
  public void testInterfaceComments() throws Exception {
    gen = new Generator().generateModel(InterfaceWithComments.class);
    assertEquals(InterfaceWithComments.class.getName(), gen.getIfaceFQCN());
    assertEquals(InterfaceWithComments.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    String comment =
      " Interface comment line 1\n" +
      " Interface comment line 2\n" +
      " Interface comment line 3\n\n" +
      " @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";
    assertEquals(comment, gen.getIfaceComment());
  }

  @Test
  public void testJsonParams() throws Exception {
    gen = new Generator().generateModel(MethodWithValidJSONParams.class);
    assertEquals(MethodWithValidJSONParams.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidJSONParams.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    checkMethod(gen.getMethods().get(0), "methodWithJsonParams", null, "void", false, false, false, false, false, false, 2);
    checkClassParam(gen.getMethods().get(0).getParams().get(0), "jsonObject", JsonObject.class.getName(), TypeKind.JSON_OBJECT);
    checkClassParam(gen.getMethods().get(0).getParams().get(1), "jsonArray", JsonArray.class.getName(), TypeKind.JSON_ARRAY);
  }

  @Test
  public void testJsonHandlers() throws Exception {
    gen = new Generator().generateModel(MethodWithValidHandlerJSON.class);
    assertEquals(MethodWithValidHandlerJSON.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerJSON.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    checkMethod(gen.getMethods().get(0), "methodWithJsonHandlers", null, "void", false, false, false, false, false, false, 2);
    checkClassParam(gen.getMethods().get(0).getParams().get(0), "jsonObjectHandler", Handler.class.getName() + "<" + JsonObject.class.getName() + ">", TypeKind.HANDLER);
    checkClassParam(gen.getMethods().get(0).getParams().get(1), "jsonArrayHandler", Handler.class.getName() + "<" + JsonArray.class.getName() + ">", TypeKind.HANDLER);
  }

  @Test
  public void testJsonAsyncResultHandlers() throws Exception {
    gen = new Generator().generateModel(MethodWithValidHandlerAsyncResultJSON.class);
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidHandlerAsyncResultJSON.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(1, gen.getMethods().size());
    checkMethod(gen.getMethods().get(0), "methodwithJsonHandlersAsyncResult", null, "void", false, false, false, false, false, false, 2);
    checkClassParam(gen.getMethods().get(0).getParams().get(0), "jsonObjectHandler", Handler.class.getName() + "<" + AsyncResult.class.getName() + "<" + JsonObject.class.getName() + ">>", TypeKind.HANDLER);
    checkClassParam(gen.getMethods().get(0).getParams().get(1), "jsonArrayHandler", Handler.class.getName() + "<" + AsyncResult.class.getName() + "<" + JsonArray.class.getName() + ">>", TypeKind.HANDLER);
  }

  @Test
  public void testJsonReturns() throws Exception {
    gen = new Generator().generateModel(MethodWithValidJSONReturn.class);
    assertEquals(MethodWithValidJSONReturn.class.getName(), gen.getIfaceFQCN());
    assertEquals(MethodWithValidJSONReturn.class.getSimpleName(), gen.getIfaceSimpleName());
    assertTrue(gen.getReferencedTypes().isEmpty());
    assertTrue(gen.getSuperTypes().isEmpty());
    assertEquals(2, gen.getMethods().size());
    checkMethod(gen.getMethods().get(0), "foo", null, JsonObject.class.getName(), false, false, false, false, false, false, 0);
    checkMethod(gen.getMethods().get(1), "bar", null, JsonArray.class.getName(), false, false, false, false, false, false, 0);
  }

/*
  @Test
  public void testGenerateModelMoreThanOnce() throws Exception {
    Generator generator = new Generator();
    gen = generator.generateModel(InterfaceWithComments.class);
    try {
      gen = generator.generateModel(InterfaceWithComments.class);
      fail("Should throw exception");
    } catch (IllegalStateException e) {
      // OK
    }
  }
*/

  //

//  @Test
//  public void testValidateCorePackage() throws Exception {
//    gen.validatePackage("io.vertx.codegen.testmodel", packageName -> packageName.contains("eventbus") && !packageName.contains("impl"));
//  }


  /*
  TODO

  also test Handlers ansd AsyncHandlers with :
  type io.vertx.core.Handler<? extends io.vertx.codegen.testmodel.eventbus.Message>

  test that supertype gets full generic type name, but referenced type only gets non generic part

  test that we CAN gen an empty interface IF it extends supertype

  better error messages - show interface and method

  tests that actually generate stuff
   */

  private void checkMethod(MethodInfo meth, String name, String comment, String returnType, boolean cacheReturn,
                           boolean fluent, boolean indexGetter, boolean indexSetter, boolean staticMethod,
                           boolean squashed, int numParams) {

    assertEquals(name, meth.getName());
    assertEquals(comment, meth.getComment());
    assertEquals(returnType, meth.getReturnType().toString());
    assertEquals(cacheReturn, meth.isCacheReturn());
    assertEquals(fluent, meth.isFluent());
    assertEquals(indexGetter, meth.isIndexGetter());
    assertEquals(indexSetter, meth.isIndexSetter());
    assertEquals(staticMethod, meth.isStaticMethod());
    assertEquals(squashed, meth.isSquashed());
    assertEquals(numParams, meth.getParams().size());
  }

  private void checkParam(ParamInfo param, String name, String type) {
    assertEquals(name, param.getName());
    assertEquals(type, param.getType().toString());
  }

  private void checkClassParam(ParamInfo param, String name, String type, TypeKind kind) {
    checkParam(param, name, type);
    TypeInfo.Class classType;
    if (param.getType() instanceof TypeInfo.Class) {
      classType = (TypeInfo.Class) param.getType();
    } else {
      classType = ((TypeInfo.Parameterized) param.getType()).getRaw();
    }
    assertEquals(kind, classType.getKind());
  }
}
