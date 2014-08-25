package io.vertx.test.codegen;

import io.vertx.codegen.Generator;
import io.vertx.codegen.ModuleModel;
import io.vertx.codegen.PackageModel;
import io.vertx.test.codegen.testapi.InterfaceWithStaticClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModelTest {

  @Test
  public void testModule() throws Exception {
    ModuleModel model = new Generator().generateModule(ModelTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule");
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testmodule", model.getFqn());
    assertEquals("mymodule", model.getName());
  }

  @Test
  public void testPackage() throws Exception {
    PackageModel model = new Generator().generatePackage(InterfaceWithStaticClass.class);
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testapi", model.getFqn());
  }
}
