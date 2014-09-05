package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface IgnoreMethods {

  public static IgnoreMethods options() {
    throw new UnsupportedOperationException();
  }

  public static IgnoreMethods optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  Locale ignoredMethod();

  boolean getWithArgument(String s);

  boolean isWithArgument(String s);

  void setWithZeroArgument();

  void setWithTwoArguments(String s1, String s2);

  void setWithIgnoredArgument(Locale locale);

}
