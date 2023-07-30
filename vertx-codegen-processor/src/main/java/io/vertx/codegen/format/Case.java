package io.vertx.codegen.format;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class Case {

  public String name() {
    throw new UnsupportedOperationException();
  }

  public String format(Iterable<String> atoms) {
    throw new UnsupportedOperationException();
  }

  /**
   * Parse the {@code name} argument and returns a list of the name atoms.
   *
   * @param name the name to parse
   * @return the name atoms
   * @throws IllegalArgumentException if the name has a syntax error
   */
  public List<String> parse(String name) {
    throw new UnsupportedOperationException();
  }

  /**
   * Convert a name from this case to the {@literal dest} case
   * @param dest the destination case
   * @param name the name to convert
   * @return the converted name
   */
  public String to(Case dest, String name) {
    return dest.format(parse(name));
  }

  protected static List<String> split(String s, String regex) {
    String[] atoms = s.split(regex);
    if (atoms.length == 1 && atoms[0].isEmpty()) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(atoms);
    }
  }

  /**
   * Useful for formatting or parsing string, eg:CASE_CAMEL.format(CASE_SNAKE.parse("foo_bar")),it will return fooBar
   */
  public static Map<String, Case> vars() {
    HashMap<String, Case> vars = new HashMap<>();
    for (Case _case : Arrays.asList(CamelCase.INSTANCE, QualifiedCase.INSTANCE, SnakeCase.INSTANCE, KebabCase.INSTANCE, LowerCamelCase.INSTANCE)) {
      vars.put("CASE_" + _case.name(), _case);
    }
    return vars;
  }
}
