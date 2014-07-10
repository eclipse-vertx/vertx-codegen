package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidListReturn {

  List<Byte> byteList();
  List<Short> shortList();
  List<Integer> intList();
  List<Long> longList();
  List<Float> floatList();
  List<Double> doubleList();
  List<Boolean> booleanList();
  List<Character> charList();
  List<String> stringList();

  List<VertxGenClass1> vertxGen1List();
  List<VertxGenClass2> vertxGen2List();


}
