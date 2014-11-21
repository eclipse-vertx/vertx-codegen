package io.vertx.codegen;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Signature {

  final String name;
  final List<ParamInfo> params;

  public Signature(String name, List<ParamInfo> params) {
    this.name = name;
    this.params = params;
  }

  public String getName() {
    return name;
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Signature) {
      Signature that = (Signature) o;
      return name.equals(that.name) && params.equals(that.params);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode() ^ params.hashCode();
  }
}
