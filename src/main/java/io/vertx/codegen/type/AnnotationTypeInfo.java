package io.vertx.codegen.type;

import io.vertx.codegen.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public class AnnotationTypeInfo {

  private String name;
  private String simpleName;
  private String packageName;
  private Map<String, Object> values;

  public AnnotationTypeInfo(String name) {
    this(name, new HashMap<>());
  }

  public AnnotationTypeInfo(String name, Map<String, Object> values) {
    this.name = name;
    this.simpleName = Helper.getSimpleName(name);
    this.packageName = Helper.getPackageName(name);
    this.values = values != null ? values : new HashMap<>();
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  public void putMember(String memberName, Object memberValue) {
    values.put(memberName, memberValue);
  }

  public String getName() {
    return name;
  }

  public String getSimpleName() {
    return simpleName;
  }

  public String getPackageName() {
    return packageName;
  }

  public List<String> getMembersNames() {
    return new ArrayList<>(values.keySet());
  }

  public Object getMember(String name) {
    return values.getOrDefault(name, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AnnotationTypeInfo that = (AnnotationTypeInfo) o;

    if (!name.equals(that.name)) return false;
    return values.equals(that.values);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }
}
