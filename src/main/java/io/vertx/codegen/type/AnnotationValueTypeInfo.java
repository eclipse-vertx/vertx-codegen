package io.vertx.codegen.type;

import java.util.List;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public class AnnotationValueTypeInfo {

  private String name;
  private Object value;

  public AnnotationValueTypeInfo(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getStringValue() {
    return (String) value;
  }

  public Integer getIntegerValue() {
    try {
      return (Integer) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Short getShortValue() {
    try {
      return (Short) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Double getDoubleValue() {
    try {
      return (Double) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Float getFloatValue() {
    try {
      return (Float) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Character getCharValue() {
    try {
      return (Character) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Long getLongValue() {
    try {
      return (Long) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Boolean getBooleanValue() {
    try {
      return (Boolean) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Byte getByteValue() {
    try {
      return (Byte) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public String[] getStringArrayValue() {
    try {
      return ((List<String>) value).toArray(new String[((List<String>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Integer[] getIntegerArrayValue() {
    try {
      return ((List<Integer>) value).toArray(new Integer[((List<Integer>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Short[] getShortArrayValue() {
    try {
      return ((List<Short>) value).toArray(new Short[((List<Short>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Double[] getDoubleArrayValue() {
    try {
      return ((List<Double>) value).toArray(new Double[((List<Double>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Float[] getFloatArrayValue() {
    try {
      return ((List<Float>) value).toArray(new Float[((List<Float>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Character[] getCharArrayValue() {
    try {
      return ((List<Character>) value).toArray(new Character[((List<Character>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Long[] getLongArrayValue() {
    try {
      return ((List<Long>) value).toArray(new Long[((List<Long>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Boolean[] getBooleanArrayValue() {
    try {
      return ((List<Boolean>) value).toArray(new Boolean[((List<Boolean>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public Byte[] getByteArrayValue() {
    try {
      return ((List<Byte>) value).toArray(new Byte[((List<Byte>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public ClassTypeInfo getClassValue() {
    try {
      return (ClassTypeInfo) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public ClassTypeInfo[] getClassArrayValue() {
    try {
      ((List<ClassTypeInfo>) value).forEach(v -> System.out.println(v.getClass().getName()));
      return ((List<ClassTypeInfo>) value).toArray(new ClassTypeInfo[((List<ClassTypeInfo>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public String getEnumValue() {
    return getStringValue();
  }

  public String[] getEnumArrayValue() {
    return getStringArrayValue();
  }

  public AnnotationTypeInfo getAnnotationValue() {
    try {
      return (AnnotationTypeInfo) value;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public AnnotationTypeInfo[] getAnnotationArrayValue() {
    try {
      return ((List<AnnotationTypeInfo>) value).toArray(new AnnotationTypeInfo[((List<AnnotationTypeInfo>) value).size()]);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Object getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AnnotationValueTypeInfo that = (AnnotationValueTypeInfo) o;

    if (!name.equals(that.name)) return false;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
