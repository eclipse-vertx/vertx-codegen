package io.vertx.codegen.generators.dataobjecthelper;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.writer.CodeWriter;
import io.vertx.core.json.JsonCodec;
import io.vertx.core.json.JsonDecoder;
import io.vertx.core.json.JsonEncoder;
import io.vertx.core.json.JsonObject;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectCodecGen extends Generator<DataObjectModel> {

  public DataObjectCodecGen() {
    kinds = Collections.singleton("dataObject");
    name = "data_object_codecs";
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    return model.getFqn() + "Codec.java";
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    StringWriter buffer = new StringWriter();
    CodeWriter code = new CodeWriter(buffer);
    boolean generateEncode = model.getType().hasJsonEncoder();
    boolean generateDecode = model.getType().hasJsonDecoder();

    code.codeln("package " + model.getType().getPackageName() + ";")
      .newLine()
      .javaImport("io.vertx.core.json.JsonObject")
      .javaImport(
      matchCodecType(generateEncode, generateDecode,
        "io.vertx.core.json.JsonCodec",
        "io.vertx.core.json.JsonEncoder",
        "io.vertx.core.json.JsonDecoder"
      )
    );
    code.newLine()
      .codeln("public class " + model.getType().getSimpleName() + "Codec implements " +
      matchCodecType(generateEncode, generateDecode,
      "JsonCodec",
      "JsonEncoder",
      "JsonDecoder"
      ) + "<" + model.getType().getSimpleName() + ", JsonObject> {"
    ).newLine();

    code.indented(() -> {
      generateSingletonInstanceAndHolder(model.getType().getSimpleName(), code);
      code.newLine();
      if (generateEncode) writeEncodeMethod(model.getType().getSimpleName(), code);
      if (generateDecode) writeDecodeMethod(model.getType().getSimpleName(), code);
    });
    code.code("}");
    return buffer.toString();
  }

  private <T> T matchCodecType(boolean generateEncode, boolean generateDecode, T completeCodec, T onlyEncoder, T onlyDecoder) {
    if (generateDecode && generateEncode) return completeCodec;
    else if (generateDecode) return onlyDecoder;
    else return onlyEncoder;
  }

  private void generateSingletonInstanceAndHolder(String dataObjectSimpleName, CodeWriter code) {
    code.codeln("private static class " + dataObjectSimpleName + "CodecHolder {")
      .indented(() -> code.codeln("static final " + dataObjectSimpleName + "Codec INSTANCE = new " + dataObjectSimpleName + "Codec();"))
      .codeln("}")
      .newLine()
      .code("public static " + dataObjectSimpleName + "Codec getInstance() { return " + dataObjectSimpleName + "CodecHolder.INSTANCE; }")
      .newLine();
  }

  private void writeDecodeMethod(String dataObjectSimpleName, CodeWriter codeWriter) {
    codeWriter.codeln("@Override public " + dataObjectSimpleName + " decode(JsonObject value) { return new " + dataObjectSimpleName + "(value); }").newLine();
  }

  private void writeEncodeMethod(String dataObjectSimpleName, CodeWriter codeWriter) {
    codeWriter.codeln("@Override public JsonObject encode(" + dataObjectSimpleName + " value) { return (value != null) ? value.toJson() : null; }").newLine();
  }
}
