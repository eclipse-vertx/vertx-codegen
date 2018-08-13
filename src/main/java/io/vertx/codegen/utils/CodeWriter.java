package io.vertx.codegen.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Function;

/**
 * Class to simplify code write that mantains indentation status
 *
 * @author <a href="http://slinkydeveloper.github.io">Francesco Guardiani @slinkydeveloper</a>
 */
public class CodeWriter extends PrintWriter {

  private int indentationFactor = 2;

  private String indent = "";

  public CodeWriter(OutputStream out) {
    super(out);
  }

  public CodeWriter(Writer out) {
    super(out);
  }

  public int getIndentationFactor() {
    return indentationFactor;
  }

  public void setIndentationFactor(int indentationFactor) {
    this.indentationFactor = indentationFactor;
  }

  public String indentation() {
    return indent;
  }

  public CodeWriter indent() {
    for (int i = 0; i < indentationFactor; i++) indent += ' ';
    return this;
  }

  public CodeWriter unindent() {
    if (indent.length() >= indentationFactor) indent = indent.substring(0, indent.length() - indentationFactor);
    return this;
  }

  public CodeWriter writeJavaImport(String i) {
    this.print("import " + i + ";\n");
    return this;
  }

  /**
   * This function add indentation before the line
   */
  public CodeWriter code(String line) {
    this.write(indent + line);
    return this;
  }

  /**
   * This function add indentation before the line and newline at the end of line
   * @param line
   * @return
   */
  public CodeWriter codeln(String line) {
    return this.code(line).newLine();
  }

  /**
   * This function add indentation before the line and semicolon and newline at the end of line
   * @param line
   * @return
   */
  public CodeWriter stmt(String line) {
    this.write(indent + line + ";\n");
    return this;
  }

  public CodeWriter newLine() {
    this.write("\n");
    return this;
  }

  public CodeWriter code(CharSequence csq, int start, int end) {
    return this.code(indent + csq.subSequence(start, end));
  }

  /**
   * Write an array of things delimited with provided delimiter
   * @param delimiter
   * @param l
   * @param map
   * @param <T>
   * @return
   */
  public <T> CodeWriter writeArray(String delimiter, List<T> l, Function<T, String> map) {
    if (l.size() == 0) return this;
    int i = 0;
    for (; i < l.size() - 1; i++) {
      write(map.apply(l.get(i)) + delimiter);
    }
    write(map.apply(l.get(i)));
    return this;
  }
}
