package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.type.JsonifiableTypeInfo;
import io.vertx.codegen.type.PrimitiveTypeInfo;
import io.vertx.test.codegen.testjsoncodecs.APIInterfaceWithZonedDateTime;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JsonifiableTest extends ClassTestBase {

  // Test valid stuff
  // ----------------

  // Valid params

  @Test
  public void testValidBasicParams() throws Exception {
    ClassModel model = new GeneratorHelper().generateClass(APIInterfaceWithZonedDateTime.class);
    assertEquals(APIInterfaceWithZonedDateTime.class.getName(), model.getIfaceFQCN());
    assertEquals(APIInterfaceWithZonedDateTime.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(1, model.getMethods().size());
    String methodName = "doSomething";

    MethodInfo method = model.getMethods().get(0);
    checkMethod(method, methodName, 1, "void", MethodKind.OTHER);
    List<ParamInfo> params = method.getParams();
    assertTrue(params.get(0).getType() instanceof JsonifiableTypeInfo);
    assertEquals(ZonedDateTime.class.getName(), params.get(0).getType().getName());
  }

}
