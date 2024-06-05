package io.vertx.codegen.processor.doc;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A structured text, it can be evaluated as a stream of tokens.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Text {

  final String value;
  final List<Token> tokens;

  public Text(String value) {
    this.value = value;
    this.tokens = Token.tokenize(value);
  }

  private Text(String value, List<Token> tokens) {
    this.value = value;
    this.tokens = tokens;
  }

  /**
   * @return the text value
   */
  public String getValue() {
    return value;
  }

  /**
   * Returns a new text object with tokens mapped by the {@code mapper} function.
   *
   * @param mapping the mapping function
   * @return the new text object
   */
  public Text map(Function<Token, Token> mapping) {
    return new Text(value, tokens.stream().map(mapping).collect(Collectors.toList()));
  }

  /**
   * @return the tokens of this comment
   */
  public List<Token> getTokens() {
    return tokens;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Text) {
      Text that = (Text) o;
      return value.equals(that.value);
    }
    return false;
  }

  @Override
  public String toString() {
    return value;
  }
}
