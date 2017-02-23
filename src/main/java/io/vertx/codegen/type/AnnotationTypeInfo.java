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
  private Map<String, AnnotationValueTypeInfo> values;

  public AnnotationTypeInfo(String name) {
    this(name, new ArrayList<>());
  }

  public AnnotationTypeInfo(String name, List<AnnotationValueTypeInfo> values) {
    this.name = name;
    this.simpleName = Helper.getSimpleName(name);
    this.packageName = Helper.getPackageName(name);
    this.values = values != null ? values.stream().collect(HashMap::new, (m, a) -> m.put(a.getName(), a), HashMap::putAll) : new HashMap<>();
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  public void addMember(AnnotationValueTypeInfo member) {
    values.put(member.getName(), member);
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

  public List<AnnotationValueTypeInfo> getMembers() {
    return new ArrayList<>(values.values());
  }

  public AnnotationValueTypeInfo getMember(String name) {
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
