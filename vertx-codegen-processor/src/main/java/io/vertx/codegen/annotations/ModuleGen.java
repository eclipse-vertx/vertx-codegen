package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a <i>Codegen</i> module, all the processed elements contained in the annotated package
 * or one of its child package will be part of the same module. The identity of the module plays an
 * important role as it can be used by a runtime to load a module.<p/>
 *
 * The {@link #name()} declares the name of the module: a non hierarchical name. Such name is used by
 * the JavaScript or Ruby language to generate modules for their runtime. The Java or Groovy runtime do
 * not use this info.<p/>
 *
 * The {@link #groupPackage()} declares the group name of the module: the package of the group used
 * for generating the generated package names (for <i>Groovy</i> or <i>RxJava</i> generation).<p/>
 *
 * <code>
 * {@literal @ModuleGen}(name = "acme", groupPackage="com.acme")
 * package com.acme.myservice;
 * </code>
 *
 * The group package must be a prefix of the annotated module package, it defines the naming of the generate
 * packages for the modules that belongs to the same group, in this case:<p/>
 *
 * <ul>
 *   <li>{@code com.acme.groovy...} for Groovy API</li>
 *   <li>{@code com.acme.rxjava...} for RxJava API</li>
 * </ul>
 * <p/>
 *
 * For this particular `com.acme.myservice` module we have:<p/>
 *
 * <ul>
 *   <li>{@code com.acme.groovy.myservice} for Groovy API</li>
 *   <li>{@code com.acme.rxjava.myservice} for RxJava API</li>
 * </ul>
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface ModuleGen {

  /**
   * @return the module name
   */
  String name();

  /**
   * @return the module group package
   */
  String groupPackage();

  /**
   * @return {@code true} when asynchronous operations are declared by {@code Future<T>} returning signatures, {@code false}
   *          when they are declared by {@code Handler<AsyncResult<T>>} callback signatures
   */
  boolean useFutures() default false;

  boolean checkCallbackDeprecation() default false;

}
