package io.vertx.codegen;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.Map;
import java.util.Set;

/**
 * A generator for a {@link Model}.
 *
 * @param <M> the model
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class Generator<M extends Model> {

  public ProcessingEnvironment env;
  public String name;
  public Set<String> kinds;
  public boolean incremental;

  public void load(ProcessingEnvironment processingEnv) {
    env = processingEnv;
  }

  /**
   * Generate the relative file name for the {@code model}
   *
   * @param model the model
   * @return the relative filename or {@code null} if no generation should happen
   */
  public String relativeFilename(M model) {
    return null;
  }

  /**
   * Render the model
   *
   * @param model the model
   * @param index the index for an incremental generator
   * @param size the number of models for an incremental generator
   * @param session the session
   * @return the rendered string or {@code null}
   */
  public String render(M model, int index, int size, Map<String, Object> session) {
    return null;
  }
}
