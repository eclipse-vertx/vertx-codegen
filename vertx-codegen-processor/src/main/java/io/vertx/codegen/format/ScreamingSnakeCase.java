package io.vertx.codegen.format;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Snake case, for instance {@literal foo_bar}.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ScreamingSnakeCase extends Case {

  /** A {@code SnakeCase} instance. */
  public static final Case INSTANCE = new ScreamingSnakeCase();

  private final Pattern validator =
      Pattern.compile("(?:[\\p{Upper}\\d]|(?:(?<=[\\p{Upper}\\d])_(?=[\\p{Upper}\\d])))*");

  @Override
  public String name() {
    return "SCREAMING_SNAKE";
  }

  @Override
  public String format(Iterable<String> atoms) {
    StringBuilder sb = new StringBuilder();
    for (String atom : atoms) {
      if (atom.length() > 0) {
        if (sb.length() > 0) {
          sb.append('_');
        }
        sb.append(atom.toUpperCase());
      }
    }
    return sb.toString();
  }

  @Override
  public List<String> parse(String name) {
    if (!validator.matcher(name).matches()) {
      throw new IllegalArgumentException("Invalid screaming snake case:" + name);
    }
    return split(name, "_");
  }
}
