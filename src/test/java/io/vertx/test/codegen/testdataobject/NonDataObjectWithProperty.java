package io.vertx.test.codegen.testdataobject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface NonDataObjectWithProperty<T extends NonDataObjectWithProperty> {

  T setNonDataObjectProperty(String value);

}
