package io.vertx.test.codegen.generator.gen5;

import io.vertx.codegen.processor.ClassModel;
import io.vertx.codegen.processor.Generator;
import io.vertx.codegen.annotations.VertxGen;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class TestGenerator1 extends Generator<ClassModel>{
  public TestGenerator1(){
    name = "testgen5";
    incremental = false;
    kinds = new HashSet<>(Collections.singletonList("class"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(VertxGen.class);
  }

  @Override
  public String filename(ClassModel model) {
    return model.getIfaceFQCN() + "_Other.java";
  }

  @Override
  public String render(ClassModel model, int index, int size, Map<String, Object> session) {
    return "should_not_be_compiled";
  }
}
