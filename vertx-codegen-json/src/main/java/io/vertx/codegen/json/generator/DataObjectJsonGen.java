package io.vertx.codegen.json.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.format.CamelCase;
import io.vertx.codegen.format.Case;
import io.vertx.codegen.format.KebabCase;
import io.vertx.codegen.format.LowerCamelCase;
import io.vertx.codegen.format.QualifiedCase;
import io.vertx.codegen.format.SnakeCase;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.DataObjectInfo;
import io.vertx.codegen.type.MapperInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.writer.CodeWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectJsonGen extends Generator<DataObjectModel> {

  private Case formatter;
  private boolean isPublic;
  private boolean inheritConverter;
  private String base64Type;
  private boolean generate;

  public DataObjectJsonGen() {
    kinds = Collections.singleton("dataObject");
    name = "data_object_converters";
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(DataObject.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    if (model.isClass() && findJsonGenAnnotation(model) != null) {
      return model.getFqn() + "Converter.java";
    }
    return null;
  }

  private AnnotationValueInfo findJsonGenAnnotation(DataObjectModel model) {
    for (AnnotationValueInfo ann : model.getAnnotations()) {
      if (ann.getName().equals(JsonGen.class.getName())) {
        return ann;
      }
    }
    return null;
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    AnnotationValueInfo jsonGenAnn = findJsonGenAnnotation(model);
    ClassTypeInfo cti = getFormatter(model, JsonGen.class, "jsonPropertyNameFormatter");
    String base64Type = (String) jsonGenAnn.getMember("base64Type");
    if (base64Type == null) {
      throw new GenException(model.getElement(), "Data object base64 type cannot be null");
    } else {
      switch (base64Type) {
        case "":
          // special type to use vertx-core default
        case "basic":
        case "base64url":
          // ok
          break;
        default:
          throw new GenException(model.getElement(), "Data object base64 unsupported type: " + base64Type);
      }
    }
    formatter = getCase(cti);
    isPublic = jsonGenAnn.getMember("publicConverter") == Boolean.TRUE;
    inheritConverter = jsonGenAnn.getMember("inheritConverter") == Boolean.TRUE;
    this.base64Type = base64Type;
    generate = true;
    return renderJson(model);
  }

  private ClassTypeInfo getFormatter(DataObjectModel model, Class<? extends Annotation> annType, String annotationName) {
    AnnotationValueInfo abc = model
      .getAnnotations()
      .stream().filter(ann -> ann.getName().equals(annType.getName()))
      .findFirst().get();
    return (ClassTypeInfo) abc.getMember(annotationName);
  }

  public String renderJson(DataObjectModel model) {
    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    CodeWriter code = new CodeWriter(writer);
    String visibility = isPublic ? "public" : "";

    writer.print("package " + model.getType().getPackageName() + ";\n");
    writer.print("\n");
    writer.print("import io.vertx.core.json.JsonObject;\n");
    writer.print("import io.vertx.core.json.JsonArray;\n");
    writer.print("import io.vertx.core.json.impl.JsonUtil;\n");
    writer.print("import java.time.Instant;\n");
    writer.print("import java.time.format.DateTimeFormatter;\n");
    writer.print("import java.util.Base64;\n");
    writer.print("\n");
    writer.print("/**\n");
    writer.print(" * Converter and mapper for {@link " + model.getType() + "}.\n");
    writer.print(" * NOTE: This class has been automatically generated from the {@link " + model.getType() + "} original class using Vert.x codegen.\n");
    writer.print(" */\n");
    code
      .codeln("public class " + model.getType().getSimpleName() + "Converter {"
      ).newLine();
    if (generate) {
      writer.print("\n");
      switch (base64Type) {
        case "basic":
          writer.print(
            "  private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();\n" +
            "  private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();\n");
          break;
        case "base64url":
          writer.print(
            "  private static final Base64.Decoder BASE64_DECODER = Base64.getUrlDecoder();\n" +
            "  private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();\n");
          break;
        default:
          writer.print(
            "  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;\n" +
            "  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;\n");
          break;
      }
      writer.print("\n");

      genFromJson(visibility, inheritConverter, model, writer);
      writer.print("\n");
      genToJson(visibility, inheritConverter, model, writer);
    }
    writer.print("}\n");
    return buffer.toString();
  }

  private void genToJson(String visibility, boolean inheritConverter, DataObjectModel model_, PrintWriter writer) {
    String simpleName = model_.getType().getSimpleName();
    writer.print("  " + visibility + " static void toJson(" + simpleName + " obj, JsonObject json) {\n");
    writer.print("    toJson(obj, json.getMap());\n");
    writer.print("  }\n");
    writer.print("\n");
    writer.print("  " + visibility + " static void toJson(" + simpleName + " obj, java.util.Map<String, Object> json) {\n");
    model_.getPropertyMap().values().forEach(prop -> {
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
          DataObjectInfo dataObject = prop.getType().getDataObject();
          if (dataObject != null) {
            if (dataObject.isSerializable()) {
              String m;
              MapperInfo mapperInfo = dataObject.getSerializer();
              String match;
              switch (mapperInfo.getKind()) {
                case SELF:
                  m = "";
                  match = "." + String.join(".", mapperInfo.getSelectors()) + "()";
                  break;
                case STATIC_METHOD:
                  m = mapperInfo.getQualifiedName() + "." + String.join(".", mapperInfo.getSelectors()) + "(";
                  match = ")";
                  break;
                default:
                  throw new UnsupportedOperationException();
              }
              genPropToJson(m, match, prop, writer);
            } else if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
              genPropToJson("BASE64_ENCODER.encodeToString(", ".getBytes())", prop, writer);
            }
          } else {
            switch (propKind) {
              case ENUM:
                genPropToJson("", ".name()", prop, writer);
                break;
              case JSON_OBJECT:
              case JSON_ARRAY:
              case OBJECT:
                genPropToJson("", "", prop, writer);
                break;
              case OTHER:
                if (prop.getType().getName().equals(Instant.class.getName())) {
                  genPropToJson("DateTimeFormatter.ISO_INSTANT.format(", ")", prop, writer);
                }
                break;
            }
          }
        }
      }
    });

    writer.print("  }\n");
  }

  private void genPropToJson(String before, String after, PropertyInfo prop, PrintWriter writer) {
    String jsonPropertyName = LowerCamelCase.INSTANCE.to(formatter, prop.getName());
    String indent = "    ";
    if (prop.isList() || prop.isSet()) {
      writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      writer.print(indent + "  JsonArray array = new JsonArray();\n");
      writer.print(indent + "  obj." + prop.getGetterMethod() + "().forEach(item -> array.add(" + before + "item" + after + "));\n");
      writer.print(indent + "  json.put(\"" + jsonPropertyName + "\", array);\n");
      writer.print(indent + "}\n");
    } else if (prop.isMap()) {
      writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      writer.print(indent + "  JsonObject map = new JsonObject();\n");
      writer.print(indent + "  obj." + prop.getGetterMethod() + "().forEach((key, value) -> map.put(key, " + before + "value" + after + "));\n");
      writer.print(indent + "  json.put(\"" + jsonPropertyName + "\", map);\n");
      writer.print(indent + "}\n");
    } else {
      String sp = "";
      if (prop.getType().getKind() != ClassKind.PRIMITIVE) {
        sp = "  ";
        writer.print(indent + "if (obj." + prop.getGetterMethod() + "() != null) {\n");
      }
      writer.print(indent + sp + "json.put(\"" + jsonPropertyName + "\", " + before + "obj." + prop.getGetterMethod() + "()" + after + ");\n");
      if (prop.getType().getKind() != ClassKind.PRIMITIVE) {
        writer.print(indent + "}\n");
      }
    }
  }

  private void genFromJson(String visibility, boolean inheritConverter, DataObjectModel model_, PrintWriter writer) {
    writer.print("  " + visibility + " static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, " + model_.getType().getSimpleName() + " obj) {\n");
    writer.print("    for (java.util.Map.Entry<String, Object> member : json) {\n");
    writer.print("      switch (member.getKey()) {\n");
    model_.getPropertyMap().values().forEach(prop -> {
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
          TypeInfo type = prop.getType();
          DataObjectInfo dataObject = type.getDataObject();
          if (dataObject != null) {
            if (dataObject.isDeserializable()) {
              String simpleName;
              String match;
              MapperInfo mapper = dataObject.getDeserializer();
              TypeInfo jsonType = mapper.getJsonType();
              switch (mapper.getKind()) {
                case SELF:
                  match = "new " + type.getName() + "((" + mapper.getJsonType().getName() + ")";
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

            } else if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
              genPropFromJson("String", "io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)", "))", prop, writer);
            }
          } else {
            switch (propKind) {
              case JSON_OBJECT:
                genPropFromJson("JsonObject", "((JsonObject)", ").copy()", prop, writer);
                break;
              case JSON_ARRAY:
                genPropFromJson("JsonArray", "((JsonArray)", ").copy()", prop, writer);
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
      }
    });
    writer.print("      }\n");
    writer.print("    }\n");
    writer.print("  }\n");
  }

  private void genPropFromJson(String cast, String before, String after, PropertyInfo prop, PrintWriter writer) {
    String jsonPropertyName = LowerCamelCase.INSTANCE.to(formatter, prop.getName());
    String indent = "        ";
    writer.print(indent + "case \"" + jsonPropertyName + "\":\n");
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

  private Case getCase(ClassTypeInfo cti) {
    switch (cti.getName()) {
      case "io.vertx.codegen.format.CamelCase":
        return CamelCase.INSTANCE;
      case "io.vertx.codegen.format.SnakeCase":
        return SnakeCase.INSTANCE;
      case "io.vertx.codegen.format.LowerCamelCase":
        return LowerCamelCase.INSTANCE;
      case "io.vertx.codegen.format.KebabCase":
        return KebabCase.INSTANCE;
      case "io.vertx.codegen.format.QualifiedCase":
        return QualifiedCase.INSTANCE;
      default:
        throw new UnsupportedOperationException("Todo");
    }
  }
}
