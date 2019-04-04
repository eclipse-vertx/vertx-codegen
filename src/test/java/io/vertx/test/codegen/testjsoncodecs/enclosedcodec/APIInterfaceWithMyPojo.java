package io.vertx.test.codegen.testjsoncodecs.enclosedcodec;

import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

@VertxGen
public interface APIInterfaceWithMyPojo {

  void doSomething(MyPojo myPojo);

}
