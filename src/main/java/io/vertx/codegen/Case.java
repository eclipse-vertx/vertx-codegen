package io.vertx.codegen;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 * @author Richard Gomez
 */
public enum Case {
  /**
   * Camel case starting with an upper case, for instance {@literal FooBar}.
   */
  UPPER_CAMEL() {
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

  /**
   * Camel case starting with a lower case, for instance {@literal fooBar}.
   */
  LOWER_CAMEL() {
    @Override
    public String format(Iterable<String> atoms) {
      StringBuilder sb = new StringBuilder();
      Iterator<String> it = atoms.iterator();

      // Ensure the first atom is lower case (e.g. ['Foo', 'Bar'] -> ['foo', 'Bar'])
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
  },

  /**
   * Kebab case, for instance {@literal foo-bar}.
   */
  KEBAB() {
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})-(?=\\p{Alnum})))*");

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
  },

  /**
   * Snake case, for instance {@literal foo_bar}.
   */
  SNAKE() {
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})_(?=\\p{Alnum})))*");

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

    @Override
    public List<String> parse(String name) {
      if (!validator.matcher(name).matches()) {
        throw new IllegalArgumentException("Invalid snake case:" + name);
      }
      return split(name, "_");
    }
  },

  /**
   * Java full qualified case, for instance {@literal foo.bar}
   */
  QUALIFIED() {
    private final Pattern validator = Pattern.compile("(?:\\p{Alnum}|(?:(?<=\\p{Alnum})\\.(?=\\p{Alnum})))*");

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
  };

  /**
   * Format the {@code atoms} into the specified case.
   *
   * @param atoms the name atoms
   * @return the name converted to the desired case
   */
  public abstract String format(Iterable<String> atoms);

  /**
   * Parse the {@code name} argument and returns a list of the name atoms.
   *
   * @param name the name to parse
   * @return the name atoms converted
   * @throws IllegalArgumentException if the name has a syntax error
   */
  public abstract List<String> parse(String name);

  /**
   * Convert a name from this case to the {@literal dest} case
   *
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
   * Useful for formatting or parsing strings,
   * e.g. UPPER_CAMEL.format(CASE_SNAKE.parse("foo_bar")),it will return fooBar
   */
  public static Map<String, Case> vars() {
    HashMap<String, Case> vars = new HashMap<>();
    for (Case _case : Arrays.asList(UPPER_CAMEL, LOWER_CAMEL, KEBAB, SNAKE, QUALIFIED)) {
      vars.put("CASE_" + _case.name(), _case);
    }
    return vars;
  }
}
