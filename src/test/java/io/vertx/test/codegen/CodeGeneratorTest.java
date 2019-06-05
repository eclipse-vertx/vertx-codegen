package io.vertx.test.codegen;

import io.vertx.codegen.CodeGenProcessor;
import io.vertx.codegen.Compiler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.InterfaceDataObject;
import io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams;
import io.vertx.test.codegen.testapi.VertxGenClass1;
import io.vertx.test.codegen.testapi.VertxGenClass2;
import io.vertx.test.codegen.testdataobject.CommentedDataObject;
import io.vertx.test.codegen.testdataobject.PropertyGettersSetters;
import io.vertx.test.codegen.testenum.ValidEnum;
import io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi;
import io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Callable;

import static io.vertx.test.codegen.Utils.assertFile;
import static io.vertx.test.codegen.Utils.assertMkDirs;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CodeGeneratorTest {

  @Rule
  public final TestName name = new TestName();

  private static File testDir;

  @Before
  public void before() throws Exception {
    int count = 0;
    while (true) {
      String suffix = "testgen_" + name.getMethodName();
      if (count > 0) {
        suffix += "-" + count;
      }
      count++;
      testDir = new File(new File("target").getAbsoluteFile(), suffix);
      if (!testDir.exists()) {
        assertTrue(testDir.mkdir());
        break;
      }
    }
  }

  private Properties assertCompile(String gen, Class... classes) throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=" + gen);
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(classes));
    File f = new File(testDir, classes[0].getName().replace('.', '_') + ".properties");
    Properties props = new Properties();
    props.load(new FileInputStream(f));
    return props;
  }

  @Test
  public void testClassGen() throws Exception {
    Properties props = assertCompile("testgen1", MethodWithValidVertxGenParams.class, VertxGenClass1.class, VertxGenClass2.class);
    assertEquals("[" + VertxGenClass1.class.getName() + ", " + VertxGenClass2.class.getName() + ", " + String.class.getName() + "]", props.remove("importedTypes"));
    assertEquals("true", props.remove("concrete"));
    assertEquals(MethodWithValidVertxGenParams.class.getName(), props.remove("type"));
    assertEquals("io.vertx.test.codegen.testapi", props.remove("ifacePackageName"));
    assertEquals("MethodWithValidVertxGenParams", props.remove("ifaceSimpleName"));
    assertEquals("io.vertx.test.codegen.testapi.MethodWithValidVertxGenParams", props.remove("ifaceFQCN"));
    assertEquals("[" + VertxGenClass1.class.getName() + ", " + VertxGenClass2.class.getName() + "]", props.remove("referencedTypes"));
    assertEquals("[]", props.remove("superTypes"));
    assertEquals("null", props.remove("concreteSuperType"));
    assertEquals("[]", props.remove("abstractSuperTypes"));
    assertEquals("null", props.remove("handlerType"));
    assertEquals("void", props.remove("method.methodWithVertxGenParams(str,myParam1,myParam2)"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testDataObjectGen() throws Exception {
    Properties props = assertCompile("testgen1", PropertyGettersSetters.class);
    assertEquals(PropertyGettersSetters.class.getName(), props.remove("type"));
    assertEquals("false", props.remove("generateConverter"));
    assertEquals("false", props.remove("inheritConverter"));
    assertEquals("true", props.remove("publicConverter"));
    assertEquals("false", props.remove("concrete"));
    assertEquals("false", props.remove("isClass"));
    assertEquals("[" + JsonArray.class.getName() + ", " + JsonObject.class.getName() + ", " + Boolean.class.getName() + ", "
        + Integer.class.getName() + ", " + Long.class.getName() + ", " + String.class.getName() + ", " + Instant.class.getName()
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
    assertEquals("java.time.Instant", props.remove("property.instant"));
    assertEquals("io.vertx.test.codegen.testdataobject.ToJsonDataObject", props.remove("property.toJsonDataObject"));
    assertEquals("false", props.remove("hasEmptyConstructor"));
    assertEquals("false", props.remove("hasJsonConstructor"));
    assertEquals("false", props.remove("hasToJsonMethod"));
    assertEquals("false", props.remove("encodable"));
    assertEquals("false", props.remove("decodable"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testEnumGen() throws Exception {
    Properties props = assertCompile("testgen1", ValidEnum.class);
    assertEquals(ValidEnum.class.getName(), props.remove("type"));
    assertEquals("RED,GREEN,BLUE", props.remove("values"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testModuleGen() throws Exception {
    URL url = CodeGenProcessor.class.getClassLoader().getResource("io/vertx/test/codegen/testmodule/customgroup/package-info.java");
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=testgen1");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(new File(url.toURI())));
    File f = new File(testDir, "io_vertx_test_codegen_testmodule_customgroup.properties");
    Properties props = new Properties();
    props.load(new FileInputStream(f));
    assertEquals("io.vertx.test.codegen.testmodule.customgroup", props.remove("fqn"));
    assertEquals("custom", props.remove("name"));
    assertEquals(new Properties(), props);
  }

  @Test
  public void testIncrementalClass() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=testgen2");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(ModuleScopedApi.class, ModuleScopedSubApi.class));
    File f = new File(testDir, "io_vertx_test_codegen_testmodule_modulescoped.properties");
    List<String> lines = Files.readAllLines(f.toPath());
    assertEquals(Arrays.asList(
        "0/2",
        "1/2",
        "[io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi, io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi]"
    ), lines);
    File classes = compiler.getClassOutput();
    ClassLoader loader = new URLClassLoader(new URL[]{classes.toURI().toURL()});
    Class clazz = loader.loadClass("testgen2.incremental_class");
    Callable<Set<String>> callable = (Callable<Set<String>>) clazz.newInstance();
    Set<String> result = callable.call();
    HashSet<String> expected = new HashSet<>();
    expected.add("io.vertx.test.codegen.testmodule.modulescoped.ModuleScopedApi");
    expected.add("io.vertx.test.codegen.testmodule.modulescoped.sub.ModuleScopedSubApi");
    assertEquals(expected, result);
  }

  @Test
  public void testSkipFileTrue() throws Exception {
    runTestSkipFile(true);
  }

  @Test
  public void testSkipFileFalse() throws Exception {
    runTestSkipFile(false);
  }

  private void runTestSkipFile(boolean skipFile) throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-AshouldSkip=" + skipFile);
    compiler.addOption("-Acodegen.generators=testgen3");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(ModuleScopedApi.class, ModuleScopedSubApi.class));
    assertEquals(!skipFile, new File(testDir, "skip.txt").exists());
    File classes = compiler.getClassOutput();
    ClassLoader loader = new URLClassLoader(new URL[]{classes.toURI().toURL()});
    for (String fqn : Arrays.asList("testgen3.ModuleScopedApi", "testgen3.ModuleScopedSubApi")) {
      try {
        loader.loadClass(fqn);
        assertFalse(skipFile);
      } catch (ClassNotFoundException e) {
        assertTrue(skipFile);
      }
    }
  }

  @Test
  public void testFileTypes() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.setClassOutput(assertMkDirs(new File(testDir, "classes")));
    compiler.setSourceOutput(assertMkDirs(new File(testDir, "sources")));
    compiler.addOption("-Acodegen.generators=testgen4");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(VertxGenClass1.class));
    assertFile("should_not_be_compiled", new File(testDir, "sources/file.txt".replace('/', File.separatorChar)));
    assertFile("should_not_be_compiled", new File(testDir, "classes/file.txt".replace('/', File.separatorChar)));
  }

  @Test
  public void testFileTypesSourceOutputIsClassOutput() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.setClassOutput(assertMkDirs(new File(testDir, "classes")));
    compiler.setSourceOutput(new File(testDir, "classes"));
    compiler.addOption("-Acodegen.generators=testgen4");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(VertxGenClass1.class));
    assertFile("should_not_be_compiled", new File(testDir, "classes/file.txt".replace('/', File.separatorChar)));
  }

  @Test
  public void testFileTypesOverwrite() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.setClassOutput(assertMkDirs(new File(testDir, "classes")));
    compiler.setSourceOutput(assertMkDirs(new File(testDir, "sources")));
    compiler.addOption("-Acodegen.generators=testgen4");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    assertTrue(compiler.compile(VertxGenClass1.class, VertxGenClass2.class));
    assertFile("should_not_be_compiled", new File(testDir, "sources/file.txt".replace('/', File.separatorChar)));
    assertFile("should_not_be_compiled", new File(testDir, "classes/file.txt".replace('/', File.separatorChar)));
  }

  @Test
  public void testRelocation() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=testgen5");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    compiler.addOption("-Acodegen.output.testgen5=foo/bar");
    assertTrue(compiler.compile(MethodWithValidVertxGenParams.class, VertxGenClass1.class, VertxGenClass2.class));
    File f = new File(testDir, "foo/bar/io/vertx/test/codegen/testapi/MethodWithValidVertxGenParams_Other.java".replace('/', File.separatorChar));
    assertTrue(f.exists());
    assertTrue(f.isFile());
  }

  @Test
  public void testMultipleTypes() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=testgen6");
    compiler.addOption("-Acodegen.output=" + testDir.getAbsolutePath());
    compiler.addOption("-Acodegen.output.testgen6=foo/bar");
    assertTrue(compiler.compile(CommentedDataObject.class, VertxGenClass1.class, VertxGenClass2.class));
    File f = new File(testDir, "resource/result.txt".replace('/', File.separatorChar));
    assertTrue(f.exists());
    assertTrue(f.isFile());
    Scanner s = new Scanner(f);
    Set<String> fileContent = new HashSet<>();
    while (s.hasNext()){
      fileContent.add(s.next());
    }
    s.close();
    assertTrue(fileContent.contains(CommentedDataObject.class.getSimpleName()));
    assertTrue(fileContent.contains(VertxGenClass1.class.getSimpleName()));
    assertTrue(fileContent.contains(VertxGenClass2.class.getSimpleName()));
  }

  @Test
  public void testServiceLoader() throws Exception {
    Properties props = assertCompile("testgen7", InterfaceDataObject.class);
    assertEquals(props.remove("MyGenerator"), "true");
  }

  public static String testAbsoluteFilenamePath() {
    return testDir.getAbsolutePath().replace(File.separatorChar, '/') + "/somedir/file.txt";
  }

  @Test
  public void testAbsoluteFilename() throws Exception {
    Compiler compiler = new Compiler(new CodeGenProcessor());
    compiler.addOption("-Acodegen.generators=testgen8");

    assertTrue(compiler.compile(VertxGenClass1.class));
    File f = new File(testDir, "somedir/file.txt".replace('/', File.separatorChar));
    assertTrue(f.exists());
    assertTrue(f.isFile());
  }
}
