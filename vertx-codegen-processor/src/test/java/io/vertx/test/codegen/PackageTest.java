package io.vertx.test.codegen;

import io.vertx.codegen.PackageModel;
import io.vertx.test.codegen.testpkg.testapi.TestApi;
import io.vertx.test.codegen.testpkg.testdataobject.TestDataObject;
import io.vertx.test.codegen.testpkg.testenum.TestEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PackageTest {

  @Test
  public void testPackageModelForEnum() throws Exception {
    PackageModel model = new GeneratorHelper().generatePackage(TestEnum.class);
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testpkg.testenum", model.getFqn());
  }

  @Test
  public void testPackageModelForApi() throws Exception {
    PackageModel model = new GeneratorHelper().generatePackage(TestApi.class);
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testpkg.testapi", model.getFqn());
  }

  @Test
  public void testPackageModelForDataObject() throws Exception {
    PackageModel model = new GeneratorHelper().generatePackage(TestDataObject.class);
    assertNotNull(model);
    assertEquals("io.vertx.test.codegen.testpkg.testdataobject", model.getFqn());
  }
}
