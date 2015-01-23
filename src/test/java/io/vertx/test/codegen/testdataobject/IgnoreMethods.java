package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface IgnoreMethods {

  public static IgnoreMethods dataObject() {
    throw new UnsupportedOperationException();
  }

  public static IgnoreMethods dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  Locale ignoredMethod();

  boolean getWithArgument(String s);

  boolean isWithArgument(String s);

  void setWithZeroArgument();

  void setWithTwoArguments(String s1, String s2);

  void setWithIgnoredArgument(Locale locale);

}
