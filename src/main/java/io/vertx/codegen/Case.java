package io.vertx.codegen;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class Case {

  /**
   * Camel case starting with a lower case, for instance {@literal fooBar}.
   */
  public static final Case LOWER_CAMEL = new LowerCamelCase();

  /**
   * Camel case, for instance {@literal FooBar}.
   */
  public static final Case CAMEL = new CamelCase();

  /**
   * Java full qualified case, for instance {@literal foo.bar}
   */
  public static final Case QUALIFIED = new QualifiedCase();

  /**
   * Kebab case, for instance {@literal foo-bar}.
   */
  public static final Case KEBAB = new KebabCase();

  /**
   * Snake case, for instance {@literal foo_bar}.
   */
  public static final Case SNAKE = new SnakeCase();

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
    for (Case _case : Arrays.asList(CAMEL, QUALIFIED, SNAKE, KEBAB, LOWER_CAMEL)) {
      vars.put("CASE_" + _case.name(), _case);
    }
    return vars;
  }
}
