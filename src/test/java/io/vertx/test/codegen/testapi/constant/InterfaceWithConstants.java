package io.vertx.test.codegen.testapi.constant;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.VertxGenClass1;

import java.util.List;
import java.util.Map;
import java.util.Set;

@VertxGen
public interface InterfaceWithConstants {

  byte BYTE = (byte) 0;
  Byte BOXED_BYTE = (byte) 0;
  short SHORT = (short) 0;
  Short BOXED_SHORT = (short) 0;
  int INT = 0;
  Integer BOXED_INT = 0;
  long LONG = 0L;
  Long BOXED_LONG = 0L;
  float FLOAT = 0F;
  Float BOXED_FLOAT = 0F;
  double DOUBLE = 0D;
  Double BOXED_DOUBLE = 0D;
  boolean BOOLEAN = false;
  Boolean BOXED_BOOLEAN = false;
  char CHAR = 'a';
  Character BOXED_CHAR = 'a';
  String STRING = null;
  VertxGenClass1 VERTX_GEN = null;
  JsonObject JSON_OBJECT = null;
  JsonArray JSON_ARRAY = null;
  TestDataObject DATA_OBJECT = null;
  TestEnum ENUM = null;
  Object OBJECT = null;

  // List
  List<Byte> BYTE_LIST = null;
  List<Short> SHORT_LIST = null;
  List<Integer> INT_LIST = null;
  List<Long> LONG_LIST = null;
  List<Float> FLOAT_LIST = null;
  List<Double> DOUBLE_LIST = null;
  List<Boolean> BOOLEAN_LIST = null;
  List<Character> CHAR_LIST = null;
  List<String> STRING_LIST = null;
  List<VertxGenClass1> VERTX_GEN_LIST = null;
  List<JsonObject> JSON_OBJECT_LIST = null;
  List<JsonArray> JSON_ARRAY_LIST = null;
  List<TestDataObject> DATA_OBJECT_LIST = null;
  List<TestEnum> ENUM_LIST = null;

  // Set
  Set<Byte> BYTE_SET = null;
  Set<Short> SHORT_SET = null;
  Set<Integer> INT_SET = null;
  Set<Long> LONG_SET = null;
  Set<Float> FLOAT_SET = null;
  Set<Double> DOUBLE_SET = null;
  Set<Boolean> BOOLEAN_SET = null;
  Set<Character> CHAR_SET = null;
  Set<String> STRING_SET = null;
  Set<VertxGenClass1> VERTX_GEN_SET = null;
  Set<JsonObject> JSON_OBJECT_SET = null;
  Set<JsonArray> JSON_ARRAY_SET = null;
  Set<TestDataObject> DATA_OBJECT_SET = null;
  Set<TestEnum> ENUM_SET = null;

  // Map
  Map<String, Byte> BYTE_MAP = null;
  Map<String, Short> SHORT_MAP = null;
  Map<String, Integer> INT_MAP = null;
  Map<String, Long> LONG_MAP = null;
  Map<String, Float> FLOAT_MAP = null;
  Map<String, Double> DOUBLE_MAP = null;
  Map<String, Boolean> BOOLEAN_MAP = null;
  Map<String, Character> CHAR_MAP = null;
  Map<String, String> STRING_MAP = null;
  Map<String, JsonObject> JSON_OBJECT_MAP = null;
  Map<String, JsonArray> JSON_ARRAY_MAP = null;

  // Any java type
  @SuppressWarnings("codegen-allow-any-java-type") Thread THREAD = null;
  @SuppressWarnings("codegen-allow-any-java-type") List<Thread> THREAD_LIST = null;
  @SuppressWarnings("codegen-allow-any-java-type") Set<Thread> THREAD_SET = null;
  @SuppressWarnings("codegen-allow-any-java-type") Map<String, Thread> THREAD_MAP = null;

}
