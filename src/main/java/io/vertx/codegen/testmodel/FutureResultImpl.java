package io.vertx.codegen.testmodel;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class FutureResultImpl<T> implements AsyncResult<T> {

  public FutureResultImpl(T t) {
  }

  public FutureResultImpl(Throwable t) {
  }
}
