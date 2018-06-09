package io.vertx.codegen.generators.helper;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Token;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class CodeGenHelper {
  /**
   * Render a doc comment to html markup.
   *
   * @param margin     left margin
   * @param doc        io.vertx.codegen.doc.Doc object
   * @param linkToHtml a function that takes as input io.vertx.codegen.doc.Tag$Link and returns an html string
   * @return the renderer HTML
   */
  public static String renderDocToHtml(String margin, Doc doc, Function<Tag.Link, String> linkToHtml) {
    return renderTokensToHtml(margin, doc.getTokens(), linkToHtml, "\n");
  }

  /**
   * Render a collection of comment tokens to html markup.
   *
   * @param margin     : left margin
   * @param tokens     : a collection of io.vertx.codegen.doc.Token objects
   * @param linkToHtml : a function that takes as input io.vertx.codegen.doc.Tag$Link and returns an html string
   * @param lineSep    : the line separator
   * @return the renderer HTML
   */
  public static String renderTokensToHtml(String margin, List<Token> tokens, Function<Tag.Link, String> linkToHtml, String lineSep) {
    boolean need = true;
    StringBuilder html = new StringBuilder();
    for (Token token : tokens) {
      if (need) {
        html.append(margin);
        need = false;
      }
      if (token.isLineBreak()) {
        html.append(lineSep);
        need = true;
      } else {
        if (token.isText()) {
          html.append(token.getValue());
        } else {
          Tag tag = ((Token.InlineTag) token).getTag();
          if (tag instanceof Tag.Link) {
            Tag.Link link = (Tag.Link) tag;
            String htmlLink = linkToHtml.apply(link);
            if (htmlLink == null || htmlLink.trim().isEmpty()) {
              htmlLink = link.getLabel();
            }
            if (htmlLink == null || htmlLink.trim().isEmpty()) {
              htmlLink = link.getTargetElement().getSimpleName().toString();
            }
            html.append(htmlLink);
          } else if (Objects.equals(tag.getName(), "code")) {
            html.append("<code>").append(tag.getValue().trim()).append("</code>");
          }
        }
      }
    }
    if (!need) {
      html.append(lineSep);
    }
    return html.toString();
  }
}
