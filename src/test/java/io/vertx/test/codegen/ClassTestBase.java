package io.vertx.test.codegen;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.TypeInfo;
import io.vertx.codegen.doc.Doc;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class ClassTestBase {

  enum MethodCheck {
    FLUENT,
    STATIC,
    CACHE_RETURN
  }

  void checkMethod(MethodInfo meth, String name, int numParams, TypeLiteral<?> returnType, MethodKind kind, MethodCheck... checks) {
    checkMethod(meth, name, numParams, returnType.type, kind, checks);
  }

  void checkMethod(MethodInfo meth, String name, int numParams, TypeLiteral<?> returnType, MethodKind kind, Doc comment, MethodCheck... checks) {
    checkMethod(meth, name, numParams, returnType.type, kind, comment, checks);
  }

  void checkMethod(MethodInfo meth, String name, int numParams, Type returnType, MethodKind kind, MethodCheck... checks) {
    checkMethod(meth, name, numParams, returnType.getTypeName().replaceAll(" ", ""), kind, checks);
  }

  void checkMethod(MethodInfo meth, String name, int numParams, Type returnType, MethodKind kind, Doc comment, MethodCheck... checks) {
    checkMethod(meth, name, numParams, returnType.getTypeName().replaceAll(" ", ""), kind, comment, checks);
  }

  void checkMethod(MethodInfo meth, String name, int numParams, String returnType, MethodKind kind, MethodCheck... checks) {
    checkMethod(meth, name, numParams, returnType, kind, null, checks);
  }

  void checkMethod(MethodInfo meth, String name, int numParams, String returnType, MethodKind kind, Doc comment, MethodCheck... checks) {
    EnumSet<MethodCheck> checkSet = EnumSet.noneOf(MethodCheck.class);
    Collections.addAll(checkSet, checks);
    assertEquals(name, meth.getName());
    if (comment != null) {
      assertNotNull(meth.getComment());
      assertEquals(comment.getFirstSentence(), meth.getDoc().getFirstSentence());
      assertEquals(comment.getBody(), meth.getDoc().getBody());
      assertEquals(comment.getBlockTags(), meth.getDoc().getBlockTags());
    } else {
      assertNull(meth.getComment());
    }
    assertEquals(kind, meth.getKind());
    assertEquals(returnType, meth.getReturnType().toString());
    assertEquals(checkSet.contains(MethodCheck.CACHE_RETURN), meth.isCacheReturn());
    assertEquals(checkSet.contains(MethodCheck.FLUENT), meth.isFluent());
    assertEquals(checkSet.contains(MethodCheck.STATIC), meth.isStaticMethod());
    assertEquals(numParams, meth.getParams().size());
  }

  void checkParam(ParamInfo param, String name, TypeLiteral<?> type) {
    checkParam(param, name, type.type);
  }

  void checkParam(ParamInfo param, String name, Type expectedType) {
    assertEquals(name, param.getName());
    TypeInfo expectedTypeInfo = TypeInfo.create(expectedType);
    assertEquals(expectedTypeInfo.getName(), param.getType().getName());
    assertEquals(expectedTypeInfo.getKind(), param.getType().getKind());
  }

  void assertGenInvalid(Class<?> c, Class<?>... rest) throws Exception {
    try {
      new Generator().generateClass(c, rest);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  void assertGenFail(Class<?> type, String msg) throws Exception {
    try {
      new Generator().generateClass(type);
      fail(msg);
    } catch (GenException e) {
      // pass
    }
  }
}
