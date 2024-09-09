package io.vertx.test.codegen.generator.gen3;

import io.vertx.codegen.processor.ClassModel;
import io.vertx.codegen.processor.Generator;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.test.codegen.generator.CodeGeneratorTest;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

public class TestGenerator extends Generator<ClassModel>{
  public TestGenerator(){
    name = "testgen3";
    incremental = false;
    kinds = new HashSet<>(Collections.singletonList("class"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.singletonList(VertxGen.class);
  }

  @Override
  public String filename(ClassModel model) {
    return CodeGeneratorTest.testAbsoluteFilenamePath();
  }

  @Override
  public String render(ClassModel model, int index, int size, Map<String, Object> session) {
    return model.getType().getSimpleName() + "\n";
  }
}
