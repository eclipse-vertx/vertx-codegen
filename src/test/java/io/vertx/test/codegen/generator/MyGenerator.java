package io.vertx.test.codegen.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Generator;

import java.util.Collections;
import java.util.Map;

public class MyGenerator extends Generator<DataObjectModel>{
  public MyGenerator(){
    name = "MyTest";
    kinds = Collections.singleton(DataObjectModel.class);
  }

  @Override
  public String relativeFilename(DataObjectModel model) {
    return model.getType().getName().replace(".", "_") + ".properties";
  }

  @Override
  public String render(DataObjectModel model, int index, int size, Map<String, Object> session) {
    return "MyGenerator=true";
  }
}
