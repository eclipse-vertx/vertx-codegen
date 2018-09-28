package io.vertx.codegen;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class LowerCamelCase extends Case {

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
  public List<String> parse(final String name) {
    String[] atoms = name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
    if (atoms.length == 1 && atoms[0].isEmpty()) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(atoms);
    }
  }

}
