package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author Richard Gomez
 */
@VertxGen
public interface InterfaceWithUnrelatedCompanionClass {
  Companion CompanionField = Companion.$$INSTANCE;
}

