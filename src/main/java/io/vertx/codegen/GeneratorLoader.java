package io.vertx.codegen;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.stream.Stream;

/**
 * A loader for generators.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface GeneratorLoader {

  /**
   * Load the generator from the processing environment.
   *
   * @param processingEnv the env
   * @return the stream of generator
   */
  Stream<Generator<?>> loadGenerators(ProcessingEnvironment processingEnv);

}
