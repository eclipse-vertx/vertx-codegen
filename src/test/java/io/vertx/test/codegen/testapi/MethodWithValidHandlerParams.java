package io.vertx.test.codegen.testapi;

import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerParams {

  void methodWithHandlerParams(Handler<Byte> byteHandler, Handler<Short> shortHandler, Handler<Integer> intHandler,
                               Handler<Long> longHandler, Handler<Float> floatHandler, Handler<Double> doubleHandler,
                               Handler<Boolean> booleanHandler, Handler<Character> charHandler, Handler<String> strHandler,
                               Handler<VertxGenClass1> gen1Handler, Handler<VertxGenClass2> gen2Handler,
                               Handler<List<Byte>> listByteHandler, Handler<List<Short>> listShortHandler, Handler<List<Integer>> listIntHandler,
                               Handler<List<Long>> listLongHandler, Handler<List<Float>> listFloatHandler, Handler<List<Double>> listDoubleHandler,
                               Handler<List<Boolean>> listBooleanHandler, Handler<List<Character>> listCharHandler, Handler<List<String>> listStrHandler,
                               Handler<Set<Byte>> setByteHandler, Handler<Set<Short>> setShortHandler, Handler<Set<Integer>> setIntHandler,
                               Handler<Set<Long>> setLongHandler, Handler<Set<Float>> setFloatHandler, Handler<Set<Double>> setDoubleHandler,
                               Handler<Set<Boolean>> setBooleanHandler, Handler<Set<Character>> setCharHandler, Handler<Set<String>> setStrHandler,
                               Handler<Void> voidHandler, Handler<Throwable> throwableHandler);
}
