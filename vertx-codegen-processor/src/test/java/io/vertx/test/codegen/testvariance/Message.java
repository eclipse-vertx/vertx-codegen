package io.vertx.test.codegen.testvariance;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Message<@Analyze T> {

  T body();

  <R> void reply(Object message, Handler<AsyncResult<Message<R>>> replyHandler);

}
