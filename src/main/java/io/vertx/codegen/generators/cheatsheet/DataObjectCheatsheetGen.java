package io.vertx.codegen.generators.cheatsheet;

import io.vertx.codegen.Generator;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

public class DataObjectCheatsheetGen extends Generator<DataObjectModel> {

  public DataObjectCheatsheetGen() {
    name = "cheatsheet";
    kinds = Collections.singleton("dataObject");
    incremental = true;
  }

  @Override
  public String filename(DataObjectModel model) {
    return "asciidoc/dataobjects.adoc";
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    PrintWriter html = new PrintWriter(buffer);
    if (index == 0) {
      html.append("= Cheatsheets\n");
      html.append("\n");
    }
    render(model, html);
    html.append("\n");
    return buffer.toString();
  }

  private void render(DataObjectModel model, PrintWriter html) {
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
    html.append("^|Name | Type ^| Description\n");
    model.getPropertyMap().values().forEach(property -> {
      String propertytype = getDataType(property.getType());
      if (propertytype != null) {
        String arrayPrefix = (property.isList() || property.isSet()) ? "Array of " : "";
        html.append("|[[").append(property.getName()).append("]]`@").append(property.getName()).append("`");
        html.append("|`").append(arrayPrefix).append(propertytype).append("`");
        html.append("|");
        if (property.getDoc() != null) {
          html.append("+++\n");
          html.append(Token.toHtml(property.getDoc().getTokens(), "", Tag::getName, "\n").trim()).append("\n");
          html.append("+++\n");
        } else {
          html.append("-\n");
        }
      }
    });
    html.append("|===\n");
  }

  private String getDataType(TypeInfo type) {
    ClassKind kind = type.getKind();
    if (kind.basic) {
      if (kind == ClassKind.STRING) {
        return "String";
      }
      String typeName = type.getName();
      switch (typeName) {
        case "java.lang.Boolean":
        case "boolean":
          return "Boolean";
        case "java.lang.Character":
        case "char":
          return "Char";
        case "java.lang.Byte":
        case "byte":
        case "java.lang.Short":
        case "short":
        case "java.lang.Integer":
        case "int":
        case "java.lang.Long":
        case "long":
        case "java.lang.Float":
        case "float":
        case "java.lang.Double":
        case "double":
          return "Number (" + type.getSimpleName() + ")";
      }
    } else {
      switch (kind) {
        case JSON_OBJECT:
          return "Json object";
        case JSON_ARRAY:
          return "Json array";
        case DATA_OBJECT:
          return "link:dataobjects.html#" + type.getRaw().getSimpleName() + '[' + type.getRaw().getSimpleName() + ']';
        case ENUM:
          return "link:enums.html#" + type.getRaw().getSimpleName() + '[' + type.getRaw().getSimpleName() + ']';
        case API:
          if (type.getName().equals("io.vertx.core.buffer.Buffer")) {
            return "Buffer";
          }
          break;
        case OTHER:
          if (type.getName().equals(Instant.class.getName())) {
            return "Instant";
          }
          break;
      }
    }
    System.out.println("unhandled type " + type);
    return null;
  }
}
