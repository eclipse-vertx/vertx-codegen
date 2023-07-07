package io.vertx.codegen.generators.dataobjecthelper.proto;

import io.vertx.codegen.Generator;
import io.vertx.codegen.GeneratorLoader;
import io.vertx.codegen.generators.dataobjecthelper.DataObjectHelperGen;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.stream.Stream;

public class DataObjectProtobufGenLoader implements GeneratorLoader {

  @Override
  public Stream<Generator<?>> loadGenerators(ProcessingEnvironment processingEnv) {
    return Stream.of(new DataObjectProtobufGen());
  }
}
