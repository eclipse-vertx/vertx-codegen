package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.ModuleModel;
import io.vertx.codegen.OptionsModel;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedOptions;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubOptions;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleApi;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleOptions;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleTest {

  @Test
  public void testModuleModel() throws Exception {
    ModuleModel model = new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.modulescoped");
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getFqn());
    assertEquals("simple", model.getName());
  }

  @Test
  public void testNestedModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testEmptyModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.emptynamemodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testModuleScopedApiModel() throws Exception {
    ClassModel model = new Generator().generateModel(ModuleScopedApi.class);
    assertEquals(ModuleScopedApi.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getFqn());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testModuleScopedSubApiModel() throws Exception {
    ClassModel model = new Generator().generateModel(ModuleScopedSubApi.class);
    assertEquals(ModuleScopedSubApi.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getFqn());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testModuleScopedOptionsModel() throws Exception {
    OptionsModel model = new Generator().generateOptions(ModuleScopedOptions.class);
    assertEquals(ModuleScopedOptions.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getFqn());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testModuleScopedSubOptionsModel() throws Exception {
    OptionsModel model = new Generator().generateOptions(ModuleScopedSubOptions.class);
    assertEquals(ModuleScopedSubOptions.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getFqn());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testNoModuleApiModel() throws Exception {
    try {
      new Generator().generateModel(NoModuleApi.class);
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testNoModuleOptionsModel() throws Exception {
    try {
      new Generator().generateOptions(NoModuleOptions.class);
      fail();
    } catch (GenException expected) {
    }
  }
}
