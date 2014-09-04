package io.vertx.codegen;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeParamInfo {

  private final String name;
  private final Set<Variance> variances;

  public TypeParamInfo(String name, Set<Variance> variances) {
    this.name = name;
    this.variances = variances;
  }

  public String getName() {
    return name;
  }

  public boolean isCovariant() {
    return variances.size() == 1 && variances.contains(Variance.COVARIANT);
  }

  public boolean isContravariant() {
    return variances.size() == 1 && variances.contains(Variance.CONTRAVARIANT);
  }
}
