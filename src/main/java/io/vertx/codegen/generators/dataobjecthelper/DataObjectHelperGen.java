package io.vertx.codegen.generators.dataobjecthelper;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.format.CamelCase;
import io.vertx.codegen.format.Case;
import io.vertx.codegen.format.KebabCase;
import io.vertx.codegen.format.LowerCamelCase;
import io.vertx.codegen.format.QualifiedCase;
import io.vertx.codegen.format.SnakeCase;
import io.vertx.codegen.generators.dataobjecthelper.proto.ProtoProperty;
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
public class DataObjectHelperGen extends Generator<DataObjectModel> {

  private Case formatter;

  public DataObjectHelperGen() {
    kinds = Collections.singleton("dataObject");
    name = "data_object_converters";
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(DataObject.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    if (model.isClass() && model.getGenerateConverter()) {
      if (model.getProtoConverter()) {
        return model.getFqn() + "ProtoConverter.java";
      } else {
        return model.getFqn() + "Converter.java";
      }
    }
    return null;
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    if (model.getProtoConverter()) {
      return renderProto(model, index, size, session);
    } else {
      return renderJson(model, index, size, session);
    }
  }

  public String renderProto(DataObjectModel model, int index, int size, Map<String, Object> session) {
    formatter = getCase(model);

    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    CodeWriter code = new CodeWriter(writer);
    String visibility = model.isPublicConverter() ? "public" : "";
    boolean inheritConverter = model.getInheritConverter();

    writer.print("package " + model.getType().getPackageName() + ";\n");
    writer.print("\n");
    writer.print("import com.google.protobuf.CodedOutputStream;\n");
    writer.print("import com.google.protobuf.CodedInputStream;\n");
    writer.print("import java.io.IOException;\n");
    writer.print("import java.time.ZonedDateTime;\n");
    writer.print("import java.util.ArrayList;\n");
    writer.print("import java.util.List;\n");
    writer.print("import java.util.HashMap;\n");
    writer.print("import java.util.Map;\n");
    writer.print("import java.util.Arrays;\n");
    writer.print("import io.vertx.core.proto.*;\n");
    writer.print("\n");
    code
      .codeln("public class " + model.getType().getSimpleName() + "ProtoConverter {"
      ).newLine();

    String simpleName = model.getType().getSimpleName();

    // fromProto()
    {
      writer.print("  " + visibility + " static void fromProto(CodedInputStream input, " + simpleName + " obj) throws IOException {\n");
      writer.print("    int tag;\n");
      writer.print("    while ((tag = input.readTag()) != 0) {\n");
      writer.print("      switch (tag) {\n");
      int fieldNumber = 1;
      for (PropertyInfo prop : model.getPropertyMap().values()) {
        ClassKind propKind = prop.getType().getKind();
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
        writer.print("        case " + protoProperty.getTag() + ": {\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("          int length = input.readUInt32();\n");
              writer.print("          int limit = input.pushLimit(length);\n");
              writer.print("          if (obj." + prop.getGetterMethod() + "() == null) {\n");
              writer.print("            obj." + prop.getSetterMethod() + "(new ArrayList<>());\n");
              writer.print("          }\n");
              writer.print("          obj." + prop.getGetterMethod() + "().add(ZonedDateTimeProtoConverter.fromProto(input));\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("          int length = input.readUInt32();\n");
              writer.print("          int limit = input.pushLimit(length);\n");
              writer.print("          Map<String, ZonedDateTime> map = obj." + prop.getGetterMethod() + "();\n");
              writer.print("          if (map == null) {\n");
              writer.print("            map = new HashMap<>();\n");
              writer.print("          }\n");
              writer.print("          input.readTag();\n");
              writer.print("          String key = input.readString();\n");
              writer.print("          input.readTag();\n");
              writer.print("          int vlength = input.readUInt32();\n");
              writer.print("          int vlimit = input.pushLimit(vlength);\n");
              writer.print("          map.put(key, ZonedDateTimeProtoConverter.fromProto(input));\n");
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
            // need casting
            String casting = "";
            if ("java.lang.Short".equals(prop.getType().getName())) {
              casting = "(short) ";
            } else if ("java.lang.Character".equals(prop.getType().getName())) {
              casting = "(char) ";
            }
            writer.print("          obj." + prop.getSetterMethod() + "(" + casting + "input." + protoProperty.getProtoType().read() + "());\n");
          } else {
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("          int length = input.readUInt32();\n");
              writer.print("          int limit = input.pushLimit(length);\n");
              writer.print("          obj." + prop.getSetterMethod() + "(ZonedDateTimeProtoConverter.fromProto(input));\n");
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
        writer.print("        }\n");
        fieldNumber++;
      }
      writer.print("      }\n");
      writer.print("    }\n");
      writer.print("  }\n");
      writer.print("\n");
    }

    // toProto2()
    {
      writer.print("  public static void toProto2(" + simpleName + " obj, CodedOutputStream output) throws IOException {\n");
      writer.print("    int[] cache = new int[100];\n");
      writer.print("    " + simpleName + "ProtoConverter.computeSize2(obj, cache, 0);\n");
      writer.print("    " + simpleName + "ProtoConverter.toProto2(obj, output, cache, 0);\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  " + visibility + " static int toProto2(" + simpleName + " obj, CodedOutputStream output, int[] cache, int index) throws IOException {\n");
      writer.print("    index = index + 1;\n");
      int fieldNumber = 1;
      for (PropertyInfo prop : model.getPropertyMap().values()) {
        ClassKind propKind = prop.getType().getKind();
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
        writer.print("    if (obj." + prop.getGetterMethod() + "() != null) {\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() +"()) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(element));\n");
              writer.print("        ZonedDateTimeProtoConverter.toProto(element, output);\n");
              writer.print("      }\n");
            } else {
              writer.print("      for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() +"()) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        output.writeUInt32NoTag(cache[index]);\n");
              writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.toProto2(element, output, cache, index);\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      for (Map.Entry<String, ZonedDateTime> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        // calculate data size\n");
              writer.print("        int elementSize = ZonedDateTimeProtoConverter.computeSize(entry.getValue());\n");
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
              writer.print("        ZonedDateTimeProtoConverter.toProto(entry.getValue(), output);\n");
              writer.print("      }\n");
            } else {
              writer.print("      for (Map.Entry<String, " + protoProperty.getMessage() + "> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
              writer.print("        output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        // calculate data size\n");
              writer.print("        int elementSize = cache[index];\n");
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
              writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.toProto2(entry.getValue(), output, cache, index);\n");
              writer.print("      }\n");
            }
          }
        } else {
          if (propKind.basic) {
            writer.print("      output." + protoProperty.getProtoType().write() + "(" + fieldNumber + ", obj." + prop.getGetterMethod() + "());\n");
          } else {
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("      output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(obj." + prop.getGetterMethod() + "()));\n");
              writer.print("      ZonedDateTimeProtoConverter.toProto(obj." + prop.getGetterMethod() +"(), output);\n");
            } else {
              writer.print("      output.writeUInt32NoTag(" + protoProperty.getTag() + ");\n");
              writer.print("      output.writeUInt32NoTag(cache[index]);\n");
              writer.print("      index = " + protoProperty.getMessage() + "ProtoConverter.toProto2(obj." + prop.getGetterMethod() + "(), output, cache, index);\n");
            }
          }
        }
        writer.print("    }\n");
        fieldNumber++;
      }
      writer.print("    return index;\n");
      writer.print("  }\n");
      writer.print("\n");
    }

    // Compute Size 2
    {
      writer.print("  " + visibility + " static int computeSize2(" + simpleName + " obj) {\n");
      writer.print("    int[] cache = new int[100];\n");
      writer.print("    " + simpleName + "ProtoConverter.computeSize2(obj, cache, 0);\n");
      writer.print("    return cache[0];\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("  " + visibility + " static int computeSize2(" + simpleName + " obj, int[] cache, final int baseIndex) {\n");
      writer.print("    int size = 0;\n");
      writer.print("    int index = baseIndex + 1;\n");
      int fieldNumber = 1;
      for (PropertyInfo prop : model.getPropertyMap().values()) {
        ClassKind propKind = prop.getType().getKind();
        ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);
        writer.print("    if (obj." + prop.getGetterMethod() + "() != null) {\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
              writer.print("        for (ZonedDateTime element: obj." + prop.getGetterMethod() + "()) {\n");
              writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("          int dataSize = ZonedDateTimeProtoConverter.computeSize(element);\n");
              writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
              writer.print("          size += dataSize;\n");
              writer.print("        }\n");
              writer.print("      }\n");
            } else {
              writer.print("      if (obj." + prop.getGetterMethod() + "().size() > 0) {\n");
              writer.print("        for (" + protoProperty.getMessage() + " element: obj." + prop.getGetterMethod() + "()) {\n");
              writer.print("          size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("          int savedIndex = index;\n");
              writer.print("          index = " + protoProperty.getMessage() + "ProtoConverter.computeSize2(element, cache, index);\n");
              writer.print("          int dataSize = cache[savedIndex];\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      for (Map.Entry<String, ZonedDateTime> entry : obj." + prop.getGetterMethod() + "().entrySet()) {\n");
              writer.print("        size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("        // calculate data size\n");
              writer.print("        int dataSize = 0;\n");
              writer.print("        // key\n");
              writer.print("        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());\n");
              writer.print("        // value\n");
              writer.print("        int elementSize = " + protoProperty.getMessage() + "ProtoConverter.computeSize(entry.getValue());\n");
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
              writer.print("        index = " + protoProperty.getMessage() + "ProtoConverter.computeSize2(entry.getValue(), cache, index);\n");
              writer.print("        int elementSize = cache[savedIndex];\n");
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
            if (prop.getType().getName().equals("java.time.ZonedDateTime")) {
              writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("      int dataSize = ZonedDateTimeProtoConverter.computeSize(obj." + prop.getGetterMethod() + "());\n");
              writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
              writer.print("      size += dataSize;\n");
            } else {
              writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(" + protoProperty.getTag() + ");\n");
              writer.print("      int savedIndex = index;\n");
              writer.print("      index = " + protoProperty.getMessage() + "ProtoConverter.computeSize2(obj." + prop.getGetterMethod() + "(), cache, index);\n");
              writer.print("      int dataSize = cache[savedIndex];\n");
              writer.print("      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);\n");
              writer.print("      size += dataSize;\n");
            }
          }
        }
        writer.print("    }\n");
        fieldNumber++;
      }
      writer.print("    cache[baseIndex] = size;\n");
      writer.print("    return index;\n");
      writer.print("  }\n");
      writer.print("\n");
      writer.print("}\n");
    }

    return buffer.toString();
  }

  public String renderJson(DataObjectModel model, int index, int size, Map<String, Object> session) {
    formatter = getCase(model);

    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);
    CodeWriter code = new CodeWriter(writer);
    String visibility = model.isPublicConverter() ? "public" : "";
    boolean inheritConverter = model.getInheritConverter();

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
    if (model.getGenerateConverter()) {
      writer.print("\n");
      switch (model.getBase64Type()) {
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
            } else {
              return;
            }
          } else {
            switch (propKind) {
              case API:
                if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
                  genPropToJson("BASE64_ENCODER.encodeToString(", ".getBytes())", prop, writer);
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

            }
          } else {
            switch (propKind) {
              case API:
                if (prop.getType().getName().equals("io.vertx.core.buffer.Buffer")) {
                  genPropFromJson("String", "io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)", "))", prop, writer);
                }
                break;
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

  private Case getCase(DataObjectModel model) {
    AnnotationValueInfo abc = model
      .getAnnotations()
      .stream().filter(ann -> ann.getName().equals(DataObject.class.getName()))
      .findFirst().get();
    ClassTypeInfo cti = (ClassTypeInfo) abc.getMember("jsonPropertyNameFormatter");
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
