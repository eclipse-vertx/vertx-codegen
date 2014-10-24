package io.vertx.test.codegen;

import io.vertx.codegen.ClassKind;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.ProxyMethodInfo;
import io.vertx.codegen.TypeInfo;
import io.vertx.test.codegen.proxytestapi.InvalidOverloaded;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ProxyTest {

  // Test invalid stuff
  // ------------------

  // Invalid classes

  @Test
  public void testInvalidOverloaded() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidOverloaded.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
      e.printStackTrace();
    }
  }


  private void checkMethod(ProxyMethodInfo meth, String name, String comment, MethodKind kind, String returnType, boolean cacheReturn,
                           boolean fluent, boolean staticMethod, int numParams, boolean proxyIgnore) {

    assertEquals(name, meth.getName());
    assertEquals(comment, meth.getComment());
    assertEquals(kind, meth.getKind());
    assertEquals(returnType, meth.getReturnType().toString());
    assertEquals(cacheReturn, meth.isCacheReturn());
    assertEquals(fluent, meth.isFluent());
    assertEquals(staticMethod, meth.isStaticMethod());
    assertEquals(numParams, meth.getParams().size());
    assertEquals(proxyIgnore, meth.isProxyIgnore());
  }

  private void checkParam(ParamInfo param, String name, String type) {
    assertEquals(name, param.getName());
    assertEquals(type, param.getType().toString());
  }

  private void checkClassParam(ParamInfo param, String name, String type, ClassKind kind) {
    checkParam(param, name, type);
    TypeInfo paramType;
    if (param.getType() instanceof TypeInfo.Parameterized) {
      paramType = param.getType().getRaw();
    } else {
      paramType = param.getType();
    }
    assertEquals(kind, paramType.getKind());
  }
}
