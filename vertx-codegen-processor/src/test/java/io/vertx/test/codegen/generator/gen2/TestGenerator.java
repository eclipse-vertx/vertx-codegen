package io.vertx.test.codegen.generator.gen2;

import io.vertx.codegen.processor.ClassModel;
import io.vertx.codegen.processor.Generator;
import io.vertx.codegen.annotations.VertxGen;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class TestGenerator extends Generator<ClassModel>{
  public TestGenerator(){
    name = "testgen2";
    incremental = true;
    kinds = new HashSet<>(Arrays.asList("class"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(VertxGen.class);
  }

  @Override
  public String filename(ClassModel model) {
    return "testgen2.incremental_class.java";
  }

  @Override
  public String render(ClassModel model, int index, int size, Map<String, Object> session) {
    StringBuilder sb = new StringBuilder();
    if (index == 0) {
      sb.append("package testgen2;\n" +
        "\n" +
        "import java.util.*;\n" +
        "import java.util.concurrent.Callable;\n" +
        "\n" +
        "public class incremental_class implements Callable<Set<String>> {\n" +
        "  public Set<String> call() throws Exception {\n" +
        "    Set<String> strings = new HashSet<>();\n");
    }
    sb.append("    strings.add(\"").append(model.getType().toString()).append("\");\n");
    if (index + 1 == size) {
      sb.append("    return strings;\n" +
        "  }\n" +
        "}\n");
    }
    return sb.toString();
  }
}
