package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.type.DataObjectInfo;
import io.vertx.codegen.type.PrimitiveTypeInfo;
import io.vertx.test.codegen.testjsonmapper.methodmapper.APIInterfaceWithZonedDateTime;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="http://slinkydeveloper.com">Francesco Guardiani</a>
 */
public class JsonMapperTest extends ClassTestBase {

  // Test invalid stuff
  // ----------------

  // Json mapper method overload check

  @Test
  public void testJsonMapperMustFailMethodOverloadCheck() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.ambiguousoverload.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

  // Illegal Json type in JsonMapper

  @Test
  public void testJsonMapperMustHaveValidJsonType() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.illegaljsontype");
      fail();
    } catch (GenException expected) { }
  }

  // Illegal Json type in JsonMapper

  @Test
  public void testJsonMapperMustHaveSameJsonType() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.mixedjsontypes");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testFunctionMapperMustBeStatic() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.nonstaticfunctionmapper");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testMethodMapperMustBeStatic() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.nonstaticmethodmapper");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testFunctionMapperMustBePublic() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.nonpublicfunctionmapper");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testMethodMapperMustBePublic() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.nonpublicmethodmapper");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testNoArgsMethodMapper() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.noargsmethodmapper");
      fail();
    } catch (GenException expected) { }
  }

  @Test
  public void testTooManyArgsMethodMapper() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.toomanyargsmethodmapper");
      fail();
    } catch (GenException expected) { }
  }

  // Test valid stuff
  // ----------------

  @Test
  public void testMethodMapper() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(APIInterfaceWithZonedDateTime.class);
    testMapper(model);
  }

  @Test
  public void testValidFunctionMapper() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.functionmapper.APIInterfaceWithZonedDateTime.class);
    testMapper(model);
  }

  @Test
  public void testMixedMapper() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.mixedmapper.APIInterfaceWithZonedDateTime.class);
    testMapper(model);
  }

  private void testMapper(ClassModel model) throws Exception {
    assertEquals(2, model.getMethods().size());

    MethodInfo method1 = model.getMethods().get(0);
    checkMethod(method1, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method1.getParams();
    assertNotNull(params.get(0).getType().getDataObject());
    assertEquals(ZonedDateTime.class.getName(), params.get(0).getType().getName());

    MethodInfo method2 = model.getMethods().get(1);
    checkMethod(method2, "returnSomething", 0, "java.time.ZonedDateTime", MethodKind.OTHER);
    assertNotNull(method2.getReturnType().getDataObject());
  }

  @Test
  public void testValidMethodOverload() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.interfacewithoverloads.APIInterfaceWithZonedDateTime.class);
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());

    MethodInfo method1 = model.getMethods().get(0);
    checkMethod(method1, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params1 = method1.getParams();
    assertNotNull(params1.get(0).getType().getDataObject());
    assertEquals(ZonedDateTime.class.getName(), params1.get(0).getType().getName());

    MethodInfo method2 = model.getMethods().get(1);
    checkMethod(method2, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params2 = method2.getParams();
    assertTrue(params2.get(0).getType() instanceof PrimitiveTypeInfo);
    assertEquals(long.class.getName(), params2.get(0).getType().getName());
  }
}
