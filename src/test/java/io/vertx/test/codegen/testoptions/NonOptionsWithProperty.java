package io.vertx.test.codegen.testoptions;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface NonOptionsWithProperty<T extends NonOptionsWithProperty> {

  T setNonOptionsProperty(String value);

}
