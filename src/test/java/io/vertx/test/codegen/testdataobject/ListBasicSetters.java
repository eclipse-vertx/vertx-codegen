package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface ListBasicSetters {

  public static ListBasicSetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static ListBasicSetters dataObjectFromJson(JsonObject obj) {
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
