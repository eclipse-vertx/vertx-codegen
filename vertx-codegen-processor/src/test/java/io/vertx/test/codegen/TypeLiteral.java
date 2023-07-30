package io.vertx.test.codegen;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeLiteral<T> {

  final Type type;

  public TypeLiteral() {
    this.type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
}
