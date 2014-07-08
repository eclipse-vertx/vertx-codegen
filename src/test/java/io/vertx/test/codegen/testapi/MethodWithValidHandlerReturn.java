package io.vertx.test.codegen.testapi;

import io.vertx.core.Handler;
import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerReturn {

  Handler<Byte> byteHandler();
  Handler<Short> shortHandler();
  Handler<Integer> intHandler();
  Handler<Long> longHandler();
  Handler<Float> floatHandler();
  Handler<Double> doubleHandler();
  Handler<Boolean> booleanHandler();
  Handler<Character> charHandler();
  Handler<String> stringHandler();

  Handler<VertxGenClass1> vertxGen1Handler();
  Handler<VertxGenClass2> vertxGen2Handler();


}
