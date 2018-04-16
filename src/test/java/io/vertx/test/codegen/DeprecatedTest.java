package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.test.codegen.proxytestapi.ValidProxyCloseWithFuture;
import io.vertx.test.codegen.testapi.DeprecatedInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testdataobject.DataObjectWithProperty;
import io.vertx.test.codegen.testdataobject.DeprecatedDataObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeprecatedTest {
  private Generator generator = new Generator();

  @Test
  public void testVertxGenDeprecated() throws Exception {
    ClassModel model = generator.generateClass(DeprecatedInterface.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertTrue(model.getMethods().get(0).isDeprecated());
  }

  @Test
  public void testProxyGenDeprecated() throws Exception {
    ProxyModel model = generator.generateProxyModel(DeprecatedInterface.class);
    assertTrue(model.isDeprecated());
    assertEquals(model.getVars().get("deprecated"), true);
    assertTrue(model.getMethods().get(0).isDeprecated());
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
    for (PropertyInfo property : model.getPropertyMap().values()) {
      assertTrue(property.isDeprecated());
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
}
