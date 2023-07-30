package io.vertx.test.codegen;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NameTest {

  @Test
  public void testPrimitive() {
    assertEquals("int", int.class.getName());
    assertEquals("int", int.class.getCanonicalName());
    assertEquals("int", int.class.getTypeName());
    assertEquals("int", int.class.getSimpleName());
  }

  @Test
  public void testPrimitiveArray() {
    assertEquals("[I", int[].class.getName());
    assertEquals("int[]", int[].class.getCanonicalName());
    assertEquals("int[]", int[].class.getTypeName());
    assertEquals("int[]", int[].class.getSimpleName());
  }

  @Test
  public void testClass() {
    assertEquals("java.util.Locale", Locale.class.getName());
    assertEquals("java.util.Locale", Locale.class.getCanonicalName());
    assertEquals("java.util.Locale", Locale.class.getTypeName());
    assertEquals("Locale", Locale.class.getSimpleName());
  }

  @Test
  public void testClassArray() {
    assertEquals("[Ljava.util.Locale;", Locale[].class.getName());
    assertEquals("java.util.Locale[]", Locale[].class.getCanonicalName());
    assertEquals("java.util.Locale[]", Locale[].class.getTypeName());
    assertEquals("Locale[]", Locale[].class.getSimpleName());
  }

  @Test
  public void testGenericClass() {
    assertEquals("java.util.List", List.class.getName());
    assertEquals("java.util.List", List.class.getCanonicalName());
    assertEquals("java.util.List", List.class.getTypeName());
    assertEquals("List", List.class.getSimpleName());
  }

  @Test
  public void testParameterizedType() {
    abstract class MapOfSomething<V> implements Map<String, V> {}
    Type type = MapOfSomething.class.getGenericInterfaces()[0];
    assertEquals("java.util.Map<java.lang.String, V>", type.getTypeName());
  }

  @Test
  public void testTypeVariable() throws Exception {
    abstract class Something<T> { T t; }
    Type type = Something.class.getDeclaredField("t").getGenericType();
    assertEquals("T", type.getTypeName());
  }
}
