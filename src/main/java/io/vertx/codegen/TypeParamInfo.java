package io.vertx.codegen;

import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeParamInfo {

  private final String name;
  private final Set<Variance> siteVariances;

  public TypeParamInfo(String name, Set<Variance> siteVariances) {
    this.name = name;
    this.siteVariances = siteVariances;
  }

  public String getName() {
    return name;
  }

  public boolean isSiteCovariant() {
    return siteVariances.size() == 1 && siteVariances.contains(Variance.COVARIANT);
  }

  public boolean isSiteContravariant() {
    return siteVariances.size() == 1 && siteVariances.contains(Variance.CONTRAVARIANT);
  }
}
