package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.JsonMapperInfo;
import io.vertx.codegen.type.PrimitiveTypeInfo;
import io.vertx.test.codegen.testjsonmapper.enclosedmapper.MyPojo;
import io.vertx.test.codegen.testjsonmapper.zoneddatetimetest.APIInterfaceWithZonedDateTime;
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
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.illegalinterfacewithoverloads.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

  // Json mapper must have getInstance static method

  @Test
  public void testJsonMapperMustHaveStaticINSTANCEField() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.missinginstancefield");
      fail();
    } catch (GenException expected) { }
  }

  // Illegal Json type in JsonMapper

  @Test
  public void testJsonMapperMustHaveValidJsonType() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsonmapper.illegaljsontypeinmapper");
      fail();
    } catch (GenException expected) { }
  }


  // Test valid stuff
  // ----------------

  // Valid param

  @Test
  public void testAbstractClass() throws Exception {
    new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.abstractclasstest.APIInterfaceWithZonedDateTime.class);
  }

  @Test
  public void testInterface() throws Exception {
    new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.interfacetest.APIInterfaceWithZonedDateTime.class);
  }

  @Test
  public void testEnclosedMapper() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.enclosedmapper.APIInterfaceWithMyPojo.class);
    DataObjectTypeInfo typeInfo = model.getReferencedDataObjectTypes().iterator().next();
    JsonMapperInfo jsonMapperInfo = typeInfo.getJsonMapperInfo();
    assertNotNull(typeInfo);
    assertTrue(typeInfo.isSerializable());
    assertTrue(typeInfo.isDeserializable());
    assertEquals(MyPojo.MyPojoMapper.class.getCanonicalName(), jsonMapperInfo.getJsonDeserializerFQCN());
    assertEquals(MyPojo.MyPojoMapper.class.getCanonicalName(), jsonMapperInfo.getJsonSerializerFQCN());
  }

  @Test
  public void testValidParamAndReturnType() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(APIInterfaceWithZonedDateTime.class);
    assertEquals(APIInterfaceWithZonedDateTime.class.getName(), model.getIfaceFQCN());
    assertEquals(APIInterfaceWithZonedDateTime.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());

    MethodInfo method1 = model.getMethods().get(0);
    checkMethod(method1, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method1.getParams();
    assertTrue(params.get(0).getType() instanceof DataObjectTypeInfo);
    assertEquals(ZonedDateTime.class.getName(), params.get(0).getType().getName());

    MethodInfo method2 = model.getMethods().get(1);
    checkMethod(method2, "returnSomething", 0, "java.time.ZonedDateTime", MethodKind.OTHER);
    assertTrue(method2.getReturnType() instanceof DataObjectTypeInfo);
  }

  @Test
  public void testValidMethodOverload() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsonmapper.interfacewithoverloads.APIInterfaceWithZonedDateTime.class);
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(2, model.getMethods().size());

    MethodInfo method1 = model.getMethods().get(0);
    checkMethod(method1, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params1 = method1.getParams();
    assertTrue(params1.get(0).getType() instanceof DataObjectTypeInfo);
    assertEquals(ZonedDateTime.class.getName(), params1.get(0).getType().getName());

    MethodInfo method2 = model.getMethods().get(1);
    checkMethod(method2, "doSomething", 1, "void", MethodKind.OTHER);
    List<ParamInfo> params2 = method2.getParams();
    assertTrue(params2.get(0).getType() instanceof PrimitiveTypeInfo);
    assertEquals(long.class.getName(), params2.get(0).getType().getName());
  }

}
