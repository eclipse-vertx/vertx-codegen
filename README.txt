In order for code generation to work effectively, Vert.x APIs must conform to a set of rules.

We also introduce a set of annotations.

Rules:
=====

1. API must be defined as a set of Java interfaces
2. No default methods in interfaces
3. An interface which is to have lang APIs generated for it must be annotated with the @VertxGen annotation
4. Parameters and return values to API methods can be any of the following types:

* Any primitive type
* Any boxed primitive type
* java.lang.String
* io.vertx.java.core.Buffer
* byte[]
* io.vertx.java.core.Handler<T> where T is any of the allowed API types with the exception of io.vertx.java.core.Handler
* io.vertx.java.core.Handler<AsyncResult<T>> where T is any of the allowed API types with the exception of io.vertx.java.core.Handler or AsyncResult
* Any user defined API type which is defined in its own interface

5. Index getter is annotated with @IndexGetter

6. Index setter is annotated with @IndexSetter

7. If method has fluent return value is annotated with @FluentReturn

8. No nested interfaces

9. Any async method should take a parameter of Handler<T> for stream of values, or Handler<AsyncResult<T>> for single value

Variables for template engine
=============================

When the template engine is called the following variables will be available:

1. Fully qualified interface name
2. Short interface name
3. List of MethodInfo objects for all interface methods
4. List of all user API types used in this interface (e.g. so they can be require()d)

The MethodInfo object contains the following:

1. Method Name (String)
2. Method return type (String)
3. isFluent() - returns true if fluent
4. isIndexGetter() - returns true if index getter
5. isIndexSetter() - returns true if index setter
3. List of MethodParam objects for the method

Each MethodParam contains the following

1. Param name (String)
2. Param type (String)
3. isHandlerParam() returns true if is Handler<T> or Handler<AsyncResult<T>>
4. getGenericHandlerType() returns T from above (String)

Also a helper object which contains these methods:



How to generate
===============

Iterate through the methods.

Generate the class outer structure

For each method:

    Convert each param by calling a nested template based on the type
    Convert return value by calling nested template based on the type OR if fluent return "this"


There should also be some templates declared in the template that handle each conversion







