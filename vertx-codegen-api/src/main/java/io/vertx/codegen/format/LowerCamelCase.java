package io.vertx.codegen.format;

import java.util.Iterator;
import java.util.List;

/**
 * Camel case starting with a lower case, for instance {@literal fooBar}.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class LowerCamelCase extends Case {

  /**
   * A {@code LowerCamelCase} instance.
   */
  public static final Case INSTANCE = new LowerCamelCase();

  @Override
  public String name() {
    return "LOWER_CAMEL";
  }

  @Override
  public String format(Iterable<String> atoms) {
    StringBuilder sb = new StringBuilder();
    Iterator<String> it = atoms.iterator();
    while (it.hasNext()) {
      String atom = it.next();
      if (atom.length() > 0) {
        sb.append(atom.toLowerCase());
        break;
      }
    }
    while (it.hasNext()) {
      String atom = it.next();
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
    return CamelCase.INSTANCE.parse(name);
  }
}
