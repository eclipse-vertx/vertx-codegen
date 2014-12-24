package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://www.campudus.com">Joern Bernhardt</a>
 */
@Options
public interface OptionWithGetterAndSetter {

  OptionWithGetterAndSetter setSomeValue(String value);

  String getSomeValue();

}
