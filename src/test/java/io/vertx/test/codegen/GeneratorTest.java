package io.vertx.test.codegen;

import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testapi.NotInterface;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class GeneratorTest {

  private Generator gen = new Generator();

  @Test
  public void testGenerateNotInterface() throws Exception {
    try {
      gen.generateModel(NotInterface.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }
}
