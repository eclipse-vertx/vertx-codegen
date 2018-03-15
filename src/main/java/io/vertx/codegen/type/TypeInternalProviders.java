package io.vertx.codegen.type;

import java.util.Arrays;
import java.util.List;

import io.vertx.codegen.type.TypeUse.TypeInternalProvider;

/**
 * Catalog of available {@link TypeInternalProvider}s.
 *
 * @author Gunnar Morling
 */
public class TypeInternalProviders {

  /**
   * Returns an immutable list of the type internal providers to be applied.
   */
  public static List<TypeInternalProvider> getProviders() {
    return Arrays.asList(
      TreeTypeInternal.PROVIDER,
      ReflectTypeInternal.PROVIDER,
      MirrorTypeInternal.PROVIDER
    );
  }
}
