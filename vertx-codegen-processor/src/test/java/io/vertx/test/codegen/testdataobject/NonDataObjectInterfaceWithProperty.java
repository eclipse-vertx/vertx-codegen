package io.vertx.test.codegen.testdataobject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface NonDataObjectInterfaceWithProperty<T extends NonDataObjectInterfaceWithProperty> {

  T setNonDataObjectProperty(String value);

}
