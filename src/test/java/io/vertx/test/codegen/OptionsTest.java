package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testapi.OptionsInterface;
import io.vertx.test.codegen.testapi.OptionsWithJsonConstructor;
import io.vertx.test.codegen.testapi.OptionsWithNoJsonConstructor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsTest {

  @Test
  public void testOptionsWithNoJsonConstructor() throws Exception {
    try {
      new Generator().checkOptions(OptionsWithNoJsonConstructor.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testOptionsWithJsonConstructor() throws Exception {
    new Generator().checkOptions(OptionsWithJsonConstructor.class);
  }

  @Test
  public void testOptionsInterface() throws Exception {
    new Generator().checkOptions(OptionsInterface.class);
  }
}
