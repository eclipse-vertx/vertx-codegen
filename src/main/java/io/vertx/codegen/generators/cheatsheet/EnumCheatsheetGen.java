package io.vertx.codegen.generators.cheatsheet;

import io.vertx.codegen.Generator;
import io.vertx.codegen.EnumModel;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Token;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

/**
 * A generator for Java enums that creates cheatsheets doc.
 */
public class EnumCheatsheetGen extends Generator<EnumModel> {

  public EnumCheatsheetGen() {
    name = "cheatsheet";
    kinds = Collections.singleton("enum");
    incremental = true;
  }

  @Override
  public String filename(EnumModel model) {
    return "asciidoc/enums.adoc";
  }

  @Override
  public String render(EnumModel model, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    PrintWriter html = new PrintWriter(buffer);
    if (index == 0) {
      html.append("= Enums\n");
      html.append("\n");
    }
    render(model, html);
    html.append("\n");
    return buffer.toString();
  }

  private void render(EnumModel model, PrintWriter html) {
    html.append("[[").append(model.getType().getSimpleName()).append("]]\n");
    html.append("== ").append(model.getType().getSimpleName()).append("\n");
    html.append("\n");
    Doc doc = model.getDoc();
    if (doc != null) {
      html.append("++++\n");
      Token.toHtml(doc.getTokens(), "", Tag::getName, "\n", html);
      html.append("++++\n");
      html.append("'''\n");
    }
    html.append("\n");
    html.append("[cols=\">25%,75%\"]\n");
    html.append("[frame=\"topbot\"]\n");
    html.append("|===\n");
    html.append("^|Name | Description\n");
    model.getValues().forEach(value -> {
      html.append("|[[").append(value.getIdentifier()).append("]]`").append(value.getIdentifier()).append("`");
      html.append("|");
      if (value.getDoc() != null) {
        html.append("+++\n");
        html.append(Token.toHtml(value.getDoc().getTokens(), "", Tag::getName, "\n").trim()).append("\n");
        html.append("+++\n");
      } else {
        html.append("-\n");
      }
    });
    html.append("|===\n");
  }
}
