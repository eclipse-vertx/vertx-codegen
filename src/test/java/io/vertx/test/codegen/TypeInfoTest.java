package io.vertx.test.codegen;

import static org.junit.Assert.*;

import io.vertx.codegen.TypeInfo;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedDeclaredSupertype;
import io.vertx.test.codegen.testapi.InterfaceWithParameterizedVariableSupertype;
import org.junit.Test;

import java.lang.reflect.Method;

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
    Method m = GenericInterface.class.getDeclaredMethod("foo", String.class);
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
}
