package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.test.codegen.proxytestapi.ValidProxyCloseWithFuture;
import io.vertx.test.codegen.testapi.DeprecatedInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testdataobject.DataObjectWithProperty;
import io.vertx.test.codegen.testdataobject.DeprecatedDataObject;
import io.vertx.test.codegen.testenum.DeprecatedEnum;
import io.vertx.test.codegen.testenum.ValidEnum;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeprecatedTest {
  private Generator generator = new Generator();

  @Test
  public void testVertxGenDeprecated() throws Exception {
    ClassModel model = generator.generateClass(DeprecatedInterface.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertNotNull(model.getDeprecatedDesc());
    assertEquals(model.getDeprecatedDesc().getValue(), "deprecated info");
    MethodInfo method = model.getMethods().get(0);
    assertTrue(method.isDeprecated());
    assertNotNull(method.getDeprecatedDesc());
    assertEquals(method.getDeprecatedDesc().getValue(), "method deprecated info");
  }

  @Test
  public void testProxyGenDeprecated() throws Exception {
    ProxyModel model = generator.generateProxyModel(DeprecatedInterface.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertNotNull(model.getDeprecatedDesc());
    assertEquals(model.getDeprecatedDesc().getValue(), "deprecated info");
    MethodInfo method = model.getMethods().get(0);
    assertTrue(method.isDeprecated());
    assertNotNull(method.getDeprecatedDesc());
    assertEquals(method.getDeprecatedDesc().getValue(), "method deprecated info");
  }

  @Test
  public void testVertxGenNotDeprecated() throws Exception {
    ClassModel model = generator.generateClass(GenericInterface.class);
    assertFalse(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), false);
    assertFalse(model.getMethods().get(0).isDeprecated());
  }

  @Test
  public void testProxyGenNotDeprecated() throws Exception {
    ProxyModel model = generator.generateProxyModel(ValidProxyCloseWithFuture.class);
    assertFalse(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), false);
    assertFalse(model.getMethods().get(0).isDeprecated());
  }

  @Test
  public void testDataObjectDeprecated() throws Exception {
    DataObjectModel model = generator.generateDataObject(DeprecatedDataObject.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertNotNull(model.getDeprecatedDesc());
    assertEquals(model.getDeprecatedDesc().getValue(), "deprecated info");
    for (PropertyInfo property : model.getPropertyMap().values()) {
      assertTrue(property.isDeprecated());
      assertNotNull(property.getDeprecatedDesc());
      assertEquals(property.getDeprecatedDesc().getValue(), "property deprecated info");
    }
  }

  @Test
  public void testDataObjectNotDeprecated() throws Exception {
    DataObjectModel model = generator.generateDataObject(DataObjectWithProperty.class);
    assertFalse(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), false);
    for (PropertyInfo property : model.getPropertyMap().values()) {
      assertFalse(property.isDeprecated());
    }
  }

  @Test
  public void testEnumDeprecated() throws Exception {
    EnumModel model = generator.generateEnum(DeprecatedEnum.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertNotNull(model.getDeprecatedDesc());
    assertEquals(model.getDeprecatedDesc().getValue(), "deprecated info");
    assertTrue(model.getValues().get(0).isDeprecated());
    assertNotNull(model.getValues().get(0).getDeprecatedDesc());
    assertEquals(model.getValues().get(0).getDeprecatedDesc().getValue(), "enum item deprecated info");
    assertFalse(model.getValues().get(1).isDeprecated());
  }

  @Test
  public void testEnumNotDeprecated() throws Exception {
    EnumModel model = generator.generateEnum(ValidEnum.class);
    assertFalse(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), false);
  }
}
