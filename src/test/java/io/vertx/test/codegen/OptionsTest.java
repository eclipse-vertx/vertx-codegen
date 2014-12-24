package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.OptionsModel;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.TypeInfo;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.OptionsInterface;
import io.vertx.test.codegen.testapi.OptionsWithNoCopyConstructor;
import io.vertx.test.codegen.testapi.OptionsWithNoDefaultConstructor;
import io.vertx.test.codegen.testapi.OptionsWithNoJsonObjectConstructor;
import io.vertx.test.codegen.testoptions.Abstract;
import io.vertx.test.codegen.testoptions.AbstractInheritsAbstract;
import io.vertx.test.codegen.testoptions.AbstractInheritsConcrete;
import io.vertx.test.codegen.testoptions.AdderNormalizationRules;
import io.vertx.test.codegen.testoptions.AdderWithNestedOptions;
import io.vertx.test.codegen.testoptions.ApiAdder;
import io.vertx.test.codegen.testoptions.ApiObject;
import io.vertx.test.codegen.testoptions.ApiSetter;
import io.vertx.test.codegen.testoptions.BasicAdders;
import io.vertx.test.codegen.testoptions.BasicGetters;
import io.vertx.test.codegen.testoptions.BasicSetters;
import io.vertx.test.codegen.testoptions.Concrete;
import io.vertx.test.codegen.testoptions.ConcreteInheritsAbstract;
import io.vertx.test.codegen.testoptions.ConcreteInheritsConcrete;
import io.vertx.test.codegen.testoptions.ConcreteInheritsNonOptions;
import io.vertx.test.codegen.testoptions.ConcreteInheritsOverridenPropertyFromNonOptions;
import io.vertx.test.codegen.testoptions.ConcreteInheritsOverridenPropertyFromOptions;
import io.vertx.test.codegen.testoptions.ConcreteInheritsPropertyFromNonOptions;
import io.vertx.test.codegen.testoptions.ConcreteInheritsPropertyFromOptions;
import io.vertx.test.codegen.testoptions.Empty;
import io.vertx.test.codegen.testoptions.IgnoreMethods;
import io.vertx.test.codegen.testoptions.ImportedNested;
import io.vertx.test.codegen.testoptions.ImportedSubinterface;
import io.vertx.test.codegen.testoptions.JsonObjectAdder;
import io.vertx.test.codegen.testoptions.JsonObjectSetter;
import io.vertx.test.codegen.testoptions.ListBasicSetters;
import io.vertx.test.codegen.testoptions.OptionWithGetterAndSetter;
import io.vertx.test.codegen.testoptions.Parameterized;
import io.vertx.test.codegen.testoptions.SetterNormalizationRules;
import io.vertx.test.codegen.testoptions.SetterWithNestedOptions;
import io.vertx.test.codegen.testoptions.SetterWithNonFluentReturnType;
import io.vertx.test.codegen.testoptions.imported.Imported;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsTest {

  @Test
  public void testOptionsWithNoDefaultConstructor() throws Exception {
    assertInvalidOptions(OptionsWithNoDefaultConstructor.class);
  }

  @Test
  public void testOptionsWithNoCopyConstructor() throws Exception {
    assertInvalidOptions(OptionsWithNoCopyConstructor.class);
  }

  @Test
  public void testOptionsWithNoJsonObjectConstructor() throws Exception {
    assertInvalidOptions(OptionsWithNoJsonObjectConstructor.class);
  }

  @Test
  public void testOptionsInterface() throws Exception {
    OptionsModel model = new Generator().generateOptions(OptionsInterface.class);
    assertNotNull(model);
  }

  @Test
  public void testEmptyOptions() throws Exception {
    OptionsModel model = new Generator().generateOptions(Empty.class);
    assertNotNull(model);
  }

  @Test
  public void testParameterizedOptions() throws Exception {
    assertInvalidOptions(Parameterized.class);
  }

  @Test
  public void testSetterWithNonFluentReturnType() throws Exception {
    OptionsModel model = new Generator().generateOptions(SetterWithNonFluentReturnType.class);
    assertNotNull(model);
    assertEquals(2, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", TypeInfo.create(String.class), true, false, false);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", TypeInfo.create(boolean.class), true, false, false);
  }

  @Test
  public void testBasicSetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicSetters.class);
    assertNotNull(model);
    assertEquals(7, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", TypeInfo.create(String.class), true, false, false);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", TypeInfo.create(Integer.class), true, false, false);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", TypeInfo.create(int.class), true, false, false);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", TypeInfo.create(Boolean.class), true, false, false);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", TypeInfo.create(boolean.class), true, false, false);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", TypeInfo.create(Long.class), true, false, false);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", TypeInfo.create(long.class), true, false, false);
  }

  @Test
  public void testSetterNormalizationRules() throws Exception {
    OptionsModel model = new Generator().generateOptions(SetterNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("ha"), "ha", TypeInfo.create(boolean.class), true, false, false);
    assertProperty(model.getPropertyMap().get("haGroup"), "haGroup", TypeInfo.create(boolean.class), true, false, false);
    assertProperty(model.getPropertyMap().get("group"), "group", TypeInfo.create(boolean.class), true, false, false);
  }

  @Test
  public void testListBasicSetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(ListBasicSetters.class);
    assertNotNull(model);
    assertEquals(5, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", TypeInfo.create(String.class), true, true, false);
    assertProperty(model.getPropertyMap().get("strings"), "strings", TypeInfo.create(String.class), true, true, false);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", TypeInfo.create(Integer.class), true, true, false);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", TypeInfo.create(Boolean.class), true, true, false);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", TypeInfo.create(Long.class), true, true, false);
  }

  @Test
  public void testBasicGetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicGetters.class);
    assertNotNull(model);
    assertEquals(5, model.getPropertyMap().size());
  }

  @Test
  public void testApiSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(ApiSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", TypeInfo.create(ApiObject.class), true, false, false);
  }

  @Test
  public void testJsonObjectSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(JsonObjectSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", TypeInfo.create(JsonObject.class), true, false, false);
  }

  @Test
  public void testBasicAdders() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicAdders.class);
    assertNotNull(model);
    assertEquals(7, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", TypeInfo.create(String.class), true, true, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", TypeInfo.create(Integer.class), true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", TypeInfo.create(int.class), true, true, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", TypeInfo.create(Boolean.class), true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", TypeInfo.create(boolean.class), true, true, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", TypeInfo.create(Long.class), true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", TypeInfo.create(long.class), true, true, true);
  }

  @Test
  public void testAdderNormalizationRules() throws Exception {
    OptionsModel model = new Generator().generateOptions(AdderNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("urls"), "urls", TypeInfo.create(boolean.class), true, true, true);
    assertProperty(model.getPropertyMap().get("urlLocators"), "urlLocators", TypeInfo.create(boolean.class), true, true, true);
    assertProperty(model.getPropertyMap().get("locators"), "locators", TypeInfo.create(boolean.class), true, true, true);
  }

  @Test
  public void testApiAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(ApiAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", TypeInfo.create(ApiObject.class), true, true, true);
  }

  @Test
  public void testJsonObjectAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(JsonObjectAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", TypeInfo.create(JsonObject.class), true, true, true);
  }

  @Test
  public void testNestedOptionsSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(SetterWithNestedOptions.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nested"), "nested", TypeInfo.create(Empty.class), true, false, false);
  }

  @Test
  public void testNestedOptionsAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(AdderWithNestedOptions.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nesteds"), "nesteds", TypeInfo.create(Empty.class), true, true, true);
  }

  @Test
  public void testIgnoreMethods() throws Exception {
    OptionsModel model = new Generator().generateOptions(IgnoreMethods.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteInheritsConcrete() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsConcrete.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Concrete.class)), model.getSuperTypes());
    assertEquals(TypeInfo.create(Concrete.class), model.getSuperType());
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getAbstractSuperTypes());
  }

  @Test
  public void testConcreteInheritsAbstract() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsAbstract.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getSuperTypes());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getAbstractSuperTypes());
    assertNull(model.getSuperType());
  }

  @Test
  public void testConcreteInheritsNonOption() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsNonOptions.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteInheritsPropertyFromNonOptions() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsPropertyFromNonOptions.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonOptionsProperty"), "nonOptionsProperty", TypeInfo.create(String.class), true, false, false);
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteInheritsPropertyFromOptions() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsPropertyFromOptions.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonOptionsProperty"), "nonOptionsProperty", TypeInfo.create(String.class), false, false, false);
  }

  @Test
  public void testConcreteInheritsOveriddenPropertyFromOptions() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsOverridenPropertyFromOptions.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonOptionsProperty"), "nonOptionsProperty", TypeInfo.create(String.class), false, false, false);
  }

  @Test
  public void testConcreteInheritsOverridenPropertyFromNonOptions() throws Exception {
    OptionsModel model = new Generator().generateOptions(ConcreteInheritsOverridenPropertyFromNonOptions.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonOptionsProperty"), "nonOptionsProperty", TypeInfo.create(String.class), true, false, false);
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testAbstractInheritsConcrete() throws Exception {
    OptionsModel model = new Generator().generateOptions(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstract() throws Exception {
    OptionsModel model = new Generator().generateOptions(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstractInheritsAbstract() throws Exception {
    OptionsModel model = new Generator().generateOptions(AbstractInheritsAbstract.class);
    assertNotNull(model);
    assertFalse(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getSuperTypes());
  }

  @Test
  public void testImportedSubinterface() throws Exception {
    OptionsModel model = new Generator().generateOptions(ImportedSubinterface.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testImportedNested() throws Exception {
    OptionsModel model = new Generator().generateOptions(ImportedNested.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testOptionWithGetterAndSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(OptionWithGetterAndSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    PropertyInfo prop = model.getPropertyMap().get("someValue");
    assertProperty(prop, "someValue", TypeInfo.create(String.class), true, false, false);
    assertEquals(prop.getGetter(), "getSomeValue");
    assertEquals(prop.getMethodName(), "setSomeValue");
  }

  private static void assertProperty(PropertyInfo property, String expectedName, TypeInfo expectedType,
                                     boolean expectedDeclared, boolean expectedArray, boolean expectedAdder) {
    assertNotNull(property);
    assertEquals(expectedDeclared, property.isDeclared());
    assertEquals(expectedName, property.getName());
    assertEquals(expectedType, property.getType());
    assertEquals(expectedArray, property.isArray());
    assertEquals(expectedAdder, property.isAdder());
  }

  private void assertInvalidOptions(Class<?> optionsClass) throws Exception {
    try {
      new Generator().generateOptions(optionsClass);
      fail("Was expecting " + optionsClass.getName() + " to fail");
    } catch (GenException ignore) {
    }
  }
}
