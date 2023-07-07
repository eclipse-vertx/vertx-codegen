package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithSameSignatureInheritedFromDistinctInterfaces<U> extends SameSignatureMethod1<U>, SameSignatureMethod2<U> {
}
