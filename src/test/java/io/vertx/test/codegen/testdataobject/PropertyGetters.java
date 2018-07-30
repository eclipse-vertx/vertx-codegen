package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.time.Instant;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class PropertyGetters {

  static PropertyGetters dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyGetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  public PropertyGetters(JsonObject json) {
    throw new UnsupportedOperationException();
  }

  public String getString() { throw new UnsupportedOperationException(); }
  public Integer getBoxedInteger() { throw new UnsupportedOperationException(); }
  public int getPrimitiveInteger() { throw new UnsupportedOperationException(); }
  public Boolean isBoxedBoolean() { throw new UnsupportedOperationException(); }
  public boolean isPrimitiveBoolean() { throw new UnsupportedOperationException(); }
  public Long getBoxedLong() { throw new UnsupportedOperationException(); }
  public long getPrimitiveLong() { throw new UnsupportedOperationException(); }
  public Instant getInstant() { throw new UnsupportedOperationException(); }

  public ApiObject getApiObject() { throw new UnsupportedOperationException(); }
  public EmptyDataObject getDataObject() { throw new UnsupportedOperationException(); }
  public ToJsonDataObject getToJsonDataObject() { throw new UnsupportedOperationException(); }

  public JsonObject getJsonObject() { throw new UnsupportedOperationException(); }
  public JsonArray getJsonArray() { throw new UnsupportedOperationException(); }

  public Enumerated getEnumerated() { throw new UnsupportedOperationException(); }

  private String getPrivateString() { throw new UnsupportedOperationException(); }
  protected String getProtectedString() { throw new UnsupportedOperationException(); }
  String getPackagePrivateString() { throw new UnsupportedOperationException(); }
  public static String getStaticPublicString() { throw new UnsupportedOperationException(); }
  private static String getStaticPrivateString() { throw new UnsupportedOperationException(); }
  static String getStaticPackagePrivateString() { throw new UnsupportedOperationException(); }
}
