package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.type.AnnotationValueInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The value (member) of an enumeration model.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumValueInfo {

  private final String identifier;
  private final Doc doc;
  private final boolean deprecated;
  private final Text deprecatedDesc;
  private final Map<String, AnnotationValueInfo> annotations;

  public EnumValueInfo(String identifier, Doc doc, boolean deprecated, Text deprecatedDesc, List<AnnotationValueInfo> annotations) {
    this.identifier = identifier;
    this.doc = doc;
    this.deprecated = deprecated;
    this.deprecatedDesc = deprecatedDesc;
    this.annotations = annotations.stream().collect(HashMap::new, (m, a) -> m.put(a.getName(), a), HashMap::putAll);
  }

  /**
   * @return the value identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * @return the optional documentation
   */
  public Doc getDoc() {
    return doc;
  }
  /**
   * @return {@code true} if the enum value has a {@code @Deprecated} annotation
   */
  public boolean isDeprecated() {
    return deprecated;
  }

  /**
   * @return the description of deprecated
   */
  public Text getDeprecatedDesc() {
    return deprecatedDesc;
  }

  /**
   * @return the list of {@link AnnotationValueInfo} for this enum value
   */
  public List<AnnotationValueInfo> getAnnotations() {
    return new ArrayList<>(annotations.values());
  }

  /**
   * @param annotationName fully qualified name of an annotation type
   * @return {@link AnnotationValueInfo} for an annotation of given type present on this enum value,
   * or {@code null} if an annotation of given type is not present on this enum value
   */
  public AnnotationValueInfo getAnnotation(String annotationName) {
    return annotations.get(annotationName);
  }
}
