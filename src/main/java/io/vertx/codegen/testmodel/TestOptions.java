package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Options
public interface TestOptions {

  static TestOptions options() {
    return new TestOptionsImpl();
  }

  static TestOptions optionsFromJson(JsonObject json) {
    return new TestOptionsImpl(json);
  }

  String getFoo();

  void setFoo(String foo);

  int getBar();

  void setBar(int bar);

  double getWibble();

  void setWibble(double wibble);
}
