# Vert.x Codegen Processor

## API generator

A code generator is a class extending `io.vertx.codegen.processor.Generator` loaded by a class implementing `io.vertx.codegen.processor.GeneratorLoader` declared as a `META-INF/services/io.vertx.codegen.processor.GeneratorLoader` JVM service.

There can be as many generators as you like.

## Generated output

A generator can create 3 different kinds of output: Java classes, resources or absolute files.

### Generated Java classes

A generator declaring a filename not starting with `/`' matching a Java FQN followed by `.java` suffix will have its content generated as a Java class. This class will be automatically compiled by the same compiler (that's a Java compiler feature).

The generated files are handled by the Java compiler (`-s` option), usually build tools configures the compiler to store them in a specific build location, for instance Maven by default uses the `target/generated-sources/annotations` directory.

The following generators use it:

- Data object [json](../vertx-codegen-json)/[protobuf](../vertx-codegen-protobuf) converters
- [Service proxies](https://vertx.io/docs/vertx-service-proxy/java/)
- [RxJava-ified](https://vertx.io/docs/vertx-rx/java2/) classes API

### Generated resources

A file not starting with `/` is considered as a java resource, its content generated is considered as a compiler resource. This resource will be stored in the generated sources directory and the generated class directory.

Generated files are handled by the Java compiler (`-s` option), usually build tools configures the compiler to store them in a specific build location, for instance Maven by default uses the `target/generated-sources/annotations` directory.

### Absolute files

A file starting with `/` will be written as an absolute file on the filesystem, this file is not managed by the java compiler.

## Processor configuration

You can configure the `>io.vertx.codegen.processor.Processor` as any Java annotation processor, here is how to do with Maven:

~~~~
<pluginManagement>
  <plugins>
    <!-- Configure the execution of the compiler to execute the codegen processor -->
    <plugin>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.13.0</version>
      <configuration>
        <release>11</release>
        <encoding>${project.build.sourceEncoding}</encoding>
        <!-- Important: there are issues with apt and incremental compilation in the maven-compiler-plugin -->
        <useIncrementalCompilation>false</useIncrementalCompilation>
      </configuration>
      <executions>
        <execution>
          <id>default-compile</id>
          <configuration>
            <annotationProcessors>
              <annotationProcessor>io.vertx.codegen.processor.Processor</annotationProcessor>
            </annotationProcessors>
             <!-- It is new option since v3.5 to instruct compiler detect annotation processors classpath -->
            <annotationProcessorPaths>
              <path>
                <groupId>io.vertx</groupId>
                <artifactId>vertx-codegen</artifactId>
                <version>${vertx.version}</version>
              </path>
              <!-- ... more path such as vertx-service-proxy/vertx-rx-java2 depends on what you want to generate ... -->
            </annotationProcessorPaths>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</pluginManagement>
~~~~

And here is a configuration example for Gradle (since `Gradle 5.0`):

Gradle Groovy

```gradle
dependencies {
    compileOnly("io.vertx:vertx-codegen:4.0.2")
    // more vertx-service-proxy/vertx-rx-java2
    // compileOnly("io.vertx:vertx-rx-java2:4.0.2")
    annotationProcessor("io.vertx:vertx-codegen:4.0.2")
}

task annotationProcessing(type: JavaCompile, group: 'other') { // codegen
  source = sourceSets.main.java
  classpath = configurations.compile
  destinationDir = project.file('${project.buildDir}/generated/main/java')
  options.annotationProcessorPath = configurations.compileClasspath
  options.compilerArgs = [
    "-proc:only",
    "-processor", ">io.vertx.codegen.processor.Processor"
  ]
}

compileJava {
  dependsOn annotationProcessing
}

sourceSets {
  main {
    java {
      srcDirs += '${project.buildDir}/generated/main/java'
    }
  }
}
```

Gradle Kotlin

```gradle
dependencies {
    compileOnly("io.vertx:vertx-codegen:4.0.2")
    // more vertx-service-proxy/vertx-rx-java2
    // compileOnly("io.vertx:vertx-rx-java2:4.0.2")
    annotationProcessor("io.vertx:vertx-codegen:4.0.2")
}

tasks.register<JavaCompile>("annotationProcessing") {
    group = "other"
    source = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).java
    destinationDir = project.file("${project.buildDir}/generated/main/java")
    classpath = configurations.compileClasspath.get()
    options.annotationProcessorPath = configurations.compileClasspath.get()
    options.compilerArgs = listOf(
        "-proc:only",
        "-processor", ">io.vertx.codegen.processor.Processor"
    )
}

tasks.compileJava {
    dependsOn(tasks.named("annotationProcessing"))
}

sourceSets {
    main {
        java {
            srcDirs(project.file("${project.buildDir}/generated/main/java"))
        }
    }
}
```

Besides you can use the `processor` classified dependency that declares the annotation processor as a
`META-INF/services/javax.annotation.processing.Processor`, if you do so, code generation triggers automatically:

```xml
<dependency>
  <groupid>io.vertx</groupId>
  <artifactId>vertx-codegen</artifactId>
  <classifier>processor</classifier>
</dependency>
```

The processor is configured by a the `codegen.generators` option,  a comma separated list of generators, each expression
is a regex, allow to filter undesired generators

## API constraints

In order for code generation to work effectively, certain constraints are put on the Java interfaces.

The constraints are

* The API must be described as a set of Java interfaces, classes are not permitted
* Nested interfaces are not permitted
* All interfaces to have generation performed on them must be annotated with the `io.vertx.codegen.annotations.VertxGen` annotation
* Fluent methods (methods which return a reference to this) must be annotated with the `io.vertx.codegen.annotations.Fluent` annotation
* Data object classes (classes which provide data (e.g. configuration) to methods) must be annotated with the `io.vertx.codegen.annotations.DataObject` annotation
* Data object classes must provide a constructor which takes a single `io.vertx.core.json.JsonObject` or `java.lang.String` parameter.
* Methods where the return value can be cached shall be annotated with the `io.vertx.codegen.annotations.CacheReturn` annotation
* Types parameters or return value types for any API methods are constrainted (defined below).
* Enums should be annotated with `@VertxGen`, although this is not mandatory to allow the usage of existing Java enums
* `JsonMapper` implementations must expose an instance as a `public static final [JsonDesdeType] INSTANCE` field

### Permitted types

We define _`Basic`_:

* any primitive type
* any boxed primitive type
* `java.lang.String`

We define _`Json`_:

* `io.vertx.core.json.JsonObject`
* `io.vertx.core.json.JsonArray`

We define _`DataObject`_:

* The set of user defined API types which are defined in its own class and annotated with `@DataObject`
* The set of types that have an associated mapper declared with a `json-mappers.properties` file

We define _`TypeVar`_ as the set of types variables where the variable is either declared by its generic method or its generic type

We define _`Api`_ as the set of user defined API types which are defined in its own interface and annotated with `@VertxGen`

We define _`JavaType`_ as the set of any Java type that does not belong to _`Basic`_, _`Json`_, _`DataObject`_, _`TypeVar`_ and _`Api`_, e.g `java.net.Socket`.

We define _`Parameterized`_ as the set of user defined API types which are defined in its own interface and annotated with `@VertxGen` where type parameters belong to:

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
* the set _`JavaType`_ (under restriction, see below)
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
* the set _`JavaType`_ (under restriction, see below)
* type `java.util.List<C>`, `java.util.Set<C>` or `java.util.Map<String, C>` where `C` belongs to `ContainerValueType`
* `io.vertx.core.Future<HA>` where `HA` contains  the set _`Return`_ where `void` is interpreted as `java.lang.Void` minus `java.lang.Throwable`

The following set _`Param`_ of types are permitted as parameters to any API method:

* the set _`Basic`_
* the set _`Json`_
* the set _`DataObject`_
* any enum type
* the type `java.lang.Throwable`
* the set _`TypeVar`_
* `java.lang.Object`
* the set _`Api`_
* the set _`JavaType`_ (under restriction, see below)
* the set _`Parameterized`_
* the type `java.lang.Class<T>` where `<T>` is among
    * the set _`Basic`_
    * the set _`Json`_
    * the set _`Api`_
    * the set _`JavaType`_
* type `java.util.List<C>`, `java.util.Set<C>` or `java.util.Map<String, C>` where `C` belongs to `ContainerValueType`

In addition,

* `java.util.function.Function<T, R>` where  `T` contains _`Return`_ and `R` contains _`Param`_
* `java.util.function.Supplier<R>` where `R` contains _`Param`_
* `io.vertx.java.core.Handler<H>` where `H` contains the set _`Return`_ where `void` is interpreted as `java.lang.Void`

By default, method parameters shall declare types among the _`Param`_ set and return types among the _`Return`_ set.

However, methods can be annotated with `@GenIgnore(GenIgnore.PERMITTED_TYPE)` to leave this restriction. Such method limit the translation of the method to other languages, so it should be used with care. It is useful to allow method previously annotated with `@GenIgnore` to be available in code generator like RxJava that can handle Java types.

### Nullable types

The `io.vertx.codegen.annotations.Nullable` annotates types declarations to signal the type value can be `null`.

Method return type can be `io.vertx.codegen.annotations.Nullable`:

```java
@Nullable String getAttribute(String name);
```

As well as method parameter type:

```java
void close(@Nullable Handler<Void> closeHandler);
```

WARNING: type validation is a non goal of this feature, its purpose is to give hints to code generators

The following rules apply to nullable types:

* primitive types cannot be nullable
* method parameter type can be nullable
* non-fluent method return type can be nullable
* `io.vertx.core.Handler` type argument can be nullable, `java.lang.Void`, `java.lang.Object` and type variables are implicitly nullable
* `io.vertx.core.Future` type argument can be nullable, `java.lang.Void`, `java.lang.Object` and type variables are implicitly nullable
* the `java.lang.Object` type is always nullable
* Liskov substitution principle
  * a method overriding another method `inherits` the nullable usage of the overridden method, it should not declare it, but it is encouraged to do so
  * a method overriding another method cannot declare nullable in its types

In addition, these rules apply to nullable type arguments:

* methods cannot declare generic api types with nullable type arguments, e.g. `<T> void method(GenericApi<Nullable T> api)` is not permitted
* methods can declare nullable collection, e.g. `void method(List<Nullable String> list)` is allowed

### Instance Methods

You can declare methods in your interfaces, e.g.

```java
interface MyInterface {

  void doSomething(String foo);

}
```

Default method works as well

```java
interface MyInterface {

  default String doSomething(String foo) {
    return foo != null ? new StringBuilder(foo).reverse().toString() : null;
  }

}
```

### Asynchronous operations

Asynchronous operations are declared using a method returning a future.

```java
@VertxGen
public interface SomeApi {
  Future<Buffer> getValue();
}
```

### Ignored methods

Methods annotated with `io.vertx.codegen.annotations.GenIgnore` are simply ignored by codegen, this  is useful when the API provides Java specific methods, for instance a method uses a type not permitted  by codegen.

### Static methods

You can declare static methods in your interfaces, e.g.

```java
interface MyInterface {

  static MyInterface newInterface(String foo) {
    return new MyInterfaceImpl();
  }

}
```

### Fields

You can declare fields in your interfaces, e.g.

```java
interface MyInterface {

  int SOME_CONSTANT = 4;

}
```

### Super interfaces

Interfaces can extend other interfaces which also have the `@VertxGen` annotation.

### *Concrete*/*abstract* interfaces

Interfaces annotated with `@VertxGen` can either be *concrete* or *abstract*, such information is important
for languages not supporting multiple class inheritance like Groovy:

- interfaces annotated with `@VertxGen(concrete = false)` are meant to be extended by *concrete* interfaces and
can inherit from *abstract* interfaces only.
- interfaces annotated with `@VertxGen` or `@VertxGen(concrete = true)` are implemented directly by Vertx
and can inherit at most one other *concrete* interface and any *abstract* interface

### Ignored methods

If you do not wish a method to be used for generation you can annotate it with the `@GenIgnore` annotation.

## Modules

Generated types must belong to a module: a java package annotated with `@ModuleGen` that defines a module. Such
file is created in a file _package-info.java_.

A module must define:

- a `name` used when generating languages that don't follow Java package naming, like JavaScript or Ruby.
- a `groupPackage` to define the package of the group used for generating the generated package names
(for _Groovy_, _RxJava_ or _Ceylon_ generation):

```java
@ModuleGen(name = "acme", groupPackage="com.acme")
package com.acme.myservice;
```

The group package must be a prefix of the annotated module, it defines the naming of the generate packages o
 for the modules that belongs to the same group, in this case:

- `com.acme.rxjava...` for RxJava API

For this particular `com.acme.myservice` module we have:

- `com.acme.rxjava.myservice` for RxJava API

Vert.x Apis uses the `io.vertx` group package and `vertx-XYZ` name, this naming is _exclusively_ reserved
to Vert.x Apis.

NOTE: using Maven coordinates for name and group package is encouraged: the name corresponding to the
Maven _artifactId_ and the group package corresponding to the `groupId`.

## Data objects

A _Data object_ is a type that can be converted back and forth to a Json type.

You can declare data objects by:

* Defining a mapper in the `META-INF/vertx/json-mappers.properties` file
* Or annotating the type itself with `@DataObject`

### Json mappers

A json mapper for type `T` is a method that maps any object or enum of type `Type`, where `J` can be:

* `JsonArray` or `JsonObject`
* a concrete type extending `Number` such as `Long` or `Double`
* `String`
* `Boolean`

Json mapped types can be used anywhere a json types used are.

A json mapper turns any Java type into a data object type.

#### Object

You can declare them as public static methods:

```java
package com.example;

public class MyMappers {

  public static String serialize(ZonedDateTime date) {
    return date.toString();
  }

  public static ZonedDateTime deserialize(String s) {
    return ZonedDateTime.parse(s);
  }
}
```

These mappers need to be declared in a `META-INF/vertx/json-mappers.properties` file as follows:

```properties
java.time.ZonedDateTime.serializer=com.example.MyMappers#serializeZonedDateTime
java.time.ZonedDateTime.deserializer=com.example.MyMappers#deserializedZoneDateTime
```

#### Enum

Enum can be defined with values parameters passed to a constructor. In this use case, you can't use default behavior of codegen (`#valueOf()` and `#name()`), you need to define like Object `serializer` and `deserializer`.

```java
package com.example;

public enum MyEnumWithCustomFactory {
  DEV("dev", "development"), ITEST("itest", "integration-test");

  private String[] names = new String[2];

  MyEnumWithCustomFactory(String pShortName, String pLongName) {
    names[0] = pShortName;
    names[1] = pLongName;
  }

  public String getLongName() {
    return names[1];
  }

  public String getShortName() {
    return names[0];
  }

  public static MyEnumWithCustomFactory of(String pName) {
    for (MyEnumWithCustomFactory item : MyEnumWithCustomFactory.values()) {
      if (item.names[0].equalsIgnoreCase(pName) || item.names[1].equalsIgnoreCase(pName)
              || pName.equalsIgnoreCase(item.name())) {
        return item;
      }
    }
    return DEV;
  }

}
```

You can declare them as public static methods:

```java
public class MyEnumWithCustomFactory {
  public static String serialize(MyEnumWithCustomFactory value) {
    return value.getLongName();
  }

  public static MyEnumWithCustomFactory deserialize(String value) {
    return MyEnumWithCustomFactory.of(value);
  }
}
```

These mappers need to be declared in a `META-INF/vertx/json-mappers.properties` file as follows:

```properties
com.example.MyEnumWithCustomFactory.serializer=com.example.MyEnumWithCustomFactory#serialize
com.example.MyEnumWithCustomFactory.deserializer=com.example.MyEnumWithCustomFactory#deserialize
```

### `@DataObject` annotated types

A `@DataObject` annotated type is a Java class with the only purpose to be a container for data.

A data object can be created from JSON with a constructor or a factory method:

_with a constructor_
```java
public class MyDataObject {
  public MyDataObject(JsonObject json) {
      // ...
  }
}
```

_with a factory_
```java
public class MyDataObject {
  public static MyDataObject fromJson(JsonObject json) {
    // ...
  }
}
```

A data object can be converted to JSON with a `toJson()` method:

_with a factory_
```java
public class MyDataObject {
  public JsonObject toJson() {
  // ...
  }
}
```

### JSON converter generation

The data object/json conversion can be tedious and error-prone.

Vertx-codegen can automate it, generating for you an auxiliary class that implements the conversion logic.  The generated converter handles the type mapping as well as the json naming convention.

Converters are generated when the data object is annotated with `@JsonGen`. Generation happens for data object declared
properties, ancestor properties are omitted, unless `inheritConverter` is set: `@JsonGen(inheritConverter=true)`.

Converters are named by appending the `Converter` suffix to the data object class name, e.g, `ContactDetails` -> `ContactDetailsConverter`. A generated converter declares two static methods:

- `public static void fromJson(JsonObject json, ContactDetails obj)`
- `public static void toJson(ContactDetails obj, JsonObject json)`

The former can be used in json constructors, the latter the `toJson` methods.

```java
@DataObject
@JsonGen
public class ContactDetails {

  public ContactDetails(JsonObject json) {
    this();
    ContactDetailsConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    ContactDetailsConverter.toJson(this, json);
    return json;
  }
}
```

The json converter generator recognize the following types as _member_ of any `@DataObject`:

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

In addition a data object can also have multi-valued properties as a `java.util.List<V>`/`java.util.Set<V>` or a
`java.util.Map<String, V>` where the `<V>` is a supported single valued type or `java.lang.Object`
that stands for anything converted by `io.vertx.core.json.JsonObject` and `io.vertx.core.json.JsonArray`.

List/set multi-valued properties can be declared via a _setter_ :

_a multi valued setter_
```java
@DataObject
@JsonGen
public class WebServerOptions {
  ...
  public WebServerOptions setCertificates(List<String> certificates) {
    this.certificates = certificates;
    return this;
  }
  ...
}
```

Or:

_a multi valued adder_
```java
@DataObject
@JsonGen
public class WebServerOptions {
  ...
  public WebServerOptions addCertificate(String certificate) {
    this.certificates.add(certificate);
    return this;
  }
}
```

Map properties can only be declared with a _setter_.

NOTE: these examples uses a _fluent_ return types for providing a better API, this is not mandatory but
encouraged.

## Enums

Enum types can be freely used in an API, custom enum types *should* be annotated with `@VertxGen`
to allow processing of the enum. This is not mandatory to allow the reuse the existing Java enums.

Enums can be processed for providing more idiomatic APIs in some languages.
