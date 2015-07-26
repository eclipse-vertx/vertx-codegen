package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.TypeInfo;
import io.vertx.codegen.doc.Doc;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.DataObjectInterface;
import io.vertx.test.codegen.testapi.DataObjectWithNoCopyConstructor;
import io.vertx.test.codegen.testapi.DataObjectWithNoDefaultConstructor;
import io.vertx.test.codegen.testapi.DataObjectWithNoJsonObjectConstructor;
import io.vertx.test.codegen.testdataobject.Abstract;
import io.vertx.test.codegen.testdataobject.AbstractCommentedProperty;
import io.vertx.test.codegen.testdataobject.AbstractInheritsAbstract;
import io.vertx.test.codegen.testdataobject.AbstractUncommentedProperty;
import io.vertx.test.codegen.testdataobject.AdderNormalizationRules;
import io.vertx.test.codegen.testdataobject.AdderWithNestedDataObject;
import io.vertx.test.codegen.testdataobject.ApiObject;
import io.vertx.test.codegen.testdataobject.PropertyAdders;
import io.vertx.test.codegen.testdataobject.PropertyGetters;
import io.vertx.test.codegen.testdataobject.PropertySetters;
import io.vertx.test.codegen.testdataobject.CommentedDataObject;
import io.vertx.test.codegen.testdataobject.CommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyInheritedFromCommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyOverridesCommentedProperty;
import io.vertx.test.codegen.testdataobject.CommentedPropertyOverridesUncommentedProperty;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromAbstractDataObject;
import io.vertx.test.codegen.testdataobject.DataObjectInterfaceWithIgnoredProperty;
import io.vertx.test.codegen.testdataobject.ToJsonDataObject;
import io.vertx.test.codegen.testdataobject.UncommentedPropertyOverridesSuperCommentedProperty;
import io.vertx.test.codegen.testdataobject.Concrete;
import io.vertx.test.codegen.testdataobject.ConcreteInheritsAbstract;
import io.vertx.test.codegen.testdataobject.ConcreteExtendsConcrete;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteOverridesFromDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsFromNonDataObject;
import io.vertx.test.codegen.testdataobject.ConcreteImplementsFromDataObject;
import io.vertx.test.codegen.testdataobject.EmptyDataObject;
import io.vertx.test.codegen.testdataobject.IgnoreMethods;
import io.vertx.test.codegen.testdataobject.ImportedNested;
import io.vertx.test.codegen.testdataobject.ImportedSubinterface;
import io.vertx.test.codegen.testdataobject.JsonObjectAdder;
import io.vertx.test.codegen.testdataobject.JsonObjectSetter;
import io.vertx.test.codegen.testdataobject.ListBasicSetters;
import io.vertx.test.codegen.testdataobject.UncommentedProperty;
import io.vertx.test.codegen.testdataobject.Parameterized;
import io.vertx.test.codegen.testdataobject.SetterNormalizationRules;
import io.vertx.test.codegen.testdataobject.SetterWithNestedDataObject;
import io.vertx.test.codegen.testdataobject.SetterWithNonFluentReturnType;
import io.vertx.test.codegen.testdataobject.UncommentedPropertyOverridesAncestorSuperCommentedProperty;
import io.vertx.test.codegen.testdataobject.imported.Imported;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectTest {

  @Test
  public void testDataObjectsWithNoDefaultConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoDefaultConstructor.class);
  }

  @Test
  public void testDataObjectWithNoCopyConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoCopyConstructor.class);
  }

  @Test
  public void testDataObjectWithNoJsonObjectConstructor() throws Exception {
    assertInvalidDataObject(DataObjectWithNoJsonObjectConstructor.class);
  }

  @Test
  public void testDataObjectInterface() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(DataObjectInterface.class);
    assertNotNull(model);
  }

  @Test
  public void testEmptyDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(EmptyDataObject.class);
    assertNotNull(model);
  }

  @Test
  public void testParameterizedDataObject() throws Exception {
    assertInvalidDataObject(Parameterized.class);
  }

  @Test
  public void testSetterWithNonFluentReturnType() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterWithNonFluentReturnType.class);
    assertNotNull(model);
    assertEquals(2, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, TypeInfo.create(String.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, TypeInfo.create(boolean.class), true, false, false, true);
  }

  @Test
  public void testPropertySetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertySetters.class);
    assertNotNull(model);
    assertEquals(10, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", null, TypeInfo.create(String.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", null, TypeInfo.create(Integer.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", null, TypeInfo.create(int.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", null, TypeInfo.create(Boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", null, TypeInfo.create(boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", null, TypeInfo.create(Long.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", null, TypeInfo.create(long.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", null, TypeInfo.create(ApiObject.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", null, TypeInfo.create(EmptyDataObject.class), true, false, false, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", null, TypeInfo.create(ToJsonDataObject.class), true, false, false, true);
  }

  @Test
  public void testSetterNormalizationRules() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("ha"), "ha", "setHA", null, TypeInfo.create(boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("haGroup"), "haGroup", "setHAGroup", null, TypeInfo.create(boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("group"), "group", "setGroup", null, TypeInfo.create(boolean.class), true, false, false, true);
  }

  @Test
  public void testListBasicSetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ListBasicSetters.class);
    assertNotNull(model);
    assertEquals(5, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("extraClassPath"), "extraClassPath", "setExtraClassPath", null, TypeInfo.create(String.class), true, true, false, true);
    assertProperty(model.getPropertyMap().get("strings"), "strings", "setStrings", null, TypeInfo.create(String.class), true, true, false, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "setBoxedIntegers", null, TypeInfo.create(Integer.class), true, true, false, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "setBoxedBooleans", null, TypeInfo.create(Boolean.class), true, true, false, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "setBoxedLongs", null, TypeInfo.create(Long.class), true, true, false, true);
  }

  @Test
  public void testPropertyGetters() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyGetters.class);
    assertNotNull(model);
    assertEquals(10, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("string"), "string", "setString", "getString", TypeInfo.create(String.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedInteger"), "boxedInteger", "setBoxedInteger", "getBoxedInteger", TypeInfo.create(Integer.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveInteger"), "primitiveInteger", "setPrimitiveInteger", "getPrimitiveInteger", TypeInfo.create(int.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedBoolean"), "boxedBoolean", "setBoxedBoolean", "isBoxedBoolean", TypeInfo.create(Boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveBoolean"), "primitiveBoolean", "setPrimitiveBoolean", "isPrimitiveBoolean", TypeInfo.create(boolean.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("boxedLong"), "boxedLong", "setBoxedLong", "getBoxedLong", TypeInfo.create(Long.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("primitiveLong"), "primitiveLong", "setPrimitiveLong", "getPrimitiveLong", TypeInfo.create(long.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("apiObject"), "apiObject", "setApiObject", "getApiObject", TypeInfo.create(ApiObject.class), true, false, false, true);
    assertProperty(model.getPropertyMap().get("dataObject"), "dataObject", "setDataObject", "getDataObject", TypeInfo.create(EmptyDataObject.class), true, false, false, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObject"), "toJsonDataObject", "setToJsonDataObject", "getToJsonDataObject", TypeInfo.create(ToJsonDataObject.class), true, false, false, true);
  }

  @Test
  public void testJsonObjectSetter() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(JsonObjectSetter.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObject"), "jsonObject", "setJsonObject", null, TypeInfo.create(JsonObject.class), true, false, false, true);
  }

  @Test
  public void testPropertyAdders() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(PropertyAdders.class);
    assertNotNull(model);
    assertEquals(10, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("strings"), "strings", "addString", null, TypeInfo.create(String.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("boxedIntegers"), "boxedIntegers", "addBoxedInteger", null, TypeInfo.create(Integer.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveIntegers"), "primitiveIntegers", "addPrimitiveInteger", null, TypeInfo.create(int.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("boxedBooleans"), "boxedBooleans", "addBoxedBoolean", null, TypeInfo.create(Boolean.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveBooleans"), "primitiveBooleans", "addPrimitiveBoolean", null, TypeInfo.create(boolean.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("boxedLongs"), "boxedLongs", "addBoxedLong", null, TypeInfo.create(Long.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("primitiveLongs"), "primitiveLongs", "addPrimitiveLong", null, TypeInfo.create(long.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("apiObjects"), "apiObjects", "addApiObject", null, TypeInfo.create(ApiObject.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("dataObjects"), "dataObjects", "addDataObject", null, TypeInfo.create(EmptyDataObject.class), true, true, true, false);
    assertProperty(model.getPropertyMap().get("toJsonDataObjects"), "toJsonDataObjects", "addToJsonDataObject", null, TypeInfo.create(ToJsonDataObject.class), true, true, true, true);
  }

  @Test
  public void testAdderNormalizationRules() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AdderNormalizationRules.class);
    assertNotNull(model);
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("urls"), "urls", "addURL", null, TypeInfo.create(boolean.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("urlLocators"), "urlLocators", "addURLLocator", null, TypeInfo.create(boolean.class), true, true, true, true);
    assertProperty(model.getPropertyMap().get("locators"), "locators", "addLocator", null, TypeInfo.create(boolean.class), true, true, true, true);
  }

  @Test
  public void testJsonObjectAdder() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(JsonObjectAdder.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("jsonObjects"), "jsonObjects", "addJsonObject", null, TypeInfo.create(JsonObject.class), true, true, true, true);
  }

  @Test
  public void testNestedDataObjectSetter() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(SetterWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nested"), "nested", "setNested", null, TypeInfo.create(EmptyDataObject.class), true, false, false, false);
  }

  @Test
  public void testNestedDataObjectAdder() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AdderWithNestedDataObject.class);
    assertNotNull(model);
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nesteds"), "nesteds", "addNested", null, TypeInfo.create(EmptyDataObject.class), true, true, true, false);
  }

  @Test
  public void testIgnoreMethods() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(IgnoreMethods.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteInheritsConcrete() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteExtendsConcrete.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Concrete.class)), model.getSuperTypes());
    assertEquals(TypeInfo.create(Concrete.class), model.getSuperType());
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getAbstractSuperTypes());
  }

  @Test
  public void testConcreteImplementsAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteInheritsAbstract.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getSuperTypes());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getAbstractSuperTypes());
    assertNull(model.getSuperType());
  }

  @Test
  public void testConcreteImplementsNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
  }

  @Test
  public void testConcreteImplementsFromNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, TypeInfo.create(String.class), true, false, false, true);
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteImplementsFromDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteImplementsFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, TypeInfo.create(String.class), true, false, false, true);
  }

  @Test
  public void testConcreteOverridesFromDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("dataObjectProperty"), "dataObjectProperty", "setDataObjectProperty", null, TypeInfo.create(String.class), false, false, false, true);
  }

  @Test
  public void testConcreteOverridesFromNonDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromNonDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(1, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("nonDataObjectProperty"), "nonDataObjectProperty", "setNonDataObjectProperty", null, TypeInfo.create(String.class), true, false, false, true);
    assertEquals(Collections.<TypeInfo.Class>emptySet(), model.getSuperTypes());
  }

  @Test
  public void testConcreteOverridesFromAbstractDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ConcreteOverridesFromAbstractDataObject.class);
    assertNotNull(model);
    assertTrue(model.isConcrete());
    assertEquals(3, model.getPropertyMap().size());
    assertProperty(model.getPropertyMap().get("inheritedProperty"), "inheritedProperty", "setInheritedProperty", null, TypeInfo.create(String.class), false, false, false, true);
    assertProperty(model.getPropertyMap().get("overriddenProperty"), "overriddenProperty", "setOverriddenProperty", null, TypeInfo.create(String.class), false, false, false, true);
    assertProperty(model.getPropertyMap().get("abstractProperty"), "abstractProperty", "setAbstractProperty", null, TypeInfo.create(String.class), true, false, false, true);
  }

  @Test
  public void testAbstractInheritsConcrete() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(Abstract.class);
    assertNotNull(model);
    assertTrue(model.isAbstract());
  }

  @Test
  public void testAbstractInheritsAbstract() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(AbstractInheritsAbstract.class);
    assertNotNull(model);
    assertFalse(model.isConcrete());
    assertEquals(0, model.getPropertyMap().size());
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Abstract.class)), model.getSuperTypes());
  }

  @Test
  public void testImportedSubinterface() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ImportedSubinterface.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testImportedNested() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(ImportedNested.class);
    assertNotNull(model);
    assertEquals(Collections.singleton((TypeInfo.Class) TypeInfo.create(Imported.class)), model.getImportedTypes());
  }

  @Test
  public void testCommentedDataObject() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedDataObject.class);
    Doc doc = model.getDoc();
    assertEquals(" The data object comment.\n", doc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    assertNull(propertyInfo.getDoc());
  }

  @Test
  public void testCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyInheritedFromCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyInheritedFromCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedPropertyOverridesSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testUncommentedPropertyOverridesAncestorCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(UncommentedPropertyOverridesAncestorSuperCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesCommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyOverridesCommentedProperty.class, AbstractCommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testCommentedPropertyOverridesUncommentedProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(CommentedPropertyOverridesUncommentedProperty.class, AbstractUncommentedProperty.class);
    PropertyInfo propertyInfo = model.getPropertyMap().get("theProperty");
    Doc propertyDoc = propertyInfo.getDoc();
    assertEquals(" The overriden property description.\n", propertyDoc.getFirstSentence().getValue());
  }

  @Test
  public void testDataObjectWithIgnoredProperty() throws Exception {
    DataObjectModel model = new Generator().generateDataObject(DataObjectInterfaceWithIgnoredProperty.class);
    assertNotNull(model);
    assertEquals(0, model.getPropertyMap().size());
  }

  private static void assertProperty(
      PropertyInfo property,
      String expectedName,
      String expectedMutator,
      String expectedReader,
      TypeInfo expectedType,
      boolean expectedDeclared,
      boolean expectedArray,
      boolean expectedAdder,
      boolean expectedJsonifiable) {
    assertNotNull(property);
    assertEquals("Was expecting property to have be declared=" + expectedDeclared, expectedDeclared, property.isDeclared());
    assertEquals(expectedMutator, property.getMutatorMethod());
    assertEquals(expectedReader, property.getReaderMethod());
    assertEquals(expectedName, property.getName());
    assertEquals(expectedType, property.getType());
    assertEquals(expectedArray, property.isArray());
    assertEquals(expectedAdder, property.isAdder());
    assertEquals(expectedJsonifiable, property.isJsonifiable());
  }

  private void assertInvalidDataObject(Class<?> dataObjectClass) throws Exception {
    try {
      new Generator().generateDataObject(dataObjectClass);
      fail("Was expecting " + dataObjectClass.getName() + " to fail");
    } catch (GenException ignore) {
    }
  }
}
