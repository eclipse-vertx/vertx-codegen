package io.vertx.test.codegen.testapi.kotlin;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author Richard Gomez
 */
@VertxGen
public interface InterfaceWithCompanionObject {

  InterfaceWithCompanionObject.Companion Companion = InterfaceWithCompanionObject.Companion.$$INSTANCE;

  @GenIgnore
  public static final class Companion {
    static final InterfaceWithCompanionObject.Companion $$INSTANCE;

    public final void create() {
    }

    static {
      InterfaceWithCompanionObject.Companion var0 = new InterfaceWithCompanionObject.Companion();
      $$INSTANCE = var0;
    }
  }
}
