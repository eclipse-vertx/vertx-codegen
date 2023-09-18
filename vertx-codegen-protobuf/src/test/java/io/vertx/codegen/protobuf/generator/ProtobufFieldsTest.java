package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.PropertyKind;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.ProtobufField;
import io.vertx.codegen.type.AnnotationValueInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ProtobufFieldsTest {
  private static PropertyInfo prop(String name, Integer fieldNumberAnnotation) {
    List<AnnotationValueInfo> anns = new ArrayList<>();
    if (fieldNumberAnnotation != null) {
      AnnotationValueInfo ann = new AnnotationValueInfo(ProtobufField.class.getName());
      ann.putMember("value", fieldNumberAnnotation);
      anns.add(ann);
    }
    return new PropertyInfo(true, name, null, null, null, null, null,
      anns, PropertyKind.VALUE, true, false, null);
  }

  @Test
  public void verifyFieldNames() {
    List<PropertyInfo> props = Arrays.asList(
      prop("foo", null),
      prop("bar", null));

    // doesn't throw
    ProtobufFields.verifyFieldNames(props, Collections.singleton("baz"));

    IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.verifyFieldNames(props, Collections.singleton("foo"));
    });
    assertTrue(error.getMessage().contains("is reserved"));

    assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.verifyFieldNames(props, new HashSet<>(Arrays.asList("foo", "bar")));
    });
    assertTrue(error.getMessage().contains("is reserved"));

    assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.verifyFieldNames(props, new HashSet<>(Arrays.asList("foo", "baz")));
    });
    assertTrue(error.getMessage().contains("is reserved"));
  }

  @Test
  public void manualFieldNumbers() {
    IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(prop("foo", null)),
        FieldNumberStrategy.MANUAL, Collections.emptySet());
    });
    assertTrue(error.getMessage().contains("does not declare a field number"));

    error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(Arrays.asList(
          prop("foo", 1),
          prop("bar", 1)),
        FieldNumberStrategy.MANUAL, Collections.emptySet());
    });
    assertTrue(error.getMessage().contains("collides with property"));

    error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(prop("foo", 1)),
        FieldNumberStrategy.MANUAL, Collections.singleton(1));
    });
    assertTrue(error.getMessage().contains("has a reserved field number"));

    Map<String, Integer> fieldNumbers = ProtobufFields.fieldNumbers(
      Arrays.asList(
        prop("foo", 1),
        prop("bar", 5),
        prop("baz", 7),
        prop("quux", 3)),
      FieldNumberStrategy.MANUAL, Collections.emptySet());
    assertEquals(1, (int) fieldNumbers.get("foo"));
    assertEquals(5, (int) fieldNumbers.get("bar"));
    assertEquals(7, (int) fieldNumbers.get("baz"));
    assertEquals(3, (int) fieldNumbers.get("quux"));
  }

  @Test
  public void compactFieldNumbers() {
    IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(prop("foo", 1)),
        FieldNumberStrategy.COMPACT, Collections.singleton(1));
    });
    assertTrue(error.getMessage().contains("has a reserved field number"));

    error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(
          prop("foo", 1),
          prop("bar", 1)),
        FieldNumberStrategy.COMPACT, Collections.emptySet());
    });
    assertTrue(error.getMessage().contains("collides with property"));

    Map<String, Integer> fieldNumbers = ProtobufFields.fieldNumbers(
      Arrays.asList(
        prop("foo", 1),
        prop("bar", null),
        prop("baz", 3),
        prop("quux", null)),
      FieldNumberStrategy.COMPACT, Collections.singleton(2));
    assertEquals(1, (int) fieldNumbers.get("foo"));
    assertEquals(4, (int) fieldNumbers.get("bar"));
    assertEquals(3, (int) fieldNumbers.get("baz"));
    assertEquals(5, (int) fieldNumbers.get("quux"));
  }

  @Test
  public void segmentedFieldNumbers() {
    IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(prop("foo", 1)),
        FieldNumberStrategy.SEGMENTED, Collections.singleton(1));
    });
    assertTrue(error.getMessage().contains("has a reserved field number"));

    error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(
          prop("foo", 1),
          prop("bar", 1)),
        FieldNumberStrategy.SEGMENTED, Collections.emptySet());
    });
    assertTrue(error.getMessage().contains("collides with property"));

    error = assertThrows(IllegalArgumentException.class, () -> {
      ProtobufFields.fieldNumbers(
        Arrays.asList(
          prop("foo", null),
          prop("bar", null),
          prop("baz", null),
          prop("quux", 3)),
        FieldNumberStrategy.SEGMENTED, Collections.emptySet());
    });
    assertTrue(error.getMessage().contains("collides with property"));

    Map<String, Integer> fieldNumbers = ProtobufFields.fieldNumbers(
      Arrays.asList(
        prop("foo", null),
        prop("bar", 5),
        prop("baz", 10),
        prop("quux", null)),
      FieldNumberStrategy.SEGMENTED, Collections.singleton(2));
    assertEquals(1, (int) fieldNumbers.get("foo"));
    assertEquals(5, (int) fieldNumbers.get("bar"));
    assertEquals(10, (int) fieldNumbers.get("baz"));
    assertEquals(11, (int) fieldNumbers.get("quux"));

    fieldNumbers = ProtobufFields.fieldNumbers(
      Arrays.asList(
        prop("foo", null),
        prop("bar", null),
        prop("baz", 3),
        prop("quux", null)),
      FieldNumberStrategy.SEGMENTED, Collections.singleton(4));
    assertEquals(1, (int) fieldNumbers.get("foo"));
    assertEquals(2, (int) fieldNumbers.get("bar"));
    assertEquals(3, (int) fieldNumbers.get("baz"));
    assertEquals(5, (int) fieldNumbers.get("quux"));
  }
}
