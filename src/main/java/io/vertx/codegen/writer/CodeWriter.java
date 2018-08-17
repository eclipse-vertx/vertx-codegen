package io.vertx.codegen.writer;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to simplify code write that maintains indentation status
 *
 * @author <a href="http://slinkydeveloper.github.io">Francesco Guardiani @slinkydeveloper</a>
 */
public class CodeWriter extends PrintWriter {

  private int indentSize = 2;
  private int indent = 0;
  private boolean first = true;

  public CodeWriter(Writer out) {
    super(out);
  }

  /**
   * @return the current underlying writer
   */
  public Writer writer() {
    return out;
  }

  /**
   * @return the current indentation size
   */
  public int indentSize() {
    return indentSize;
  }

  /**
   * Set the current indentation size, this can only be done when the current indentation is {@code 0}.
   *
   * @param size the new size
   * @return this object
   */
  public CodeWriter indentSize(int size) {
    if (size < 0) {
      throw new IllegalArgumentException();
    }
    indentSize = size;
    return this;
  }

  /**
   * @return the current indentation string
   */
  public String indentation() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0;i < indent * indentSize;i++) {
      sb.append(' ');
    }
    return sb.toString();
  }

  /**
   * Increment the current indentation.
   *
   * @return this object
   */
  public CodeWriter indent() {
    indent++;
    return this;
  }

  /**
   * Decrement the current indentation.
   *
   * @return this object
   */
  public CodeWriter unindent() {
    if (indent == 0) {
      throw new IllegalStateException();
    }
    indent--;
    return this;
  }

  /**
   * Append the {@code name} as a java package import.
   *
   * @return this object
   */
  public CodeWriter javaImport(String name) {
    return append("import ").append(name).append(";").newLine();
  }

  /**
   * Print the specified {@code string} with an indent prefix.
   *
   * This method forces the writer to be the first line
   *
   * @param string the string to print
   * @return this object
   */
  public CodeWriter code(String string) {
    if (!first) {
      println();
    }
    print(string);
    return this;
  }

  /**
   * Print the specified {@code line} with an indent prefix and end with a new line char.
   *
   * @param line the line to print
   * @return this object
   */
  public CodeWriter codeln(String line) {
    return code(line).newLine();
  }

  /**
   * This function prints an indentation before the {@code statement} and a semicolon followed by a newline
   * after.
   *
   * @param statement the statement
   * @return this object
   */
  public CodeWriter stmt(String statement) {
    return code(statement).append(";").newLine();
  }

  /**
   * Print a new line char.
   *
   * @return this object
   */
  public CodeWriter newLine() {
    println();
    return this;
  }

  /**
   * Write a {@code sequence} delimited by a {@code delimiter}.
   *
   * @return this object
   */
  public CodeWriter writeSeq(Stream<String> sequence, String delimiter) {
    return append(sequence.collect(Collectors.joining(delimiter)));
  }

  public void println() {
    print('\n');
  }

  @Override
  public void write(String s, int off, int len) {
    for (int i = 0;i < len;i++) {
      write(s.charAt(off + i));
    }
  }

  @Override
  public void write(int c) {
    if (c == '\n') {
      first = true;
    } else if (first) {
      first = false;
      for (int i = 0; i < indent * indentSize; i++) {
        super.write(' ');
      }
    }
    super.write(c);
  }

  @Override
  public CodeWriter append(CharSequence csq) {
    return (CodeWriter) super.append(csq);
  }

  @Override
  public CodeWriter append(CharSequence csq, int start, int end) {
    return (CodeWriter) super.append(csq, start, end);
  }

  @Override
  public CodeWriter append(char c) {
    return (CodeWriter) super.append(c);
  }
}
