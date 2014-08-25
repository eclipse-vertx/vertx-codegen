package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testapi.OptionsClass;
import io.vertx.test.codegen.testapi.OptionsWithFactoryMethod;
import io.vertx.test.codegen.testapi.OptionsWithNoFactoryMethod;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsTest {

  @Test
  public void testOptionsWithNoFactoryMethod() throws Exception {
    try {
      new Generator().validateOption(OptionsWithNoFactoryMethod.class);
      fail();
    } catch (GenException e) {
    }
  }

  @Test
  public void testOptionsWithFactoryMethod() throws Exception {
    new Generator().validateOption(OptionsWithFactoryMethod.class);
  }

  @Test
  public void testOptionsClass() throws Exception {
    try {
      new Generator().validateOption(OptionsClass.class);
      fail();
    } catch (GenException e) {
    }
  }

}
