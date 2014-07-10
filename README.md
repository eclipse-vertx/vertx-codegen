# Vert.x API Generation

This projects contains tools which allow idiomatic other language API shims to be generated from Java APIs.

## API constraints

In order for code generation to work effectively, certain constraints are put on the Java interfaces.

The constraints are

* The API must be described as a set of Java interfaces, classes are not permitted
* Default methods are not permitted
* Nested interfaces are not permitted
* All interfaces to have generation performed on them must be annotated with the `io.vertx.codegen.annotations
.VertxGen` annotation
* Fluent methods (methods which return a reference to this) must be annotated with the `io.vertx.codegen.annotations
.Fluent` annotation
* Options classes (classes which provide configuration options to methods) must be annotated with the `io.vertx.codegen.annotations
.Options` annotation
* Options classes must provide a constructor which takes a single `io.vertx.core.json.JsonObject` parameter.
* Methods where the return value must be cached in the API shim must be annotated with the `io.vertx.codegen.annotations
.CacheReturn` annotation
* Only certain types are allowed as parameter or return value types for any API methods (defined below).


### Permitted types

We define the following set `B` of basic types:

* any primitive type
* any boxed primitive type
* `java.lang.String`

We define `V` as the set of user defined API types which are defined in its own interface and annotated with `@VertxGen`

The following set `P` of types are permitted as parameters to any API method:

* the set `B`
* `java.lang.Object`
* the set `V`
* any options class annotated with `@Options`
* `io.vertx.java.core.Handler<io.vertx.java.core.AsyncResult<HA>>` where `HA` contains
    * the set `B`
    * the set `V`
    * `java.lang.Void`
    * `java.lang.Throwable`
    * type `java.util.List<C>` or `java.util.Set<C>` where `C` contains
        * the set `B`
        * the set `V`
* `io.vertx.java.core.Handler<H>` where `H` contains
    * the set `HA`
    * `java.lang.Throwable`

The following set `R` of types are permitted as return types from any API method:

* void
* the set `B`
* the set `V`
* type `java.util.List<C>` or `java.util.Set<C>` where `C` contains
    * the set `B`
    * the set `V`

### Overloaded methods

Because some languages do not support operator overloading, we make the constraint that if there are overloaded
versions of the same method in an interface, then they must have the same parameters at the same positions. I.e. the
following would be legal:

    void myMethod(String str, int num);

    void myMethod(String str, int num, double d, Handler<String> myHandler);

    void myMethod(String str, int num, double d);

But the following would be illegal:

    void myMethod(String str, int num);

    void myMethod(int num, String str, double d, Handler<String> myHandler);

### Static factory methods

You may add static factory methods in your interfaces, e.g.

    interface MyInterface {

        static MyInterface newInterface(String foo) {
          return new ....
        }

    }


### Super interfaces

Interfaces can extend other interfaces with also have the `@VertxGen` annotation.

### Ignoring methods

If you do not wish a method to be used for generation you can annotate it with the `@GenIgnore` annotation.

## Templates

We use MVEL templating to generate APIs.

There should be a single MVEL template for each language API that is to be generated.

The template will be called once for each interface that is annotated with `@VertxGen` in the Java API. One
output file (e.g. one .js file) will be created for each Java interface.

### Template variables

The following variables are made available to templates:

* `ifaceSimpleName` - the simple name of the Java interface
* `ifaceFQCN` - the fully qualified class name of the Java interface
* `ifaceComment` - the class comment from the Java interface
* `helper` - a helper class that of type `io.vertx.codegen.Helper` which contains useful methods for things such as
converting CamelCase to underscores.
* `methods` - a list of `MethodInfo` objects describing each method in the interface.
* `referencedTypes` - a list of strings representing the set of user defined types (also annotated with `VertxGen`) which
are referenced from the current interface
* `superTypes` - a list of strings representing the set of user defined types which the current interface extends from
* `squashedMethods` - this is a list of methods where all methods of the same name (i.e. overloaded methods) are squashed
into a single method.
* `methodMap` - this is a Map<String, MethodInfo> - which allows you to look up all methods with a given name


The `MethodInfo` object has the following fields:

* `name`. The name of the method
* `returnType`. The fully qualified return type (or `void`) of the method
* `fluent`. `true` if the method is fluent (i.e. returns a reference to the interface itself for chaining calls)
* `cacheReturn`. `true` if the generated API method should cache return value
* `squashed`. `true` if the method is squashed (i.e. contains all overloaded params)
* `comment`. Method comment.
* `params`. List of `ParamInfo` objects representing the parameters of the method.
* `staticMethod`. `true` if it's a static method.

The `ParamInfo` object has the following fields:

* `name`. The name of the parameter
* `type`. The fully qualified type of the parameter
* `options`. `true` If the parameter is an options type.







