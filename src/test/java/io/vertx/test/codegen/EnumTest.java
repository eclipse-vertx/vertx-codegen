package io.vertx.test.codegen;

import io.vertx.codegen.EnumModel;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testenum.InvalidEmptyEnum;
import io.vertx.test.codegen.testenum.ValidEnum;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumTest {

  @Test
  public void testEnum() throws Exception {
    EnumModel model = new Generator().generateEnum(ValidEnum.class);
    assertEquals(Arrays.asList("RED", "GREEN", "BLUE"), model.getValues());
    assertEquals("enum", model.getKind());
    assertEquals(ValidEnum.class.getName(), model.getFqn());
    assertEquals(null, model.getModule());
    assertTrue(model.getType().isGen());
  }

  @Test
  public void testInvalidEmptyEnum() throws Exception {
    try {
      new Generator().generateEnum(InvalidEmptyEnum.class);
      fail();
    } catch (GenException ignore) {
    }
  }
}
