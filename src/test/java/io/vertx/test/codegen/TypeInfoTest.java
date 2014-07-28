package io.vertx.test.codegen;

import io.vertx.codegen.TypeInfo;
import io.vertx.codegen.TypeKind;
import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedDeclaredSupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedVariableSupertype;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeInfoTest {

  @Test
  public void testVoid() throws Exception {
    TypeInfo.Void info = (TypeInfo.Void) TypeInfo.create(void.class);
    assertEquals("void", info.getName());
    assertEquals("void", info.getSimpleName());
  }

  @Test
  public void testPrimitive() throws Exception {
    TypeInfo.Primitive info = (TypeInfo.Primitive) TypeInfo.create(int.class);
    assertEquals("int", info.getName());
    assertEquals("int", info.getSimpleName());
  }

  @Test
  public void testParameterizedWithClass() {
    TypeInfo.Parameterized info = (TypeInfo.Parameterized) TypeInfo.create(InterfaceWithParameterizedDeclaredSupertype.class.getGenericInterfaces()[0]);
    assertEquals("io.vertx.test.codegen.testapi.GenericInterface<java.lang.String>", info.getName());
    assertEquals("GenericInterface<String>", info.getSimpleName());
    assertEquals("foo.bar.GenericInterface<java.lang.String>", info.renamePackage("io.vertx.test.codegen.testapi", "foo.bar").getName());
    assertEquals("io.vertx.test.codegen.testapi.GenericInterface<foo.bar.String>", info.renamePackage("java.lang", "foo.bar").getName());
  }

  @Test
  public void testParameterizedWithTypeVariable() {
    TypeInfo.Parameterized info = (TypeInfo.Parameterized) TypeInfo.create(InterfaceWithParameterizedVariableSupertype.class.getGenericInterfaces()[0]);
    assertEquals("io.vertx.test.codegen.testapi.GenericInterface<T>", info.getName());
    assertEquals("GenericInterface<T>", info.getSimpleName());
    assertEquals("foo.bar.GenericInterface<T>", info.renamePackage("io.vertx.test.codegen.testapi", "foo.bar").getName());
  }

  @Test
  public void testTypeVariable() throws Exception {
    Method m = GenericInterface.class.getMethods()[0];
    TypeInfo.Variable info = (TypeInfo.Variable) TypeInfo.create(m.getGenericReturnType());
    assertEquals("T", info.getName());
    assertEquals("T", info.getSimpleName());
  }

  @Test
  public void testClass() {
    TypeInfo.Class info = (TypeInfo.Class) TypeInfo.create(String.class);
    assertEquals("java.lang.String", info.getName());
    assertEquals("String", info.getSimpleName());
    assertEquals("foo.bar.String", info.renamePackage("java.lang", "foo.bar").getName());
  }

  @Test
  public void testInterface() {
    TypeInfo.Class info = (TypeInfo.Class) TypeInfo.create(Runnable.class);
    assertEquals("java.lang.Runnable", info.getName());
    assertEquals("Runnable", info.getSimpleName());
    assertEquals("foo.bar.Runnable", info.renamePackage("java.lang", "foo.bar").getName());
  }

  // TypeKind testing

  @Test
  public void testComposeKinds() {
    abstract class Container implements AsyncResult<List<String>>  {}
    TypeInfo.Parameterized info = (TypeInfo.Parameterized) TypeInfo.create(Container.class.getGenericInterfaces()[0]);
    assertEquals(TypeKind.ASYNC_RESULT, info.getKind());
    TypeInfo.Parameterized a = (TypeInfo.Parameterized) info.getArgs().get(0);
    assertEquals(TypeKind.LIST, a.getKind());
    TypeInfo.Class b = (TypeInfo.Class) a.getArgs().get(0);
    assertEquals(TypeKind.STRING, b.getKind());
  }

  @Test
  public void testPrimitiveKind() {

    @VertxGen class ApiObject {}
    @Options class OptionsObject {}
    class Other {}

    assertEquals(TypeKind.OTHER, TypeInfo.create(Other.class).getKind());
    assertEquals(TypeKind.OPTIONS, TypeInfo.create(OptionsObject.class).getKind());
    assertEquals(TypeKind.API, TypeInfo.create(ApiObject.class).getKind());
    assertEquals(TypeKind.HANDLER, TypeInfo.create(Handler.class).getKind());
    assertEquals(TypeKind.ASYNC_RESULT, TypeInfo.create(AsyncResult.class).getKind());
    assertEquals(TypeKind.VOID, TypeInfo.create(Void.class).getKind());
    assertEquals(TypeKind.JSON_ARRAY, TypeInfo.create(JsonArray.class).getKind());
    assertEquals(TypeKind.JSON_OBJECT, TypeInfo.create(JsonObject.class).getKind());
    assertEquals(TypeKind.OBJECT, TypeInfo.create(Object.class).getKind());
    assertEquals(TypeKind.STRING, TypeInfo.create(String.class).getKind());
    assertEquals(TypeKind.LIST, TypeInfo.create(List.class).getKind());
    assertEquals(TypeKind.SET, TypeInfo.create(Set.class).getKind());
    assertEquals(TypeKind.THROWABLE, TypeInfo.create(Throwable.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(boolean.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(int.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(long.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(double.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(float.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(byte.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(short.class).getKind());
    assertEquals(TypeKind.PRIMITIVE, TypeInfo.create(char.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Boolean.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Integer.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Long.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Double.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Float.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Byte.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Short.class).getKind());
    assertEquals(TypeKind.BOXED_PRIMITIVE, TypeInfo.create(Character.class).getKind());
  }

  @Test
  public void testBoxedPrimitiveKind() {
  }

  @Test
  public void testGetErased() {
    abstract class Container<M> implements AsyncResult<List<M>>  {}
    abstract class Expected implements AsyncResult<List<Object>>  {}
    TypeInfo.Parameterized info = (TypeInfo.Parameterized) TypeInfo.create(Container.class.getGenericInterfaces()[0]);
    TypeInfo.Parameterized expected = (TypeInfo.Parameterized) TypeInfo.create(Expected.class.getGenericInterfaces()[0]);
    assertEquals(expected, info.getErased());
  }
}
