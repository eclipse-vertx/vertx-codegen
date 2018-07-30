package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class PropertySetters {

  public PropertySetters(JsonObject json) {
    throw new UnsupportedOperationException();
  }

  public static PropertySetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static PropertySetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  public PropertySetters setString(String s) { throw new UnsupportedOperationException(); }
  public PropertySetters setBoxedInteger(Integer i) { throw new UnsupportedOperationException(); }
  public PropertySetters setPrimitiveInteger(int i) { throw new UnsupportedOperationException(); }
  public PropertySetters setBoxedBoolean(Boolean b) { throw new UnsupportedOperationException(); }
  public PropertySetters setPrimitiveBoolean(boolean b) { throw new UnsupportedOperationException(); }
  public PropertySetters setBoxedLong(Long b) { throw new UnsupportedOperationException(); }
  public PropertySetters setPrimitiveLong(long b) { throw new UnsupportedOperationException(); }
  public PropertySetters setInstant(Instant i) { throw new UnsupportedOperationException(); }

  public PropertySetters setApiObject(ApiObject s) { throw new UnsupportedOperationException(); }
  public PropertySetters setDataObject(EmptyDataObject nested) { throw new UnsupportedOperationException(); }
  public PropertySetters setToJsonDataObject(ToJsonDataObject nested) { throw new UnsupportedOperationException(); }

  public PropertySetters setJsonObject(JsonObject jsonObject) { throw new UnsupportedOperationException(); }
  public PropertySetters setJsonArray(JsonArray jsonArray) { throw new UnsupportedOperationException(); }

  public PropertySetters setEnumerated(Enumerated enumerated) { throw new UnsupportedOperationException(); }

  private void setPrivateString(String s) { throw new UnsupportedOperationException(); }
  protected void setProtectedString(String s) { throw new UnsupportedOperationException(); }
  void setPackagePrivateString(String s) { throw new UnsupportedOperationException(); }
  public static void setStaticPublicString(String s) { throw new UnsupportedOperationException(); }
  private static void setStaticPrivateString(String s) { throw new UnsupportedOperationException(); }
  static void setStaticPackagePrivateString(String s) { throw new UnsupportedOperationException(); }
}
