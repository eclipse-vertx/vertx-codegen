package io.vertx.test.codegen;

import io.vertx.codegen.Generator;
import io.vertx.codegen.ModuleInfo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleTest {

  @Test
  public void testModule() throws Exception {
    ModuleInfo module = new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule");
    assertNotNull(module);
    assertEquals("io.vertx.test.codegen.testmodule", module.getFqn());
    assertEquals("mymodule", module.getName());
  }
}
