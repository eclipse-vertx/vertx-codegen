package io.vertx.test.codegen;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.PropertyKind;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.type.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.annotations.EmptyAnnotation;
import io.vertx.test.codegen.testdataobject.Foo;
import io.vertx.test.codegen.annotations.TestEnum;
import io.vertx.test.codegen.testapi.InterfaceDataObject;
import io.vertx.test.codegen.testdataobject.*;
import io.vertx.test.codegen.testdataobject.imported.Imported;
import io.vertx.test.codegen.testdataobject.jsoncodec.DataObjectWithPojoWithCodec;
import org.junit.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTest {

  @Test
  public void testDataObjectWithEmptyConstructor() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(DataObjectWithEmptyConstructor.class);
    assertTrue(model.hasEmptyConstructor());
  }

  @Test
  public void testDataObjectInterface() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(InterfaceDataObject.class);
    assertNotNull(model);
    assertFalse(model.isClass());
  }

  @Test
  public void testEmptyDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(EmptyDataObject.class);
    assertNotNull(model);
    assertTrue(model.isClass());
    assertFalse(model.getGenerateConverter());
    assertFalse(model.getInheritConverter());
    assertTrue(model.isPublicConverter());
    try {
      EmptyDataObject.class.getConstructor();
      fail();
    } catch (NoSuchMethodException ignore) {
    }
    try {
      EmptyDataObject.class.getConstructor(EmptyDataObject.class);
      fail();
    } catch (NoSuchMethodException ignore) {
    }
  }

  @Test
  public void testParameterizedDataObject() throws Exception {
    assertInvalidDataObject(Parameterized.class);
  }

  @Test
  public void testSetterWithNonFluentReturnType() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(SetterWithNonFluentReturnType.class);
    assertNotNull(model);
    assertEquals(2, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testObjectProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(DataObjectWithObjectProperty.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testPropertySetters() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertySetters.class);
    assertNotNull(model);
    assertEquals(14, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", null, null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", null, null, TypeReflectionFactory.create(int.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", null, null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", null, null, TypeReflectionFactory.create(Long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", null, null, TypeReflectionFactory.create(long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("instant"), "instant", "setInstant", null, null, TypeReflectionFactory.create(Instant.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", null, null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", null, null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", null, null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonArray"), "jsonArray", "setJsonArray", null, null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("enumerated"), "enumerated", "setEnumerated", null, null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testSetterNormalizationRules() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(SetterNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("ha"), "ha", "setHA", null, null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("haGroup"), "haGroup", "setHAGroup", null, null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("group"), "group", "setGroup", null, null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testPropertyListSetters() throws Exception {
    testPropertyCollectionSetters(PropertyListSetters.class, PropertyKind.LIST);
  }

  @Test
  public void testPropertySetSetters() throws Exception {
    testPropertyCollectionSetters(PropertySetSetters.class, PropertyKind.SET);
  }

  private void testPropertyCollectionSetters(Class<?> dataObjectClass, PropertyKind expectedKind) throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(dataObjectClass);
    assertNotNull(model);
    assertEquals(12, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", "setExtraClassPath", null, null, TypeReflectionFactory.create(String.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", "setStrings", null, null, TypeReflectionFactory.create(String.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", "setInstants", null, null, TypeReflectionFactory.create(Instant.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "setBoxedIntegers", null, null, TypeReflectionFactory.create(Integer.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "setBoxedBooleans", null, null, TypeReflectionFactory.create(Boolean.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "setBoxedLongs", null, null, TypeReflectionFactory.create(Long.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "setApiObjects", null, null, TypeReflectionFactory.create(ApiObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "setDataObjects", null, null, TypeReflectionFactory.create(EmptyDataObject.class), true, expectedKind, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "setToJsonDataObjects", null, null, TypeReflectionFactory.create(ToJsonDataObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "setJsonObjects", null, null, TypeReflectionFactory.create(JsonObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "setJsonArrays", null, null, TypeReflectionFactory.create(JsonArray.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "setEnumerateds", null, null, TypeReflectionFactory.create(Enumerated.class), true, expectedKind, true);
  }

  @Test
  public void testPropertyListGettersSetters() throws Exception {
    testPropertyCollectionGettersSetters(PropertyListGettersSetters.class, PropertyKind.LIST);
  }

  @Test
  public void testPropertySetGettersSetters() throws Exception {
    testPropertyCollectionGettersSetters(PropertySetGettersSetters.class, PropertyKind.SET);
  }

  private void testPropertyCollectionGettersSetters(Class<?> dataObjectClass, PropertyKind expectedKind) throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(dataObjectClass);
    assertNotNull(model);
    assertEquals(12, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", "setExtraClassPath", null, "getExtraClassPath", TypeReflectionFactory.create(String.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", "setStrings", null, "getStrings", TypeReflectionFactory.create(String.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", "setInstants", null, "getInstants", TypeReflectionFactory.create(Instant.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "setBoxedIntegers", null, "getBoxedIntegers", TypeReflectionFactory.create(Integer.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "setBoxedBooleans", null, "getBoxedBooleans", TypeReflectionFactory.create(Boolean.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "setBoxedLongs", null, "getBoxedLongs", TypeReflectionFactory.create(Long.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "setApiObjects", null, "getApiObjects", TypeReflectionFactory.create(ApiObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "setDataObjects", null, "getDataObjects", TypeReflectionFactory.create(EmptyDataObject.class), true, expectedKind, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "setToJsonDataObjects", null, "getToJsonDataObjects", TypeReflectionFactory.create(ToJsonDataObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "setJsonObjects", null, "getJsonObjects", TypeReflectionFactory.create(JsonObject.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", "setJsonArrays", null, "getJsonArrays", TypeReflectionFactory.create(JsonArray.class), true, expectedKind, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", "setEnumerateds", null, "getEnumerateds", TypeReflectionFactory.create(Enumerated.class), true, expectedKind, true);
  }

  @Test
  public void testPropertyMapGettersAdders() throws Exception {

  }
  @Test
  public void testPropertyMapGettersSetters() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyMapGettersSetters.class);
    assertNotNull(model);
    assertEquals(12, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("stringMap"), "stringMap", "setStringMap", null, "getStringMap", TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("instantMap"), "instantMap", "setInstantMap", null, "getInstantMap", TypeReflectionFactory.create(Instant.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegerMap"), "boxedIntegerMap", "setBoxedIntegerMap", null, "getBoxedIntegerMap", TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleanMap"), "boxedBooleanMap", "setBoxedBooleanMap", null, "getBoxedBooleanMap", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongMap"), "boxedLongMap", "setBoxedLongMap", null, "getBoxedLongMap", TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjectMap"), "apiObjectMap", "setApiObjectMap", null, "getApiObjectMap", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjectMap"), "dataObjectMap", "setDataObjectMap", null, "getDataObjectMap", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjectMap"), "toJsonDataObjectMap", "setToJsonDataObjectMap", null, "getToJsonDataObjectMap", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjectMap"), "jsonObjectMap", "setJsonObjectMap", null, "getJsonObjectMap", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrayMap"), "jsonArrayMap", "setJsonArrayMap", null, "getJsonArrayMap", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumeratedMap"), "enumeratedMap", "setEnumeratedMap", null, "getEnumeratedMap", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objectMap"), "objectMap", "setObjectMap", null, "getObjectMap", TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyMapAdders() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyMapAdders.class);
    assertNotNull(model);
    assertEquals(15, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", null, "addString", null, TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", null, "addInstant", null, TypeReflectionFactory.create(Instant.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", null, "addBoxedInteger", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", null, "addPrimitiveInteger", null, TypeReflectionFactory.create(int.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", null, "addBoxedBoolean", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", null, "addPrimitiveBoolean", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", null, "addBoxedLong", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", null, "addPrimitiveLong", null, TypeReflectionFactory.create(long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", null, "addApiObject", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", null, "addDataObject", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", null, "addToJsonDataObject", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", null, "addJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", null, "addJsonArray", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", null, "addEnumerated", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objects"), "objects", null, "addObject", null, TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyMapSetters() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyMapSetters.class);
    assertNotNull(model);
    assertEquals(12, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("stringMap"), "stringMap", "setStringMap", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("instantMap"), "instantMap", "setInstantMap", null, null, TypeReflectionFactory.create(Instant.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegerMap"), "boxedIntegerMap", "setBoxedIntegerMap", null, null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleanMap"), "boxedBooleanMap", "setBoxedBooleanMap", null, null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongMap"), "boxedLongMap", "setBoxedLongMap", null, null, TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjectMap"), "apiObjectMap", "setApiObjectMap", null, null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjectMap"), "dataObjectMap", "setDataObjectMap", null, null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjectMap"), "toJsonDataObjectMap", "setToJsonDataObjectMap", null, null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjectMap"), "jsonObjectMap", "setJsonObjectMap", null, null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrayMap"), "jsonArrayMap", "setJsonArrayMap", null, null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumeratedMap"), "enumeratedMap", "setEnumeratedMap", null, null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objectMap"), "objectMap", "setObjectMap", null, null, TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyMapSetterAdders() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyMapGettersAdders.class);
    assertNotNull(model);
    assertEquals(12, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", null, "addString", "getStrings", TypeReflectionFactory.create(String.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", null, "addInstant", "getInstants", TypeReflectionFactory.create(Instant.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", null, "addBoxedInteger", "getBoxedIntegers", TypeReflectionFactory.create(Integer.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", null, "addBoxedBoolean", "getBoxedBooleans", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", null, "addBoxedLong", "getBoxedLongs", TypeReflectionFactory.create(Long.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", null, "addApiObject", "getApiObjects", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", null, "addDataObject", "getDataObjects", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.MAP, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", null, "addToJsonDataObject", "getToJsonDataObjects", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", null, "addJsonObject", "getJsonObjects", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", null, "addJsonArray", "getJsonArrays", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", null, "addEnumerated", "getEnumerateds", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.MAP, true);
    assertProperty(model.getPropertyMap().get("objects"), "objects", null, "addObject", "getObjects", TypeReflectionFactory.create(Object.class), true, PropertyKind.MAP, true);
  }

  @Test
  public void testPropertyGetters() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyGetters.class);
    assertNotNull(model);
    assertEquals(14, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", null, null, "getString", TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", null, null, "getBoxedInteger", TypeReflectionFactory.create(Integer.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", null, null, "getPrimitiveInteger", TypeReflectionFactory.create(int.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", null, null, "isBoxedBoolean", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", null, null, "isPrimitiveBoolean", TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", null, null, "getBoxedLong", TypeReflectionFactory.create(Long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", null, null, "getPrimitiveLong", TypeReflectionFactory.create(long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("instant"), "instant", null, null, "getInstant", TypeReflectionFactory.create(Instant.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", null, null, "getApiObject", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", null, null, "getDataObject", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", null, null, "getToJsonDataObject", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", null, null, "getJsonObject", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonArray"), "jsonArray", null, null, "getJsonArray", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("enumerated"), "enumerated", null, null, "getEnumerated", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testPropertyGettersSetters() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyGettersSetters.class);
    assertNotNull(model);
    assertEquals(14, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, "getString", TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("instant"), "instant", "setInstant", null, "getInstant", TypeReflectionFactory.create(Instant.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", null, "getBoxedInteger", TypeReflectionFactory.create(Integer.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", null, "getPrimitiveInteger", TypeReflectionFactory.create(int.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", null, "isBoxedBoolean", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, "isPrimitiveBoolean", TypeReflectionFactory.create(boolean.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", null, "getBoxedLong", TypeReflectionFactory.create(Long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", null, "getPrimitiveLong", TypeReflectionFactory.create(long.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", null, "getApiObject", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", null, "getDataObject", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", null, "getToJsonDataObject", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, "getJsonObject", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("jsonArray"), "jsonArray", "setJsonArray", null, "getJsonArray", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("enumerated"), "enumerated", "setEnumerated", null, "getEnumerated", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testJsonObjectSetter() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(JsonObjectSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testPropertyListAdders() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyListAdders.class);
    assertNotNull(model);
    assertEquals(14, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", null, "addString", null, TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", null, "addInstant", null, TypeReflectionFactory.create(Instant.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", null, "addBoxedInteger", null, TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", null, "addPrimitiveInteger", null, TypeReflectionFactory.create(int.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", null, "addBoxedBoolean", null, TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", null, "addPrimitiveBoolean", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", null, "addBoxedLong", null, TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", null, "addPrimitiveLong", null, TypeReflectionFactory.create(long.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", null, "addApiObject", null, TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", null, "addDataObject", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", null, "addToJsonDataObject", null, TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", null, "addJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", null, "addJsonArray", null, TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", null, "addEnumerated", null, TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testPropertyListGettersAdders() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(PropertyListGettersAdders.class);
    assertNotNull(model);
    assertEquals(11, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", null, "addString", "getStrings", TypeReflectionFactory.create(String.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("instants"), "instants", null, "addInstant", "getInstants", TypeReflectionFactory.create(Instant.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", null, "addBoxedInteger", "getBoxedIntegers", TypeReflectionFactory.create(Integer.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", null, "addBoxedBoolean", "getBoxedBooleans", TypeReflectionFactory.create(Boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", null, "addBoxedLong", "getBoxedLongs", TypeReflectionFactory.create(Long.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", null, "addApiObject", "getApiObjects", TypeReflectionFactory.create(ApiObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", null, "addDataObject", "getDataObjects", TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", null, "addToJsonDataObject", "getToJsonDataObjects", TypeReflectionFactory.create(ToJsonDataObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", null, "addJsonObject", "getJsonObjects", TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("jsonArrays"), "jsonArrays", null, "addJsonArray", "getJsonArrays", TypeReflectionFactory.create(JsonArray.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("enumerateds"), "enumerateds", null, "addEnumerated", "getEnumerateds", TypeReflectionFactory.create(Enumerated.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testAdderNormalizationRules() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AdderNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("urls"), "urls", null, "addURL", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("urlLocators"), "urlLocators", null, "addURLLocator", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST, true);
    assertProperty(model.getPropertyMap().get("locators"), "locators", null, "addLocator", null, TypeReflectionFactory.create(boolean.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testJsonObjectAdder() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(JsonObjectAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", null, "addJsonObject", null, TypeReflectionFactory.create(JsonObject.class), true, PropertyKind.LIST, true);
  }

  @Test
  public void testNestedDataObjectSetter() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(SetterWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nested"), "nested", "setNested", null, null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.VALUE, false);
  }

  @Test
  public void testNestedDataObjectAdder() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AdderWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nesteds"), "nesteds", null, "addNested", null, TypeReflectionFactory.create(EmptyDataObject.class), true, PropertyKind.LIST, false);
  }

  @Test
  public void testIgnoreMethods() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(IgnoreMethods.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteInheritsConcrete() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteExtendsConcrete.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Concrete.class)), model.getSuperTypes());
    assertEquals(TypeReflectionFactory.create(Concrete.class), model.getSuperType());
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getAbstractSuperTypes());
  }

  @Test
  public void testConcreteImplementsAbstract() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteInheritsAbstract.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getSuperTypes());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getAbstractSuperTypes());
    assertNull(model.getSuperType());
  }

  @Test
  public void testConcreteImplementsNonDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteImplementsNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteImplementsFromNonDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteImplementsFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteImplementsFromDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteImplementsFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testConcreteOverridesFromDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteOverridesFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
  }

  @Test
  public void testConcreteOverridesFromNonDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteOverridesFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
    assertEquals(Collections.<ClassTypeInfo>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteOverridesFromAbstractDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConcreteOverridesFromAbstractDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("inheritedProperty"), "inheritedProperty", "setInheritedProperty", null, null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("overriddenProperty"), "overriddenProperty", "setOverriddenProperty", null, null, TypeReflectionFactory.create(String.class), false, PropertyKind.VALUE, true);
    assertProperty(model.getPropertyMap().get("abstractProperty"), "abstractProperty", "setAbstractProperty", null, null, TypeReflectionFactory.create(String.class), true, PropertyKind.VALUE, true);
  }

  @Test
  public void testAbstractInheritsConcrete() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstract() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstractInheritsAbstract() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AbstractInheritsAbstract.class);
    assertNotNull(model);
    assertFalse(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Abstract.class)), model.getSuperTypes());
  }

  @Test
  public void testImportedSubinterface() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ImportedSubinterface.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testImportedNested() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ImportedNested.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((ClassTypeInfo) TypeReflectionFactory.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testCommentedDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(CommentedDataObject.class);
    Doc doc = model.getDoc();
    assertEquals(" The data object comment.\n", doc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(UncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    assertNull(propertyInfo.getDoc());
  }

  @Test
  public void testCommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(CommentedProperty.class);
    PropertyInfo setterProperty = model.getPropertyMap().get("setterProperty");
    assertEquals(" Setter setter property description.\n", setterProperty.getDoc().getFirstSentence().getValue());
    PropertyInfo getterProperty = model.getPropertyMap().get("getterProperty");
    assertEquals(" Getter getter property description.\n", getterProperty.getDoc().getFirstSentence().getValue());
    PropertyInfo getterAndSetterProperty = model.getPropertyMap().get("getterAndSetterProperty");
    assertEquals(" GetterAndSetter setter property description.\n", getterAndSetterProperty.getDoc().getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyInheritedFromCommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(CommentedPropertyInheritedFromCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(UncommentedPropertyOverridesSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesAncestorCommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(UncommentedPropertyOverridesAncestorSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(CommentedPropertyOverridesCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesUncommentedProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(CommentedPropertyOverridesUncommentedProperty.class, AbstractUncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testDataObjectWithIgnoredProperty() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(DataObjectInterfaceWithIgnoredProperty.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConverterDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConverterDataObject.class);
    assertTrue(model.getGenerateConverter());
    assertFalse(model.isPublicConverter());
  }

  @Test
  public void testNoConverterDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(NoConverterDataObject.class);
    assertFalse(model.getGenerateConverter());
    assertTrue(model.isPublicConverter());
  }

  @Test
  public void testInheritedConverterDataObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(InheritingConverterDataObject.class);
    assertTrue(model.getInheritConverter());
  }

  @Test
  public void testToJson() throws Exception {
    assertTrue(new GeneratorHelper().generateDataObject(ToJsonDataObject.class).isEncodable());
    assertFalse(new GeneratorHelper().generateDataObject(EmptyDataObject.class).isEncodable());
  }

  @Test
  public void testAnnotatedObject() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertEquals(2, model.getAnnotations().size());
    assertEquals(EmptyAnnotation.class.getSimpleName(),model.getAnnotations().get(1).getSimpleName());
  }

  @Test
  public void testAnnotatedField() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedField").getAnnotations().size());
    assertEquals(0, model.getPropertyMap().get("annotatedField").getAnnotations().get(0).getMembersNames().size());
  }

  @Test
  public void testAnnotatatedClassWithAnnotatedValue() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(Foo.class);
  }

  @Test
  public void testStringAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals("aString", model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new String[]{"one", "two"}, ((List) model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals("defaultString", model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testShortAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1, (short) model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Short[]{1, 2}, ((List) model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1, (short) model.getPropertyMap().get("annotatedWithShortValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testLongAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1, (long) model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Long[]{1L, 2L}, ((List) model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1, (long) model.getPropertyMap().get("annotatedWithLongValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testIntegerAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1, (int) model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Integer[]{1, 2}, ((List) model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1, (int) model.getPropertyMap().get("annotatedWithIntegerValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testFloatAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1.0f, (float) model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("value"), 0);
    assertArrayEquals(new Float[]{1.0f, 2.0f}, ((List) model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1.0f, (float) model.getPropertyMap().get("annotatedWithFloatValue").getAnnotations().get(0).getMember("defaultValue"), 0);
  }

  @Test
  public void testAnnotationAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    AnnotationValueInfo expected = model.getPropertyMap().get("annotatedWithStringValue").getAnnotations().get(0);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().size());
    assertEquals(2, model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().get(0).getMember("array"));
    assertEquals(expected, model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new AnnotationValueInfo[]{expected, expected}, ((List) model.getPropertyMap().get("annotatedWithAnnotationValue").getAnnotations().get(0).getMember("array")).toArray());
  }

  @Test
  public void testBooleanAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(true, model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Boolean[]{true, true}, ((List) model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(true, model.getPropertyMap().get("annotatedWithBooleanValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testEnumAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(TestEnum.TEST.name(), model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new String[]{TestEnum.TEST.name(), TestEnum.TEST.name()}, ((List) model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(TestEnum.TEST.name(), model.getPropertyMap().get("annotatedWithEnumValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testByteAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1, (byte) model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Byte[]{0, 1}, ((List) model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1, (byte) model.getPropertyMap().get("annotatedWithByteValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testCharAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals('a', (char) model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("value"));
    assertArrayEquals(new Character[]{'a', 'b'}, ((List) model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals('a', (char) model.getPropertyMap().get("annotatedWithCharValue").getAnnotations().get(0).getMember("defaultValue"));
  }

  @Test
  public void testClassAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(String.class, Class.forName(((ClassTypeInfo) model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("value")).getName()));
    assertArrayEquals(new Class[]{String.class, String.class}, ((List) model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("array")).stream().map(i -> {
      try {
        return Class.forName(((ClassTypeInfo) i).getName());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
      }
    }).toArray());
    assertEquals(String.class, Class.forName(((ClassTypeInfo) model.getPropertyMap().get("annotatedWithClassValue").getAnnotations().get(0).getMember("defaultValue")).getName()));
  }

  @Test
  public void testDoubleAnnotated() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(AnnotatedDataObject.class);
    assertTrue(model.getPropertyMap().values().stream().allMatch(PropertyInfo::isAnnotated));
    assertEquals(1, model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().size());
    assertEquals(3, model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMembersNames().size());
    assertNotNull(model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("value"));
    assertNotNull(model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("array"));
    assertNotNull(model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("defaultValue"));
    assertEquals(1.0, (double) model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("value"), 0);
    assertArrayEquals(new Double[]{1.0, 2.0}, ((List) model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("array")).toArray());
    assertEquals(1.0, (double) model.getPropertyMap().get("annotatedWithDoubleValue").getAnnotations().get(0).getMember("defaultValue"), 0);
  }

  private static void assertProperty(
      PropertyInfo property,
      String expectedName,
      String expectedSetter,
      String expectedAdder,
      String expectedGetter,
      TypeInfo expectedType,
      boolean expectedDeclared,
      PropertyKind expectedKind,
      boolean expectedJsonifiable) {
    assertNotNull(property);
    assertEquals("Was expecting property to have be declared=" + expectedDeclared, expectedDeclared, property.isDeclared());
    assertEquals(expectedSetter, property.getSetterMethod());
    assertEquals(expectedAdder, property.getAdderMethod());
    assertEquals(expectedGetter, property.getGetterMethod());
    assertEquals(expectedName, property.getName());
    assertEquals(expectedType, property.getType());
    assertEquals(expectedKind, property.getKind());
    assertEquals(expectedJsonifiable, property.isJsonifiable());
  }

  @Test
  public void testDataObjectWithAnnotations() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(DataObjectWithAnnotatedField.class);
    assertNotNull(model);
    assertTrue(model.isClass());
    assertTrue(model.getGenerateConverter());
    assertTrue(model.isPublicConverter());
    PropertyInfo idModel = model.getPropertyMap().get("id");
    assertEquals(1, idModel.getAnnotations().size());
    assertNotNull(idModel.getAnnotation(SomeAnnotation.class.getName()).getName());
    PropertyInfo fieldWithMethodAnnotationModel = model.getPropertyMap().get("fieldWithMethodAnnotation");
    assertEquals(2, fieldWithMethodAnnotationModel.getAnnotations().size());
    assertNotNull(fieldWithMethodAnnotationModel.getAnnotation(SomeAnnotation.class.getName()).getName());
    assertNotNull(fieldWithMethodAnnotationModel.getAnnotation(SomeMethodAnnotation.class.getName()).getName());
  }

  @Test
  public void testDataObjectWithJsonCodec() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(DataObjectWithPojoWithCodec.class);
    assertNotNull(model);
    assertTrue(model.isClass());
    assertTrue(model.getGenerateConverter());
    assertTrue(model.isPublicConverter());

    PropertyInfo myPojoProperty = model.getPropertyMap().get("myPojo");
    assertEquals(ClassKind.DATA_OBJECT, myPojoProperty.getType().getKind());
    assertTrue(((DataObjectTypeInfo)myPojoProperty.getType()).hasJsonDecoder());
    assertTrue(((DataObjectTypeInfo)myPojoProperty.getType()).hasJsonEncoder());
  }

  @Test
  public void testDataObjectWithGenerateConverterMustGenerateCompleteCodec() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConverterGeneratesCompleteCodec.class);
    assertNotNull(model);
    assertTrue(model.isEncodable());
    assertTrue(model.isDecodable());
    assertTrue(model.hasEmptyConstructor());
    assertFalse(model.hasToJsonMethod());
    assertFalse(model.hasJsonConstructor());
    assertEquals(ConverterGeneratesCompleteCodec.class.getPackage().getName() + "." + ConverterGeneratesCompleteCodec.class.getSimpleName() + "Converter", model.getType().getJsonDecoderFQCN());
    assertEquals(ConverterGeneratesCompleteCodec.class.getPackage().getName() + "." + ConverterGeneratesCompleteCodec.class.getSimpleName() + "Converter", model.getType().getJsonEncoderFQCN());
  }

  @Test
  public void testDataObjectWithGenerateConverterWithoutEmptyConstructorMustGenerateEncoder() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConverterWithNoEmptyConstructorGeneratesEncodableCodec.class);
    assertNotNull(model);
    assertFalse(model.isDecodable());
    assertTrue(model.isEncodable());
    assertNull(model.getType().getJsonDecoderFQCN());
    assertEquals(ConverterWithNoEmptyConstructorGeneratesEncodableCodec.class.getPackage().getName() + "." + ConverterWithNoEmptyConstructorGeneratesEncodableCodec.class.getSimpleName() + "Converter", model.getType().getJsonEncoderFQCN());
  }

  @Test
  public void testAbstractDataObjectMustGenerateEncoder() throws Exception {
    DataObjectModel model = new GeneratorHelper().generateDataObject(ConverterWithAbstractClassGeneratesEncodableCodec.class);
    assertNotNull(model);
    assertFalse(model.getType().hasJsonDecoder());
    assertTrue(model.getType().hasJsonEncoder());
    assertNull(model.getType().getJsonDecoderFQCN());
    assertEquals(ConverterWithAbstractClassGeneratesEncodableCodec.class.getPackage().getName() + "." + ConverterWithAbstractClassGeneratesEncodableCodec.class.getSimpleName() + "Converter", model.getType().getJsonEncoderFQCN());
  }

  @Test
  public void testAbstractDataObjectAndDecodeMustGenerateCompleteCodec() throws Exception {
    Class<ConverterWithAbstractClassAndDecodeGeneratesCompleteCodec> clazz = ConverterWithAbstractClassAndDecodeGeneratesCompleteCodec.class;
    DataObjectModel model = new GeneratorHelper().generateDataObject(clazz);
    assertNotNull(model);
    assertTrue(model.isEncodable());
    assertTrue(model.isDecodable());
    assertFalse(model.hasEmptyConstructor());
    assertFalse(model.hasToJsonMethod());
    assertFalse(model.hasJsonConstructor());
    assertTrue(model.hasDecodeStaticMethod());
    assertEquals(clazz.getPackage().getName() + "." + clazz.getSimpleName() + "Converter", model.getType().getJsonDecoderFQCN());
    assertEquals(clazz.getPackage().getName() + "." + clazz.getSimpleName() + "Converter", model.getType().getJsonEncoderFQCN());
  }

  private void assertInvalidDataObject(Class<?> dataObjectClass) throws Exception {
    try {
      new GeneratorHelper().generateDataObject(dataObjectClass);
      fail("Was expecting " + dataObjectClass.getName() + " to fail");
    } catch (GenException ignore) {
    }
  }
}
