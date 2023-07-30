package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.annotations.AnnotationValueAnnotation;
import io.vertx.test.codegen.annotations.BooleanValueAnnotation;
import io.vertx.test.codegen.annotations.ByteValueAnnotation;
import io.vertx.test.codegen.annotations.CharValueAnnotation;
import io.vertx.test.codegen.annotations.ClassValueAnnotation;
import io.vertx.test.codegen.annotations.DoubleValueAnnotation;
import io.vertx.test.codegen.annotations.EmptyAnnotation;
import io.vertx.test.codegen.annotations.EnumValueAnnotation;
import io.vertx.test.codegen.annotations.FloatValuedAnnotation;
import io.vertx.test.codegen.annotations.IntegerValuedAnnotation;
import io.vertx.test.codegen.annotations.LongValuedAnnotation;
import io.vertx.test.codegen.annotations.ShortValueAnnotation;
import io.vertx.test.codegen.annotations.StringValuedAnnotation;
import io.vertx.test.codegen.annotations.TestEnum;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
@DataObject
@EmptyAnnotation
public class AnnotatedDataObject {

  @EmptyAnnotation
  private String annotatedField;

  public AnnotatedDataObject() {

  }

  public AnnotatedDataObject(JsonObject json) {

  }

  public String getAnnotatedField() {
    return annotatedField;
  }

  public AnnotatedDataObject setAnnotatedField(String annotatedField) {
    this.annotatedField = annotatedField;
    return this;
  }

  @EmptyAnnotation
  public String getAnnotatedType() {
    return "";
  }

  @StringValuedAnnotation(value = "aString", array = {"one", "two"})
  public String getAnnotatedWithStringValue() {
    return "";
  }

  @ShortValueAnnotation(value = 1, array = {1, 2})
  public String getAnnotatedWithShortValue() {
    return "";
  }

  @LongValuedAnnotation(value = 1, array = {1L, 2L})
  public String getAnnotatedWithLongValue() {
    return "";
  }

  @IntegerValuedAnnotation(value = 1, array = {1, 2})
  public String getAnnotatedWithIntegerValue() {
    return "";
  }

  @FloatValuedAnnotation(value = 1.0f, array = {1.0f, 2.0f})
  public String getAnnotatedWithFloatValue() {
    return "";
  }

  @AnnotationValueAnnotation(value = @StringValuedAnnotation(value = "aString", array = {"one", "two"}),
    array = {
      @StringValuedAnnotation(value = "aString", array = {"one", "two"}),
      @StringValuedAnnotation(value = "aString", array = {"one", "two"})
    })
  public String getAnnotatedWithAnnotationValue() {
    return "";
  }

  @BooleanValueAnnotation(value = true, array = {true, true})
  public String getAnnotatedWithBooleanValue() {
    return "";
  }

  @EnumValueAnnotation(value = TestEnum.TEST, array = {TestEnum.TEST, TestEnum.TEST})
  public String getAnnotatedWithEnumValue() {
    return "";
  }

  @ByteValueAnnotation(value = 1, array = {0, 1})
  public String getAnnotatedWithByteValue() {
    return "";
  }

  @CharValueAnnotation(value = 'a', array = {'a', 'b'})
  public String getAnnotatedWithCharValue() {
    return "";
  }

  @ClassValueAnnotation(value = String.class, array = {String.class, String.class})
  public String getAnnotatedWithClassValue() {
    return "";
  }

  @DoubleValueAnnotation(value = 1.0, array = {1.0, 2.0})
  public String getAnnotatedWithDoubleValue() {
    return "";
  }
}
