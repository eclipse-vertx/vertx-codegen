package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.ModuleModel;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.type.ApiTypeInfo;
import io.vertx.codegen.type.TypeNameTranslator;
import io.vertx.test.codegen.testmodule.emptypkg.empty.sub.EmptyPkg;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedDataObject;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubDataObject;
import io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule.NestedApi;
import io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule.NestedDataObject;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleApi;
import io.vertx.test.codegen.testmodule.nomodule.NoModuleDataObject;
import io.vertx.test.codegen.testmodule.vertx.core.VertxCoreModuleScopedApi;
import io.vertx.test.codegen.testmodule.vertx.other.VertxOtherModuleScopedApi;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleTest {

  @Test
  public void testModuleModel() throws Exception {
    ModuleModel model = new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.modulescoped");
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getFqn());
    assertEquals("simple", model.getName());
    assertEquals("io.vertx.test.groovy.codegen.testmodule.modulescoped", model.translateFqn("groovy"));
    assertNotNull(model.getModule());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    assertEquals("io.vertx.test.groovy.codegen.testmodule.modulescoped", model.getModule().translatePackageName("groovy"));
  }

  @Test
  public void testNestedModuleModel() throws Exception {
    ModuleModel module = new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule");
    assertEquals("io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule", module.getFqn());
    assertEquals("nested", module.getName());
    assertEquals("io.vertx.test.groovy.codegen.testmodule.nestingmodule.nestedmodule", module.translateFqn("groovy"));
    ClassModel api = new GeneratorHelper().generateClass(NestedApi.class);
    assertEquals("io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule", api.getModule().getPackageName());
    assertEquals("nested", api.getModule().getName());
    DataObjectModel dataObj = new GeneratorHelper().generateDataObject(NestedDataObject.class);
    assertEquals("io.vertx.test.codegen.testmodule.nestingmodule.nestedmodule", dataObj.getModule().getPackageName());
    assertEquals("nested", dataObj.getModule().getName());
  }

  @Test
  public void testEmptyNameModuleModel() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.emptynamemodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testIllegalNameModuleModel() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.illegalnamemodule");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testCustomGroupModuleModel() throws Exception {
    ModuleModel model = new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.customgroup");
    assertEquals("io.vertx.test.codegen.testmodule.customgroup", model.getFqn());
    assertEquals("custom", model.getName());
    assertEquals("io.vertx.test.codegen.testmodule.groovy.customgroup", model.translateFqn("groovy"));
  }

  @Test
  public void testNotPrefixingGroupModuleModel() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.notprefixinggroup");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testInvalidGroupModuleModel() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testmodule.invalidgroup");
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testModuleScopedApiModel() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(ModuleScopedApi.class);
    assertEquals(ModuleScopedApi.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    ApiTypeInfo type = (ApiTypeInfo) model.getType();
    assertEquals("io.vertx.test.groovy.codegen.testmodule.modulescoped.ModuleScopedApi", type.translateName("groovy"));
    assertEquals("io.vertx.test.ceylon.simple.ModuleScopedApi", type.translateName(TypeNameTranslator.composite("ceylon")));
  }

  @Test
  public void testEmptyPkg() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(EmptyPkg.class);
    assertEquals(EmptyPkg.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.emptypkg", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    ApiTypeInfo type = (ApiTypeInfo) model.getType();
    assertEquals("io.vertx.test.groovy.codegen.testmodule.emptypkg.empty.sub.EmptyPkg", type.translateName("groovy"));
    assertEquals("io.vertx.test.ceylon.simple.empty.sub.EmptyPkg", type.translateName(TypeNameTranslator.composite("ceylon")));
  }

  @Test
  public void testModuleScopedSubApiModel() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(ModuleScopedSubApi.class);
    assertEquals(ModuleScopedSubApi.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
    ApiTypeInfo type = (ApiTypeInfo) model.getType();
    assertEquals("io.vertx.test.groovy.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi", type.translateName("groovy"));
    assertEquals("io.vertx.test.ceylon.simple.sub.ModuleScopedSubApi", type.translateName(TypeNameTranslator.composite("ceylon")));
  }

  @Test
  public void testModuleScopedDataObjectModel() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ModuleScopedDataObject.class);
    assertEquals(ModuleScopedDataObject.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testModuleScopedSubDataObjectModel() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ModuleScopedSubDataObject.class);
    assertEquals(ModuleScopedSubDataObject.class.getName(), model.getFqn());
    assertEquals("io.vertx.test.codegen.testmodule.modulescoped", model.getModule().getPackageName());
    assertEquals("simple", model.getModule().getName());
  }

  @Test
  public void testVertxCoreModuleScopedApiModel() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(VertxCoreModuleScopedApi.class);
    assertEquals(VertxCoreModuleScopedApi.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.vertx.core", model.getModule().getPackageName());
    assertEquals("vertx", model.getModule().getName());
    ApiTypeInfo type = (ApiTypeInfo) model.getType();
    assertEquals("io.vertx.groovy.test.codegen.testmodule.vertx.core.VertxCoreModuleScopedApi", type.translateName("groovy"));
    assertEquals("io.vertx.ceylon.core.VertxCoreModuleScopedApi", type.translateName(TypeNameTranslator.composite("ceylon")));
  }

  @Test
  public void testVertxOtherModuleScopedApiModel() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(VertxOtherModuleScopedApi.class);
    assertEquals(VertxOtherModuleScopedApi.class.getName(), model.getIfaceFQCN());
    assertEquals("io.vertx.test.codegen.testmodule.vertx.other", model.getModule().getPackageName());
    assertEquals("vertx-other", model.getModule().getName());
    ApiTypeInfo type = (ApiTypeInfo) model.getType();
    assertEquals("io.vertx.groovy.test.codegen.testmodule.vertx.other.VertxOtherModuleScopedApi", type.translateName("groovy"));
    assertEquals("io.vertx.ceylon.other.VertxOtherModuleScopedApi", type.translateName(TypeNameTranslator.composite("ceylon")));
  }

  @Test
  public void testNoModuleApiModel() throws Exception {
    try {
      new GeneratorHelper().generateClass(NoModuleApi.class);
      fail();
    } catch (GenException expected) {
    }
  }

  @Test
  public void testNoModuleDataObjectModel() throws Exception {
    try {
      new GeneratorHelper().generateDataObject(NoModuleDataObject.class);
      fail();
    } catch (GenException expected) {
    }
  }
}
