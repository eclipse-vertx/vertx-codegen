package io.vertx.codegen.generators.dataobjecthelper;

import io.vertx.codegen.Generator;
import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.MapperInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.writer.CodeWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectHelperGen extends Generator<DataObjectModel> {

  public DataObjectHelperGen() {
    kinds = Collections.singleton("dataObject");
    name = "data_object_converters";
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    if (model.isClass() && model.getGenerateConverter()) {
      return model.getFqn() + "Converter.java";
    }
    return null;
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    CodeWriter code = new CodeWriter(writer);
    String visibility= model.isPublicConverter() ? "public" : "";
    String simpleName = model.getType().getSimpleName();
    boolean inheritConverter = model.getInheritConverter();
    boolean genSerialize = model.isSerializable();
    boolean genDeserialize = model.isDeserializable();

    writer.print("package " + model.getType().getPackageName() + ";\n");
    writer.print("\n");
    writer.print("import io.vertx.core.json.JsonObject;\n");
    writer.print("import io.vertx.core.json.JsonArray;\n");
    writer.print("import java.time.Instant;\n");
    writer.print("import java.time.format.DateTimeFormatter;\n");
    writer.print("\n");
    writer.print("/**\n");
    writer.print(" * Converter and mapper for {@link " + model.getType() + "}.\n");
    writer.print(" * NOTE: This class has been automatically generated from the {@link " + model.getType() + "} original class using Vert.x codegen.\n");
    writer.print(" */\n");
    code
      .codeln("public class " + model.getType().getSimpleName() + "Converter {"
      ).newLine();
    if (model.getGenerateConverter()) {
      writer.print("\n");
      genFromJson(visibility, inheritConverter, model, writer);
      writer.print("\n");
      genToJson(visibility, inheritConverter, model, writer);
    }
    writer.print("}\n");
    return buffer.toString();
  }

  private void genToJson(String visibility, boolean inheritConverter, DataObjectModel model, PrintWriter writer) {
    String simpleName = model.getType().getSimpleName();
    writer.print("  " + visibility + " static void toJson(" + simpleName + " obj, JsonObject json) {\n");
    writer.print("    toJson(obj, json.getMap());\n");
    writer.print("  }\n");
    writer.print("\n");
    writer.print("  " + visibility + " static void toJson(" + simpleName + " obj, java.util.Map<String, Object> json) {\n");
    model.getPropertyMap().values().forEach(prop -> {
      if ((prop.isDeclared() || inheritConverter) && prop.getGetterMethod() != null && prop.isJsonifiable()) {
        ClassKind propKind = prop.getType().getKind();
        if (propKind.basic) {
          if (propKind == ClassKind.STRING) {
            genPropToJson("", "", prop, writer);
          } else {
            switch (prop.getType().getSimpleName()) {
              case "char":
              case "Character":
                genPropToJson("Character.toString(", ")", prop, writer);
                break;
              default:
                genPropToJson("", "", prop, writer);
            }
          }
        } else {
          switch (propKind) {
            case API:
              if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
                genPropToJson("java.util.Base64.getEncoder().encodeToString(", ".getBytes())", prop, writer);
              }
              break;
            case ENUM:
              genPropToJson("", ".name()", prop, writer);
              break;
            case JSON_OBJECT:
            case JSON_ARRAY:
            case OBJECT:
              genPropToJson("", "", prop, writer);
              break;
            case DATA_OBJECT:
              DataObjectTypeInfo dataObjectType = ((DataObjectTypeInfo)prop.getType());
              if (dataObjectType.isSerializable()) {
                String m;
                MapperInfo mapperInfo = dataObjectType.getSerializer();
                String match;
                switch (mapperInfo.getKind()) {
                  case SELF:
                    m = "";
                    match = ".toJson()";
                    break;
                  case STATIC_METHOD:
                    m = mapperInfo.getQualifiedName() + "." + String.join(".", mapperInfo.getSelectors()) + "(";
                    match = ")";
                    break;
                  default:
                    throw new UnsupportedOperationException();
                }
                genPropToJson(m, match, prop, writer);
              } else {
                return;
              }
              break;
            case OTHER:
              if (prop.getType().getName().equals(Instant.class.getName())) {
                genPropToJson("DateTimeFormatter.ISO_INSTANT.format(", ")", prop, writer);
              }
              break;
          }
        }
      }
    });

    writer.print("  }\n");
  }

  private void genPropToJson(String before, String after, PropertyInfo prop, PrintWriter writer) {
    String indent = "    ";
    if (prop.isList() || prop.isSet()) {
      writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      writer.print(indent + "  JsonArray array = new JsonArray();\n");
      writer.print(indent + "  obj." + prop.getGetterMethod() + "().forEach(item -> array.add(" + before + "item" + after + "));\n");
      writer.print(indent + "  json.put(\"" + prop.getName() + "\", array);\n");
      writer.print(indent + "}\n");
    } else if (prop.isMap()) {
      writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      writer.print(indent + "  JsonObject map = new JsonObject();\n");
      writer.print(indent + "  obj." + prop.getGetterMethod() + "().forEach((key, value) -> map.put(key, " + before + "value" + after + "));\n");
      writer.print(indent + "  json.put(\"" + prop.getName() + "\", map);\n");
      writer.print(indent + "}\n");
    } else {
      String sp = "";
      if (prop.getType().getKind() != ClassKind.PRIMITIVE) {
        sp = "  ";
        writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      }
      writer.print(indent + sp + "json.put(\"" + prop.getName() + "\", " + before + "obj." + prop.getGetterMethod() + "()" + after + ");\n");
      if (prop.getType().getKind() != ClassKind.PRIMITIVE) {
        writer.print(indent + "}\n");
      }
    }
  }

  private void genFromJson(String visibility, boolean inheritConverter, DataObjectModel model, PrintWriter writer) {
    writer.print("  " + visibility + " static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, " + model.getType().getSimpleName() + " obj) {\n");
    writer.print("    for (java.util.Map.Entry<String, Object> member : json) {\n");
    writer.print("      switch (member.getKey()) {\n");
    model.getPropertyMap().values().forEach(prop -> {
      if (prop.isDeclared() || inheritConverter) {
        ClassKind propKind = prop.getType().getKind();
        if (propKind.basic) {
          if (propKind == ClassKind.STRING) {
            genPropFromJson("String", "(String)", "", prop, writer);
          } else {
            switch (prop.getType().getSimpleName()) {
              case "boolean":
              case "Boolean":
                genPropFromJson("Boolean", "(Boolean)", "", prop, writer);
                break;
              case "byte":
              case "Byte":
                genPropFromJson("Number", "((Number)", ").byteValue()", prop, writer);
                break;
              case "short":
              case "Short":
                genPropFromJson("Number", "((Number)", ").shortValue()", prop, writer);
                break;
              case "int":
              case "Integer":
                genPropFromJson("Number", "((Number)", ").intValue()", prop, writer);
                break;
              case "long":
              case "Long":
                genPropFromJson("Number", "((Number)", ").longValue()", prop, writer);
                break;
              case "float":
              case "Float":
                genPropFromJson("Number", "((Number)", ").floatValue()", prop, writer);
                break;
              case "double":
              case "Double":
                genPropFromJson("Number", "((Number)", ").doubleValue()", prop, writer);
                break;
              case "char":
              case "Character":
                genPropFromJson("String", "((String)", ").charAt(0)", prop, writer);
                break;
            }
          }
        } else {
          switch (propKind) {
            case API:
              if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
                genPropFromJson("String", "io.vertx.core.buffer.Buffer.buffer(java.util.Base64.getDecoder().decode((String)", "))", prop, writer);
              }
              break;
            case JSON_OBJECT:
              genPropFromJson("JsonObject", "((JsonObject)", ").copy()", prop, writer);
              break;
            case JSON_ARRAY:
              genPropFromJson("JsonArray", "((JsonArray)", ").copy()", prop, writer);
              break;
            case DATA_OBJECT:
              DataObjectTypeInfo dataObjectTypeInfo = ((DataObjectTypeInfo)prop.getType());
              if (dataObjectTypeInfo.isDeserializable()) {
                String simpleName;
                String match;
                MapperInfo mapper = dataObjectTypeInfo.getDeserializer();
                TypeInfo jsonType = mapper.getTargetType();
                switch (mapper.getKind()) {
                  case SELF:
                    match = "new " + dataObjectTypeInfo.getName() + "((JsonObject)";
                    simpleName = jsonType.getSimpleName();
                    break;
                  case STATIC_METHOD:
                    match = mapper.getQualifiedName() + "." + String.join(".", mapper.getSelectors()) + "((" + jsonType.getSimpleName() + ")";
                    simpleName = jsonType.getSimpleName();
                    break;
                  default:
                    throw new AssertionError();
                }
                genPropFromJson(
                  simpleName,
                  match,
                  ")",
                  prop,
                  writer
                );
              } else {
                return;
              }
              break;
            case ENUM:
              genPropFromJson("String", prop.getType().getName() + ".valueOf((String)", ")", prop, writer);
              break;
            case OBJECT:
              genPropFromJson("Object", "", "", prop, writer);
              break;
            case OTHER:
              if (prop.getType().getName().equals(Instant.class.getName())) {
                genPropFromJson("String", "Instant.from(DateTimeFormatter.ISO_INSTANT.parse((String)", "))", prop, writer);
              }
              break;
            default:
          }
        }
      }
    });
    writer.print("      }\n");
    writer.print("    }\n");
    writer.print("  }\n");
  }

  private void genPropFromJson(String cast, String before, String after, PropertyInfo prop, PrintWriter writer) {
    String indent = "        ";
    writer.print(indent + "case \"" + prop.getName() + "\":\n");
    if (prop.isList() || prop.isSet()) {
      writer.print(indent + "  if (member.getValue() instanceof JsonArray) {\n");
      if (prop.isSetter()) {
        String coll = prop.isList() ? "java.util.ArrayList" : "java.util.LinkedHashSet";
        writer.print(indent + "    " + coll + "<" + prop.getType().getName() + "> list =  new " + coll + "<>();\n");
        writer.print(indent + "    ((Iterable<Object>)member.getValue()).forEach( item -> {\n");
        writer.print(indent + "      if (item instanceof " + cast + ")\n");
        writer.print(indent + "        list.add(" + before + "item" + after + ");\n");
        writer.print(indent + "    });\n");
        writer.print(indent + "    obj." + prop.getSetterMethod() + "(list);\n");
      } else if (prop.isAdder()) {
        writer.print(indent + "    ((Iterable<Object>)member.getValue()).forEach( item -> {\n");
        writer.print(indent + "      if (item instanceof " + cast + ")\n");
        writer.print(indent + "        obj." + prop.getAdderMethod() + "(" + before + "item" + after + ");\n");
        writer.print(indent + "    });\n");
      }
      writer.print(indent + "  }\n");
    } else if (prop.isMap()) {
      writer.print(indent + "  if (member.getValue() instanceof JsonObject) {\n");
      if (prop.isAdder()) {
        writer.print(indent + "    ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {\n");
        writer.print(indent + "      if (entry.getValue() instanceof " + cast + ")\n");
        writer.print(indent + "        obj." + prop.getAdderMethod() + "(entry.getKey(), " + before + "entry.getValue()" + after + ");\n");
        writer.print(indent + "    });\n");
      } else if (prop.isSetter()) {
        writer.print(indent + "    java.util.Map<String, " + prop.getType().getName() + "> map = new java.util.LinkedHashMap<>();\n");
        writer.print(indent + "    ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {\n");
        writer.print(indent + "      if (entry.getValue() instanceof " + cast + ")\n");
        writer.print(indent + "        map.put(entry.getKey(), " + before + "entry.getValue()" + after + ");\n");
        writer.print(indent + "    });\n");
        writer.print(indent + "    obj." + prop.getSetterMethod() + "(map);\n");
      }
      writer.print(indent + "  }\n");
    } else {
      if (prop.isSetter()) {
        writer.print(indent + "  if (member.getValue() instanceof " + cast + ") {\n");
        writer.print(indent + "    obj." + prop.getSetterMethod()+ "(" + before + "member.getValue()" + after + ");\n");
        writer.print(indent + "  }\n");
      }
    }
    writer.print(indent + "  break;\n");
  }
}
