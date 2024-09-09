package io.vertx.test.codegen.generator.gen6;

import io.vertx.codegen.processor.ClassModel;
import io.vertx.codegen.processor.DataObjectModel;
import io.vertx.codegen.processor.Generator;
import io.vertx.codegen.processor.Model;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class TestGenerator extends Generator<Model>{
  public TestGenerator(){
    name = "testgen6";
    incremental = true;
    kinds = new HashSet<>(Arrays.asList("class", "dataObject"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, VertxGen.class);
  }

  @Override
  public String filename(Model model) {
    return "result.txt";
  }

  @Override
  public String render(Model model, int index, int size, Map<String, Object> session) {
    if (model instanceof ClassModel) {
      return ((ClassModel)model).getType().getSimpleName() + "\n";
    } else if (model instanceof DataObjectModel) {
      return ((DataObjectModel)model).getType().getSimpleName() + "\n";
    } else {
      throw new UnsupportedOperationException();
    }
  }
}
