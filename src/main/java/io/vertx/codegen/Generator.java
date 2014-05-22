package io.vertx.codegen;

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

import org.mvel2.templates.TemplateRuntime;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.gen.Fluent;
import org.vertx.java.core.gen.IndexGetter;
import org.vertx.java.core.gen.IndexSetter;
import org.vertx.java.core.gen.VertxGen;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Generator {

  public static void main(String[] args) {
    try {
      new Generator().run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() throws Exception {
    //process(NetServer.class, "js.templ");
    //process(NetSocket.class, "js.templ");
    process("src/gentarget/com/foo/APIClass.java", "api_class.js", "js.templ");
  }

  public void processFromClasspath(Class c, String outputFileName, String templateName) throws Exception {
    String className = c.getCanonicalName();
    String fileName = className.replace(".", "/") + ".java";
    InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
    if (is == null) {
      throw new IllegalStateException("Can't find file on classpath: " + fileName);
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
    boolean ok = parent.mkdirs();
    try (PrintStream out = new PrintStream(new FileOutputStream(tmpFileName))) {
      out.print(source);
    }
    process(tmpFileName, outputFileName, templateName);
  }

  public void process(String sourceFileName, String outputFileName, String templateName) throws Exception {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics =
      new DiagnosticCollector<>();
    StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
    Iterable<? extends JavaFileObject> iter = fm.getJavaFileObjects(sourceFileName);
    JavaFileObject fo = iter.iterator().next();
    Writer out = new NullWriter();
    List<JavaFileObject> fileObjects = Collections.singletonList(fo);
    JavaCompiler.CompilationTask task = compiler.getTask(out, fm, diagnostics, null, null, fileObjects);
    MyProcessor processor = new MyProcessor();
    List<Processor> processors = Collections.<Processor>singletonList(processor);
    task.setProcessors(processors);
    task.call();

    if (!processor.processed) {
      throw new IllegalStateException("Interface not processed. Does it have the VertxGen annotation?");
    }
    String template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/" + templateName)));

    // MVEL preserves all whitespace therefore, so we can have readable templates we remove all line breaks
    // and replace all occurrences of "\n" with a line break
    // "\n" signifies we want an actual line break in the output
    // We use actual tab characters in the template so we can see indentation, but we strip these out
    // before parsing - so much sure you configure your IDE to use tabs as spaces for .templ files
    template = template.replace("\n", "").replace("\\n", "\n").replace("\t", "");

    Map<String, Object> vars = new HashMap<>();
    vars.put("ifaceSimpleName", processor.ifaceSimpleName);
    vars.put("ifaceFQCN", processor.ifaceFQCN);
    vars.put("ifaceComment", processor.ifaceComment);
    vars.put("helper", new Helper());
    vars.put("methods", processor.methods);
    vars.put("referencedTypes", processor.referencedTypes);

    String output = (String)TemplateRuntime.eval(template, vars);
    File genDir = new File("src/gen/javascript");
    genDir.mkdirs();
    File outFile = new File(genDir, outputFileName);
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outFile))) {
      outStream.print(output);
    }
  }


  private void checkType(String type) {
    if (!Helper.isBasicType(type)) {
      if (type.startsWith("java.") || type.startsWith("javax.")) {
        throw new IllegalStateException("Invalid type " + type + " in return type or parameter of API method");
      } else {
        //TODO - test user defined and generic types
      }
    }
  }

  class NullWriter extends Writer {
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

  class MyProcessor implements Processor {

    @Override
    public Set<String> getSupportedOptions() {
      return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      HashSet<String> set = new HashSet<>();
      set.add(VertxGen.class.getCanonicalName());
      return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.RELEASE_8;
    }

    ProcessingEnvironment env;
    Elements elementUtils;
    Types typeUtils;
    boolean processed;
    List<MethodInfo> methods = new ArrayList<>();
    Set<String> referencedTypes = new HashSet<>();
    String ifaceSimpleName;
    String ifaceFQCN;
    String ifaceComment;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      roundEnv.getRootElements().forEach(this::traverseElem);
      processed = true;
      return true;
    }

    private void traverseElem(Element elem) {
      switch (elem.getKind()) {
        case INTERFACE:
          if (ifaceFQCN != null) {
            throw new IllegalStateException("Can only have one interface per file");
          }
          ifaceFQCN = elem.asType().toString();
          ifaceSimpleName = elem.getSimpleName().toString();
          ifaceComment = elementUtils.getDocComment(elem);
          break;
        case METHOD:
          ExecutableElement execElem = (ExecutableElement)elem;
          boolean isFluent = execElem.getAnnotation(Fluent.class) != null;
          boolean isIndexGetter = execElem.getAnnotation(IndexGetter.class) != null;
          boolean isIndexSetter = execElem.getAnnotation(IndexSetter.class) != null;
          List<? extends VariableElement> params = execElem.getParameters();
          List<ParamInfo> mParams = new ArrayList<>();
          for (VariableElement param: params) {
            String paramType = param.asType().toString();
            checkType(paramType);
            String nonGenericType = Helper.getNonGenericType(paramType);
            if (nonGenericType.equals(Handler.class.getCanonicalName())) {
              String genericType = Helper.getGenericType(paramType);
              if (genericType != null) {
                if (Helper.getNonGenericType(genericType).equals(AsyncResult.class.getCanonicalName())) {
                  genericType = Helper.getGenericType(genericType);
                  if (genericType != null) {
                    checkAddReferencedType(genericType);
                  }
                } else {
                  checkAddReferencedType(genericType);
                }
              }
            }
            ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), param.asType().toString());
            mParams.add(mParam);
          }
          String returnType = execElem.getReturnType().toString();
          checkType(returnType);
          checkAddReferencedType(returnType);
          MethodInfo methodInfo = new MethodInfo(execElem.getSimpleName().toString(), returnType,
                 isFluent, isIndexGetter, isIndexSetter, mParams, elementUtils.getDocComment(execElem));
          methods.add(methodInfo);
          break;
      }

      elem.getEnclosedElements().forEach(this::traverseElem);
    }

    private void checkAddReferencedType(String type) {
      if (type != null && !type.startsWith("java.") && !type.equals(ifaceFQCN) && type.contains(".")) {
        referencedTypes.add(type);
      }
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }

  }
}
