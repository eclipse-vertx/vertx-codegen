package io.vertx.test.codegen;

import io.vertx.codegen.Helper;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.type.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testtype.*;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeInfoTest {

  private void doTest(Class<?> container, Consumer<Map<String, TypeInfo>> assertion) throws Exception {
    Map<String, TypeInfo> reflectedMap = Stream.of(container.getDeclaredMethods()).filter(m -> Modifier.isPublic(m.getModifiers())).
        collect(Collectors.toMap(Method::getName, m -> TypeReflectionFactory.create(m.getGenericReturnType())));
    assertion.accept(reflectedMap);
    Utils.assertProcess((proc, env) -> {
      TypeElement modelMap = proc.getElementUtils().getTypeElement(container.getName());
      TypeMirrorFactory factory = new TypeMirrorFactory(proc.getElementUtils(), proc.getTypeUtils());
      Map<String, TypeInfo> collect = modelMap.getEnclosedElements().stream().
          flatMap(Helper.FILTER_METHOD).
          filter(elt -> elt.getModifiers().contains(javax.lang.model.element.Modifier.PUBLIC)).
          collect(Collectors.toMap(m -> m.getSimpleName().toString(), m -> factory.create(m.getReturnType())));
      assertion.accept(collect);
    });
  }

  @Test
  public void testVoid() throws Exception {
    doTest(VoidHolder.class, map -> {
      VoidTypeInfo voidType = (VoidTypeInfo) map.get("voidType");
      assertEquals("void", voidType.getName());
      assertEquals("void", voidType.getSimpleName());
      assertEquals(ClassKind.VOID, voidType.getKind());
      assertClass(map.get("VoidType"), "java.lang.Void", ClassKind.VOID);
    });
  }

  @Test
  public void testApi() throws Exception {
    doTest(ApiHolder.class, map -> {
      ApiTypeInfo api = assertApi(map.get("api"), ApiObject.class.getName());
      assertEquals(ApiObject.class.getName(), api.getName());
      ParameterizedTypeInfo apiParameterizedByClass = assertParameterized(map.get("apiParameterizedByClass"), GenericInterface.class.getName() + "<java.lang.String>", ClassKind.API);
      assertClass(apiParameterizedByClass.getArg(0), "java.lang.String", ClassKind.STRING);
      ParameterizedTypeInfo apiParameterizedByClassTypeParam = assertParameterized(map.get("apiParameterizedByClassTypeParam"), GenericInterface.class.getName() + "<ClassTypeParam>", ClassKind.API);
      TypeParamInfo.Class classTypeParam = (TypeParamInfo.Class) assertTypeVariable(apiParameterizedByClassTypeParam.getArg(0), "ClassTypeParam").getParam();
      assertEquals("ClassTypeParam", classTypeParam.getName());
      ParameterizedTypeInfo apiParameterizedByMethodTypeParam = assertParameterized(map.get("apiParameterizedByMethodTypeParam"), GenericInterface.class.getName() + "<MethodTypeParam>", ClassKind.API);
      TypeParamInfo.Method methodTypeParam = (TypeParamInfo.Method) assertTypeVariable(apiParameterizedByMethodTypeParam.getArg(0), "MethodTypeParam").getParam();
      assertEquals("MethodTypeParam", methodTypeParam.getName());
    });
  }

  @Test
  public void testTypeParam() throws Exception {
    doTest(TypeParamHolder.class, map -> {
      TypeParamInfo.Class classTypeParam = (TypeParamInfo.Class) assertTypeVariable(map.get("classTypeParam"), "ClassTypeParam").getParam();
      assertEquals("ClassTypeParam", classTypeParam.getName());
      TypeParamInfo.Method methodTypeParam = (TypeParamInfo.Method) assertTypeVariable(map.get("methodTypeParam"), "MethodTypeParam").getParam();
      assertEquals("MethodTypeParam", methodTypeParam.getName());
    });
  }

  @Test
  public void testClass() throws Exception {
    doTest(OtherHolder.class, map -> {
      assertClass(map.get("classType"), "java.util.Locale", ClassKind.OTHER);
      assertClass(map.get("interfaceType"), "java.lang.Runnable", ClassKind.OTHER);
      assertClass(map.get("genericInterface"), "java.util.concurrent.Callable", ClassKind.OTHER);
      assertClass(assertParameterized(map.get("interfaceParameterizedByInterface"), "java.util.concurrent.Callable<java.lang.Runnable>", ClassKind.OTHER).getArg(0), "java.lang.Runnable", ClassKind.OTHER);
      assertClass(assertParameterized(map.get("interfaceParameterizedByClass"), "java.util.concurrent.Callable<java.util.Locale>", ClassKind.OTHER).getArg(0), "java.util.Locale", ClassKind.OTHER);
      assertTypeVariable(assertParameterized(map.get("interfaceParameterizedByClassTypeParam"), "java.util.concurrent.Callable<ClassTypeParam>", ClassKind.OTHER).getArg(0), "ClassTypeParam");
      assertTypeVariable(assertParameterized(map.get("interfaceParameterizedByMethodTypeParam"), "java.util.concurrent.Callable<MethodTypeParam>", ClassKind.OTHER).getArg(0), "MethodTypeParam");
      assertClass(assertParameterized(map.get("classParameterizedByInterface"), "java.util.Vector<java.lang.Runnable>", ClassKind.OTHER).getArg(0), "java.lang.Runnable", ClassKind.OTHER);
      assertClass(assertParameterized(map.get("classParameterizedByClass"), "java.util.Vector<java.util.Locale>", ClassKind.OTHER).getArg(0), "java.util.Locale", ClassKind.OTHER);
      assertTypeVariable(assertParameterized(map.get("classParameterizedByClassTypeParam"), "java.util.Vector<ClassTypeParam>", ClassKind.OTHER).getArg(0), "ClassTypeParam");
      assertTypeVariable(assertParameterized(map.get("classParameterizedByMethodTypeParam"), "java.util.Vector<MethodTypeParam>", ClassKind.OTHER).getArg(0), "MethodTypeParam");
    });
  }

  @Test
  public void testHandler() throws Exception {
    doTest(HandlerHolder.class, map -> {
      assertClass(assertHandler(map.get("handlerVoid"), "java.lang.Void"), "java.lang.Void", ClassKind.VOID);
      assertClass(assertHandler(map.get("handlerString"), "java.lang.String"), "java.lang.String", ClassKind.STRING);
      assertClass(assertParameterized(assertHandler(map.get("handlerListString"), "java.util.List<java.lang.String>"), "java.util.List<java.lang.String>", ClassKind.LIST).getArg(0), "java.lang.String", ClassKind.STRING);
      assertApi(assertParameterized(assertHandler(map.get("handlerListApi"), "java.util.List<" + ApiObject.class.getName() + ">"), "java.util.List<" + ApiObject.class.getName() + ">", ClassKind.LIST).getArg(0), ApiObject.class.getName());
      assertTypeVariable(assertParameterized(assertHandler(map.get("handlerParameterizedByClassTypeParam"), GenericInterface.class.getName() + "<ClassTypeParam>"), GenericInterface.class.getName() + "<ClassTypeParam>", ClassKind.API).getArg(0), "ClassTypeParam");
      assertTypeVariable(assertParameterized(assertHandler(map.get("handlerParameterizedByMethodTypeParam"), GenericInterface.class.getName() + "<MethodTypeParam>"), GenericInterface.class.getName() + "<MethodTypeParam>", ClassKind.API).getArg(0), "MethodTypeParam");
    });
  }

  @Test
  public void testAsync() throws Exception {
    doTest(AsyncHolder.class, map -> {
      assertClass(assertAsync(map.get("asyncVoid"), "java.lang.Void"), "java.lang.Void", ClassKind.VOID);
      assertClass(assertAsync(map.get("asyncString"), "java.lang.String"), "java.lang.String", ClassKind.STRING);
      assertClass(assertParameterized(assertAsync(map.get("asyncListString"), "java.util.List<java.lang.String>"), "java.util.List<java.lang.String>", ClassKind.LIST).getArg(0), "java.lang.String", ClassKind.STRING);
      assertApi(assertParameterized(assertAsync(map.get("asyncListApi"), "java.util.List<" + ApiObject.class.getName() + ">"), "java.util.List<" + ApiObject.class.getName() + ">", ClassKind.LIST).getArg(0), ApiObject.class.getName());
      assertTypeVariable(assertParameterized(assertAsync(map.get("asyncParameterizedByClassTypeParam"), GenericInterface.class.getName() + "<ClassTypeParam>"), GenericInterface.class.getName() + "<ClassTypeParam>", ClassKind.API).getArg(0), "ClassTypeParam");
      assertTypeVariable(assertParameterized(assertAsync(map.get("asyncParameterizedByMethodTypeParam"), GenericInterface.class.getName() + "<MethodTypeParam>"), GenericInterface.class.getName() + "<MethodTypeParam>", ClassKind.API).getArg(0), "MethodTypeParam");
    });
  }

  @Test
  public void testDataObject() throws Exception {
    doTest(DataObjectHolder.class, map -> {
      assertDataObject(map.get("dataObject"), TestDataObject.class.getName(), ClassKind.OTHER);
    });
  }

  @Test
  public void testJson() throws Exception {
    doTest(JsonHolder.class, map -> {
      assertClass(map.get("jsonObject"), JsonObject.class.getName(), ClassKind.JSON_OBJECT);
      assertClass(map.get("jsonArray"), JsonArray.class.getName(), ClassKind.JSON_ARRAY);
    });
  }

  @Test
  public void testThrowable() throws Exception {
    doTest(ThrowableHolder.class, map -> {
      assertClass(map.get("throwable"), "java.lang.Throwable", ClassKind.THROWABLE);
    });
  }

  @Test
  public void testBasic() throws Exception {
    doTest(BasicHolder.class, map -> {
      assertPrimitive(map.get("booleanType"), "boolean");
      assertPrimitive(map.get("byteType"), "byte");
      assertPrimitive(map.get("shortType"), "short");
      assertPrimitive(map.get("intType"), "int");
      assertPrimitive(map.get("longType"), "long");
      assertPrimitive(map.get("floatType"), "float");
      assertPrimitive(map.get("doubleType"), "double");
      assertPrimitive(map.get("charType"), "char");
      assertClass(map.get("BooleanType"), "java.lang.Boolean", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("ShortType"), "java.lang.Short", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("IntegerType"), "java.lang.Integer", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("LongType"), "java.lang.Long", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("FloatType"), "java.lang.Float", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("DoubleType"), "java.lang.Double", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("CharacterType"), "java.lang.Character", ClassKind.BOXED_PRIMITIVE);
      assertClass(map.get("StringType"), "java.lang.String", ClassKind.STRING);
    });
  }

  private PrimitiveTypeInfo assertPrimitive(TypeInfo type, String expectedName) {
    assertEquals(PrimitiveTypeInfo.class, type.getClass());
    PrimitiveTypeInfo primitiveType = (PrimitiveTypeInfo) type;
    assertEquals(ClassKind.PRIMITIVE, primitiveType.getKind());
    assertEquals(expectedName, primitiveType.getName());
    return primitiveType;
  }

  private TypeVariableInfo assertTypeVariable(TypeInfo type, String expectedName) {
    assertEquals(TypeVariableInfo.class, type.getClass());
    TypeVariableInfo classType = (TypeVariableInfo) type;
    assertEquals(ClassKind.OBJECT, classType.getKind());
    assertEquals(expectedName, classType.getName());
    return classType;
  }

  private ClassTypeInfo assertClass(TypeInfo type, String expectedName, ClassKind expectedKind) {
    assertEquals(ClassTypeInfo.class, type.getClass());
    ClassTypeInfo classType = (ClassTypeInfo) type;
    assertEquals(expectedKind, classType.getKind());
    assertEquals(expectedName, classType.getName());
    return classType;
  }

  private ApiTypeInfo assertApi(TypeInfo type, String expectedName) {
    assertEquals(ApiTypeInfo.class, type.getClass());
    ApiTypeInfo apiType = (ApiTypeInfo) type;
    assertEquals(ClassKind.API, apiType.getKind());
    assertEquals(expectedName, apiType.getName());
    return apiType;
  }

  private DataObjectInfo assertDataObject(TypeInfo type, String expectedName, ClassKind expectedKind) {
    DataObjectInfo dataObject = type.getDataObject();
    assertNotNull(dataObject);
    assertEquals(expectedKind, type.getKind());
    assertEquals(expectedName, type.getName());
    return dataObject;
  }

  private ParameterizedTypeInfo assertParameterized(TypeInfo type, String expectedName, ClassKind expectedKind) {
    assertEquals(ParameterizedTypeInfo.class, type.getClass());
    ParameterizedTypeInfo parameterizedType = (ParameterizedTypeInfo) type;
    assertEquals(expectedKind, parameterizedType.getKind());
    assertEquals(expectedName, parameterizedType.getName());
    return parameterizedType;
  }

  private TypeInfo assertAsync(TypeInfo type, String name) {
    String asyncName = "io.vertx.core.AsyncResult<" + name + ">";
    ParameterizedTypeInfo handlerType = assertParameterized(assertHandler(type, asyncName), asyncName, ClassKind.ASYNC_RESULT);
    ParameterizedTypeInfo resultType = assertParameterized(handlerType, asyncName, ClassKind.ASYNC_RESULT);
    return resultType.getArg(0);
  }

  private TypeInfo assertHandler(TypeInfo type, String name) {
    String handlerName = "io.vertx.core.Handler<" + name + ">";
    ParameterizedTypeInfo handlerType = assertParameterized(type, handlerName, ClassKind.HANDLER);
    return handlerType.getArg(0);
  }

  @Test
  public void testCollection() throws Exception {
    doTest(CollectionHolder.class, map -> {
      String[] colTypes = { "list", "set", "map" };
      ClassKind[] colKinds = { ClassKind.LIST, ClassKind.SET, ClassKind.MAP };
      List<List<String>> colTypeParams = Arrays.asList(
          Collections.singletonList("E"),
          Collections.singletonList("E"),
          Arrays.asList("K", "V"));
      int[] typeParamIndexes = {0, 0, 1};
      for (int idx = 0;idx < colKinds.length;idx++) {
        String colType = colTypes[idx];
        ClassKind colKind = colKinds[idx];
        ClassTypeInfo col = (ClassTypeInfo) map.get(colType);
        int typeParamIndex = typeParamIndexes[idx];
        assertEquals(colKind, col.getKind());
        assertEquals(ClassTypeInfo.class, col.getClass());
        assertEquals(colTypeParams.get(idx), col.getParams().stream().map(TypeParamInfo::getName).collect(Collectors.toList()));
        ParameterizedTypeInfo ofString = (ParameterizedTypeInfo) map.get(colType + "OfString");
        assertEquals(colKind, ofString.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofString.getClass());
        assertEquals(col, ofString.getRaw());
        assertEquals(map.get("String"), ofString.getArg(typeParamIndex));
        ParameterizedTypeInfo ofClassTypeParam = (ParameterizedTypeInfo) map.get(colType + "OfClassTypeParam");
        assertEquals(colKind, ofClassTypeParam.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofClassTypeParam.getClass());
        assertEquals(col, ofClassTypeParam.getRaw());
        assertEquals(map.get("ClassTypeParam"), ofClassTypeParam.getArg(typeParamIndex));
        ParameterizedTypeInfo ofMethodTypeParam = (ParameterizedTypeInfo) map.get(colType + "OfMethodTypeParam");
        assertEquals(colKind, ofMethodTypeParam.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofMethodTypeParam.getClass());
        assertEquals(col, ofMethodTypeParam.getRaw());
        assertEquals(1 + typeParamIndex, ofMethodTypeParam.getArgs().size());
        TypeParamInfo.Method methodTypeParam = (TypeParamInfo.Method) ((TypeVariableInfo) ofMethodTypeParam.getArg(typeParamIndex)).getParam();
        assertEquals("MethodTypeParam", methodTypeParam.getName());
        ParameterizedTypeInfo ofDataObject = (ParameterizedTypeInfo) map.get(colType + "OfDataObject");
        assertEquals(colKind, ofDataObject.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofDataObject.getClass());
        assertEquals(map.get("DataObject"), ofDataObject.getArg(typeParamIndex));
        ParameterizedTypeInfo ofJsonObject = (ParameterizedTypeInfo) map.get(colType + "OfJsonObject");
        assertEquals(colKind, ofJsonObject.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofJsonObject.getClass());
        assertEquals(map.get("JsonObject"), ofJsonObject.getArg(typeParamIndex));
        ParameterizedTypeInfo ofJsonArray = (ParameterizedTypeInfo) map.get(colType + "OfJsonArray");
        assertEquals(colKind, ofJsonArray.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofJsonArray.getClass());
        assertEquals(map.get("JsonArray"), ofJsonArray.getArg(typeParamIndex));
        ParameterizedTypeInfo ofEnum = (ParameterizedTypeInfo) map.get(colType + "OfEnum");
        assertEquals(colKind, ofEnum.getKind());
        assertEquals(ParameterizedTypeInfo.class, ofEnum.getClass());
        assertEquals(map.get("Enum"), ofEnum.getArg(typeParamIndex));
      }
    });
  }

  @Test
  public void testEnum() throws Exception {
    doTest(EnumHolder.class, map -> {
      EnumTypeInfo apiEnum = (EnumTypeInfo) map.get("apiEnum");
      assertEquals(ClassKind.ENUM, apiEnum.getKind());
      assertEquals(Arrays.asList("RED", "GREEN", "BLUE"), apiEnum.getValues());
      assertTrue(apiEnum.isGen());
      EnumTypeInfo otherEnum = (EnumTypeInfo) map.get("otherEnum");
      assertEquals(ClassKind.ENUM, otherEnum.getKind());
      assertEquals(Arrays.asList("NANOSECONDS", "MICROSECONDS", "MILLISECONDS", "SECONDS", "MINUTES", "HOURS", "DAYS"), otherEnum.getValues());
      assertFalse(otherEnum.isGen());
    });
  }

  @Test
  public void testGetErased() {
    abstract class Container<M> implements AsyncResult<List<M>>  {}
    abstract class Expected implements AsyncResult<List<Object>>  {}
    ParameterizedTypeInfo info = (ParameterizedTypeInfo) TypeReflectionFactory.create(Container.class.getGenericInterfaces()[0]);
    ParameterizedTypeInfo expected = (ParameterizedTypeInfo) TypeReflectionFactory.create(Expected.class.getGenericInterfaces()[0]);
    assertEquals(expected, info.getErased());
  }
}
