package io.vertx.test.codegen.format;

import io.vertx.codegen.format.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * Chain everything with foo
 */
public class FooCase extends Case {

  @Override
  public String format(Iterable<String> atoms) {
    List<String> names = new ArrayList<>();
    for (String s : atoms) {
      names.add(s);
    }
    return String.join("foo", names);
  }
}
