package io.vertx.test.codegen;

import io.vertx.codegen.Generator;
import io.vertx.codegen.PackageModel;
import io.vertx.test.codegen.testapi.InterfaceWithStaticClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PackageTest {

  @Test
  public void testPackageModel() throws Exception {
    PackageModel model = new Generator().generatePackage(InterfaceWithStaticClass.class);
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testapi", model.getFqn());
  }
}
