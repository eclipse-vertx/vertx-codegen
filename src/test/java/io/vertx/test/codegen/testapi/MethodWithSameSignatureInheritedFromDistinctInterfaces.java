package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithSameSignatureInheritedFromDistinctInterfaces<T> extends SameSignatureMethod1<T>, SameSignatureMethod2<T> {
}
