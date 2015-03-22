package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.ModuleModel;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.TypeInfo;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedDataObject;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubDataObject;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleApi;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleDataObject;
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
    assertEquals("io.vertx.groovy.test.codegen.testmodule.modulescoped", model.translateFqn("groovy"));
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
  public void testEmptyNameModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.emptynamemodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testIllegalNameModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.illegalnamemodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testCustomGroupModuleModel() throws Exception {
    ModuleModel model = new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.customgroup");
    assertEquals("io.vertx.test.codegen.testmodule.customgroup", model.getFqn());
    assertEquals("custom", model.getName());
    assertEquals("io.vertx.test.codegen.testmodule.groovy.customgroup", model.translateFqn("groovy"));
  }

  @Test
  public void testNotPrefixingGroupModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.notprefixinggroup");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testInvalidGroupModuleModel() throws Exception {
    try {
      new Generator().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.invalidgroup");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testModuleScopedApiModel() throws Exception {
    ClassModel model = new Generator().generateClass(ModuleScopedApi.class);
    assertEquals(ModuleScopedApi.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    TypeInfo.Class.Api type = (TypeInfo.Class.Api) model.getType();
    assertEquals("io.vertx.groovy.test.codegen.testmodule.modulescoped.ModuleScopedApi", type.translateName("groovy"));
  }

  @Test
  public void testModuleScopedSubApiModel() throws Exception {
    ClassModel model = new Generator().generateClass(ModuleScopedSubApi.class);
    assertEquals(ModuleScopedSubApi.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    TypeInfo.Class.Api type = (TypeInfo.Class.Api) model.getType();
    assertEquals("io.vertx.groovy.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi", type.translateName("groovy"));
  }

  @Test
  public void testModuleScopedDataObjectModel() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ModuleScopedDataObject.class);
    assertEquals(ModuleScopedDataObject.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testModuleScopedSubDataObjectModel() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ModuleScopedSubDataObject.class);
    assertEquals(ModuleScopedSubDataObject.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testNoModuleApiModel() throws Exception {
    try {
      new Generator().generateClass(NoModuleApi.class);
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testNoModuleDataObjectModel() throws Exception {
    try {
      new Generator().generateDataObject(NoModuleDataObject.class);
      fail();
    } catch (GenException expected) {
    }
  }
}
