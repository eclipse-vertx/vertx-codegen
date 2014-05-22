# Vert.x API Generation

This projects contains tools which allow idiomatic other language API shims to be generated from Java APIs.

## API constraints

In order for code generation to work effectively, certain constraints are put on the Java interfaces.

The constraints are

* The API must be described as a set of Java interfaces
* Default methods are not permitted
* Nested interfaces are not permitted
* All interfaces to have generation performed on them must be annotated with the `org.vertx.java.core.gen.VertxGen` annotation
* Only certain types are allowed as parameter or return value types for any API methods (defined below).
* Fluent methods must be annotated with the `org.vertx.java.core.gen.Fluent` annotation
* Index getter methods must be annotated with the `org.vertx.java.core.gen.IndexGetter` annotation
* Index setter methods must be annotated with the `org.vertx.java.core.gen.IndexSetter` annotation

### Permitted types

The following set `P` of types are permitted as parameters or return values to any API method:

* `void`
* any primitive type
* any boxed primitive type
* `java.lang.String`
* `io.vertx.java.core.Buffer`
* `byte[]`
* any other user defined API type which is defined in its own interface and annotated with `@VertxGen`

We also allow the following set `Q` of additional types for parameters or return values:

* `io.vertx.java.core.Handler<T>` where `T` is any of `P`
* `io.vertx.java.core.Handler<io.vertx.java.core.AsyncResult<T>>` where `T` is any of `P`


## Templates

We use MVEL templating to generate APIs.

There should be a single MVEL template for each language API that is to be generated.

The template will be called once for each interface that is annotated with `@VertxGen` in the Java API. Normally one
output file (e.g. one .js file) should be created for each Java interface.

### Template variables

The following variables are made available to templates:

* `ifaceSimpleName` - the simple class name of the Java interface
* `ifaceFQCN" - the fully qualified class name of the Java interface
* `ifaceComment` - the class comment from the Java interface
* `helper` - a helper class that of type `io.vertx.codegen.Helper` which contains useful methods for things such as
converting CamelCase to underscores.
* `methods` - a list of `MethodInfo` objects describing each method in the interface.
* `referencedTypes` - a list of strings representing the set of user defined types (also annotated with `VertxGen`) which
are referenced from the current interface

The `MethodInfo` object has the following fields:

* `name`. The name of the method
* `returnType`. The fully qualified return type (or `void`) of the method
* `fluent`. `true` if the method is fluent (i.e. returns a reference to the interface itself for chaining calls)
* `getter`. `true` if the method is an index getter.
* `setter`. `true` if the method is an index setter.
* `comments`. Method comment.
* `params`. List of `ParamInfo` objects representing the paramaters of the method.

The `ParamInfo` object has the following fields:

* `name`. The name of the parameter
* `type`. The fully qualified type of the paramater
* `handlerParam`. `true` if the parameter is of type `io.vertx.java.core.Handler<T>`
* `asyncResultHandlerParam`. `true` if the parameter is of type `io.vertx.java.core.Handler<io.vertx.java.core.AsyncResult<T>>`
* `genericHandlerType`. The type `T` if `asyncResultHandlerParam` is `true`.

## TODO

* Check all return and param types
* Parse interfaces that are extended
* Create some test Java interfaces which exercise all the different types of methods and interfaces that can be used
* Create a simple set of tests per language that we generate which tests the different types of methods to ensure
they return correct values etc







