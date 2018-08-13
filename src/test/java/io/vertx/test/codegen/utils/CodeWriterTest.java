package io.vertx.test.codegen.utils;

import io.vertx.codegen.utils.CodeWriter;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="http://slinkydeveloper.github.io">Francesco Guardiani @slinkydeveloper</a>
 */
public class CodeWriterTest {

  private CodeWriter testCodeWriter(Consumer<CodeWriter> codeToExecute, String expectedOutput) {
    StringWriter w = new StringWriter();
    CodeWriter codeWriter = new CodeWriter(w);
    codeToExecute.accept(codeWriter);
    assertEquals(expectedOutput, w.toString());
    return codeWriter;
  }

  @Test
  public void testIndentation(){
    CodeWriter writer = testCodeWriter(w ->
      w.codeln("line 1")
        .indent()
          .codeln("line 2")
          .codeln("line 3")
          .indent()
            .codeln("line 4")
          .unindent()
        .unindent()
        .code("line 5"),
      "line 1\n" +
        "  line 2\n" +
        "  line 3\n" +
        "    line 4\n" +
        "line 5"
    );
    assertEquals("", writer.indentation());
    assertEquals(2, writer.getIndentationFactor());
  }

  @Test
  public void testIndentationFactor(){
    CodeWriter writer = testCodeWriter(w -> {
      w.setIndentationFactor(4);
        w.codeln("line 1")
          .indent()
          .codeln("line 2")
          .codeln("line 3")
          .indent()
          .codeln("line 4")
          .unindent()
          .unindent()
          .code("line 5");
      },
      "line 1\n" +
        "    line 2\n" +
        "    line 3\n" +
        "        line 4\n" +
        "line 5"
    );
    assertEquals("", writer.indentation());
    assertEquals(4, writer.getIndentationFactor());
  }

  @Test
  public void testStmt(){
    CodeWriter writer = testCodeWriter(w ->
        w.stmt("line 1")
          .indent()
          .stmt("line 2")
          .stmt("line 3")
          .indent()
          .stmt("line 4")
          .unindent()
          .unindent()
          .stmt("line 5"),
      "line 1;\n" +
        "  line 2;\n" +
        "  line 3;\n" +
        "    line 4;\n" +
        "line 5;\n"
    );
  }

  @Test
  public void testJavaImport(){
    CodeWriter writer = testCodeWriter(w ->
        w.writeJavaImport("io.vertx.test.codegen.utils.CodeWriterTest"),
      "import io.vertx.test.codegen.utils.CodeWriterTest;\n"
    );
  }

  @Test
  public void testArray(){
    testCodeWriter(w ->
      w.writeArray(", ", Arrays.asList(2, 4, 1, 5, 6, 1), Object::toString),
      "2, 4, 1, 5, 6, 1"
    );
  }

}
