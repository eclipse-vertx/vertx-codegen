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
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.net.NetSocket;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.*;
import java.io.*;
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
    process(NetServer.class, "basic_js.templ");
    process(NetSocket.class, "basic_js.templ");
  }

  public void process(Class c, String templateName) throws Exception {

    String outputFileName = Helper.convertCamelCaseToFileNameWithUnderscores(c.getSimpleName()) + ".js";

    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics =
      new DiagnosticCollector<>();
    StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);

    String className = c.getCanonicalName();
    String fileName = className.replace(".", "/") + ".java";
    System.out.println("filename of source is: " + fileName);
    InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
    if (is == null) {
      throw new IllegalStateException("Can't find file on classpath: " + fileName);
    }
    // Load the source
    String source;
    try (Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
      source = scanner.next();
    }
    System.out.println("source is: " + source);
    // Now copy it to a file (this is clunky but not sure how to get around it)
    String tmpFileName = System.getProperty("java.io.tmpdir") + "/" + fileName;
    File f = new File(tmpFileName);
    File parent = f.getParentFile();
    System.out.println("Parent file is " + parent);
    boolean ok = parent.mkdirs();
    System.out.println("parent mkdirs returned: " + ok);
    System.out.println("tmp file name is: " + tmpFileName);
    try (PrintStream out = new PrintStream(new FileOutputStream(tmpFileName))) {
      out.print(source);
    }
    Iterable<? extends JavaFileObject> iter = fm.getJavaFileObjects(tmpFileName);
    JavaFileObject fo = iter.iterator().next();
    System.out.println("JavaFileObject is: " + fo);
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

    //MVEL preserves all whitespace therefore, so we can have readable templates we remove all line breaks
    //and replace all occurrences of "\n" with a line break
    //"\n" signifies we want an actual line break in the output
    template = template.replace("\n", "").replace("\\n", "\n");

    Map<String, Object> vars = new HashMap<>();
    vars.put("ifaceSimpleName", processor.ifaceSimpleName);
    vars.put("ifaceFQCN", processor.ifaceFQCN);
    vars.put("ifaceComment", processor.ifaceComment);
    vars.put("helper", new Helper());
    vars.put("methods", processor.methods);
    vars.put("referencedTypes", processor.referencedTypes);

    String output = (String)TemplateRuntime.eval(template, vars);
    System.out.println("OUTPUT:");
    System.out.println(output);
    File genDir = new File("src/gen/javascript");
    genDir.mkdirs();
    File outFile = new File(genDir, outputFileName);
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outFile))) {
      outStream.print(output);
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
      return SourceVersion.RELEASE_7;
    }

    ProcessingEnvironment env;
    Elements elementUtils;
    Types typeUtils;
    boolean processed;
    List<MethodInfo> methods = new ArrayList<>();
    List<String> referencedTypes = new ArrayList<>();
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
      try {
        roundEnv.getRootElements().forEach(this::traverseElem);
      } catch (Exception e) {
        e.printStackTrace();
      }
      processed = true;
      return true;
    }

    private void traverseElem(Element elem) {
      ///System.out.println("Elem is " + elem);
     // System.out.println("Kind is " + elem.getKind());

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
          System.out.println("Method is " + execElem.getSimpleName());
          boolean isFluent = execElem.getAnnotation(Fluent.class) != null;
          boolean isIndexGetter = execElem.getAnnotation(IndexGetter.class) != null;
          boolean isIndexSetter = execElem.getAnnotation(IndexSetter.class) != null;
          System.out.println("Fluent?:" + isFluent);
          List<? extends VariableElement> params = execElem.getParameters();
          System.out.println("params size is " + params.size());
          List<ParamInfo> mParams = new ArrayList<>();
          for (VariableElement param: params) {
            System.out.println("param name " + param.getSimpleName());
            TypeMirror elemType = param.asType();
            String handlerTypeString = Handler.class.getCanonicalName() + "<";
            String asyncResultHandlerTypeString = handlerTypeString + AsyncResult.class.getCanonicalName() + "<";
            String elemTypeString = elemType.toString();
            String genericHandlerType = null;
            boolean isHandlerParam = false;
            boolean isAsyncResultHandlerParam = false;
            if (elemTypeString.startsWith(asyncResultHandlerTypeString)) {
              genericHandlerType = elemTypeString.substring(asyncResultHandlerTypeString.length(), elemTypeString.length() - 2);
              isHandlerParam = true;
              isAsyncResultHandlerParam = true;
            } else if (elemTypeString.startsWith(handlerTypeString)) {
              genericHandlerType = elemTypeString.substring(handlerTypeString.length(), elemTypeString.length() - 1);
              isHandlerParam = true;
            }
            checkAddReferencedType(genericHandlerType);
            ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), param.asType().toString(),
                                                 isHandlerParam, isAsyncResultHandlerParam, genericHandlerType);
            mParams.add(mParam);
            System.out.println("param type is " + elemType);
            TypeKind typeKind = elemType.getKind();
            System.out.println("type kind is " + typeKind);
            System.out.println("isHandlerParam: " + isHandlerParam);
            System.out.println("isAsyncResultHandlerParam: " + isAsyncResultHandlerParam);
            System.out.println("genericHandlerType: " + genericHandlerType);
          }
          String returnType = execElem.getReturnType().toString();
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
