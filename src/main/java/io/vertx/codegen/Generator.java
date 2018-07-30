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
   * Generate the file name for the {@code model}.
   * <p/>
   * When the returned value
   * <ul>
   *   <li>is {@code null}, no file is created</li>
   *   <li>does not contain {@code /} and ends with {@code .java}, the file is created as a source file by the annotation processor
   *   and the class will be compiled by the current compilation process</li>
   *   <li>starts with the {@code resources/} prefix, the file is created as a resource file by the annotation processor using the
   *   remaining suffix value and the file will likely end in the classes directory</li>
   *   <li>otherwise the resource will be created as a file on the filesystem, a value not starting with
   *   {@code /} is created relative to the {@code codegen.output} directory</li>
   * </ul>
   *
   * @param model the model
   * @return the filename or {@code null} if no generation should happen
   */
  public String filename(M model) {
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
