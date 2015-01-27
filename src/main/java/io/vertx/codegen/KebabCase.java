package io.vertx.codegen;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class KebabCase extends Case {

  private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})-(?=\\p{Alnum})))*");

  @Override
  public String name() {
    return "KEBAB";
  }

  @Override
  public String format(Iterable<String> atoms) {
    StringBuilder sb = new StringBuilder();
    for (String atom : atoms) {
      if (atom.length() > 0) {
        if (sb.length() > 0) {
          sb.append('-');
        }
        sb.append(atom.toLowerCase());
      }
    }
    return sb.toString();
  }

  @Override
  public List<String> parse(String name) {
    if (!validator.matcher(name).matches()) {
      throw new IllegalArgumentException("Invalid kebab case:" + name);
    }
    return split(name, "\\-");
  }
}
