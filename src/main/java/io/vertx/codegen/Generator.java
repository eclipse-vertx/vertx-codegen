package io.vertx.codegen;

import org.mvel2.templates.TemplateRuntime;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.gen.Fluent;
import org.vertx.java.core.gen.IndexGetter;
import org.vertx.java.core.gen.IndexSetter;
import org.vertx.java.core.gen.VertxGen;
import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.JsonObject;
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
import javax.lang.model.util.Types;
import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
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
    //process(NetServer.class, "net_server.js", "basic_js.templ");
    process(NetSocket.class, "net_socket.js", "basic_js.templ");
  }

  public void process(Class c, String outputFileName, String templateName) throws Exception {
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

    String output = (String)TemplateRuntime.eval(template, processor.vars);
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

    private ProcessingEnvironment env;
    //private Elements elementUtils;
    private Types typeUtils;
    private boolean processed;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      //elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
      System.out.println("Init called!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      System.out.println("In process!!!");
      try {
        roundEnv.getRootElements().forEach(this::traverseElem);
      } catch (Exception e) {
        e.printStackTrace();
      }
      processed = true;
      return true;
    }


    Map<String, Object> vars = new HashMap<>();
    boolean gotInterface;
    List<MethodInfo> methods = new ArrayList<>();
    {
      vars.put("helper", new Helper());
      vars.put("methods", methods);
    }


    private void traverseElem(Element elem) {
      ///System.out.println("Elem is " + elem);
     // System.out.println("Kind is " + elem.getKind());
     // System.out.println("Got comment: " + elementUtils.getDocComment(elem));


      switch (elem.getKind()) {
        case INTERFACE:
          if (gotInterface) {
            throw new IllegalStateException("Can only have one interface per file");
          }
          vars.put("ifaceSimpleName", elem.getSimpleName());
          vars.put("ifaceFQCN", elem.asType().toString());
          gotInterface = true;
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
          List<MethodParam> mParams = new ArrayList<>();
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
            MethodParam mParam = new MethodParam(param.getSimpleName().toString(), param.asType().toString(),
                                                 isHandlerParam, isAsyncResultHandlerParam, genericHandlerType);
            mParams.add(mParam);
            System.out.println("param type is " + elemType);
            TypeKind typeKind = elemType.getKind();
            System.out.println("type kind is " + typeKind);
            System.out.println("isHandlerParam: " + isHandlerParam);
            System.out.println("isAsyncResultHandlerParam: " + isAsyncResultHandlerParam);
            System.out.println("genericHandlerType: " + genericHandlerType);
          }
          MethodInfo methodInfo = new MethodInfo(execElem.getSimpleName().toString(), execElem.getReturnType().toString(),
                 isFluent, isIndexGetter, isIndexSetter, mParams);
          methods.add(methodInfo);
          break;
      }

      elem.getEnclosedElements().forEach(this::traverseElem);
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }

  }
}
