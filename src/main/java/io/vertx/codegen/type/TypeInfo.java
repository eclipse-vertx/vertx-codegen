package io.vertx.codegen.type;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Describes a java type.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeInfo {

  public abstract boolean equals(Object obj);

  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Collect the import fqcn needed by this type.
   *
   * @param imports the imports
   */
  public void collectImports(Collection<ClassTypeInfo> imports) {
  }

  /**
   * @return the erased type of this type
   */
  public TypeInfo getErased() {
    return this;
  }

  /**
   * @return the corresponding raw type or null
   */
  public ClassTypeInfo getRaw() {
    return null;
  }

  /**
   * @return the class kind this type resolves to
   */
  public ClassKind getKind() {
    return ClassKind.OTHER;
  }

  /**
   * @return the declaration suitable for source code represented using qualified names, for instance
   * <code>io.vertx.core.Handler&lt;io.vertx.core.buffer.Buffer&gt;</code>
   */
  public String getName() {
    return format(true);
  }

  /**
   * @return true if the type is nullable
   */
  public boolean isNullable() {
    return false;
  }

  /**
   * Translate the current type name based on the module group package name and the specified
   * {@code lang} parameter. This has effect only for {@link ApiTypeInfo} or
   * {@link ParameterizedTypeInfo} types.
   *
   * @param lang the target language, for instance {@literal groovy}
   * @return the translated name
   */
  public String translateName(String lang) {
    return translateName(TypeNameTranslator.hierarchical(lang));
  }

  public String translateName(TypeNameTranslator translator) {
    return getName();
  }

  /**
   * @return the declaration suitable for source code represented using unqualified names, for instance
   * <code>Handler&lt;Buffer&gt;</code>
   */
  public String getSimpleName() { return format(false); }

  /**
   * @return the @{link #getName} value of this type
   */
  public String toString() {
    return getName();
  }

  /**
   * @return true if the type is a parameterized type
   */
  public boolean isParameterized() {
    return false;
  }

  /**
   * @return true if the type is a type variable
   */
  public boolean isVariable() {
    return false;
  }

  /**
   * @return true if the type <i>void</i>
   */
  public boolean isVoid() {
    return false;
  }

  /**
   * Renders the type name.
   *
   * @param qualified true when class fqcn should be used, otherwise simple names will be used
   * @return the representation of the type
   */
  abstract String format(boolean qualified);


}
