package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.type.AnnotationTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes a property of a {@link io.vertx.codegen.DataObjectModel data object model}.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PropertyInfo {

  final PropertyKind kind;
  final boolean declared;
  final String name;
  final Doc doc;
  final TypeInfo type;
  final String setterMethod;
  final String adderMethod;
  final String getterMethod;
  final boolean jsonifiable;
  final Map<String, AnnotationTypeInfo> annotations;

  public PropertyInfo(boolean declared, String name, Doc doc, TypeInfo type, String setterMethod, String adderMethod, String getterMethod,
                      List<AnnotationTypeInfo> annotations, PropertyKind kind, boolean jsonifiable) {
    this.kind = kind;
    this.declared = declared;
    this.name = name;
    this.doc = doc;
    this.type = type;
    this.annotations = annotations.stream().collect(HashMap::new, (m, a) -> m.put(a.getSimpleName(), a), HashMap::putAll);
    this.adderMethod = adderMethod;
    this.setterMethod = setterMethod;
    this.getterMethod = getterMethod;
    this.jsonifiable = jsonifiable;
  }

  /**
   * @return true if the property is declared by the its data object, that means it does not override the same property
   * from other data object ancestors
   */
  public boolean isDeclared() {
    return declared;
  }

  /**
   * @return the resolved documentation of this property
   */
  public Doc getDoc() {
    return doc;
  }

  /**
   * @return the property kind
   */
  public PropertyKind getKind() {
    return kind;
  }

  /**
   * @return the property name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the property type
   */
  public TypeInfo getType() {
    return type;
  }

  /**
   * @return the name of the Java method that can read the state of this property on the data object.
   */
  public String getGetterMethod() {
    return getterMethod;
  }

  /**
   * @return the name of the Java method that will update the state of this property on the data object, the nature of the method
   * depends on the {@link #isAdder()} and {@link #isList()} values.
   */
  public String getSetterMethod() {
    return setterMethod;
  }

  public String getAdderMethod() {
    return adderMethod;
  }

  /**
   * @return the list of {@link AnnotationTypeInfo} for this property
   */
  public List<AnnotationTypeInfo> getAnnotations() {
    return new ArrayList<>(annotations.values());
  }

  public AnnotationTypeInfo getAnnotation(String annotationName) {
    return annotations.get(annotationName);
  }

  /**
   * @return true if the property is managed as a single value
   */
  public boolean isValue() {
    return kind == PropertyKind.VALUE;
  }

  /**
   * @return true if the property is managed by a {@code java.util.List<T>}
   */
  public boolean isList() {
    return kind == PropertyKind.LIST;
  }

  /**
   * @return true if the property is managed by a {@code java.util.Set<T>}
   */
  public boolean isSet() {
    return kind == PropertyKind.SET;
  }

  /**
   * @return true if the property is managed by a {@code java.util.Map<String, T>}
   */
  public boolean isMap() {
    return kind == PropertyKind.MAP;
  }

  /**
   * @return true if the property has a setter method
   */
  public boolean isSetter() {
    return setterMethod != null;
  }

  /**
   * @return true if the property has an adder method
   */
  public boolean isAdder() {
    return adderMethod != null;
  }

  /**
   * @return true if the property is annotated, either on field or method
   */
  public boolean isAnnotated() {
    return !annotations.isEmpty();
  }

  /**
   * @return true if the property type can be converted to a Json type
   */
  public boolean isJsonifiable() {
    return jsonifiable;
  }
}
