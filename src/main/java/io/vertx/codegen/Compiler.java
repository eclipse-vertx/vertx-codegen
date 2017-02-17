package io.vertx.codegen;

import javax.annotation.processing.Processor;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Compiler {

  private static File createTempDir() throws IOException {
    File dir = Files.createTempDirectory("codegen").toFile();
    dir.deleteOnExit();
    return dir;
  }

  private Processor processor;
  private DiagnosticListener<JavaFileObject> diagnosticListener;
  private List<String> options = new ArrayList<>();
  private File classOutput;
  private File sourceOutput;

  public Compiler(Processor processor) {
    this(processor, new DiagnosticCollector<>());
  }

  public Compiler(Processor processor, DiagnosticListener<JavaFileObject> diagnosticListener) {
    this.processor = processor;
    this.diagnosticListener = diagnosticListener;
  }

  public Compiler() {
    this(null);
  }

  public Processor getProcessor() {
    return processor;
  }

  public Compiler addOption(String option) {
    options.add(option);
    return this;
  }

  public File getClassOutput() {
    return classOutput;
  }

  public void setClassOutput(File classOutput) {
    this.classOutput = classOutput;
  }

  public File getSourceOutput() {
    return sourceOutput;
  }

  public void setSourceOutput(File sourceOutput) {
    this.sourceOutput = sourceOutput;
  }

  public boolean compile(Class... types) throws Exception {
    return compile(Arrays.asList(types));
  }

  public boolean compile(List<Class> types) throws Exception {
    ArrayList<File> tmpFiles = new ArrayList<>();
    for (Class type : types) {
      String className = type.getCanonicalName();
      String fileName = className.replace(".", "/") + ".java";
      ClassLoader loader = type.getClassLoader();
      InputStream is = loader.getResourceAsStream(fileName);
      if (is == null) {
        throw new IllegalStateException("Can't find source on classpath: " + fileName);
      }
      // Load the source
      String source;
      try (Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
        source = scanner.next();
      }
      // Now copy it to a file (this is clunky but not sure how to get around it)
      String tmpFileName = System.getProperty("java.io.tmpdir") + "/" + fileName;
      File f = new File(tmpFileName);
      File parent = f.getParentFile();
      parent.mkdirs();
      try (PrintStream out = new PrintStream(new FileOutputStream(tmpFileName))) {
        out.print(source);
      }
      tmpFiles.add(f);
    }
    return compile(tmpFiles.toArray(new File[tmpFiles.size()]));
  }

  public boolean compile(File... sourceFiles) throws Exception {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fm = compiler.getStandardFileManager(diagnosticListener, null, null);
    if (classOutput == null) {
      classOutput = createTempDir();
    }
    if (sourceOutput == null) {
      sourceOutput = createTempDir();
    }
    fm.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(classOutput));
    fm.setLocation(StandardLocation.SOURCE_OUTPUT, Collections.singletonList(sourceOutput));
    Iterable<? extends JavaFileObject> fileObjects = fm.getJavaFileObjects(sourceFiles);
    Writer out = new NullWriter();
    JavaCompiler.CompilationTask task = compiler.getTask(out, fm, diagnosticListener, options, null, fileObjects);
    List<Processor> processors = Collections.<Processor>singletonList(processor);
    task.setProcessors(processors);
    try {
      return task.call();
    } catch (RuntimeException e) {
      if (e.getCause() != null && e.getCause() instanceof RuntimeException) {
        throw (RuntimeException)e.getCause();
      } else {
        throw e;
      }
    }
  }

  private static class NullWriter extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
  }

}
