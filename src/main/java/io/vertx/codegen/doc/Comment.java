package io.vertx.codegen.doc;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A structured comment, it can be evaluated as a stream of tokens.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Comment {

  final String value;
  final List<Token> tokens;

  public Comment(String value) {
    this.value = value;
    this.tokens = Token.tokenize(value);
  }

  private Comment(String value, List<Token> tokens) {
    this.value = value;
    this.tokens = tokens;
  }

  /**
   * @return the comment value
   */
  public String getValue() {
    return value;
  }

  /**
   * Returns a new comment with tokens mapped by the {@code mapper} function.
   *
   * @param mapping the mapping function
   * @return the new comment
   */
  Comment map(Function<Token, Token> mapping) {
    return new Comment(value, tokens.stream().map(mapping).collect(Collectors.toList()));
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
    if (o instanceof Comment) {
      Comment that = (Comment) o;
      return value.equals(that.value);
    }
    return false;
  }

  @Override
  public String toString() {
    return value;
  }
}
