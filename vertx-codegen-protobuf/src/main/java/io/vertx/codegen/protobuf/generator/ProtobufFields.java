package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.ProtobufField;
import io.vertx.codegen.type.AnnotationValueInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ProtobufFields {
  private static final String PROTOBUF_FIELD_ANNOTATION_NAME = ProtobufField.class.getName();

  static void verifyFieldNames(Collection<PropertyInfo> props, Set<String> reservedFieldNames) {
    for (PropertyInfo prop : props) {
      if (reservedFieldNames.contains(prop.getName())) {
        throw new IllegalArgumentException("Field name '" + prop.getName() + "' is reserved");
      }
    }
  }

  static Map<String, Integer> fieldNumbers(Collection<PropertyInfo> props, FieldNumberStrategy fieldNumberStrategy,
      Set<Integer> reservedFieldNumbers) {
    switch (fieldNumberStrategy) {
      case MANUAL:
        return manualFieldNumbers(props, reservedFieldNumbers);
      case COMPACT:
        return compactFieldNumbers(props, reservedFieldNumbers);
      case SEGMENTED:
        return segmentedFieldNumbers(props, reservedFieldNumbers);
      default:
        throw new IllegalArgumentException("Unknown field number strategy: " + fieldNumberStrategy);
    }
  }

  private static Map<String, Integer> manualFieldNumbers(Collection<PropertyInfo> props, Set<Integer> reservedFieldNumbers) {
    Map<String, Integer> result = new HashMap<>();
    Map<Integer, String> alreadyUsed = new HashMap<>();
    for (PropertyInfo prop : props) {
      AnnotationValueInfo ann = prop.getAnnotation(PROTOBUF_FIELD_ANNOTATION_NAME);
      if (ann != null) {
        Integer fieldNumber = (Integer) ann.getMember("value");
        if (reservedFieldNumbers.contains(fieldNumber)) {
          throw new IllegalArgumentException("Property '" + prop.getName()
            + "' has a reserved field number " + fieldNumber);
        }
        if (alreadyUsed.containsKey(fieldNumber)) {
          String collidingProperty = alreadyUsed.get(fieldNumber);
          throw new IllegalArgumentException("Property '" + prop.getName() + "' is assigned field number "
            + fieldNumber + ", which collides with property '" + collidingProperty + "'");
        }
        result.put(prop.getName(), fieldNumber);
        alreadyUsed.put(fieldNumber, prop.getName());
      } else {
        throw new IllegalArgumentException("Property '" + prop.getName()
          + "' does not declare a field number; use @ProtobufField");
      }
    }
    return result;
  }

  private static Map<String, Integer> compactFieldNumbers(Collection<PropertyInfo> props, Set<Integer> reservedFieldNumbers) {
    Map<String, Integer> result = new HashMap<>();
    Map<Integer, String> alreadyUsed = new HashMap<>();
    // 1. find pre-allocated field numbers
    for (PropertyInfo prop : props) {
      AnnotationValueInfo ann = prop.getAnnotation(PROTOBUF_FIELD_ANNOTATION_NAME);
      if (ann != null) {
        Integer fieldNumber = (Integer) ann.getMember("value");
        if (reservedFieldNumbers.contains(fieldNumber)) {
          throw new IllegalArgumentException("Property '" + prop.getName()
            + "' has a reserved field number " + fieldNumber);
        }
        if (alreadyUsed.containsKey(fieldNumber)) {
          String collidingProperty = alreadyUsed.get(fieldNumber);
          throw new IllegalArgumentException("Property '" + prop.getName() + "' is assigned field number "
            + fieldNumber + ", which collides with property '" + collidingProperty + "'");
        }
        result.put(prop.getName(), fieldNumber);
        alreadyUsed.put(fieldNumber, prop.getName());
      }
    }
    // 2. allocate field numbers to properties that don't have one pre-allocated
    int lastFieldNumber = 1;
    for (PropertyInfo prop : props) {
      if (result.containsKey(prop.getName())) {
        continue;
      }
      while (alreadyUsed.containsKey(lastFieldNumber) || reservedFieldNumbers.contains(lastFieldNumber)) {
        lastFieldNumber++;
      }
      result.put(prop.getName(), lastFieldNumber++);
    }
    return result;
  }

  private static Map<String, Integer> segmentedFieldNumbers(Collection<PropertyInfo> props, Set<Integer> reservedFieldNumbers) {
    Map<String, Integer> result = new HashMap<>();
    Map<Integer, String> alreadyUsed = new HashMap<>();
    int lastFieldNumber = 0;
    for (PropertyInfo prop : props) {
      AnnotationValueInfo ann = prop.getAnnotation(PROTOBUF_FIELD_ANNOTATION_NAME);
      if (ann != null) {
        lastFieldNumber = (Integer) ann.getMember("value");
        if (reservedFieldNumbers.contains(lastFieldNumber)) {
          throw new IllegalArgumentException("Property '" + prop.getName()
            + "' has a reserved field number " + lastFieldNumber);
        }
      } else {
        lastFieldNumber++;
        while (reservedFieldNumbers.contains(lastFieldNumber)) {
          lastFieldNumber++;
        }
      }
      if (alreadyUsed.containsKey(lastFieldNumber)) {
        String collidingProperty = alreadyUsed.get(lastFieldNumber);
        throw new IllegalArgumentException("Property '" + prop.getName() + "' is assigned field number "
          + lastFieldNumber + ", which collides with property '" + collidingProperty + "'");
      }
      result.put(prop.getName(), lastFieldNumber);
      alreadyUsed.put(lastFieldNumber, prop.getName());
    }
    return result;
  }

  /**
   * Returns a list with the same elements as the given collection of properties, except
   * the list is ordered so that properties with lower protobuf field number precede
   * properties with higher protobuf field number.
   *
   * @param props a collection of {@link PropertyInfo}s
   * @param fieldNumbers field numbers for all properties
   * @return a list of the same {@link PropertyInfo}s, in field number order
   */
  static List<PropertyInfo> inFieldNumberOrder(Collection<PropertyInfo> props, Map<String, Integer> fieldNumbers) {
    List<PropertyInfo> result = new ArrayList<>(props);
    result.sort((a, b) -> {
      Integer ai = fieldNumbers.get(a.getName());
      Integer bi = fieldNumbers.get(b.getName());
      // there should be no `null` values at the moment
      return Integer.compare(ai != null ? ai : 0, bi != null ? bi : 0);
    });
    return result;
  }
}
