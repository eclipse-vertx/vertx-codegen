package io.vertx.codegen.processor;

import javax.annotation.processing.ProcessingEnvironment;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
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

  public Collection<Class<? extends Annotation>> annotations() {
    return Collections.emptySet();
  }

  public void load(ProcessingEnvironment processingEnv) {
    env = processingEnv;
  }

  /**
   * Generate the file name for the {@code model}.
   * <p/>
   * When the returned value
   * <ul>
   *   <li>is {@code null}, no file is created</li>
   *   <li>ends with {@code .java} suffix, the file is created as a Java source file by the annotation processor,
   *   the class will be compiled by the current compilation process</li>
   *   <li>otherwise, the file is created as a resource file by the annotation processor and the file will likely end
   *   in the classes directory</li>
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
