package io.vertx.codegen;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ConstructorInfo {

  final List<ParamInfo> params;
  final String comment;

  public ConstructorInfo(List<ParamInfo> params, String comment) {
    this.params = params;
    this.comment = comment;
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public String getComment() {
    return comment;
  }
}
