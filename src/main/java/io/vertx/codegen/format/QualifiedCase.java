package io.vertx.codegen.format;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Java full qualified case, for instance {@literal foo.bar}
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class QualifiedCase extends Case {

  private static final Pattern VALIDATOR = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})\\.(?=\\p{Alnum})))*");

  /**
   * A {@code QualifiedCase} instance.
   */
  public static final Case INSTANCE = new QualifiedCase(VALIDATOR);

  private final Pattern validator;

  public QualifiedCase(Pattern validator) {
    this.validator = validator;
  }

  @Override
  public String name() {
    return "QUALIFIED";
  }

  @Override
  public String format(Iterable<String> atoms) {
    StringBuilder sb = new StringBuilder();
    for (String atom : atoms) {
      if (atom.length() > 0) {
        if (sb.length() > 0) {
          sb.append('.');
        }
        sb.append(atom);
      }
    }
    return sb.toString();
  }

  @Override
  public List<String> parse(String name) {
    if (!validator.matcher(name).matches()) {
      throw new IllegalArgumentException("Invalid qualified case:" + name);
    }
    return split(name, "\\.");
  }
}
