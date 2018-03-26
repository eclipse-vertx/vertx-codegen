package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.ProxyModel;
import io.vertx.test.codegen.proxytestapi.ValidProxyCloseWithFuture;
import io.vertx.test.codegen.testapi.DeprecatedInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
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
}
