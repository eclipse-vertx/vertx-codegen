package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.OptionsModel;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.TypeInfo;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.OptionsClass;
import io.vertx.test.codegen.testapi.OptionsWithFactoryMethod;
import io.vertx.test.codegen.testapi.OptionsWithNoFactoryMethod;
import io.vertx.test.codegen.testoptions.AdderNormalizationRules;
import io.vertx.test.codegen.testoptions.AdderWithNestedOptions;
import io.vertx.test.codegen.testoptions.ApiAdder;
import io.vertx.test.codegen.testoptions.ApiObject;
import io.vertx.test.codegen.testoptions.ApiSetter;
import io.vertx.test.codegen.testoptions.BasicAdders;
import io.vertx.test.codegen.testoptions.BasicGetters;
import io.vertx.test.codegen.testoptions.BasicSetters;
import io.vertx.test.codegen.testoptions.Empty;
import io.vertx.test.codegen.testoptions.IgnoreMethods;
import io.vertx.test.codegen.testoptions.JsonObjectAdder;
import io.vertx.test.codegen.testoptions.JsonObjectSetter;
import io.vertx.test.codegen.testoptions.ListBasicSetters;
import io.vertx.test.codegen.testoptions.Parameterized;
import io.vertx.test.codegen.testoptions.SetterNormalizationRules;
import io.vertx.test.codegen.testoptions.SetterWithInvalidReturnType;
import io.vertx.test.codegen.testoptions.SetterWithInvalidType;
import io.vertx.test.codegen.testoptions.SetterWithNestedOptions;
import io.vertx.test.codegen.testoptions.SetterWithTwoArguments;
import io.vertx.test.codegen.testoptions.SetterWithZeroArgument;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsTest {

  @Test
  public void testOptionsWithNoFactoryMethod() throws Exception {
    try {
      new Generator().validateOption(OptionsWithNoFactoryMethod.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testOptionsWithFactoryMethod() throws Exception {
    new Generator().validateOption(OptionsWithFactoryMethod.class);
  }

  @Test
  public void testOptionsClass() throws Exception {
    try {
      new Generator().validateOption(OptionsClass.class);
      fail();
    } catch (GenException e) {
    }
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
  public void testInvalidSetters() throws Exception {
    assertInvalidOptions(SetterWithZeroArgument.class);
    assertInvalidOptions(SetterWithTwoArguments.class);
    assertInvalidOptions(SetterWithInvalidType.class);
    assertInvalidOptions(SetterWithInvalidReturnType.class);
  }

  @Test
  public void testBasicSetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicSetters.class);
    assertNotNull(model);
    assertEquals(7, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", TypeInfo.create(String.class), false);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", TypeInfo.create(Integer.class), false);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", TypeInfo.create(int.class), false);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", TypeInfo.create(Boolean.class), false);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", TypeInfo.create(boolean.class), false);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", TypeInfo.create(Long.class), false);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", TypeInfo.create(long.class), false);
  }

  @Test
  public void testSetterNormalizationRules() throws Exception {
    OptionsModel model = new Generator().generateOptions(SetterNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("ha"), "ha", TypeInfo.create(boolean.class), false);
    assertProperty(model.getPropertyMap().get("haGroup"), "haGroup", TypeInfo.create(boolean.class), false);
    assertProperty(model.getPropertyMap().get("group"), "group", TypeInfo.create(boolean.class), false);
  }

  @Test
  public void testListBasicSetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(ListBasicSetters.class);
    assertNotNull(model);
    assertEquals(5, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", TypeInfo.create(String.class), true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", TypeInfo.create(String.class), true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", TypeInfo.create(Integer.class), true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", TypeInfo.create(Boolean.class), true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", TypeInfo.create(Long.class), true);
  }

  @Test
  public void testBasicGetters() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicGetters.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testApiSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(ApiSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", TypeInfo.create(ApiObject.class), false);
  }

  @Test
  public void testJsonObjectSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(JsonObjectSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", TypeInfo.create(JsonObject.class), false);
  }

  @Test
  public void testBasicAdders() throws Exception {
    OptionsModel model = new Generator().generateOptions(BasicAdders.class);
    assertNotNull(model);
    assertEquals(7, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", TypeInfo.create(String.class), true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", TypeInfo.create(Integer.class), true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", TypeInfo.create(int.class), true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", TypeInfo.create(Boolean.class), true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", TypeInfo.create(boolean.class), true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", TypeInfo.create(Long.class), true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", TypeInfo.create(long.class), true);
  }

  @Test
  public void testAdderNormalizationRules() throws Exception {
    OptionsModel model = new Generator().generateOptions(AdderNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("urls"), "urls", TypeInfo.create(boolean.class), true);
    assertProperty(model.getPropertyMap().get("urlLocators"), "urlLocators", TypeInfo.create(boolean.class), true);
    assertProperty(model.getPropertyMap().get("locators"), "locators", TypeInfo.create(boolean.class), true);
  }

  @Test
  public void testApiAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(ApiAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", TypeInfo.create(ApiObject.class), true);
  }

  @Test
  public void testJsonObjectAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(JsonObjectAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", TypeInfo.create(JsonObject.class), true);
  }

  @Test
  public void testNestedOptionsSetter() throws Exception {
    OptionsModel model = new Generator().generateOptions(SetterWithNestedOptions.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nested"), "nested", TypeInfo.create(Empty.class), false);
  }

  @Test
  public void testNestedOptionsAdder() throws Exception {
    OptionsModel model = new Generator().generateOptions(AdderWithNestedOptions.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nesteds"), "nesteds", TypeInfo.create(Empty.class), true);
  }

  @Test
  public void testIgnoreMethods() throws Exception {
    OptionsModel model = new Generator().generateOptions(IgnoreMethods.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  private static void assertProperty(PropertyInfo property, String expectedName, TypeInfo expectedType, boolean expectedArray) {
    assertNotNull(property);
    assertEquals(expectedName, property.getName());
    assertEquals(expectedType, property.getType());
    assertEquals(expectedArray, property.isArray());
  }

  private void assertInvalidOptions(Class<?> optionsClass) throws Exception {
    try {
      new Generator().generateOptions(optionsClass);
      fail("Was expecting " + optionsClass.getName() + " to fail");
    } catch (GenException ignore) {
    }
  }
}
