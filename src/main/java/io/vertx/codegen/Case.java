package io.vertx.codegen;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum Case {

  /**
   * Camel case, for instance {@literal FooBar}.
   */
  CAMEL() {
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
    public List<String> parse(String s) {
      String[] atoms = s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
      if (atoms.length == 1 && atoms[0].isEmpty()) {
        return Collections.emptyList();
      } else {
        return Arrays.asList(atoms);
      }
    }
  },

  QUALIFIED() {
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
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})\\.(?=\\p{Alnum})))*");
    @Override
    public List<String> parse(String s) {
      if (!validator.matcher(s).matches()) {
        throw new IllegalArgumentException("Invalid qualified case:" + s);
      }
      return split(s, "\\.");
    }
  },

  /**
   * Kebab case, for instance {@literal foo-bar}.
   */
  KEBAB() {
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
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})-(?=\\p{Alnum})))*");
    @Override
    public List<String> parse(String s) {
      if (!validator.matcher(s).matches()) {
        throw new IllegalArgumentException("Invalid kebab case:" + s);
      }
      return split(s, "\\-");
    }
  },

  /**
   * Snake case, for instance {@literal foo_bar}.
   */
  SNAKE() {
    @Override
    public String format(Iterable<String> atoms) {
      StringBuilder sb = new StringBuilder();
      for (String atom : atoms) {
        if (atom.length() > 0) {
          if (sb.length() > 0) {
            sb.append('_');
          }
          sb.append(atom.toLowerCase());
        }
      }
      return sb.toString();
    }
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})_(?=\\p{Alnum})))*");
    @Override
    public List<String> parse(String s) {
      if (!validator.matcher(s).matches()) {
        throw new IllegalArgumentException("Invalid snake case:" + s);
      }
      return split(s, "_");
    }
  };

  public abstract String format(Iterable<String> atoms);

  public abstract List<String> parse(String s);

  private static List<String> split(String s, String regex) {
    String[] atoms = s.split(regex);
    if (atoms.length == 1 && atoms[0].isEmpty()) {
      return Collections.emptyList();
    } else {
      return Arrays.asList(atoms);
    }
  }

  /**
   * Useful for testing the method kind, allows to do method.kind == METHOD_HANDLER instead of method.kind.name() == "HANDLER"
   */
  public static Map<String, Case> vars() {
    HashMap<String, Case> vars = new HashMap<>();
    for (Case _case : Case.values()) {
      vars.put("CASE_" + _case.name(), _case);
    }
    return vars;
  }
}
