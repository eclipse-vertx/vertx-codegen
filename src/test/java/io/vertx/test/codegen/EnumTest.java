package io.vertx.test.codegen;

import io.vertx.codegen.EnumModel;
import io.vertx.codegen.EnumValueInfo;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testenum.InvalidEmptyEnum;
import io.vertx.test.codegen.testenum.ValidEnum;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumTest {

  @Test
  public void testEnum() throws Exception {
    EnumModel model = new Generator().generateEnum(ValidEnum.class);
    assertEquals(Arrays.asList("RED", "GREEN", "BLUE"), model.getValues().stream().
        map(EnumValueInfo::getIdentifier).
        collect(Collectors.toList()));
    assertEquals(Arrays.asList("RED doc", "GREEN doc", "BLUE doc"), model.getValues().stream().
        map(e -> e.getDoc().toString()).
        collect(Collectors.toList()));
    assertEquals("enum", model.getKind());
    assertEquals("ValidEnum doc", model.getDoc().toString());
    assertEquals(ValidEnum.class.getName(), model.getFqn());
    assertEquals("dummy", model.getModule().getName());
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
