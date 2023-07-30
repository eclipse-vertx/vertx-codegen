package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.Generator;
import io.vertx.codegen.GeneratorLoader;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.stream.Stream;

public class DataObjectProtobufGenLoader implements GeneratorLoader {

  @Override
  public Stream<Generator<?>> loadGenerators(ProcessingEnvironment processingEnv) {
    return Stream.of(new DataObjectProtobufGen(), new ProtoFileGen());
  }
}
