package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare a data object mapper.
 * <br/>
 * Methods can be annotated to convert between JSON and any Java.
 *
 * <pre>
 * &#64;Mapper
 * public static java.net.URI decodeURI(String s) {
 *   ...
 * }
 *
 * &#64;Mapper
 * public static String encodeURI(java.net.URI uri) {
 *   ...
 * }
 * </pre>
 * <br/>
 * {@code java.util.function.Function} can also be used.
 * <pre>
 * &#64;Mapper
 * public static final Function&lt;String, java.net.URI> uriDecoder = ...;
 *
 * &#64;Mapper
 * public static final Function&lt;java.net.URI, String> uriEncoder = ...;
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Mapper {
}
