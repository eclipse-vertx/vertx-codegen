package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a data object, such object must provide at least:
 *
 * <ul>
 *   <li>a constructor with no arguments</li>
 *   <li>a constructor with {@link io.vertx.core.json.JsonObject}</li>
 *   <li>a copy constructor</li>
 * </ul>
 * <p/>
 *
 * When the data object is processed, a model of properties from the setter methods or adder methods. Single valued
 * and multi valued properties are supported.<p/>
 *
 * A setter is just like a JavaBean setter, however the method return type shall be fluent and return the
 * current object. A {@link java.util.List} setter type declares a multi valued property, otherwise it declares
 * a single valued property.<p/>
 *
 * An adder is a method prefixed by {@code add}, its purpose is to declare a {@link java.util.List} of properties.
 * An adder declares a multi valued property, mapped to the {@link io.vertx.core.json.JsonArray} type. An adder
 * and a multi valued setter of the same property name are naturally compatible and is encouraged.<p/>
 *
 * Properties types can be:<p/>
 *
 * <ul>
 *   <li>any basic type</li>
 *   <li>a valid data object type</li>
 *   <li>{@link io.vertx.core.json.JsonObject}</li>
 *   <li>{@link io.vertx.core.json.JsonArray}</li>
 *   <li>an enum type</li>
 *   <li>a {@link java.util.List} of above</li>
 * </ul>
 * <p/>
 *
 * A data object can be an interface or an abstract class to accomodate the api design. Data object can extend
 * other data objects.<p/>
 *
 * Sometimes data object can have a {@code toJson()} method that takes no arguments and returns a {@code JsonObject} representing
 * the data object as a {@code JsonObject}.<p/>
 *
 * Vertx core will generate a json converters using annotation processing to ease conversion, for a given data object,
 * a converter is generated, the name of this converter is the name of the data object with the {@literal Converter} suffix.<p/>
 *
 * The converter has a {@code fromJson(JsonObject,T)} and a {@code toJson(T,JsonObject)} public static methods, such methods
 * can be used by the json constructor or the {@code toJson()} method to implement the conversion code. The generated methods
 * only handle the conversion of the property of the data object and do not handle the properties of the ancestors of this
 * data object. The converter generation can be prevented with the {@link #generateConverters()} annotation member.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataObject {
  /**
   * @return true if converters should be generated for the data object
   */
  boolean generateConverters() default true;
}
