package io.vertx.test.codegen;

import io.vertx.codegen.Helper;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelperTest {

  @Test
  public void testGetEmptyPackageName() {
    assertEquals("", Helper.getPackageName("SomeClass"));
  }

  @Test
  public void testExtractCommentTags() {
    String comment =
      " Interface comment line 1\n" +
      " Interface comment line 2\n" +
      " Interface comment line 3\n\n" +
      " @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";

    String tagVal = Helper.getJavadocTag(comment, "@author");
    assertEquals("<a href=\"http://tfox.org\">Tim Fox</a>", tagVal);
    tagVal = Helper.getJavadocTag(comment, "@version");
    assertEquals("12.2", tagVal);
    tagVal = Helper.getJavadocTag(comment, "@see");
    assertEquals("io.vertx.codegen.testmodel.TestInterface", tagVal);
  }

  @Test
  public void removeTags1() {
    String comment =
      " Interface comment line 1\n" +
      " Interface comment line 2\n" +
      " Interface comment line 3\n\n" +
      " @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";

    String removed = Helper.removeTags(comment);
    String removedExpected =
      " Interface comment line 1\n" +
        " Interface comment line 2\n" +
        " Interface comment line 3\n";
    assertEquals(removedExpected, removed);
  }

  @Test
  public void removeTags2() {
    String comment =
      " @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";

    String removed = Helper.removeTags(comment);
    String removedExpected = "";
    assertEquals(removedExpected, removed);
  }

  @Test
  public void removeTags3() {
    String comment =
      "    @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";

    String removed = Helper.removeTags(comment);
    String removedExpected = "";
    assertEquals(removedExpected, removed);
  }
  @Test
  public void removeTags4() {
    String comment =
      "X\n" +
      " @author <a href=\"http://tfox.org\">Tim Fox</a>\n" +
      " @version 12.2\n" +
      " @see io.vertx.codegen.testmodel.TestInterface\n";

    String removed = Helper.removeTags(comment);
    String removedExpected = "X";
    assertEquals(removedExpected, removed);
  }

  @Test
  public void testResolveClassSignature() throws Exception {
    Utils.assertProcess((processingEnv, roundEnv) -> {
      Element elt = Helper.resolveSignature(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),
          null,  "java.lang.Class");
      assertEquals(ElementKind.CLASS, elt.getKind());
      TypeElement typeElt = (TypeElement) elt;
      assertEquals("java.lang.Class", typeElt.getQualifiedName().toString());
    });
  }

  @Test
  public void testResolveFieldSignature() throws Exception {
    Utils.assertProcess((processingEnv, roundEnv) -> {
      Element elt = Helper.resolveSignature(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),
          null, "java.util.Locale#FRENCH");
      assertEquals(ElementKind.FIELD, elt.getKind());
      VariableElement varElt = (VariableElement) elt;
      TypeElement typeElt = (TypeElement) varElt.getEnclosingElement();
      assertEquals("java.util.Locale", typeElt.getQualifiedName().toString());
      assertEquals("FRENCH", varElt.getSimpleName().toString());
    });
  }

  private void assertSignature(ProcessingEnvironment processingEnv, String signature, String className, String methodName, String... parameterTypes) {
    assertSignature(processingEnv, null, signature, className, methodName, parameterTypes);
  }

  private void assertSignature(ProcessingEnvironment processingEnv, TypeElement declaringElt, String signature, String className, String methodName, String... parameterTypes) {
    Element elt = Helper.resolveSignature(processingEnv.getElementUtils(), processingEnv.getTypeUtils(), declaringElt, signature);
    assertEquals(ElementKind.METHOD, elt.getKind());
    ExecutableElement methodElt = (ExecutableElement) elt;
    TypeElement typeElt = (TypeElement) methodElt.getEnclosingElement();
    assertEquals(className, typeElt.getQualifiedName().toString());
    assertEquals(methodName, methodElt.getSimpleName().toString());
    assertEquals(parameterTypes.length, methodElt.getParameters().size());
    for (int i = 0;i < parameterTypes.length;i++) {
      assertEquals(parameterTypes[i], methodElt.getParameters().get(i).asType().toString());
    }
  }

  @Test
  public void testResolveMethodSignature() throws Exception {
    Utils.assertProcess((processingEnv, roundEnv) -> {
      assertSignature(processingEnv, "java.util.Locale#createConstant", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, "java.util.Locale#createConstant(String,String)", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, "java.util.Locale#createConstant(java.lang.String,java.lang.String)", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, "java.util.List#containsAll", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, "java.util.List#containsAll(Collection)", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, "java.util.List#containsAll(java.util.Collection)", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, "java.util.List#get", "java.util.List", "get", "int");
      assertSignature(processingEnv, "java.util.List#get(int)", "java.util.List", "get", "int");
      assertSignature(processingEnv, "java.util.List#toArray(Object[])", "java.util.List", "toArray", "T[]");
    });
  }

  @Test
  public void testResolveRelativeMethodSignature() throws Exception {
    Utils.assertProcess((processingEnv, roundEnv) -> {
      TypeElement localeElt = processingEnv.getElementUtils().getTypeElement("java.util.Locale");
      TypeElement listElt = processingEnv.getElementUtils().getTypeElement("java.util.List");
      assertSignature(processingEnv, localeElt, "#createConstant", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, localeElt, "#createConstant(String,String)", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, localeElt, "#createConstant(java.lang.String,java.lang.String)", "java.util.Locale", "createConstant", "java.lang.String", "java.lang.String");
      assertSignature(processingEnv, listElt, "#containsAll", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, listElt, "#containsAll(Collection)", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, listElt, "#containsAll(java.util.Collection)", "java.util.List", "containsAll", "java.util.Collection<?>");
      assertSignature(processingEnv, listElt, "#get", "java.util.List", "get", "int");
      assertSignature(processingEnv, listElt, "#get(int)", "java.util.List", "get", "int");
      assertSignature(processingEnv, listElt, "#parallelStream()", "java.util.Collection", "parallelStream");
    });
  }

  @Test
  public void testUnresolveSignatures() throws Exception {
    Utils.assertProcess((processingEnv, roundEnv) -> {
      String[] signatures = {
          "java.util.Locale#createConstant(int,String)",
      };
      for (String signature : signatures) {
        Element elt = Helper.resolveSignature(processingEnv.getElementUtils(), processingEnv.getTypeUtils(), null, signature);
        assertNull(elt);
      }
    });
  }
}
