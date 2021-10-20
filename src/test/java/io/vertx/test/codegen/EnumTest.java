package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.test.codegen.testapi.jsonmapper.MyEnumWithCustomFactory;
import io.vertx.test.codegen.testapi.jsonmapper.WithMyCustomEnumWithMapper;
import io.vertx.test.codegen.testenum.EnumAsParam;
import io.vertx.test.codegen.testenum.InvalidEmptyEnum;
import io.vertx.test.codegen.testenum.ValidEnum;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumTest extends ClassTestBase {

  @Test
  public void testEnum() throws Exception {
    EnumModel model = new GeneratorHelper().generateEnum(ValidEnum.class);
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
  public void testJsonMapper() throws Exception {
    ClassModel model = new GeneratorHelper()
      .registerConverter(MyEnumWithCustomFactory.class, WithMyCustomEnumWithMapper.class.getName(), "serializeMyEnumWithCustomFactory")
      .registerConverter(MyEnumWithCustomFactory.class, WithMyCustomEnumWithMapper.class.getName(), "deserializeMyEnumWithCustomFactory")
      .generateClass(WithMyCustomEnumWithMapper.class);
    assertFalse(model.getAnnotations().isEmpty());
    assertEquals(1, model.getAnnotations().size());
    assertEquals(VertxGen.class.getName(), model.getAnnotations().get(0).getName());

    assertEquals(1, model.getReferencedDataObjectTypes().size());
    assertEquals("MyEnumWithCustomFactory", model.getReferencedDataObjectTypes().iterator().next().getSimpleName());

    checkMethod(model.getMethodMap().get("returnMyEnumWithCustomFactory").get(0),
        "returnMyEnumWithCustomFactory", 0, new TypeLiteral<MyEnumWithCustomFactory>() {}, MethodKind.OTHER);

    MethodInfo myPojoParam = model.getMethodMap().get("setMyEnumWithCustomFactory").get(0);
    checkMethod(myPojoParam, "setMyEnumWithCustomFactory", 1, "void", MethodKind.OTHER);
    checkParam(myPojoParam.getParam(0), "myEnum", MyEnumWithCustomFactory.class.getName(), ClassKind.ENUM);


  }

  @Test
  public void testInvalidEmptyEnum() throws Exception {
    try {
      new GeneratorHelper().generateEnum(InvalidEmptyEnum.class);
      fail();
    } catch (GenException ignore) {
    }
  }

  @Test
  public void testEnumListingFromApi() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(EnumAsParam.class);
    assertTrue(model.getReferencedEnumTypes().size() > 0);
    TypeInfo typeInfo = (TypeInfo) model.getReferencedEnumTypes().toArray()[0];
    assertTrue(typeInfo instanceof EnumTypeInfo);
    assertEquals("ValidEnum", typeInfo.getSimpleName());
  }
}
