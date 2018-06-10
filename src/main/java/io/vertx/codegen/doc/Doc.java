package io.vertx.codegen.doc;

import io.vertx.codegen.type.TypeMirrorFactory;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Documention of a program element, the documentation is a composed of a:
 *
 * <ul>
 *   <li>a first sentence</li>
 *   <li>an optional body</li>
 *   <li>a list of block tags</li>
 * </ul>
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Doc {

  private static final Pattern TAG_START = Pattern.compile("(^|\n)\\p{javaWhitespace}*@([^\\p{javaWhitespace}]+)", Pattern.MULTILINE);
  private static final Pattern BODY_START = Pattern.compile("\n{2,}");

  /**
   * Create and returns a doc object for the specified javadoc argument.
   *
   * @param javadoc the javadoc
   * @return the doc object
   */
  public static Doc create(String javadoc) {
    Matcher matcher = TAG_START.matcher(javadoc);
    List<Tag> blockTags = new ArrayList<>();
    String first;
    if (matcher.find()) {
      first = javadoc.substring(0, matcher.start());
      while (true) {
        String name = matcher.group(2);
        int prev = matcher.end() + 1;
        if (matcher.find()) {
          blockTags.add(new Tag(name, javadoc.substring(prev, matcher.start())));
        } else {
          blockTags.add(new Tag(name, javadoc.substring(prev)));
          break;
        }
      }
    } else {
      first = javadoc;
    }
    matcher = BODY_START.matcher(first);
    String body = null;
    if (matcher.find()) {
      body = first.substring(matcher.end());
      first = first.substring(0, matcher.start());
    }
    return new Doc(first, body, blockTags);
  }

  final Text firstSentence;
  final Text body;
  final List<Tag> blockTags;

  public Doc(Text firstSentence) {
    this(firstSentence, null);
  }

  public Doc(Text firstSentence, Text body) {
    this(firstSentence, body, Collections.emptyList());
  }

  public Doc(Text firstSentence, Text body, List<Tag> blockTags) {
    this.firstSentence = firstSentence;
    this.body = body;
    this.blockTags = blockTags;
  }

  public Doc(String firstSentence) {
    this(firstSentence, null);
  }

  public Doc(String firstSentence, String body) {
    this(firstSentence, body, Collections.emptyList());
  }

  public Doc(String firstSentence, String body, List<Tag> blockTags) {
    this.firstSentence = new Text(firstSentence);
    this.body = body != null ? new Text(body) : null;
    this.blockTags = blockTags;
  }

  /**
   * @return the tokens of this comment
   */
  public List<Token> getTokens() {
    if (body != null) {
      ArrayList<Token> tokens = new ArrayList<>(firstSentence.tokens.size() + 2 + body.tokens.size());
      tokens.addAll(firstSentence.tokens);
      tokens.add(new Token.LineBreak("\n"));
      tokens.add(new Token.LineBreak("\n"));
      tokens.addAll(body.tokens);
      return tokens;
    } else {
      return firstSentence.getTokens();
    }
  }

  public Text getFirstSentence() {
    return firstSentence;
  }

  public Text getBody() {
    return body;
  }

  public List<Tag> getBlockTags() {
    return blockTags;
  }

  public String toHtml(String margin,
                     Function<Tag.Link, String> linkToHtml,
                     String sep) {
    StringWriter sw = new StringWriter();
    toHtml(margin, linkToHtml, sep, new PrintWriter(sw));
    return sw.toString();
  }

  public void toHtml(String margin,
                     Function<Tag.Link, String> linkToHtml,
                     String sep,
                     PrintWriter writer) {
    boolean need = true;
    for (Token token : getTokens()) {
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(firstSentence);
    if (body != null) {
      sb.append("\n\n").append(body);
    }
    for (Tag blockTag : blockTags) {
      sb.append("\n").append(blockTag);
    }
    return sb.toString();
  }

  /**
   * Doc factory.
   */
  public static class Factory {

    private final Messager messager;
    private final Elements elementUtils;
    private final Types typeUtils;
    private final TypeMirrorFactory typeFactory;
    private final TypeElement ownerElt;

    public Factory(Messager messager, Elements elementUtils, Types typeUtils, TypeMirrorFactory typeFactory, TypeElement ownerElt) {
      this.messager = messager;
      this.elementUtils = elementUtils;
      this.typeUtils = typeUtils;
      this.typeFactory = typeFactory;
      this.ownerElt = ownerElt;
    }

    /**
     * When the {@code elt} argument has a comment, this comment is parsed and returned as a {@link Doc}
     * object.
     *
     * @param elt the element
     * @return the comment object
     */
    public Doc createDoc(Element elt) {
      String docComment = elementUtils.getDocComment(elt);
      if (docComment != null) {
        Doc doc = Doc.create(docComment);

        // Rewrite the link token with more contextual type info
        Function<Token, Token> mapper = Token.tagMapper(elementUtils, typeUtils, ownerElt);

        //
        return new Doc(
            doc.getFirstSentence().map(mapper),
            doc.getBody() != null ? doc.getBody().map(mapper) : null,
            doc.getBlockTags()
        );
      } else {
        return null;
      }
    }
  }
}
