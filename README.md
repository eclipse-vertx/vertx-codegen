# Vert.x API Generation

[![Build Status](https://vertx.ci.cloudbees.com/buildStatus/icon?job=vert.x3-codegen)](https://vertx.ci.cloudbees.com/view/vert.x-3/job/vert.x3-codegen/)

This projects contains tools which allow idiomatic other language API shims to be generated from Java APIs.

## Render documentation

```
> mvn clean package -Pdocs
> open target/docs/vertx-codegen/java/index.html
```

## Helper projects

- Codegen CLI: a codegen [CLI](https://github.com/vietj/vertx-codegen-cli) to help code generating files.
- Codegen starter: a codegen [Starter](https://github.com/vietj/vertx-codegen-starter) that can be forked to create a new Vert.x code generator

## API generator

A code generator consist of an _MVEL_ template declared in a `codegen.json` descriptor:

~~~~
{
  "name": "Groovy",
  "generators": [ {
    "kind": "class",
    "filename": "'groovy/' + fqn.replace('io.vertx', 'io.vertx.groovy').replace('.', '/') + '.groovy'",
    "templateFilename": "vertx-groovy/template/groovy.templ"
  } ]
}
~~~~

- `filename` is an _MVEL_ expression for the file name, returning null skips the generation.
- `templateFilename` is the name of the _MVEL_ template to apply
- `incremental` true when the template performs incremental processing, false or absent otherwise
- `kind`: can be a String or an Array of Strings each representing a generator type. There are several kinds of generators for different use cases:
    - `class` : applied on each API classes
    - `package` : applied on each Java package
    - `module` : applied on each declared module, a module uniquely identifies an API
    - `dataObject`: applied on each data object class
    - `proxy`: applied on each proxy class
    - `enum`: applied on each enum class annotated with `@VertxGen`

There can be as many generators as you like.

## Generated output

A generator can create 3 different kinds of output: Java classes, resources and anything else

### Generated Java classes

A generator declaring a filename that matches a Java FQN followed by `.java` suffix will have its content generated
as a Java class. This class will be automatically compiled by the same compiler (that's a Java compiler feature).

The generated files are handled by the Java compiler (`-s` option), usually build tools configures the compiler to store
 them in a specific build location, for instance Maven by default uses the `target/generated-sources/annotations` directory.

The following generators use it:

- Data object converters
- Service proxy and service handler
- RxJava-ified classes API
- Groovy extension methods API

### Generated resources

A generator declaring a filename prefixed by `resources/` will have its content generated as a compiler resource. This
 resource will be stored in the generated sources directory and the generated classes directory.

The generated files are handled by the Java compiler (`-s` option), usually build tools configures the compiler to store
 them in a specific build location, for instance Maven by default uses the `target/generated-sources/annotations` directory.

 The following generators use it:

- JavaScript generator
- Ruby generator

### Other generated files

Anything else will be stored in the file system using the filename, when the `filename` is relative (it usually is)
the target path will be resolved agains the `codegen.output` directory.

The following generators use it:

- Ceylon generator
- Scala generator
- Kotlin extension methods

When the `codegen.output` is not specified, the generated files are discarded.

### Relocation

Sometimes you want to have a generator to output its files in another directory, you can do that with the
`codegen.output.generator-name` compiler option:

```
<codegen.output.data_object_converters>generated</codegen.output.data_object_converters>
```

The generator will store its content in the `codegen.output/generated` directory instead as a Java class.

## Processor configuration

You can configure the `CodeGenProcessor` as any Java annotation processor, here is how to do with Maven:

~~~~
<pluginManagement>
  <plugins>
    <!-- Configure the execution of the compiler to execute the codegen processor -->
    <plugin>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.1</version>
      <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <encoding>${project.build.sourceEncoding}</encoding>
        <!-- Important: there are issues with apt and incremental compilation in the maven-compiler-plugin -->
        <useIncrementalCompilation>false</useIncrementalCompilation>
      </configuration>
      <executions>
        <execution>
          <id>default-compile</id>
          <configuration>
            <annotationProcessors>
              <annotationProcessor>io.vertx.codegen.CodeGenProcessor</annotationProcessor>
            </annotationProcessors>
            <compilerArgs>
              <arg>-AoutputDirectory=${project.basedir}/src/main</arg>
            </compilerArgs>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</pluginManagement>
~~~~

And here is a configuration example for Gradle:

```gradle
task annotationProcessing(type: JavaCompile, group: 'build') { // codegen
  source = sourceSets.main.java
  classpath = configurations.compile + configurations.compileOnly
  destinationDir = project.file('src/main/generated')
  options.compilerArgs = [
    "-proc:only",
    "-processor", "io.vertx.codegen.CodeGenProcessor",
    "-AoutputDirectory=${project.projectDir}/src/main"
  ]
}

compileJava {
  targetCompatibility = 1.8
  sourceCompatibility = 1.8

  dependsOn annotationProcessing
}

sourceSets {
  main {
    java {
      srcDirs += 'src/main/generated'
    }
  }
}
```

Besides you can use the `processor` classified dependency that declares the annotation processor as a
`META-INF/services/javax.annotation.processing.Processor`, if you do so, code generation happens automatically:

```
<dependency>
  <groupid>io.vertx</groupId>
  <artifactId>vertx-codegen</artifactId>
  <classifier>processor</classifier>
</dependency>
```

You still need to configure the `outputDirectory` for generating files non resources/classes as the processors
requires this option to know where to place them.

The processor is configured by a few options

- `codegen.output` : where the non Java classes / non resources are stored
- `codegen.output.<generator-name>` : relocate the output of _<generator-name>_ to another directory
- `codegen.generators` : a comma separated list of generators, each expression is a regex, allow to filter undesired generators

## API constraints

In order for code generation to work effectively, certain constraints are put on the Java interfaces.

The constraints are

* The API must be described as a set of Java interfaces, classes are not permitted
* Default methods are not permitted
* Nested interfaces are not permitted
* All interfaces to have generation performed on them must be annotated with the `io.vertx.codegen.annotations.VertxGen` annotation
* Fluent methods (methods which return a reference to this) must be annotated with the `io.vertx.codegen.annotations.Fluent` annotation
* Data object classes (classes which provide data (e.g. configuration) to methods) must be annotated with the `io.vertx.codegen.annotations.DataObject` annotation
* Data object classes must provide a constructor which takes a single `io.vertx.core.json.JsonObject` parameter.
* Methods where the return value must be cached in the API shim must be annotated with the `io.vertx.codegen.annotations.CacheReturn` annotation
* Only certain types are allowed as parameter or return value types for any API methods (defined below).
* Custom enums should be annotated with `@VertxGen`, although this is not mandatory to allow the usage of existing Java enums


### Permitted types

We define the following set _`Basic`_ of basic types:

* any primitive type
* any boxed primitive type
* `java.lang.String`

We define _`Json`_ as the set of types `io.vertx.core.json.JsonObject` and `io.vertx.core.json.JsonArray`

We define _`DataObject`_ as the set of user defined API types which are defined in its own class and annotated with `@DataObject`

We define _`TypeVar`_ as the set of of types variables where the variable is either declared by its generic method or its generic type

We define _`Api`_ as the set of user defined API types which are defined in its own interface and annotated with `@VertxGen`

We define _`Parameterized`_ as the set of user defined API types which are defined in its own interface and annotated with
`@VertxGen` where the type parameters belong to:
* the type `java.lang.Void`
* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* the set _`Api`_
* the set _`TypeVar`_

The following set _`Return`_ of types are permitted as return types from any API method:

* `void`
* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* `java.lang.Throwable`
* the set _`TypeVar`_
* `java.lang.Object`
* the set _`Api`_
* the set _`Parameterized`_
* type `java.util.List<C>` or `java.util.Set<C>` where `C` contains
    * the set _`Basic`_
    * the set _`Json`_
    * any enum type
    * the set _`Api`_
    * the set _`Da  taObject`_
* `java.util.Map<String, C>` where `C` contains
    * the set _`Basic`_
    * the set _`Json`_

The following set _`Param`_ of types are permitted as parameters to any API method:

* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* the type `java.lang.Throwable`
* the set _`TypeVar`_
* `java.lang.Object`
* the set _`Api`_
* the set _`Parameterized`_
* the type `java.lang.Class<T>` where `<T>` is among
    * the set _`Basic`_
    * the set _`Json`_
    * the set _`Api`_
* type `java.util.List<C>` or `java.util.Set<C>` where `C` contains
    * the set _`Basic`_
    * the set _`Json`_
    * the set _`DataObject`_
    * the set _`Api`_
* type `java.util.Map<String, C>` where `C` contains
    * the set _`Basic`_
    * the set _`Json`_
    * the set _`Api`_

In addition any API method can have as parameter:

* `io.vertx.java.core.Handler<io.vertx.java.core.AsyncResult<HA>>` where `HA` contains
    * the set _`Return`_ where `void` is interpreted as `java.lang.Void` minus `java.lang.Throwable`
* `io.vertx.java.core.Handler<H>` where `H` contains
    * the set _`Return`_ where `void` is interpreted as `java.lang.Void`
* `java.util.Function<T, R>` where  `T` contains _`Return`_ and `R` contains _`Param`_

Notes:

* Why no support for data object in `Map` param values ?

### Static factory methods

You may add static factory methods in your interfaces, e.g.

    interface MyInterface {

        static MyInterface newInterface(String foo) {
          return new ....
        }

    }


### Super interfaces

Interfaces can extend other interfaces which also have the `@VertxGen` annotation.

### *Concrete*/*abstract* interfaces

Interfaces annotated with `@VertxGen` can either be *concrete* or *abstract*, such information is important
for languages not supporting multiple class inheritance like Groovy:

- interfaces annotated with `@VertxGen(concrete = false)` are meant to be extended by *concrete* interfaces and
can inherit from *abstract* interfaces only.
- interfaces annotated with `@VertxGen` or `@VertxGen(concrete = true)` are implemented directly by Vertx
and can inherit at most one other *concrete* interface and any *abstract* interface

### Ignoring methods

If you do not wish a method to be used for generation you can annotate it with the `@GenIgnore` annotation.

## Modules

Generated types must belong to a module: a java package annotated with `@ModuleGen` that defines a module. Such
file is created in a file _package-info.java_.

A module must define:

- a `name` used when generating languages that don't follow Java package naming, like JavaScript or Ruby.
- a `groupPackage` to define the package of the group used for generating the generated package names
(for _Groovy_, _RxJava_ or _Ceylon_ generation):

```
@ModuleGen(name = "acme", groupPackage="com.acme")
package com.acme.myservice;
```

The group package must be a prefix of the annotated module, it defines the naming of the generate packages o
 for the modules that belongs to the same group, in this case:

- `com.acme.groovy...` for Groovy API
- `com.acme.rxjava...` for RxJava API

For this particular `com.acme.myservice` module we have:

- `com.acme.groovy.myservice` for Groovy API
- `com.acme.rxjava.myservice` for RxJava API

Vert.x Apis uses the `io.vertx` group package and `vertx-XYZ` name, this naming is _exclusively_ reserved
to Vert.x Apis.

NOTE: using Maven coordinates for name and group package is encouraged: the name corresponding to the
Maven _artifactId_ and the group package corresponding to the `groupId`.

## Data objects

A data object is a plain Java public class annotated with `@DataObject` that follows these minimum requirements:

* A constructor with `io.vertx.core.json.JsonObject` parameter type

Optionally a data object can define a public `io.vertx.core.json.JsonObject toJson()` method: such method makes the
data object convertible to `JsonObject`, the data object can then be used as an Api return type.

By default, a data object is responsible to decode from Json (via the `JsonObject` constructor) and encode
to Json (via the `toJson` method).

Data object converter can be generated with `@DataObject(generateConverter=true)` by Vert.x Core. Such
 Data object conversion recognize the following types as _member_ of any `@DataObject`:

* the set _`Basic`_
* these specific types
    * `io.vertx.core.Buffer`
    * `java.time.Instant`
* the set _`Json`_
* any data object class annotated with `@DataObject`
* type `java.util.List<C>` where `C` contains
    * the specific `io.vertx.core.Buffer` type
    * the set _`Basic`_
    * the set _`Json`_
    * any `@DataObject`
    * the Object type : the `List<Object>` acts like a `JsonArray`
* type `java.util.Map<String, C>` where `C` contains
    * the specific `io.vertx.core.Buffer` type
    * the set _`Basic`_
    * the set _`Json`_
    * any `@DataObject`
    * the Object type : the `Map<String, Object>` acts like a `JsonMap`

This is also used for data object _cheatsheet_ generation.

## Enums

Enum types can be freely used in an API, custom enum types *should* be annotated with `@VertxGen`
to allow processing of the enum. This is not mandatory to allow the reuse the existing Java enums.

Enums can be processed for providing more idiomatic APIs in some languages.

## Templates

We use MVEL templating to generate APIs.

There should be a single MVEL template for each language API that is to be generated.

The template will be called once for each interface that is annotated with `@VertxGen` in the Java API. One
output file (e.g. one .js file) will be created for each Java interface.

### Template variables for class models

The following variables are made available to templates:

* `ifaceSimpleName` - the simple name of the Java interface
* `ifaceFQCN` - the fully qualified class name of the Java interface
* `ifacePackageName` - the name of the Java package the Java interface belongs to
* `ifaceComment` - the class comment from the Java interface
* `concrete` - true when the interface is implemented by vert.x useful to decide the generation of a class or interface in the API shim
* `helper` - a helper class that of type `io.vertx.codegen.Helper` which contains useful methods for things such as
converting CamelCase to underscores.
* `methods` - a list of `MethodInfo` objects describing each method in the interface.
* `referencedTypes` - a list of strings representing the set of user defined types (also annotated with `VertxGen`) which
are referenced from the current interface
* `superTypes` - a list of `TypeInfo` representing the set of user defined types which the current interface extends from
* `concreteSuperType` - the concrete super type or null
* `abstractSuperTypes` - subset of `superTypes` which are *abstract*
* `methodMap` - this is a Map<String, MethodInfo> - which allows you to look up all methods with a given name
* `importedTypes`- this is a `Set<TypeInfo>` containing the types used by this class
* `referencedDataObjectTypes`- this is a `Set<TypeInfo>` containing the `DataObject` types used by this class
* `referencedEnumTypes`- this is a `Set<TypeInfo>` containing the `Enum` types used by this class

The `TypeInfo` represents a Java type:

* `name`. Generates a string of a form suitable for representing this type in source code using qualified names, for instance `io.vertx.core.Handler<io.vertx.core.buffer.Buffer>`
* `simpleName`. Generates a string of a form suitable for representing this type in source code using simple names, for instance `Handler<Buffer>`
* `toString`. Same as `name`
* `collectImports(Collection<TypeInfo.Class> imports)`. Collect all imports required by this type


The `TypeInfo.Class` is a subclass of `TypeInfo` representing a Java class:

* `kind`. An enum providing more information about the type
    * `STRING`, `BOXED_PRIMITIVE`, `PRIMITIVE`: basic types
    * `JSON_OBJECT`, `JSON_ARRAY`: io.vertx.core.json.JsonObject and io.vertx.core.json.JsonArray
    * `THROWABLE`: java.lang.Throwable
    * `VOID`: java.lang.Void
    * `OBJECT`: java.lang.Object
    * `LIST`, `SET`: corresponding java collections
    * `API`: a type annotated with @VertxGen
    * `DATA_OBJECT`: a type annotations with @DataObject
    * `HANDLER`: io.vertx.core.Handler
    * `ASYNC_RESULT`: io.vertx.core.AsyncResult
    * `ENUM`: An enum
    * `OTHER`: anything else

The `MethodInfo` object has the following fields:

* `name`. The name of the method
* `kind`. The method kind
    * `HANDLER`: last parameter type is `io.vertx.core.Handler<T>` and a `void` or _fluent_ return
    * `FUTURE`: last parameter type is `io.vertx.core.Handler<io.vertx.core.AsyncResult<T>>` and a `void` or _fluent_ return
    * `INDEX_GETTER`: an index getter
    * `INDEX_SETTER`: an index setter
    * `OTHER`: anything else
* `returnType`. The fully qualified return type (or `void`) of the method
* `fluent`. `true` if the method is fluent (i.e. returns a reference to the interface itself for chaining calls)
* `cacheReturn`. `true` if the generated API method should cache return value
* `comment`. Method comment.
* `params`. List of `ParamInfo` objects representing the parameters of the method.
* `staticMethod`. `true` if it's a static method.
* `typeParams`. The list of the type parameters declared by the method

The `ParamInfo` object has the following fields:

* `name`. The name of the parameter
* `type`. The type of the parameter as a `TypeInfo`
* `dataObject`. `true` If the parameter is a data object type.

### Template variables in data object models

* `type` - the `TypeInfo` of the current option
* `doc` - the `Doc` object
* `concrete`" - true when the option is implemented by vert.x - useful to decide the generation of a class or interface in the API shim
* `generateConverter` - whether a converter should be generated or not
* `inheritConverter` - whether the generated converter should convert all properties
* `properties` - a `Set<PropertyInfo>` of the available properties in this option
* `importedTypes` - the imported types by the option
* `superTypes` - a list of `TypeInfo` representing the set of user defined types which the current interface extends from
* `superType` - the supertype of this option ???
* `jsonifiable` - True if the object has a `toJson()` method

The `PropertyInfo` object has the following fields:

* `name`. The name of the property
* `type`. The type of the property as `TypeInfo`
* `doc` - the `Doc` object
* `declared`. True if the property is declared by its data object and does not override the same property from an ancestor
* `writerMethod`. The name of the setter/adder method in Java
* `readerMethod`. The optional name of the getter method in Java
* `array`. True if the property is an array
* `adder`. True if the property is an adder (addSomething)
* `jsonifiable`. True if the object can be converted somehow to json

### Incremental templating

Incremental templating allows the same template to process several models and create a single result. This is
useful when several sources files needs to generate a same file and the output is the result of the models. To
achieve incremental processing, a generator must declares `"incremental": true` in its descriptor.

During the processing phase, the codegen processors collects all the files generated by incremental templates
and groups them by file name. Obviously, the _filename_ expression of the generator needs to return an appropriate
string.

At the end of the processing phase, templates are invoked for each model, pretty much like the normal templating but
with the following differences:

- the variable `incrementalIndex` gives the sequence number of the current model, starting at 0
- the variable `incrementalSize` gives the total number of models processed by the template
- the variable `session` is a map provided that allows the template to maintain state
- the generated content are appended instead of overwritten

For instance the template:

```
@if{incrementalIndex==0}
<html>\n
  <body>\n
    <ul>\n
@end{}
      <li>@{type.name}</li>\n
@if{incrementalIndex==incrementalSize-1}
    </ul>\n
  </body>\n
</html>\n
@end{}
```

With `codegen.json`:

```
{
  "name": "index",
  "generators": [ {
    "kind": "class",
    "incremental": true,
    "filename": "'index.html'",
    "templateFilename": "html-index.templ"
  } ]
}
```

Generates an HTML page with the name of all the API classes.

### Skipping generation

Sometimes a template can skip the generation of the file. Setting the value of
`skipFile` to true will do that:

```
@code{skipFile=true}
```
