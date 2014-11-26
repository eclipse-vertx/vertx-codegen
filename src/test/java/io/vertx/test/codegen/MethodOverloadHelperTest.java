/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.test.codegen;

import io.vertx.codegen.ClassKind;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.codegen.overloadcheck.SimpleMethod;
import io.vertx.codegen.overloadcheck.SimpleParam;
import io.vertx.codegen.testmodel.RefedInterface1;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodOverloadHelperTest {

  protected MethodOverloadChecker checker = new MethodOverloadChecker();

  @Test
  public void testAmbiguousMethods1() throws Exception {
    List<SimpleMethod> meths = new ArrayList<>();

    List<SimpleParam> params1 = new ArrayList<>();
    params1.add(new SimpleParam("arg0", ClassKind.JSON_ARRAY, JsonArray.class.getName()));
    params1.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth1 = new SimpleMethod("meth1", params1);

    meths.add(meth1);

    List<SimpleParam> params2 = new ArrayList<>();
    params2.add(new SimpleParam("arg0", ClassKind.LIST, List.class.getName()));
    params2.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth2 = new SimpleMethod("meth1", params2);

    meths.add(meth2);

    testAmbiguousMethods(meths);
  }

  @Test
  public void testAmbiguousMethodsArrayClash1() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_ARRAY, JsonArray.class.getName(), ClassKind.LIST, List.class.getName());
  }

  @Test
  public void testAmbiguousMethodsArrayClash2() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_ARRAY, JsonArray.class.getName(), ClassKind.SET, Set.class.getName());
  }

  @Test
  public void testAmbiguousMethodsArrayClash3() throws Exception {
    testAmbiguousMethods(ClassKind.LIST, List.class.getName(), ClassKind.SET, Set.class.getName());
  }

  @Test
  public void testAmbiguousMethodsArrayClash4() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_ARRAY, JsonArray.class.getName(), ClassKind.JSON_ARRAY, JsonArray.class.getName());
  }
  
  

  @Test
  public void testAmbiguousMethodsObjectClash1() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_OBJECT, JsonObject.class.getName(), ClassKind.OPTIONS, TestOptions.class.getName());
  }

  @Test
  public void testAmbiguousMethodsObjectClash2() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_OBJECT, JsonObject.class.getName(), ClassKind.MAP, Map.class.getName());
  }

  @Test
  public void testAmbiguousMethodsObjectClash3() throws Exception {
    testAmbiguousMethods(ClassKind.MAP, Map.class.getName(), ClassKind.OPTIONS, TestOptions.class.getName());
  }

  @Test
  public void testAmbiguousMethodsObjectClash4() throws Exception {
    testAmbiguousMethods(ClassKind.JSON_OBJECT, JsonObject.class.getName(), ClassKind.JSON_OBJECT, JsonObject.class.getName());
  }

  
  @Test
  public void testFunctionClash1() throws Exception {
    testAmbiguousMethods(ClassKind.HANDLER, Handler.class.getName(), ClassKind.HANDLER, Handler.class.getName());
  }

  @Test
  public void testVertxGenClash1() throws Exception {
    testAmbiguousMethods(ClassKind.API, RefedInterface1.class.getName(), ClassKind.API, RefedInterface1.class.getName());
  }

  @Test
  public void testStringClash1() throws Exception {
    testAmbiguousMethods(ClassKind.STRING, String.class.getName(), ClassKind.ENUM, TestEnum.class.getName());
  }

  @Test
  public void testStringClash2() throws Exception {
    testAmbiguousMethods(ClassKind.STRING, String.class.getName(), ClassKind.STRING, String.class.getName());
  }

  private void testNumberClash(ClassKind kind, String typeName) throws Exception {
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "long");
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "int");
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "double");
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "float");
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "short");
    testAmbiguousMethods(kind, typeName, ClassKind.PRIMITIVE, "byte");

    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Long");
    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Integer");
    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Double");
    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Float");
    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Short");
    testAmbiguousMethods(kind, typeName, ClassKind.BOXED_PRIMITIVE, "java.lang.Byte");
  }

  @Test
  public void testNumberClash1() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "long");
  }

  @Test
  public void testNumberClash2() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "int");
  }

  @Test
  public void testNumberClash3() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "short");
  }

  @Test
  public void testNumberClash4() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "float");
  }

  @Test
  public void testNumberClash5() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "double");
  }

  @Test
  public void testNumberClash6() throws Exception {
    testNumberClash(ClassKind.PRIMITIVE, "byte");
  }

  @Test
  public void testNumberClash7() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Long");
  }

  @Test
  public void testNumberClash8() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Integer");
  }

  @Test
  public void testNumberClash9() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Short");
  }

  @Test
  public void testNumberClash10() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Float");
  }

  @Test
  public void testNumberClash11() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Double");
  }

  @Test
  public void testNumberClash12() throws Exception {
    testNumberClash(ClassKind.BOXED_PRIMITIVE, "java.lang.Byte");
  }

  @Test
  public void testAmbiguousMethodsBooleanClash1() throws Exception {
    testAmbiguousMethods(ClassKind.PRIMITIVE, "boolean", ClassKind.PRIMITIVE, "boolean");
  }

  @Test
  public void testAmbiguousMethodsBooleanClash2() throws Exception {
    testAmbiguousMethods(ClassKind.BOXED_PRIMITIVE, "java.lang.Boolean", ClassKind.PRIMITIVE, "boolean");
  }


  @Test
  public void testAllClash1() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.STRING, String.class.getName());
  }

  @Test
  public void testAllClash2() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.PRIMITIVE, "int");
  }

  @Test
  public void testAllClash3() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.BOXED_PRIMITIVE, "java.lang.Integer");
  }

  @Test
  public void testAllClash4() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.API, Object.class.getName());
  }

  @Test
  public void testAllClash5() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.JSON_OBJECT, JsonObject.class.getName());
  }

  @Test
  public void testAllClash6() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.JSON_ARRAY, JsonArray.class.getName());
  }

  @Test
  public void testAllClash7() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.OPTIONS, TestOptions.class.getName());
  }

  @Test
  public void testAllClash8() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.ENUM, TestEnum.class.getName());
  }

  @Test
  public void testAllClash9() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.HANDLER, Handler.class.getName());
  }

  @Test
  public void testAllClash10() throws Exception {
    testAmbiguousMethods(ClassKind.OBJECT, TestEnum.class.getName(), ClassKind.OBJECT, TestEnum.class.getName());
  }



  private void testAmbiguousMethods(ClassKind kind1, String typeName1, ClassKind kind2, String typeName2) throws Exception {
    List<SimpleMethod> meths = new ArrayList<>();

    List<SimpleParam> params1 = new ArrayList<>();
    params1.add(new SimpleParam("arg0", kind1, typeName1));
    SimpleMethod meth1 = new SimpleMethod("meth1", params1);

    meths.add(meth1);

    List<SimpleParam> params2 = new ArrayList<>();
    params2.add(new SimpleParam("arg0", kind2, typeName2));
    SimpleMethod meth2 = new SimpleMethod("meth1", params2);

    meths.add(meth2);

    testAmbiguousMethods(meths);
  }

  @Test
  public void testMethodsOK1() throws Exception {
    List<SimpleMethod> meths = new ArrayList<>();

    List<SimpleParam> params1 = new ArrayList<>();
    params1.add(new SimpleParam("arg0", ClassKind.JSON_ARRAY, JsonArray.class.getName()));
    params1.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth1 = new SimpleMethod("meth1", params1);

    meths.add(meth1);

    List<SimpleParam> params2 = new ArrayList<>();
    params2.add(new SimpleParam("arg0", ClassKind.JSON_OBJECT, List.class.getName()));
    params2.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth2 = new SimpleMethod("meth1", params2);

    meths.add(meth2);

    testAmbiguousMethodsOK(meths);

  }

  @Test
  public void testMethodsOKDifferentPositions() throws Exception {
    List<SimpleMethod> meths = new ArrayList<>();

    // These would clash if it wasn't for params in different positions

    List<SimpleParam> params1 = new ArrayList<>();
    params1.add(new SimpleParam("arg0", ClassKind.STRING, String.class.getName()));
    params1.add(new SimpleParam("arg1", ClassKind.JSON_ARRAY, JsonArray.class.getName()));

    SimpleMethod meth1 = new SimpleMethod("meth1", params1);

    meths.add(meth1);

    List<SimpleParam> params2 = new ArrayList<>();
    params2.add(new SimpleParam("arg0", ClassKind.LIST, List.class.getName()));
    params2.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth2 = new SimpleMethod("meth1", params2);

    meths.add(meth2);

    testAmbiguousMethodsOK(meths);
  }

  @Test
  public void testMethodsOKDifferentNumbersOfParams() throws Exception {
    List<SimpleMethod> meths = new ArrayList<>();

    // These would clash if it wasn't for different numbers of params

    List<SimpleParam> params1 = new ArrayList<>();
    params1.add(new SimpleParam("arg0", ClassKind.JSON_ARRAY, JsonArray.class.getName()));
    params1.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    params1.add(new SimpleParam("arg2", ClassKind.PRIMITIVE, "long"));
    SimpleMethod meth1 = new SimpleMethod("meth1", params1);

    meths.add(meth1);

    List<SimpleParam> params2 = new ArrayList<>();
    params2.add(new SimpleParam("arg0", ClassKind.LIST, List.class.getName()));
    params2.add(new SimpleParam("arg1", ClassKind.STRING, String.class.getName()));
    SimpleMethod meth2 = new SimpleMethod("meth1", params2);

    meths.add(meth2);

    testAmbiguousMethodsOK(meths);

  }

  private void testAmbiguousMethods(List<SimpleMethod> meths) throws Exception {
    try {
      checker.checkAmbiguousSimple(meths);
      fail("should throw exception");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  private void testAmbiguousMethodsOK(List<SimpleMethod> meths) throws Exception {
    checker.checkAmbiguousSimple(meths);
  }

}
