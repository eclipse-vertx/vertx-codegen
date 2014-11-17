package io.vertx.test.codegen;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Utils {

  static <E> HashSet<E> set(E... elements) {
    HashSet<E> set = new HashSet<>();
    Collections.addAll(set, elements);
    return set;
  }

}
