package io.vertx.test.codegen;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.ClassModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.nullable.DiamondGenericBottomFluentNullableParam;
import io.vertx.test.codegen.testapi.nullable.InterfaceWithInvalidListNullableParamOverride;
import io.vertx.test.codegen.testapi.nullable.InterfaceWithInvalidNullableParamOverride;
import io.vertx.test.codegen.testapi.nullable.InterfaceWithInvalidNullableReturnOverride;
import io.vertx.test.codegen.testapi.nullable.InterfaceWithNullableReturnMethod;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableTypeArgumentHandler;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableTypeArgumentHandlerAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableTypeArgumentParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableTypeArgumentReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithListNullableParamOverride;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableNonAnnotatedObjectParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableNonAnnotatedTypeVariableHandlerAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableNonAnnotatedTypeVariableHandler;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableNonAnnotatedTypeVariableParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableNonAnnotatedTypeVariableReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableParamOverride;
import io.vertx.test.codegen.testapi.nullable.InterfaceWithNullableReturnOverride;
import io.vertx.test.codegen.testapi.nullable.MethodWithHandlerNullable;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidHandlerNullableVoid;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidHandlerAsyncResultNullableVoid;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidHandlerNullableAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableBooleanReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableByteReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableCharReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableDoubleReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableFloatReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableFluentReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableIntReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableLongReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidNullableShortReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithInvalidOverloadedNullableParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableHandler;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableHandlerAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableStringHandlerAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableTypeArgReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableTypeVariableHandlerAsyncResult;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableTypeVariableHandler;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableTypeVariableParam;
import io.vertx.test.codegen.testapi.nullable.MethodWithNullableTypeVariableReturn;
import io.vertx.test.codegen.testapi.nullable.MethodWithOverloadedNullableParam;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ClassNullableTest extends ClassTestBase {

  // Invalid params

  @Test
  public void testMethodWithHandlerNullableVoid() throws Exception {
    assertGenInvalid(MethodWithInvalidHandlerNullableVoid.class);
  }

  @Test
  public void testMethodWithInvalidHandlerNullableAsyncResult() throws Exception {
    assertGenInvalid(MethodWithInvalidHandlerNullableAsyncResult.class);
  }

  @Test
  public void testMethodWithInvalidHandlerAsyncResultNullableVoid() throws Exception {
    assertGenInvalid(MethodWithInvalidHandlerAsyncResultNullableVoid.class);
  }

  @Test
  public void testInterfaceWithInvalidNullableParamOverride() throws Exception {
    assertGenInvalid(InterfaceWithInvalidNullableParamOverride.class);
  }

  @Test
  public void testInterfaceWithInvalidListNullableParamOverride() throws Exception {
    assertGenInvalid(InterfaceWithInvalidListNullableParamOverride.class);
  }

  @Test
  public void testMethodWithInvalidNullableTypeArgumentParam() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableTypeArgumentParam.class);
  }

  @Test
  public void testMethodWithInvalidNullableTypeArgumentHandler() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableTypeArgumentHandler.class);
  }

  @Test
  public void testMethodWithInvalidNullableTypeArgumentHandlerAsyncResult() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableTypeArgumentHandlerAsyncResult.class);
  }

  // Invalid returns

  @Test
  public void testMethodWithInvalidNullableBooleanReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableBooleanReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableByteReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableByteReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableShortReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableShortReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableIntReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableIntReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableLongReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableLongReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableFloatReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableFloatReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableDoubleReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableDoubleReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableCharReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableCharReturn.class);
  }

  @Test
  public void testMethodWithInvalidOverloadedNullableParam() throws Exception {
    assertGenInvalid(MethodWithInvalidOverloadedNullableParam.class);
  }

  @Test
  public void testInterfaceWithInvalidNullableReturnOverride() throws Exception {
    assertGenInvalid(InterfaceWithInvalidNullableReturnOverride.class);
  }

  @Test
  public void testMethodWithInvalidNullableFluentReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableFluentReturn.class);
  }

  @Test
  public void testMethodWithInvalidNullableTypeArgumentReturn() throws Exception {
    assertGenInvalid(MethodWithInvalidNullableTypeArgumentReturn.class);
  }

  // Valid params

  @Test
  public void testMethodWithNullableParam() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.OTHER);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableParam.class);
  }

  @Test
  public void testMethodWithNullableHandler() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.HANDLER);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableHandler.class);
  }

  @Test
  public void testMethodWithNullableHandlerAsyncResult() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.FUTURE);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableHandlerAsyncResult.class);
  }

  @Test
  public void testInterfaceWithNullableParamOverride() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableParamOverride.class, MethodWithNullableParam.class);
  }

  @Test
  public void testInterfaceWithListNullableParamOverride() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithListNullableParamOverride.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    MethodInfo mi2 = methods.get(0);
    assertTrue(((ParameterizedTypeInfo) mi2.getParams().get(0).getType()).getArg(0).isNullable());
  }

  @Test
  public void testMethodWithHandlerNullable() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithHandlerNullable.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(1, methods.size());
    MethodInfo mi1 = methods.get(0);
    assertTrue(((ParameterizedTypeInfo) mi1.getParams().get(0).getType()).getArg(0).isNullable());
  }

  @Test
  public void testMethodWithNullableTypeVariableParam() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.OTHER);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableTypeVariableParam.class);
  }

  @Test
  public void testMethodWithNullableNonAnnotatedTypeVariableParam() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.OTHER);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableNonAnnotatedTypeVariableParam.class);
  }

  @Test
  public void testMethodWithNullableNonAnnotatedObjectParam() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.OTHER);
      assertTrue(mi1.getParams().get(0).isNullable());
    }, MethodWithNullableNonAnnotatedObjectParam.class);
  }

  @Test
  public void testMethodWithNullableTypeVariableHandler() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.HANDLER);
      assertTrue(mi1.getParams().get(0).isNullableCallback());
    }, MethodWithNullableTypeVariableHandler.class);
  }

  @Test
  public void testMethodWithNullableNonAnnotatedTypeVariableHandler() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.HANDLER);
      assertTrue(mi1.getParams().get(0).isNullableCallback());
    }, MethodWithNullableNonAnnotatedTypeVariableHandler.class);
  }

  @Test
  public void testMethodWithNullableTypeVariableHandlerAsyncResult() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.FUTURE);
      assertTrue(mi1.getParams().get(0).isNullableCallback());
    }, MethodWithNullableTypeVariableHandlerAsyncResult.class);
  }

  @Test
  public void testMethodWithNullableStringHandlerAsyncResult() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.FUTURE);
      assertTrue(mi1.getParams().get(0).isNullableCallback());
    }, MethodWithNullableStringHandlerAsyncResult.class);
  }

  @Test
  public void testMethodWithNullableNonAnnotatedTypeVariableHandlerAsyncResult() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 1, "void", MethodKind.FUTURE);
      assertTrue(mi1.getParams().get(0).isNullableCallback());
    }, MethodWithNullableNonAnnotatedTypeVariableHandlerAsyncResult.class);
  }

  @Test
  public void testMethodWithOverloadedNullableParam() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(2, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 2, "void", MethodKind.OTHER);
      assertEquals(ClassKind.STRING, mi1.getParams().get(0).getType().getKind());
      checkParam(mi1.getParams().get(0), "s", String.class);
      assertTrue(mi1.getParams().get(0).isNullable());
      checkParam(mi1.getParams().get(1), "i", Integer.class);
      assertFalse(mi1.getParams().get(1).isNullable());
      MethodInfo mi2 = methods.get(1);
      checkMethod(mi2, "method", 2, "void", MethodKind.OTHER);
      checkParam(mi2.getParams().get(0), "i", Integer.class);
      assertFalse(mi2.getParams().get(0).isNullable());
      checkParam(mi2.getParams().get(1), "s", String.class);
      assertTrue(mi2.getParams().get(1).isNullable());
    }, MethodWithOverloadedNullableParam.class);
  }

  // Valid returns

  @Test
  public <T> void testMethodWithNullableReturn() throws Exception {
    AtomicBoolean checkModel = new AtomicBoolean();
    TypeLiteral<T> typeLiteral = new TypeLiteral<T>() {};
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(50, methods.size());
      checkMethod(methods.get(0), "nullableByteReturn", 0, new TypeLiteral<Byte>() {}, MethodKind.OTHER);
      checkMethod(methods.get(1), "nullableShortReturn", 0, new TypeLiteral<Short>() {}, MethodKind.OTHER);
      checkMethod(methods.get(2), "nullableIntegerReturn", 0, new TypeLiteral<Integer>() {}, MethodKind.OTHER);
      checkMethod(methods.get(3), "nullableLongReturn", 0, new TypeLiteral<Long>() {}, MethodKind.OTHER);
      checkMethod(methods.get(4), "nullableFloatReturn", 0, new TypeLiteral<Float>() {}, MethodKind.OTHER);
      checkMethod(methods.get(5), "nullableDoubleReturn", 0, new TypeLiteral<Double>() {}, MethodKind.OTHER);
      checkMethod(methods.get(6), "nullableCharacterReturn", 0, new TypeLiteral<Character>() {}, MethodKind.OTHER);
      checkMethod(methods.get(7), "nullableStringReturn", 0, new TypeLiteral<String>() {}, MethodKind.OTHER);
      checkMethod(methods.get(8), "nullableJsonObjectReturn", 0, new TypeLiteral<JsonObject>() {}, MethodKind.OTHER);
      checkMethod(methods.get(9), "nullableJsonArrayReturn", 0, new TypeLiteral<JsonArray>() {}, MethodKind.OTHER);
      checkMethod(methods.get(10), "nullableTypeVariableReturn", 0, typeLiteral, MethodKind.OTHER);
      checkMethod(methods.get(11), "nullableEnumReturn", 0, new TypeLiteral<TestEnum>() {}, MethodKind.OTHER);
      checkMethod(methods.get(12), "nullableDataObjectReturn", 0, new TypeLiteral<TestDataObject>() {}, MethodKind.OTHER);
      checkMethod(methods.get(13), "nullableApiReturn", 0, new TypeLiteral<VertxGenClass1>() {}, MethodKind.OTHER);
      checkMethod(methods.get(14), "nullableListByteReturn", 0, new TypeLiteral<List<Byte>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(15), "nullableListShortReturn", 0, new TypeLiteral<List<Short>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(16), "nullableListIntegerReturn", 0, new TypeLiteral<List<Integer>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(17), "nullableListLongReturn", 0, new TypeLiteral<List<Long>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(18), "nullableListFloatReturn", 0, new TypeLiteral<List<Float>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(19), "nullableListDoubleReturn", 0, new TypeLiteral<List<Double>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(20), "nullableListCharacterReturn", 0, new TypeLiteral<List<Character>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(21), "nullableListStringReturn", 0, new TypeLiteral<List<String>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(22), "nullableListJsonObjectReturn", 0, new TypeLiteral<List<JsonObject>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(23), "nullableListJsonArrayReturn", 0, new TypeLiteral<List<JsonArray>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(24), "nullableListEnumReturn", 0, new TypeLiteral<List<TestEnum>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(25), "nullableListDataObjectReturn", 0, new TypeLiteral<List<TestDataObject>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(26), "nullableListApiReturn", 0, new TypeLiteral<List<VertxGenClass1>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(27), "nullableSetByteReturn", 0, new TypeLiteral<Set<Byte>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(28), "nullableSetShortReturn", 0, new TypeLiteral<Set<Short>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(29), "nullableSetIntegerReturn", 0, new TypeLiteral<Set<Integer>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(30), "nullableSetLongReturn", 0, new TypeLiteral<Set<Long>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(31), "nullableSetFloatReturn", 0, new TypeLiteral<Set<Float>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(32), "nullableSetDoubleReturn", 0, new TypeLiteral<Set<Double>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(33), "nullableSetCharacterReturn", 0, new TypeLiteral<Set<Character>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(34), "nullableSetStringReturn", 0, new TypeLiteral<Set<String>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(35), "nullableSetJsonObjectReturn", 0, new TypeLiteral<Set<JsonObject>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(36), "nullableSetJsonArrayReturn", 0, new TypeLiteral<Set<JsonArray>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(37), "nullableSetEnumReturn", 0, new TypeLiteral<Set<TestEnum>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(38), "nullableSetDataObjectReturn", 0, new TypeLiteral<Set<TestDataObject>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(39), "nullableSetApiReturn", 0, new TypeLiteral<Set<VertxGenClass1>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(40), "nullableMapByteReturn", 0, new TypeLiteral<Map<String, Byte>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(41), "nullableMapShortReturn", 0, new TypeLiteral<Map<String, Short>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(42), "nullableMapIntegerReturn", 0, new TypeLiteral<Map<String, Integer>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(43), "nullableMapLongReturn", 0, new TypeLiteral<Map<String, Long>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(44), "nullableMapFloatReturn", 0, new TypeLiteral<Map<String, Float>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(45), "nullableMapDoubleReturn", 0, new TypeLiteral<Map<String, Double>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(46), "nullableMapCharacterReturn", 0, new TypeLiteral<Map<String, Character>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(47), "nullableMapStringReturn", 0, new TypeLiteral<Map<String, String>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(48), "nullableMapJsonObjectReturn", 0, new TypeLiteral<Map<String, JsonObject>>() {}, MethodKind.OTHER);
      checkMethod(methods.get(49), "nullableMapJsonArrayReturn", 0, new TypeLiteral<Map<String, JsonArray>>() {}, MethodKind.OTHER);
      if (!checkModel.compareAndSet(false, true)) {
        // nullableTypeVariableReturn does not pass with model api
        methods.remove(10);
      }
      methods.forEach(m -> {
        assertTrue("Expects " + m.getName() + " to have nullable return type", m.isNullableReturn());
      });
    }, MethodWithNullableReturn.class);
  }

  @Test
  public void testMethodWithNullableTypeArgReturn() throws Exception {
    ClassModel model = new Generator().generateClass(MethodWithNullableTypeArgReturn.class);
    List<MethodInfo> methods = model.getMethods();
    assertEquals(36, methods.size());
    checkMethod(methods.get(0), "listNullableByteReturn", 0, new TypeLiteral<List<Byte>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(1), "listNullableShortReturn", 0, new TypeLiteral<List<Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(2), "listNullableIntegerReturn", 0, new TypeLiteral<List<Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(3), "listNullableLongReturn", 0, new TypeLiteral<List<Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(4), "listNullableFloatReturn", 0, new TypeLiteral<List<Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(5), "listNullableDoubleReturn", 0, new TypeLiteral<List<Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(6), "listNullableCharacterReturn", 0, new TypeLiteral<List<Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(7), "listNullableStringReturn", 0, new TypeLiteral<List<String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(8), "listNullableJsonObjectReturn", 0, new TypeLiteral<List<JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(9), "listNullableJsonArrayReturn", 0, new TypeLiteral<List<JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(10), "listNullableEnumReturn", 0, new TypeLiteral<List<TestEnum>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(11), "listNullableDataObjectReturn", 0, new TypeLiteral<List<TestDataObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(12), "listNullableApiReturn", 0, new TypeLiteral<List<VertxGenClass1>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(13), "setNullableByteReturn", 0, new TypeLiteral<Set<Byte>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(14), "setNullableShortReturn", 0, new TypeLiteral<Set<Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(15), "setNullableIntegerReturn", 0, new TypeLiteral<Set<Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(16), "setNullableLongReturn", 0, new TypeLiteral<Set<Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(17), "setNullableFloatReturn", 0, new TypeLiteral<Set<Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(18), "setNullableDoubleReturn", 0, new TypeLiteral<Set<Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(19), "setNullableCharacterReturn", 0, new TypeLiteral<Set<Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(20), "setNullableStringReturn", 0, new TypeLiteral<Set<String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(21), "setNullableJsonObjectReturn", 0, new TypeLiteral<Set<JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(22), "setNullableJsonArrayReturn", 0, new TypeLiteral<Set<JsonArray>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(23), "setNullableEnumReturn", 0, new TypeLiteral<Set<TestEnum>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(24), "setNullableDataObjectReturn", 0, new TypeLiteral<Set<TestDataObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(25), "setNullableApiReturn", 0, new TypeLiteral<Set<VertxGenClass1>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(26), "mapNullableByteReturn", 0, new TypeLiteral<Map<String, Byte>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(27), "mapNullableShortReturn", 0, new TypeLiteral<Map<String, Short>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(28), "mapNullableIntegerReturn", 0, new TypeLiteral<Map<String, Integer>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(29), "mapNullableLongReturn", 0, new TypeLiteral<Map<String, Long>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(30), "mapNullableFloatReturn", 0, new TypeLiteral<Map<String, Float>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(31), "mapNullableDoubleReturn", 0, new TypeLiteral<Map<String, Double>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(32), "mapNullableCharacterReturn", 0, new TypeLiteral<Map<String, Character>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(33), "mapNullableStringReturn", 0, new TypeLiteral<Map<String, String>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(34), "mapNullableJsonObjectReturn", 0, new TypeLiteral<Map<String, JsonObject>>() {}, MethodKind.OTHER);
    checkMethod(methods.get(35), "mapNullableJsonArrayReturn", 0, new TypeLiteral<Map<String, JsonArray>>() {}, MethodKind.OTHER);

    methods.forEach(m -> {
      assertFalse("Expects " + m.getName() + " to have nullable return type", m.isNullableReturn());
      List<TypeInfo> args = ((ParameterizedTypeInfo) m.getReturnType()).getArgs();
      assertTrue("Expects " + m.getName() + " to have nullable return type", args.get(args.size() - 1).isNullable());
    });
  }

  @Test
  public void testInterfaceWithNullableReturnOverride() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi3 = methods.get(0);
      assertTrue(mi3.getReturnType().isNullable());
    }, InterfaceWithNullableReturnOverride.class, InterfaceWithNullableReturnMethod.class);
  }

  @Test
  public void testDiamondFluentNullableReturn() throws Exception {
    ClassModel model = new Generator().generateClass(DiamondGenericBottomFluentNullableParam.class);

  }

  @Test
  public void testMethodWithNullableTypeVariableReturn() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 0, "T", MethodKind.OTHER);
      assertTrue(mi1.isNullableReturn());
    }, MethodWithNullableTypeVariableReturn.class);
  }

  @Test
  public void testMethodWithNullableNonAnnotatedTypeVariableReturn() throws Exception {
    generateClass(model -> {
      List<MethodInfo> methods = model.getMethods();
      assertEquals(1, methods.size());
      MethodInfo mi1 = methods.get(0);
      checkMethod(mi1, "method", 0, "T", MethodKind.OTHER);
      assertTrue(mi1.isNullableReturn());
    }, MethodWithNullableNonAnnotatedTypeVariableReturn.class);
  }

  private void generateClass(Consumer<ClassModel> test, Class<?> clazz, Class<?>... rest) throws Exception {
//    ClassModel model = new Generator().generateClass(clazz);
//    test.accept(model);
    Thread thread = Thread.currentThread();
    ClassLoader prev = thread.getContextClassLoader();
    thread.setContextClassLoader(new ClassLoader(prev) {
      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("io.vertx.test.codegen.testapi.nullable.")) {
          throw new ClassNotFoundException();
        }
        return super.loadClass(name);
      }
    });
    ClassModel model;
    try {
      model = new Generator().generateClass(clazz, rest);
    } finally {
      thread.setContextClassLoader(prev);
    }
    test.accept(model);
  }
}
