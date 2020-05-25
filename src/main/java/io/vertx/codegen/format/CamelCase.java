package io.vertx.codegen.format;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Camel case, for instance {@literal FooBar}.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CamelCase extends Case {

  /**
   * A {@code CamelCase} instance.
   */
  public static final Case INSTANCE = new CamelCase();

  @Override
  public String name() {
    return "CAMEL";
  }

  @Override
  public String format(Iterable<String> atoms) {
    StringBuilder sb = new StringBuilder();
    for (String atom : atoms) {
      if (atom.length() > 0) {
        char c = atom.charAt(0);
        if (Character.isLowerCase(c)) {
          sb.append(Character.toUpperCase(c));
          sb.append(atom, 1, atom.length());
        } else {
          sb.append(atom);
        }
      }
    }
    return sb.toString();
  }

  @Override
  public List<String> parse(String name) {
    String[] atoms = name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
    if (atoms.length == 1 && atoms[0].isEmpty()) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(atoms);
    }
  }
}
