package io.vertx.test.codegen.generator.gen4;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.annotations.VertxGen;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class TestGenerator extends Generator<ClassModel>{
  public TestGenerator(){
    name = "testgen4";
    incremental = false;
    kinds = new HashSet<>(Collections.singletonList("class"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(VertxGen.class);
  }

  @Override
  public String filename(ClassModel model) {
    return "resources/file.txt";
  }

  @Override
  public String render(ClassModel model, int index, int size, Map<String, Object> session) {
    return "should_not_be_compiled";
  }
}
