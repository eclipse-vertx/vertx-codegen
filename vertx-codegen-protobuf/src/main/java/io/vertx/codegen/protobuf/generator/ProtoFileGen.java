package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.type.ClassKind;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ProtoFileGen extends Generator<DataObjectModel> {

  public ProtoFileGen() {
    name = "protobuf";
    kinds = Collections.singleton("dataObject");
    incremental = true;
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    return "resources/dataobjects.proto";
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    PrintWriter writer = new PrintWriter(buffer);

    if (index == 0) {
      writer.print("// Automatically generated by vertx-codegen.\n");
      writer.print("// Do not edit. Changes made to this file may be overwritten.\n");
      writer.print("\n");
      writer.print("syntax = \"proto3\";\n");
      writer.print("option java_multiple_files = true;\n");
      writer.print("option java_package = \"io.vertx.protobuf.generated\";\n");
      writer.print("\n");
      writer.print("package io.vertx.protobuf.generated;\n");
      writer.print("\n");
      writer.print("import \"json-object.proto\";\n");
      writer.print("import \"datetime.proto\";\n");
      writer.print("\n");
    }

    String messageName = model.getType().getSimpleName();
    int fieldNumber = 1;

    writer.print("message " + messageName + " {\n");
    for (PropertyInfo prop : model.getPropertyMap().values()) {
      ClassKind propKind = prop.getType().getKind();
      ProtoProperty protoProperty = ProtoProperty.getProtoProperty(prop, fieldNumber);

      String protoType;
      if (propKind.basic) {
        protoType = protoProperty.getProtoType().value;
      } else {
        String builtInType = ProtoProperty.getBuiltInType(prop);
        if (builtInType != null) {
          protoType = "io.vertx.protobuf." + builtInType;
        } else {
          protoType = protoProperty.getMessage();
        }
      }

      if (prop.getKind().isList()) {
        writer.print("  repeated " + protoType + " " + prop.getName() + " = " + fieldNumber + ";\n");
      } else if (prop.getKind().isMap()) {
        writer.print("  map<string, " + protoType + "> " + prop.getName() + " = " + fieldNumber + ";\n");
      } else {
        writer.print("  " + protoType + " " + prop.getName() + " = " + fieldNumber + ";\n");
      }
      fieldNumber++;
    }
    writer.print("}\n");
    writer.print("\n");

    return buffer.toString();
  }
}