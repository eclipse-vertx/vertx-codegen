package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.PrimitiveTypeInfo;
import io.vertx.test.codegen.testjsoncodecs.zoneddatetimetest.APIInterfaceWithZonedDateTime;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="http://slinkydeveloper.com">Francesco Guardiani</a>
 */
public class JsonCodecTest extends ClassTestBase {

  // Test invalid stuff
  // ----------------

  // Json codec method overload check

  @Test
  public void testJsonCodecMustFailMethodOverloadCheck() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.illegalinterfacewithoverloads.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

  // Json codec must have getInstance static method

  @Test
  public void testJsonCodecMustHaveStaticINSTANCEField() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsoncodecs.missinginstancefield");
      fail();
    } catch (GenException expected) { }
  }

  // Illegal Json type in JsonCodec

  @Test
  public void testJsonCodecMustHaveValidJsonType() throws Exception {
    try {
      new GeneratorHelper().generateModule(ModuleTest.class.getClassLoader(), "io.vertx.test.codegen.testjsoncodecs.illegaljsontypeincodec");
      fail();
    } catch (GenException expected) { }
  }


  // Test valid stuff
  // ----------------

  // Valid param

  @Test
  public void testAbstractClass() throws Exception {
    new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.abstractclasstest.APIInterfaceWithZonedDateTime.class);
  }

  @Test
  public void testInterface() throws Exception {
    new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.interfacetest.APIInterfaceWithZonedDateTime.class);
  }

  @Test
  public void testEnclosedCodec() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.enclosedcodec.APIInterfaceWithMyPojo.class);
    DataObjectTypeInfo typeInfo = model.getReferencedDataObjectTypes().iterator().next();
    assertNotNull(typeInfo);
    assertTrue(typeInfo.hasJsonEncoder());
    assertTrue(typeInfo.hasJsonDecoder());
    assertEquals(io.vertx.test.codegen.testjsoncodecs.enclosedcodec.MyPojo.MyPojoCodec.class.getCanonicalName(), typeInfo.getJsonEncoderFQCN());
    assertEquals(io.vertx.test.codegen.testjsoncodecs.enclosedcodec.MyPojo.MyPojoCodec.class.getCanonicalName(), typeInfo.getJsonDecoderFQCN());
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
    ClassModel model = new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.interfacewithoverloads.APIInterfaceWithZonedDateTime.class);
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
