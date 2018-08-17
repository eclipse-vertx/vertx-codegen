package io.vertx.test.codegen.writer;

import io.vertx.codegen.writer.CodeWriter;
import org.junit.Test;

import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * @author <a href="http://slinkydeveloper.github.io">Francesco Guardiani @slinkydeveloper</a>
 */
public class CodeWriterTest {

  @Test
  public void testUnderlyingWriter() {
    StringWriter w = new StringWriter();
    CodeWriter writer = new CodeWriter(w);
    assertSame(w, writer.writer());
  }

  @Test
  public void testIndentation(){
    CodeWriter writer = assertWriter(w ->
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
    assertEquals(2, writer.indentSize());
  }

  @Test
  public void testIndentationFactor(){
    CodeWriter writer = assertWriter(w -> {
      w.indentSize(4);
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
    assertEquals(4, writer.indentSize());
  }

  @Test
  public void testStmt(){
    CodeWriter writer = assertWriter(w ->
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
    CodeWriter writer = assertWriter(w ->
        w.javaImport("io.vertx.test.codegen.utils.CodeWriterTest"),
      "import io.vertx.test.codegen.utils.CodeWriterTest;\n"
    );
  }

  @Test
  public void testArray(){
    assertWriter(w ->
      w.writeSeq(Stream.of(2, 4, 1, 5, 6, 1).map(Object::toString), ", "),
      "2, 4, 1, 5, 6, 1"
    );
  }

  @Test
  public void testCodeForceNewLine() {
    assertWriter(w -> {
        w.indent().print("foo");
        w.code("bar");
      },
      "  foo\n" +
        "  bar"
    );
  }

  @Test
  public void testPrint1() {
    assertWriter(w -> {
        w.indent().print("foo");
      },
      "  foo"
    );
  }

  @Test
  public void testPrint2() {
    assertWriter(w -> {
        w.indent().print("foo");
        w.print("bar");
      },
      "  foobar"
    );
  }

  @Test
  public void testPrintNewLine() {
    assertWriter(w -> {
        w.indent().print("foo\nbar");
      },
      "  foo\n  bar"
    );
  }

  private CodeWriter assertWriter(Consumer<CodeWriter> codeToExecute, String expectedOutput) {
    StringWriter w = new StringWriter();
    CodeWriter codeWriter = new CodeWriter(w);
    codeToExecute.accept(codeWriter);
    assertEquals(expectedOutput, w.toString());
    return codeWriter;
  }
}
