package io.vertx.codegen.processor;

import javax.lang.model.element.Element;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class GenException extends RuntimeException {

  final Element element;
  final String msg;

  public GenException(Element element, String msg) {
    super(msg);
    this.element = element;
    this.msg = msg;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GenException exception = (GenException) o;

    if (element != null ? !element.equals(exception.element) : exception.element != null) return false;
    if (msg != null ? !msg.equals(exception.msg) : exception.msg != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = element != null ? element.hashCode() : 0;
    result = 31 * result + (msg != null ? msg.hashCode() : 0);
    return result;
  }
}
