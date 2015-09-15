package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;

/**
 * The value (member) of an enumeration model.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumValueInfo {

  private final String identifier;
  private final Doc doc;

  public EnumValueInfo(String identifier, Doc doc) {
    this.identifier = identifier;
    this.doc = doc;
  }

  /**
   * @return the value identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * @return the optional documentation
   */
  public Doc getDoc() {
    return doc;
  }
}
