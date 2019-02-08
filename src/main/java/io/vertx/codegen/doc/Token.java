package io.vertx.codegen.doc;

import io.vertx.codegen.Helper;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.type.TypeMirrorFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A comment token.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class Token {

  private static final Pattern TOKEN_SPLITTER = Pattern.compile("(\\{@\\p{Alpha}[^\\}]*\\})|(\\r?\\n)");
  private static final Pattern INLINE_TAG_PATTERN = Pattern.compile("\\{@([^\\p{javaWhitespace}]+)((?:.|\\n)*)\\}");

  /**
   * Tokenize the string.
   *
   * @param s the string to tokenize
   * @return the tokens after analysis
   */
  public static List<Token> tokenize(String s) {
    ArrayList<Token> events = new ArrayList<>();
    parse(s, 0, TOKEN_SPLITTER.matcher(s), events);
    return events;
  }

  private static void parse(String s, int prev, Matcher matcher, ArrayList<Token> events) {
    if (matcher.find()) {
      if (matcher.start() > prev) {
        events.add(new Token.Text(s.substring(prev, matcher.start())));
      }
      String value = s.substring(matcher.start(), matcher.end());
      if (matcher.group(1) != null) {
        Matcher tagMatcher = INLINE_TAG_PATTERN.matcher(value);
        if (!tagMatcher.matches()) {
          // If we are here, it means the INLINE_TAG_PATTERN matches less then TOKEN_SPLITTER and this is a bug.
          // so we should know at least the value that raised it
          throw new AssertionError("bug -->" + value + "<--");
        }
        events.add(new Token.InlineTag(value, new Tag(tagMatcher.group(1), tagMatcher.group(2))));
      } else {
        events.add(new Token.LineBreak(value));
      }
      parse(s, matcher.end(), matcher, events);
    } else {
      if (prev < s.length()) {
        events.add(new Token.Text(s.substring(prev, s.length())));
      }
    }
  }

  final String value;

  public Token(String value) {
    this.value = value;
  }

  /**
   * @return true if the token is text
   */
  public boolean isText() {
    return false;
  }

  /**
   * @return true if the token is an inline tag
   */
  public boolean isInlineTag() {
    return false;
  }

  /**
   * @return true if the token is line break
   */
  public boolean isLineBreak() {
    return false;
  }

  /**
   * @return the token text
   */
  public String getValue() {
    return value;
  }

  public static class Text extends Token {
    public Text(String value) {
      super(value);
    }

    @Override
    public boolean isText() {
      return true;
    }
  }

  public static class LineBreak extends Token {
    public LineBreak(String value) {
      super(value);
    }

    @Override
    public boolean isLineBreak() {
      return true;
    }
  }

  public static class InlineTag extends Token {

    private final Tag tag;

    public InlineTag(String value, Tag tag) {
      super(value);
      this.tag = tag;
    }

    @Override
    public boolean isInlineTag() {
      return true;
    }

    /**
     * @return the parsed tag
     */
    public Tag getTag() {
      return tag;
    }
  }

  // Slight modification to accomodate left whitespace trimming
  private static final Pattern LINK_REFERENCE_PATTERN = Pattern.compile(
      "^\\s*(" +
          Helper.LINK_REFERENCE_PATTERN.pattern() +
          ")");

  /**
   * Create a tag mapper that remaps tags with extra contexutal info like @link tags.
   *
   * @param elementUtils the element utils
   * @param typeUtils the type utils
   * @param ownerElt the type element in which this tag is declared
   * @return the mapper
   */
  public static Function<Token, Token> tagMapper(
      Elements elementUtils, Types typeUtils, TypeElement ownerElt) {
    TypeMirrorFactory typeFactory = new TypeMirrorFactory(elementUtils, typeUtils, elementUtils.getPackageOf(ownerElt));
    return token -> {
      if (token.isInlineTag()) {
        Tag tag = ((Token.InlineTag) token).getTag();
        if (tag.getName().equals("link")) {
          Matcher matcher = LINK_REFERENCE_PATTERN.matcher(tag.getValue());
          if (matcher.find()) {
            Element resolvedElt = Helper.resolveSignature(
                elementUtils,
                typeUtils,
                ownerElt,
                matcher.group(1));
            if (resolvedElt != null) {
              TypeElement resolvedTypeElt = Helper.getElementTypeOf(resolvedElt);
              if (resolvedTypeElt != null) {
                DeclaredType resolvedType = (DeclaredType) resolvedTypeElt.asType();
                Tag.Link tagLink = new Tag.Link(
                    tag.getValue(),
                    resolvedElt,
                    typeFactory.create(resolvedType),
                    tag.getValue().substring(matcher.end()));
                token = new Token.InlineTag(token.getValue(), tagLink);
              }
            }
          }
        }
      }
      return token;
    };
  }

  /**
   * Render a list of tokens to HTML markup and return it.
   *
   * @param tokens the tokens to render
   * @param margin the left margin
   * @param linkToHtml the function that renders a tag link to HTML markup
   * @param sep the separator
   * @return the rendered HTML
   */
  public static String toHtml(List<Token> tokens, String margin,
                       Function<Tag.Link, String> linkToHtml,
                       String sep) {
    StringWriter sw = new StringWriter();
    toHtml(tokens, margin, linkToHtml, sep, new PrintWriter(sw));
    return sw.toString();
  }

  /**
   * Render a list of tokens to HTML markup.
   *
   * @param tokens the tokens to render
   * @param margin the left margin
   * @param linkToHtml the function that renders a tag link to HTML markup
   * @param sep the separator
   * @param writer the writer for output
   */
  public static void toHtml(List<Token> tokens,
                            String margin,
                     Function<Tag.Link, String> linkToHtml,
                     String sep,
                     PrintWriter writer) {
    boolean need = true;
    for (Token token : tokens) {
      if (need) {
        writer.append(margin);
        need = false;
      }
      if (token.isLineBreak()) {
        writer.append(sep);
        need = true;
      } else if (token.isText()) {
        writer.append(token.getValue());
      } else {
        Tag tag = ((Token.InlineTag) token).getTag();
        if (tag instanceof Tag.Link) {
          Tag.Link tagLink = (Tag.Link) tag;
          String link = linkToHtml.apply((Tag.Link) tag);
          if (link == null || link.trim().isEmpty()) {
            link = tagLink.getLabel();
          }
          if (link == null || link.trim().isEmpty()) {
            link = tagLink.targetElement.getSimpleName().toString();
          }
          writer.append(link);
        } else if (tag.getName().equals("code")) {
          writer.append("<code>").append(tag.value.trim()).append("</code>");
        }
      }
    }
    if (!need) {
      writer.append(sep);
    }
  }
}
