package io.vertx.test.codegen;

import io.vertx.codegen.processor.ClassModel;
import io.vertx.codegen.processor.MethodInfo;
import io.vertx.test.codegen.testapi.OverrideA;
import io.vertx.test.codegen.testapi.OverrideB;
import org.junit.Test;

import static org.junit.Assert.*;

public class OverrideTest {
  private GeneratorHelper generator = new GeneratorHelper();

  @Test
  public void testVertxGenBaseOverride() throws Exception {
    ClassModel model = generator.generateClass(OverrideA.class);
    MethodInfo method = model.getMethods().get(0);
    // Base class should not be annotated
    assertFalse(method.isMethodOverride());
  }

  @Test
  public void testVertxGenOverride() throws Exception {
    ClassModel model = generator.generateClass(OverrideB.class);
    MethodInfo method = model.getMethods().get(0);
    // child class should be annotated
    assertTrue(method.isMethodOverride());
  }
}
