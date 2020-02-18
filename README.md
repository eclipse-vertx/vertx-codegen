# Vert.x API Generation

[![Build Status](https://travis-ci.org/vert-x3/vertx-codegen.svg?branch=master)](https://travis-ci.org/vert-x3/vertx-codegen)

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
              <arg>-Acodegen.output=${project.basedir}/src/main</arg>
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
    "-Acodegen.output=${project.projectDir}/src/main"
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

You still need to configure the `codegen.output` for generating files non resources/classes as the processors
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
* JsonMapper implementations must expose an instance as a `public static final [JsonDesdeType] INSTANCE` field

### Permitted types

We define the following set _`Basic`_ of basic types:

* any primitive type
* any boxed primitive type
* `java.lang.String`

We define _`Json`_ as the set of types `io.vertx.core.json.JsonObject` and `io.vertx.core.json.JsonArray`

We define _`DataObject`_:

* The set of user defined API types which are defined in its own class and annotated with `@DataObject`
* The set of types that have an associated mapper declared with the `@Mapper` annotation

We define _`TypeVar`_ as the set of of types variables where the variable is either declared by its generic method or its generic type

We define _`Api`_ as the set of user defined API types which are defined in its own interface and annotated with `@VertxGen`

We define _`JavaType`_ as the set of any Java type that does not belong to _`Basic`_, _`Json`_, _`DataObject`_, _`TypeVar`_ and _`Api`_, e.g `java.net.Socket`.
Methods are not allowed to declare such type by default and can be annotated with `@GenIgnore(GenIgnore.PERMITTED_TYPE)` to allow them. Such
method limit the translation of the method to other languages, so it should be used with care. It is useful to allow method
previously annotated with `@GenIgnore` to be available in code generator like RxJava that can handle Java types.

We define _`Parameterized`_ as the set of user defined API types which are defined in its own interface and annotated with
`@VertxGen` where the type parameters belong to:
* the type `java.lang.Void`
* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* the set _`Api`_
* the set _`TypeVar`_

We define _`ContainerValueType`_ as the set of any Java type that belongs to:
* the set _`Basic`_
* the set _`Json`_
* any enum type
* the set _`Api`_
* the set _`DataObject`_
* the set _`JavaType`_
* `java.lang.Object`

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
* the set _`JavaType`_
* type `java.util.List<C>`, `java.util.Set<C>` or `java.util.Map<String, C>` where `C` belongs to `ContainerValueType`

The following set _`Param`_ of types are permitted as parameters to any API method:

* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* the type `java.lang.Throwable`
* the set _`TypeVar`_
* `java.lang.Object`
* the set _`Api`_
* the set _`JavaType`_
* the set _`Parameterized`_
* the type `java.lang.Class<T>` where `<T>` is among
    * the set _`Basic`_
    * the set _`Json`_
    * the set _`Api`_
    * the set _`JavaType`_
* type `java.util.List<C>`, `java.util.Set<C>` or `java.util.Map<String, C>` where `C` belongs to `ContainerValueType`

In addition any _`Api_ method can have as parameter:

* `io.vertx.java.core.Handler<io.vertx.java.core.AsyncResult<HA>>` where `HA` contains
    * the set _`Return`_ where `void` is interpreted as `java.lang.Void` minus `java.lang.Throwable`
* `io.vertx.java.core.Handler<H>` where `H` contains
    * the set _`Return`_ where `void` is interpreted as `java.lang.Void`
* `java.util.Function<T, R>` where  `T` contains _`Return`_ and `R` contains _`Param`_

Notes:

* Why no support for data object in `Map` param values ?

### Instance Methods

You can declare methods in your interfaces, e.g.

    interface MyInterface {

        void doSomething(String foo);

    }

Default method works as well

    interface MyInterface {

        default String doSomething(String foo) {
          return foo != null ? new StringBuilder(foo).reverse().toString() : null;
        }

    }


### Static methods

You can declare static methods in your interfaces, e.g.

    interface MyInterface {

        static MyInterface newInterface(String foo) {
          return new ....
        }

    }

### Fields

You can declare fields in your interfaces, e.g.

    interface MyInterface {

        int SOME_CONSTANT = 4;

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

A _Data object_ is a type that can be converted back and forth to a Json type.

You can declare data objects by:

* Defining an annotated `@Mapper` method or function for it
* Or annotating the type itself with `@DataObject`

### Json mappers

A json mapper for type `T` is a method or function that maps any object of type `Type`, where `J` can be:

* `JsonArray` or `JsonObject`
* a concrete type extending `Number` such as `Long` or `Double`
* `String`
* `Boolean`

Json mapped types can be used anywhere a json types used are.

A json mapper turns any Java type into a data object type.

You can declare them as public static methods:

```java
@Mapper
public static String serialize(ZonedDateTime date) {
  return date.toString();
}

@Mapper
public static ZonedDateTime deserialize(String s) {
  return ZonedDateTime.parse(s);
}
```

Or as functions:

```java
@Mapper
public static final Function<ZonedDateTime, String> SERIALIZER = date -> date.toString();

@Mapper
public static final Function<String, ZonedDateTime> DESERIALIZER = s -> ZonedDateTime.parse(s);
```

### `@DataObject` annotated types

A `@DataObject` annotated type is a Java class with the only purpose to be a container for data.

* Codegen recognizes the type as deserializable when the annotated type has a `io.vertx.core.json.JsonObject` constructor
* The mapper recognizes the type as serializable when the annotated type has a `io.vertx.core.json.JsonObject toJson()` method

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
