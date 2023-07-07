package io.vertx.test.codegen.testtype;

import io.vertx.test.codegen.testenum.ValidEnum;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface EnumHolder {


  ValidEnum apiEnum();
  TimeUnit otherEnum();

}
