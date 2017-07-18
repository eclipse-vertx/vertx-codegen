package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares mapping of generic type parameters to generated types.
 * This can be used to remove wildcards in generated code but leave them in Java.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GenTypeParams {
  
  Param[] value();
  
  /**
   * Declares mapping of generic type parameters to generated types.
   * This can be used to remove wildcards in generated code but leave them in Java.
   */
  public @interface Param {

    /**
     * name of Java generic parameter that should be discarded, for example 'T'
     */
    String name();
    /**
     * class that be used for code generation instead 
     */
    Class generated();
    
  }
  
}
