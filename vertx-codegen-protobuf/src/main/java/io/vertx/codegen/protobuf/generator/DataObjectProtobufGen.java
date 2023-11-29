package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.JsonProtoEncoding;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.writer.CodeWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectProtobufGen extends Generator<DataObjectModel> {

  public static int CACHE_INITIAL_CAPACITY = 16;

  public DataObjectProtobufGen() {
    kinds = Collections.singleton("dataObject");
    name = "data_object_converters";
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(ProtobufGen.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    if (model.isClass() && model.getAnnotations().stream().anyMatch(ann -> ann.getName().equals(ProtobufGen.class.getName()))) {
      return model.getFqn() + "ProtoConverter.java";
    }
    return null;
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    AnnotationValueInfo protobufGen = model.getAnnotation(ProtobufGen.class).get();
    return renderProto(model, protobufGen.getMember("publicConverter") == Boolean.TRUE, index, size, session);
  }

  public String renderProto(DataObjectModel model, boolean isPublic, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    CodeWriter code = new CodeWriter(writer);
    String visibility = isPublic ? "public" : "";

    JsonProtoEncoding jsonProtoEncoding = ProtobufGenAnnotation.jsonProtoEncoding(model);
    FieldNumberStrategy fieldNumberStrategy = ProtobufGenAnnotation.fieldNumberStrategy(model);
    Set<Integer> reservedFieldNumbers = ProtobufGenAnnotation.reservedFieldNumbers(model);
    Set<String> reservedFieldNames = ProtobufGenAnnotation.reservedFieldNames(model);

    writer.print("package " + model.getType().getPackageName() + ";\n");
    writer.print("\n");
    writer.print("import com.google.protobuf.CodedOutputStream;\n");
    writer.print("import com.google.protobuf.CodedInputStream;\n");
    writer.print("import java.io.IOException;\n");
    writer.print("import java.time.Instant;\n");
    writer.print("import java.time.ZonedDateTime;\n");
    writer.print("import java.util.ArrayList;\n");
    writer.print("import java.util.List;\n");
    writer.print("import java.util.HashMap;\n");
    writer.print("import java.util.Map;\n");
    writer.print("import java.util.Arrays;\n");
    writer.print("import io.vertx.codegen.protobuf.ProtobufEncodingMode;\n");
    writer.print("import io.vertx.core.json.JsonObject;\n");
    writer.print("import io.vertx.codegen.protobuf.utils.ExpandableIntArray;\n");
    writer.print("import io.vertx.codegen.protobuf.converters.*;\n");
    writer.print("\n");
    code
      .codeln("public class " + model.getType().getSimpleName() + "ProtoConverter {"
      ).newLine();

    String simpleName = model.getType().getSimpleName();

    Collection<PropertyInfo> properties = model.getPropertyMap().values();
    ProtobufFields.verifyFieldNames(properties, reservedFieldNames);
    Map<String, Integer> fieldNumbers = ProtobufFields.fieldNumbers(properties, fieldNumberStrategy, reservedFieldNumbers);
    List<PropertyInfo> orderedProperties = ProtobufFields.inFieldNumberOrder(properties, fieldNumbers);

    // fromProto()
    {
      writer.print("  " + visibility + " static void fromProto(CodedInputStream input, " + simpleName + " obj) throws IOException {\n");
      writer.print("    fromProto(input, obj, ProtobufEncodingMode.VERTX);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  " + visibility + " static void fromProto(CodedInputStream input, " + simpleName + " obj, ProtobufEncodingMode encodingMode) throws IOException {\n");
      writer.print("    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;\n");
      // Compatible Mode
      {
        int fieldNumber = 1;
        writer.print("    if (compatibleMode) {\n");
        for (PropertyInfo prop : model.getPropertyMap().values()) {
          ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
          ClassKind propKind = prop.getType().getKind();
          // Only applicable to Boxed type
          if (!prop.getKind().isList() && !prop.getKind().isMap() && propKind.basic) {
            String defaultValue = protoProperty.getDefaultValue();
            if (protoProperty.isBoxedType() && defaultValue != null) {
              writer.print("      obj." + prop.getSetterMethod() + "(" + defaultValue + ");\n");
            }
          }
          fieldNumber++;
        }
        writer.print("    }\n");
      }
      writer.print("    int tag;\n");
      writer.print("    while ((tag = input.readTag()) != 0) {\n");
      writer.print("      switch (tag) {\n");
      for (PropertyInfo prop : orderedProperties) {
        ClassKind propKind = prop.getType().getKind();
        int fieldNumber = fieldNumbers.get(prop.getName());
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
        writer.print("        case " + protoProperty.getTag() + ": {\n");
        if (prop.getType().getKind() == ClassKind.ENUM) {
          writer.print("          switch (input.readEnum()) {\n");
          EnumTypeInfo enumTypeInfo = (EnumTypeInfo) prop.getType();
          int enumIntValue = 0;
          for (String enumValue : enumTypeInfo.getValues()) {
            writer.print("            case " + enumIntValue + ":\n" );
            writer.print("              obj." + prop.getSetterMethod() +"(" + enumTypeInfo.getSimpleName() + "." + enumValue +");\n");
            writer.print("              break;\n");
            enumIntValue++;
          }
          writer.print("          }\n");
          writer.print("          break;\n");
        } else { // Not Enum
          if (prop.getKind().isList()) {
            if (propKind.basic) {
              writer.print("          int length = input.readRawVarint32();\n");
              writer.print("          int limit = input.pushLimit(length);\n");
              writer.print("          List<Integer> list = new ArrayList<>();\n");
              writer.print("          while (input.getBytesUntilLimit() > 0) {\n");
              writer.print("            list.add(input." + protoProperty.getProtoType().read() + "());\n");
              writer.print("          }\n");
              writer.print("          obj." + prop.getSetterMethod() + "(list);\n");
              writer.print("          input.popLimit(limit);\n");
              writer.print("          break;\n");
            } else {
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          if (obj." + prop.getGetterMethod() + "() == null) {\n");
                writer.print("            obj." + prop.getSetterMethod() + "(new ArrayList<>());\n");
                writer.print("          }\n");
                writer.print("          obj." + prop.getGetterMethod() + "().add(" + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".fromProto(input));\n");
                writer.print("          input.popLimit(limit);\n");
                writer.print("          break;\n");
              } else {
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          " + protoProperty.getMessage() + " nested = new " + protoProperty.getMessage() + "();\n");
                writer.print("          " + protoProperty.getMessage() + "ProtoConverter.fromProto(input, nested);\n");
                writer.print("          if (obj." + prop.getGetterMethod() + "() == null) {\n");
                writer.print("            obj." + prop.getSetterMethod() + "(new ArrayList<>());\n");
                writer.print("          }\n");
                writer.print("          obj." + prop.getGetterMethod() + "().add(nested);\n");
                writer.print("          input.popLimit(limit);\n");
                writer.print("          break;\n");
              }
            }
          } else if (prop.getKind().isMap()) {
            if (propKind.basic) {
              writer.print("          int length = input.readRawVarint32();\n");
              writer.print("          int limit = input.pushLimit(length);\n");
              writer.print("          Map<String, " + prop.getType().getSimpleName() + "> map = obj." + prop.getGetterMethod() + "();\n");
              writer.print("          if (map == null) {\n");
              writer.print("            map = new HashMap<>();\n");
              writer.print("          }\n");
              writer.print("          input.readTag();\n");
              writer.print("          String key = input.readString();\n");
              writer.print("          input.readTag();\n");
              writer.print("          " + prop.getType().getSimpleName() + " value = input." + protoProperty.getProtoType().read() + "();\n");
              writer.print("          map.put(key, value);\n");
              writer.print("          obj." + prop.getSetterMethod() + "(map);\n");
              writer.print("          input.popLimit(limit);\n");
              writer.print("          break;\n");
            } else {
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          Map<String, " + builtInType + "> map = obj." + prop.getGetterMethod() + "();\n");
                writer.print("          if (map == null) {\n");
                writer.print("            map = new HashMap<>();\n");
                writer.print("          }\n");
                writer.print("          input.readTag();\n");
                writer.print("          String key = input.readString();\n");
                writer.print("          input.readTag();\n");
                writer.print("          int vlength = input.readUInt32();\n");
                writer.print("          int vlimit = input.pushLimit(vlength);\n");
                writer.print("          map.put(key, " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".fromProto(input));\n");
                writer.print("          obj." + prop.getSetterMethod() + "(map);\n");
                writer.print("          input.popLimit(vlimit);\n");
                writer.print("          input.popLimit(limit);\n");
                writer.print("          break;\n");
              } else {
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          Map<String, " + protoProperty.getMessage() + "> map = obj." + prop.getGetterMethod() + "();\n");
                writer.print("          if (map == null) {\n");
                writer.print("            map = new HashMap<>();\n");
                writer.print("          }\n");
                writer.print("          input.readTag();\n");
                writer.print("          String key = input.readString();\n");
                writer.print("          input.readTag();\n");
                writer.print("          int vlength = input.readUInt32();\n");
                writer.print("          int vlimit = input.pushLimit(vlength);\n");
                writer.print("          " + protoProperty.getMessage() + " value = new " + protoProperty.getMessage() + "();\n");
                writer.print("          " + protoProperty.getMessage() + "ProtoConverter.fromProto(input, value);\n");
                writer.print("          map.put(key, value);\n");
                writer.print("          obj." + prop.getSetterMethod() + "(map);\n");
                writer.print("          input.popLimit(vlimit);\n");
                writer.print("          input.popLimit(limit);\n");
                writer.print("          break;\n");
              }
            }
          } else {
            if (propKind.basic) {
              String javaDataType = prop.getType().getName();
              String casting = "";
              if ("java.lang.Short".equals(javaDataType) || "short".equals(javaDataType)) {
                casting = "(short) ";
              } else if ("java.lang.Character".equals(javaDataType) || "char".equals(javaDataType)) {
                casting = "(char) ";
              } else if ("java.lang.Byte".equals(javaDataType) || "byte".equals(javaDataType)) {
                casting = "(byte) ";
              }
              writer.print("          obj." + prop.getSetterMethod() + "(" + casting + "input." + protoProperty.getProtoType().read() + "());\n");
            } else {
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          obj." + prop.getSetterMethod() + "(" + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".fromProto(input));\n");
                writer.print("          input.popLimit(limit);\n");
              } else {
                writer.print("          int length = input.readUInt32();\n");
                writer.print("          int limit = input.pushLimit(length);\n");
                writer.print("          " + protoProperty.getMessage() + " nested = new " + protoProperty.getMessage() + "();\n");
                writer.print("          " + protoProperty.getMessage() + "ProtoConverter.fromProto(input, nested);\n");
                writer.print("          obj." + prop.getSetterMethod() + "(nested);\n");
                writer.print("          input.popLimit(limit);\n");
              }
            }
            writer.print("          break;\n");
          }
        } // Not Enum
        writer.print("        }\n");
      }
      writer.print("      }\n");
      writer.print("    } // while loop\n");
      writer.print("  }\n");
      writer.print("\n");
    }

    // toProto()
    {
      writer.print("  " + visibility + " static void toProto(" + simpleName + " obj, CodedOutputStream output) throws IOException {\n");
      writer.print("    toProto(obj, output, ProtobufEncodingMode.VERTX);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  " + visibility + " static void toProto(" + simpleName + " obj, CodedOutputStream output, ProtobufEncodingMode encodingMode) throws IOException {\n");
      writer.print("    ExpandableIntArray cache = new ExpandableIntArray(" + CACHE_INITIAL_CAPACITY + ");\n");
      writer.print("    " + simpleName + "ProtoConverter.computeSize(obj, cache, 0, encodingMode);\n");
      writer.print("    " + simpleName + "ProtoConverter.toProto(obj, output, cache, 0, encodingMode);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  static int toProto(" + simpleName + " obj, CodedOutputStream output, ExpandableIntArray cache, int index, ProtobufEncodingMode encodingMode) throws IOException {\n");
      writer.print("    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;\n");
      writer.print("    index = index + 1;\n");
      for (PropertyInfo prop : orderedProperties) {
        ClassKind propKind = prop.getType().getKind();
        int fieldNumber = fieldNumbers.get(prop.getName());
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
          writer.print("    // " + prop.getName() + "\n");
          if (!prop.getKind().isList() && !prop.getKind().isMap() && propKind.basic && protoProperty.isBoxedType()) {
            writer.print("    if (compatibleMode && obj." + prop.getGetterMethod() + "() == null) {\n");
            writer.print("      throw new IllegalArgumentException(\"Null values are not allowed for boxed types in compatibility mode\");\n");
            writer.print("    }\n");
            String javaDataType = prop.getType().getName();
            String defaultValue = protoProperty.getDefaultValue();
            if ("java.lang.Boolean".equals(javaDataType) || "Boolean".equals(javaDataType)) {
              writer.print("    if ((!compatibleMode && obj." + prop.getGetterMethod() + "() != null) || (compatibleMode && !obj." + prop.getGetterMethod() + "())) {\n");
            } else if ("java.lang.String".equals(javaDataType) || "String".equals(javaDataType)) {
              writer.print("    if ((!compatibleMode && obj." + prop.getGetterMethod() + "() != null) || (compatibleMode && !obj." + prop.getGetterMethod() + "().isEmpty())) {\n");
            } else {
              if (defaultValue != null) {
                writer.print("    if ((!compatibleMode && obj." + prop.getGetterMethod() + "() != null) || (compatibleMode && obj." + prop.getGetterMethod() + "() != " + defaultValue + ")) {\n");
              } else {
                throw new RuntimeException();
              }
            }
          } else {
            if (protoProperty.isNullable()) {
              writer.print("    if (obj." + prop.getGetterMethod() + "() != null) {\n");
            } else {
              if ("boolean".equals(prop.getType().getName())) {
                writer.print("    if (obj." + prop.getGetterMethod() + "()) {\n");
              } else {
                writer.print("    if (obj." + prop.getGetterMethod() + "() != 0) {\n");
              }
            }
          }
        if (prop.getType().getKind() == ClassKind.ENUM) {
          writer.print("      switch (obj." + prop.getGetterMethod() + "()) {\n");
          EnumTypeInfo enumTypeInfo = (EnumTypeInfo) prop.getType();
          int enumIntValue = 0;
          for (String enumValue : enumTypeInfo.getValues()) {
            writer.print("        case " + enumValue + ":\n" );
            writer.print("          output.writeEnum(" + fieldNumber + ", " + enumIntValue +");\n");
            writer.print("          break;\n");
            enumIntValue++;
          }
          writer.print("      }\n");
        } else { // Not Enum
          if (prop.getKind().isList()) {
            if (propKind.basic) {
              writer.print("      // list | tag | data size | value[0] | value[1] | value[2] |\n");
              writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        int dataSize = 0;\n");
              writer.print("        for (Integer element: obj." + prop.getGetterMethod() + "()) {\n");
              writer.print("          dataSize += CodedOutputStream.computeInt32SizeNoTag(element);\n");
              writer.print("        }\n");
              writer.print("        output.writeUInt32NoTag(dataSize);\n");
              writer.print("        for (Integer element: obj." + prop.getGetterMethod() + "()) {\n");
              writer.print("          output." + protoProperty.getProtoType().writeNoTag() + "(element);\n");
              writer.print("        }\n");
              writer.print("      }\n");
            } else {
              writer.print("      // list[0] | tag | data size | value |\n");
              writer.print("      // list[1] | tag | data size | value |\n");
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() + "()) {\n");
                writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        output.writeUInt32NoTag(" + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(element));\n");
                writer.print("        " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".toProto(element, output);\n");
                writer.print("      }\n");
              } else {
                writer.print("      for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() + "()) {\n");
                writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        output.writeUInt32NoTag(cache.get(index));\n");
                writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.toProto(element, output, cache, index, encodingMode);\n");
                writer.print("      }\n");
              }
            }
          } else if (prop.getKind().isMap()) {
            if (propKind.basic) {
              writer.print("      // map[0] | tag | data size | key | value |\n");
              writer.print("      // map[1] | tag | data size | key | value |\n");
              writer.print("      for (Map.Entry<String, " + prop.getType().getSimpleName() + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        // calculate data size\n");
              writer.print("        int dataSize = 0;\n");
              writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
              writer.print("        dataSize += CodedOutputStream." + protoProperty.getProtoType().computeSize() + "(2, entry.getValue());\n");
              writer.print("        // key\n");
              writer.print("        output.writeUInt32NoTag(dataSize);\n");
              writer.print("        // value\n");
              writer.print("        output.writeString(1, entry.getKey());\n");
              writer.print("        output." + protoProperty.getProtoType().write() + "(2, entry.getValue());\n");
              writer.print("      }\n");
            } else {
              writer.print("      // map[0] | tag | data size | key | value |\n");
              writer.print("      // map[1] | tag | data size | key | value |\n");
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      for (Map.Entry<String, " + builtInType + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
                writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        // calculate data size\n");
                writer.print("        int elementSize = " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(entry.getValue());\n");
                writer.print("        int dataSize = 0;\n");
                writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);\n");
                writer.print("        dataSize += elementSize;\n");
                writer.print("        // key\n");
                writer.print("        output.writeUInt32NoTag(dataSize);\n");
                writer.print("        // value\n");
                writer.print("        output.writeString(1, entry.getKey());\n");
                writer.print("        output.writeUInt32NoTag(18);\n");
                writer.print("        output.writeUInt32NoTag(elementSize);\n");
                writer.print("        " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".toProto(entry.getValue(), output);\n");
                writer.print("      }\n");
              } else {
                writer.print("      for (Map.Entry<String, " + protoProperty.getMessage() + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
                writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        // calculate data size\n");
                writer.print("        int elementSize = cache.get(index);\n");
                writer.print("        int dataSize = 0;\n");
                writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);\n");
                writer.print("        dataSize += elementSize;\n");
                writer.print("        // key\n");
                writer.print("        output.writeUInt32NoTag(dataSize);\n");
                writer.print("        // value\n");
                writer.print("        output.writeString(1, entry.getKey());\n");
                writer.print("        output.writeUInt32NoTag(18);\n");
                writer.print("        output.writeUInt32NoTag(elementSize);\n");
                writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.toProto(entry.getValue(), output, cache, index, encodingMode);\n");
                writer.print("      }\n");
              }
            }
          } else {
            if (propKind.basic) {
              writer.print("      output." + protoProperty.getProtoType().write() + "(" + fieldNumber + ", obj." + prop.getGetterMethod() + "());\n");
            } else {
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("      output.writeUInt32NoTag(" + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(obj." + prop.getGetterMethod() + "()));\n");
                writer.print("      " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".toProto(obj." + prop.getGetterMethod() + "(), output);\n");
              } else {
                writer.print("      output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
                writer.print("      output.writeUInt32NoTag(cache.get(index));\n");
                writer.print("      index = " + protoProperty.getMessage() + "ProtoConverter.toProto(obj." + prop.getGetterMethod() + "(), output, cache, index, encodingMode);\n");
              }
            }
          }
        } // Not Enum
        writer.print("    }\n");
      }
      writer.print("    return index;\n");
      writer.print("  }\n");
      writer.print("\n");
    }

    // computeSize()
    {
      writer.print("  " + visibility + " static int computeSize(" + simpleName + " obj) {\n");
      writer.print("    return computeSize(obj, ProtobufEncodingMode.VERTX);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  " + visibility + " static int computeSize(" + simpleName + " obj, ProtobufEncodingMode encodingMode) {\n");
      writer.print("    ExpandableIntArray cache = new ExpandableIntArray(" + CACHE_INITIAL_CAPACITY + ");\n");
      writer.print("    " + simpleName + "ProtoConverter.computeSize(obj, cache, 0, encodingMode);\n");
      writer.print("    return cache.get(0);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  static int computeSize(" + simpleName + " obj, ExpandableIntArray cache, final int baseIndex, ProtobufEncodingMode encodingMode) {\n");
      writer.print("    int size = 0;\n");
      writer.print("    int index = baseIndex + 1;\n");
      for (PropertyInfo prop : orderedProperties) {
        ClassKind propKind = prop.getType().getKind();
        int fieldNumber = fieldNumbers.get(prop.getName());
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
        if (protoProperty.isNullable()) {
          writer.print("    if (obj." + prop.getGetterMethod() + "() != null) {\n");
        } else {
          if ("boolean".equals(prop.getType().getName())) {
            writer.print("    if (obj." + prop.getGetterMethod() + "()) {\n");
          } else {
            writer.print("    if (obj." + prop.getGetterMethod() + "() != 0) {\n");
          }
        }
        if (prop.getType().getKind() == ClassKind.ENUM) {
          writer.print("      switch (obj." + prop.getGetterMethod() + "()) {\n");
          EnumTypeInfo enumTypeInfo = (EnumTypeInfo) prop.getType();
          int enumIntValue = 0;
          for (String enumValue : enumTypeInfo.getValues()) {
            writer.print("        case " + enumValue + ":\n" );
            writer.print("          size += CodedOutputStream.computeEnumSize(" + fieldNumber + ", " + enumIntValue +");\n");
            writer.print("          break;\n");
            enumIntValue++;
          }
          writer.print("      }\n");
        } else { // Not Enum
          if (prop.getKind().isList()) {
            if (propKind.basic) {
              writer.print("      // list | tag | data size | value[0] | value[1] | value[2] |\n");
              writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
              writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        int dataSize = 0;\n");
              writer.print("        for (Integer element: obj." + prop.getGetterMethod() + "()) {\n");
              writer.print("          dataSize += CodedOutputStream." + protoProperty.getProtoType().computeSizeNoTag() + "(element);\n");
              writer.print("        }\n");
              writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
              writer.print("        size += dataSize;\n");
              writer.print("      }\n");
            } else {
              writer.print("      // list[0] | tag | data size | value |\n");
              writer.print("      // list[1] | tag | data size | value |\n");
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
                writer.print("        for (" + builtInType + " element: obj." + prop.getGetterMethod() + "()) {\n");
                writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("          int dataSize = " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(element);\n");
                writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("          size += dataSize;\n");
                writer.print("        }\n");
                writer.print("      }\n");
              } else {
                writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
                writer.print("        for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() + "()) {\n");
                writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("          int savedIndex = index;\n");
                writer.print("          index = " + protoProperty.getMessage() + "ProtoConverter.computeSize(element, cache, index, encodingMode);\n");
                writer.print("          int dataSize = cache.get(savedIndex);\n");
                writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("          size += dataSize;\n");
                writer.print("        }\n");
                writer.print("      }\n");
              }
            }
          } else if (prop.getKind().isMap()) {
            if (propKind.basic) {
              writer.print("      // map[0] | tag | data size | key | value |\n");
              writer.print("      // map[1] | tag | data size | key | value |\n");
              writer.print("      for (Map.Entry<String, " + prop.getType().getSimpleName() + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
              writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        int dataSize = 0;\n");
              writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
              writer.print("        dataSize += CodedOutputStream." + protoProperty.getProtoType().computeSize() + "(2, entry.getValue());\n");
              writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
              writer.print("        size += dataSize;\n");
              writer.print("      }\n");
            } else {
              writer.print("        // map[0] | tag | data size | key | value |\n");
              writer.print("        // map[1] | tag | data size | key | value |\n");
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      for (Map.Entry<String, " + builtInType + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
                writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        // calculate data size\n");
                writer.print("        int dataSize = 0;\n");
                writer.print("        // key\n");
                writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
                writer.print("        // value\n");
                writer.print("        int elementSize = " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(entry.getValue());\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);\n");
                writer.print("        dataSize += elementSize;\n");
                writer.print("        // data size\n");
                writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("        size += dataSize;\n");
                writer.print("      }\n");
              } else {
                writer.print("      for (Map.Entry<String, " + protoProperty.getMessage() + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
                writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("        // calculate data size\n");
                writer.print("        int dataSize = 0;\n");
                writer.print("        // key\n");
                writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
                writer.print("        // value\n");
                writer.print("        int savedIndex = index;\n");
                writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.computeSize(entry.getValue(), cache, index, encodingMode);\n");
                writer.print("        int elementSize = cache.get(savedIndex);\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);\n");
                writer.print("        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);\n");
                writer.print("        dataSize += elementSize;\n");
                writer.print("        // data size\n");
                writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("        size += dataSize;\n");
                writer.print("      }\n");
              }
            }
          } else {
            if (propKind.basic) {
              writer.print("      size += CodedOutputStream." + protoProperty.getProtoType().computeSize() + "(" + fieldNumber + ", obj." + prop.getGetterMethod() + "());\n");
            } else {
              if (protoProperty.isBuiltinType()) {
                String builtInType = prop.getType().getSimpleName();
                writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("      int dataSize = " + ProtoProperty.getBuiltInProtoConverter(builtInType, jsonProtoEncoding) + ".computeSize(obj." + prop.getGetterMethod() + "());\n");
                writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("      size += dataSize;\n");
              } else {
                writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
                writer.print("      int savedIndex = index;\n");
                writer.print("      index = " + protoProperty.getMessage() + "ProtoConverter.computeSize(obj." + prop.getGetterMethod() + "(), cache, index, encodingMode);\n");
                writer.print("      int dataSize = cache.get(savedIndex);\n");
                writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
                writer.print("      size += dataSize;\n");
              }
            }
          }
        } // Not Enum
        writer.print("    }\n");
      }
      writer.print("    cache.set(baseIndex, size);\n");
      writer.print("    return index;\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("}\n");
    }

    return buffer.toString();
  }

  private static String getString(PropertyInfo prop) {
    String javaDataType = prop.getType().getName();
    String casting = "";
    if ("java.lang.Short".equals(javaDataType) || "short".equals(javaDataType)) {
      casting = "(short) ";
    } else if ("java.lang.Character".equals(javaDataType) || "char".equals(javaDataType)) {
      casting = "(char) ";
    } else if ("java.lang.Byte".equals(javaDataType) || "byte".equals(javaDataType)) {
      casting = "(byte) ";
    }
    return casting;
  }
}
