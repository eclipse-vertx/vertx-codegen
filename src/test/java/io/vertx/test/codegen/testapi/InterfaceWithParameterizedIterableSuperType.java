package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author Thomas Segismont
 */
@VertxGen
public interface InterfaceWithParameterizedIterableSuperType<U> extends Iterable<U> {
}
