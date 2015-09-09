package io.vertx.test.codegen;

import io.vertx.codegen.CodeGenProcessor;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.VertxGenClass2;
import io.vertx.test.codegen.testdataobject.PropertyGettersSetters;
import io.vertx.test.codegen.testenum.ValidEnum;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.vertx.codegen.Compiler;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGeneratorTest {

  @Rule
  public final TestName name = new TestName();

  File testDir;

  @Before
  public void before() throws Exception {
    testDir = new File(new File("target").getAbsoluteFile(), "testgen_" + name.getMethodName());
    if (!testDir.exists()) {
      assertTrue(testDir.mkdir());
    }
  }

  private Properties assertCompile(Class... classes) throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-AcodeGenerators=testgen");
    compiler.addOption("-AoutputDirectory=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(classes));
    File f = new File(testDir, classes[0].getName().replace('.', '_') + ".properties");
    Properties props = new Properties();
    props.load(new FileInputStream(f));
    return props;
  }

  @Test
  public void testClassGen() throws Exception {
    Properties props = assertCompile(MethodWithValidVertxGenParams.class, VertxGenClass1.class, VertxGenClass2.class);
    assertEquals("[" + VertxGenClass1.class.getName() + ", " + VertxGenClass2.class.getName() + ", " + String.class.getName() + "]", props.remove("importedTypes"));
    assertEquals("true", props.remove("concrete"));
    assertEquals(MethodWithValidVertxGenParams.class.getName(), props.remove("type"));
    assertEquals("io.vertx.test.codegen.testapi", props.remove("ifacePackageName"));
    assertEquals("MethodWithValidVertxGenParams", props.remove("ifaceSimpleName"));
    assertEquals("io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams", props.remove("ifaceFQCN"));
    assertEquals("[" + VertxGenClass1.class.getName() + ", " + VertxGenClass2.class.getName() + "]", props.remove("referencedTypes"));
    assertEquals("[]", props.remove("superTypes"));
    assertEquals("[]", props.remove("concreteSuperTypes"));
    assertEquals("[]", props.remove("abstractSuperTypes"));
    assertEquals("null", props.remove("handlerSuperType"));
    assertEquals("void", props.remove("method.methodWithVertxGenParams(str,myParam1,myParam2)"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testDataObjectGen() throws Exception {
    Properties props = assertCompile(PropertyGettersSetters.class);
    assertEquals(PropertyGettersSetters.class.getName(), props.remove("type"));
    assertEquals("false", props.remove("generateConverter"));
    assertEquals("false", props.remove("inheritConverter"));
    assertEquals("false", props.remove("concrete"));
    assertEquals("false", props.remove("isClass"));
    assertEquals("[" + JsonArray.class.getName() + ", " + JsonObject.class.getName() + ", " + Boolean.class.getName() + ", "
        + Integer.class.getName() + ", " + Long.class.getName() + ", " + String.class.getName()
        + "]", props.remove("importedTypes"));
    assertEquals("[]", props.remove("superTypes"));
    assertEquals("[]", props.remove("abstractSuperTypes"));
    assertEquals("null", props.remove("superType"));
    assertEquals("io.vertx.test.codegen.testdataobject.ApiObject", props.remove("property.apiObject"));
    assertEquals("java.lang.Boolean", props.remove("property.boxedBoolean"));
    assertEquals("java.lang.Integer", props.remove("property.boxedInteger"));
    assertEquals("java.lang.Long", props.remove("property.boxedLong"));
    assertEquals("io.vertx.test.codegen.testdataobject.EmptyDataObject", props.remove("property.dataObject"));
    assertEquals("io.vertx.test.codegen.testdataobject.Enumerated", props.remove("property.enumerated"));
    assertEquals("io.vertx.core.json.JsonArray", props.remove("property.jsonArray"));
    assertEquals("io.vertx.core.json.JsonObject", props.remove("property.jsonObject"));
    assertEquals("boolean", props.remove("property.primitiveBoolean"));
    assertEquals("int", props.remove("property.primitiveInteger"));
    assertEquals("long", props.remove("property.primitiveLong"));
    assertEquals("java.lang.String", props.remove("property.string"));
    assertEquals("io.vertx.test.codegen.testdataobject.ToJsonDataObject", props.remove("property.toJsonDataObject"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testEnumGen() throws Exception {
    Properties props = assertCompile(ValidEnum.class);
    assertEquals(ValidEnum.class.getName(), props.remove("type"));
    assertEquals("[RED, GREEN, BLUE]", props.remove("values"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testModuleGen() throws Exception {
    URL url = CodeGenProcessor.class.getClassLoader().getResource("io/vertx/test/codegen/testmodule/customgroup/package-info.java");
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-AcodeGenerators=testgen");
    compiler.addOption("-AoutputDirectory=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(new File(url.toURI())));
    File f = new File(testDir, "io_vertx_test_codegen_testmodule_customgroup.properties");
    Properties props = new Properties();
    props.load(new FileInputStream(f));
    assertEquals("io.vertx.test.codegen.testmodule.customgroup", props.remove("fqn"));
    assertEquals("custom", props.remove("name"));
    assertEquals(new Properties(), props);
  }
}
