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

  // Json codec must be a class

  @Test
  public void testJsonCodecMustBeAClass() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecinterface.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

  // Json codec must be a concrete class

  @Test
  public void testJsonCodecMustBeAConcreteClass() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecabstract.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

  // Json codec must contain an empty constructor

  @Test
  public void testJsonCodecMustContainAnEmptyConstructor() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecnoemptyconstructor.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }

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
  public void testJsonCodecMustHaveStaticGetInstanceMethod() throws Exception {
    try {
      new GeneratorHelper().generateClass(io.vertx.test.codegen.testjsoncodecs.missinggetinstancemethod.APIInterfaceWithZonedDateTime.class);
      fail();
    } catch (GenException expected) { }
  }


  // Test valid stuff
  // ----------------

  // Valid param

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
