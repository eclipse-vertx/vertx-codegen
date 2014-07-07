package io.vertx.test.codegen;

import io.vertx.codegen.Generator;
import io.vertx.test.codegen.testapi.InterfaceWithNoMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNoNotIgnoredMethods;
import io.vertx.test.codegen.testapi.InterfaceWithNonVertxGenSupertype;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectParam;
import io.vertx.test.codegen.testapi.MethodWithJavaDotObjectReturn;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandler;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectInHandlerAsyncResult;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectParam;
import io.vertx.test.codegen.testapi.MethodWithNotVertxGenObjectReturn;
import io.vertx.test.codegen.testapi.NestedInterface;
import io.vertx.test.codegen.testapi.NoVertxGen;
import io.vertx.test.codegen.testapi.NotInterface;
import io.vertx.test.codegen.testapi.OverloadedMethodsInWrongOrder;
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

  @Test
  public void testGenerateNoVertxGenAnnotation() throws Exception {
    try {
      gen.generateModel(NoVertxGen.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateNestedInterfaces() throws Exception {
    try {
      gen.generateModel(NestedInterface.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateInterfaceWithNoMethods() throws Exception {
    try {
      gen.generateModel(InterfaceWithNoMethods.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateInterfaceWithNoNotIgnoredMethods() throws Exception {
    try {
      gen.generateModel(InterfaceWithNoNotIgnoredMethods.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectParam() throws Exception {
    try {
      gen.generateModel(MethodWithJavaDotObjectParam.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectReturn() throws Exception {
    try {
      gen.generateModel(MethodWithJavaDotObjectReturn.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandler() throws Exception {
    try {
      gen.generateModel(MethodWithJavaDotObjectInHandler.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithJavaDotObjectInHandlerAsyncResult() throws Exception {
    try {
      gen.generateModel(MethodWithJavaDotObjectInHandlerAsyncResult.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenParam() throws Exception {
    try {
      gen.generateModel(MethodWithNotVertxGenObjectParam.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenReturn() throws Exception {
    try {
      gen.generateModel(MethodWithNotVertxGenObjectReturn.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandler() throws Exception {
    try {
      gen.generateModel(MethodWithNotVertxGenObjectInHandler.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testGenerateMethodWithNonVertxGenInHandlerAsyncResult() throws Exception {
    try {
      gen.generateModel(MethodWithNotVertxGenObjectInHandlerAsyncResult.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testOverloadedMethodsInWrongOrder() throws Exception {
    try {
      gen.generateModel(OverloadedMethodsInWrongOrder.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testSuperTypeNotVertxGen() throws Exception {
    try {
      gen.generateModel(InterfaceWithNonVertxGenSupertype.class);
      fail("Should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  /*
  TODO:

  Things that shouldn't work:

  1. void method marked as Fluent
  2. void method marked as CacheReturn




  Things that should work

  1. Method params of all primitive types - both boxed and not, plus String
  2. Method params with other VertxGen classes

  3. Return of all primitive types - both boxed and not, plus String
  4. Return with other VertxGen classes

  5. Handler of all primitive types - both boxed and not, plus String
  6. Handler with other VertxGen classes

  7. Handler<AsyncResult> of all primitive types - both boxed and not, plus String
  8. Handler<AsyncResult> with other VertxGen classes

  9. java.lang.Object in param

  10. VertxIgnore

  11, static methods

  12. squashed methods

  13. Referenced types: Specified in return types or Handler param or Handler<AsyncResult> param

  14. Super types

  15. Handler or handler<AsyncResult> where the type param itself is generic:
  e.g. Handler<AsyncResult<Message<T>> - make sure referenced type is Message<T>

  16. void return

  17. @Fluent

  18. @CacheReturn

  19. @IndexSetter

  20. @IndexGetter







   */




}
