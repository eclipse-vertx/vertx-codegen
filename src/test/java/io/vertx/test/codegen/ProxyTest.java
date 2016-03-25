package io.vertx.test.codegen;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ProxyMethodInfo;
import io.vertx.codegen.ProxyModel;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.test.codegen.proxytestapi.InvalidClose1;
import io.vertx.test.codegen.proxytestapi.InvalidClose2;
import io.vertx.test.codegen.proxytestapi.InvalidClose3;
import io.vertx.test.codegen.proxytestapi.InvalidOverloaded;
import io.vertx.test.codegen.proxytestapi.InvalidParams1;
import io.vertx.test.codegen.proxytestapi.InvalidParams2;
import io.vertx.test.codegen.proxytestapi.InvalidParams3;
import io.vertx.test.codegen.proxytestapi.InvalidParams4;
import io.vertx.test.codegen.proxytestapi.InvalidParamsDataObject;
import io.vertx.test.codegen.proxytestapi.InvalidReturn1;
import io.vertx.test.codegen.proxytestapi.InvalidReturn2;
import io.vertx.test.codegen.proxytestapi.InvalidReturn3;
import io.vertx.test.codegen.proxytestapi.ValidProxy;
import io.vertx.test.codegen.proxytestapi.ValidProxyCloseWithFuture;
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
    }
  }

  @Test
  public void testInvalidParams1() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidParams1.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidParams2() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidParams2.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidParams3() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidParams3.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidParams4() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidParams4.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidParamsDataObject() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidParamsDataObject.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidReturn1() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidReturn1.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidReturn2() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidReturn2.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidReturn3() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidReturn3.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testValid() throws Exception {
    ProxyModel model = new Generator().generateProxyModel(ValidProxy.class);
    assertEquals(ValidProxy.class.getName(), model.getIfaceFQCN());
    assertEquals(ValidProxy.class.getSimpleName(), model.getIfaceSimpleName());
    assertTrue(model.getSuperTypes().isEmpty());
    assertEquals(46, model.getMethods().size());

    // Not going to check all the types are correct as this is already tested in the VertxGen tests
    // but we do want to check the proxyIgnore flag is correctly set
    for (MethodInfo mi: model.getMethods()) {
      ProxyMethodInfo pmi = (ProxyMethodInfo)mi;
      if (pmi.getName().equals("ignored")) {
        assertTrue(pmi.isProxyIgnore());
      } else {
        assertFalse(pmi.isProxyIgnore());
      }
      if (pmi.getName().equals("closeIt")) {
        assertTrue(pmi.isProxyClose());
      } else {
        assertFalse(pmi.isProxyClose());
      }
    }
  }

  @Test
  public void testValidCloseWithFuture() throws Exception {
    ProxyModel model = new Generator().generateProxyModel(ValidProxyCloseWithFuture.class);
    assertEquals(1, model.getMethods().size());
    assertEquals(MethodKind.FUTURE, model.getMethods().get(0).getKind());
    ParameterizedTypeInfo handlerType = (ParameterizedTypeInfo) model.getMethods().get(0).getParams().get(0).getType();
    ParameterizedTypeInfo asyncResultType = (ParameterizedTypeInfo) handlerType.getArgs().get(0);
    assertEquals(ClassKind.VOID, asyncResultType.getArgs().get(0).getKind());
  }

  @Test
  public void testInvalidClose1() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidClose1.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidClose2() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidClose2.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  @Test
  public void testInvalidClose3() throws Exception {
    try {
      new Generator().generateProxyModel(InvalidClose3.class);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }
}
