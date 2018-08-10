package io.vertx.test.codegen.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;

import java.util.Collections;
import java.util.Map;

public class TestGenerator extends Generator<DataObjectModel>{
  public TestGenerator(){
    name = "testgen7";
    kinds = Collections.singleton("dataObject");
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
