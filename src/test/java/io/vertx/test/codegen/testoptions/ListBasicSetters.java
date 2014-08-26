package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ListBasicSetters {

  public static ListBasicSetters options() {
    throw new UnsupportedOperationException();
  }

  public static ListBasicSetters optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  // Singular
  ListBasicSetters setExtraClassPath(List<String> s);

  // Plural
  ListBasicSetters setStrings(List<String> s);
  ListBasicSetters setBoxedIntegers(List<Integer> i);
  ListBasicSetters setBoxedBooleans(List<Boolean> b);
  ListBasicSetters setBoxedLongs(List<Long> b);

}
