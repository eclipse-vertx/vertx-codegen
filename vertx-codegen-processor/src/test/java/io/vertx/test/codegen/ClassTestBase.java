package io.vertx.test.codegen;

import io.vertx.codegen.*;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeReflectionFactory;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.doc.Doc;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

  void checkParam(ParamInfo param, String name, TypeLiteral<?> expectedType) {
    checkParam(param, name, expectedType.type);
  }

  void checkParam(ParamInfo param, String name, Type expectedType) {
    assertEquals(name, param.getName());
    TypeInfo expectedTypeInfo = TypeReflectionFactory.create(expectedType);
    assertEquals(expectedTypeInfo.getName(), param.getType().getName());
    assertEquals(expectedTypeInfo.getKind(), param.getType().getKind());
  }

  void checkParam(ParamInfo param, String name, String expectedTypeName, ClassKind expectedTypeKind) {
    assertEquals(name, param.getName());
    assertEquals(expectedTypeName.replaceAll(Pattern.quote(" "),  ""), param.getType().getName().replaceAll(Pattern.quote(" "),  ""));
    assertEquals(expectedTypeKind, param.getType().getKind());
  }

  void checkParam(ParamInfo param, String name, TypeLiteral<?> expectedType, TypeLiteral<?> expectedUnresolvedType) {
    checkParam(param, name, expectedType.type, expectedUnresolvedType.type);
  }

  void checkParam(ParamInfo param, String name, Type expectedType, Type expectedUnresolvedType) {
    checkParam(param, name ,expectedType);
    TypeInfo expectedUnresolvedTypeInfo = TypeReflectionFactory.create(expectedUnresolvedType);
    assertEquals(expectedUnresolvedTypeInfo.getName(), param.getUnresolvedType().getName());
    assertEquals(expectedUnresolvedTypeInfo.getKind(), param.getUnresolvedType().getKind());
  }

  void checkConstant(ConstantInfo param, String name, TypeLiteral<?> expectedType) {
    checkConstant(param, name, expectedType.type);
  }

  void checkConstant(ConstantInfo param, String name, Type expectedType) {
    TypeInfo expectedTypeInfo = TypeReflectionFactory.create(expectedType);
    checkConstant(param, name, expectedTypeInfo);
  }

  void checkConstant(ConstantInfo param, String name, TypeInfo expectedType) {
    assertEquals(name, param.getName());
    assertEquals(expectedType.getName(), param.getType().getName());
    assertEquals(expectedType.getKind(), param.getType().getKind());
  }

  void assertGenInvalid(Class<?> c, Class<?>... rest) throws Exception {
    try {
      new GeneratorHelper().generateClass(c, rest);
      fail("Should throw exception");
    } catch (GenException e) {
      // OK
    }
  }

  void assertGenValid(Class<?> c, Class<?>... rest) throws Exception {
    new GeneratorHelper().generateClass(c, rest);
  }

  void assertGenFail(Class<?> type, String msg) throws Exception {
    try {
      new GeneratorHelper().generateClass(type);
      fail(msg);
    } catch (GenException e) {
      // pass
    }
  }

  static void blacklist(Runnable test, Stream<Class<?>> classes) {
    Set<String> blacklist = new HashSet<>();
    classes.map(Class::getName).forEach(blacklist::add);
    Thread thread = Thread.currentThread();
    ClassLoader prev = thread.getContextClassLoader();
    thread.setContextClassLoader(new ClassLoader(prev) {
      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (blacklist.contains(name)) {
          throw new ClassNotFoundException();
        }
        return super.loadClass(name);
      }
    });
    try {
      test.run();
    } finally {
      thread.setContextClassLoader(prev);
    }
  }


  static <T> Set<T> set(T... values) {
    return new HashSet<T>(Arrays.asList(values));
  }
}
