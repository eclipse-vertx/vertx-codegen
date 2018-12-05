package io.vertx.test.codegen.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class TestGenerator extends Generator<DataObjectModel>{
  public TestGenerator(){
    name = "testgen7";
    kinds = Collections.singleton("dataObject");
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class);
  }

  @Override
  public String filename(DataObjectModel model) {
    return model.getType().getName().replace(".", "_") + ".properties";
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    return "MyGenerator=true";
  }
}
