package io.vertx.test.codegen;

import io.vertx.codegen.Helper;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelperTest {

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
}
