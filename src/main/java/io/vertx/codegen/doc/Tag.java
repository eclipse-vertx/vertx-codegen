package io.vertx.codegen.doc;

import io.vertx.codegen.Helper;
import io.vertx.codegen.TypeInfo;

import javax.lang.model.element.Element;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A doc tag.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Tag {

  final String name;
  final String value;

  public Tag(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * @return the tag name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the tag value
   */
  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Tag) {
      Tag that = (Tag) o;
      return name.equals(that.name) && value.equals(that.value);
    }
    return false;
  }

  @Override
  public String toString() {
    return "@" + name + " " + value;
  }

  public static class Param extends Tag {

    private static final Pattern PARAM_PATTERN = Pattern.compile("^\\s*(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)(?:\\s((?:\\n|.)*))?");

    final String paramName;
    final String paramDescription;

    public Param(Tag other) {
      this(other.name, other.value);
    }

    public Param(String name, String value) {
      super(name, value);

      //
      Matcher matcher = PARAM_PATTERN.matcher(value);
      if (matcher.matches()) {
        paramName = matcher.group(1);
        String desc = matcher.group(2);
        if (desc != null) {
          desc = Helper.normalizeWhitespaces(desc);
        }
        paramDescription = desc != null && !desc.trim().isEmpty() ? desc.trim() : null;
      } else {
        paramName = null;
        paramDescription = null;
      }
    }

    public String getParamName() {
      return paramName;
    }

    public String getParamDescription() {
      return paramDescription;
    }
  }

  /**
   * A link tag providing information of element target of the link.
   */
  public static class Link extends Tag {

    final Element targetElement;
    final TypeInfo targetType;
    final String label;

    public Link(String rawValue, Element targetElement, TypeInfo type, String label) {
      super("link", rawValue);
      this.targetElement = targetElement;
      this.targetType = type;
      this.label = label;
    }

    /**
     * @return the target element like a type, a method, a constructor or a field
     */
    public Element getTargetElement() {
      return targetElement;
    }

    /**
     * @return the target element owner type, either the type if the target element is a type, otherwise
     *         the type declaring the element as its member
     */
    public TypeInfo getTargetType() {
      return targetType;
    }

    /**
     * @return the target label, it may be an empty string
     */
    public String getLabel() {
      return label;
    }
  }
}
