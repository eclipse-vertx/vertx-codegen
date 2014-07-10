package io.vertx.codegen;

import javax.lang.model.element.Element;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class GenException extends RuntimeException {

  final Element element;
  final String msg;

  public GenException(Element element, String msg) {
    this.element = element;
    this.msg = msg;
  }
}
