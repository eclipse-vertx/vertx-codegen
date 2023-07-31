package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.test.codegen.testapi.DeprecatedInterface;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.OverrideA;
import io.vertx.test.codegen.testapi.OverrideB;
import io.vertx.test.codegen.testdataobject.DataObjectWithProperty;
import io.vertx.test.codegen.testdataobject.DeprecatedDataObject;
import io.vertx.test.codegen.testenum.DeprecatedEnum;
import io.vertx.test.codegen.testenum.ValidEnum;
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
