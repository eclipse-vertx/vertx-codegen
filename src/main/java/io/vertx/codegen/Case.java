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
    public List<String> parse(String name) {
      String[] atoms = name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
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
    public List<String> parse(String name) {
      if (!validator.matcher(name).matches()) {
        throw new IllegalArgumentException("Invalid qualified case:" + name);
      }
      return split(name, "\\.");
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
    public List<String> parse(String name) {
      if (!validator.matcher(name).matches()) {
        throw new IllegalArgumentException("Invalid kebab case:" + name);
      }
      return split(name, "\\-");
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
    public List<String> parse(String name) {
      if (!validator.matcher(name).matches()) {
        throw new IllegalArgumentException("Invalid snake case:" + name);
      }
      return split(name, "_");
    }
  };

  public abstract String format(Iterable<String> atoms);

  /**
   * Parse the {@code name} argument and returns a list of the name atoms.
   *
   * @param name the name to parse
   * @return the name atoms
   * @throws IllegalArgumentException if the name has a syntax error
   */
  public abstract List<String> parse(String name);

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
