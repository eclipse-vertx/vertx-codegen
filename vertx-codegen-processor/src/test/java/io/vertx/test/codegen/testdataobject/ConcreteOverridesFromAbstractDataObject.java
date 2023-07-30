package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteOverridesFromAbstractDataObject extends AbstractDataObjectWithProperty {

  public ConcreteOverridesFromAbstractDataObject() {
  }

  public ConcreteOverridesFromAbstractDataObject(ConcreteOverridesFromAbstractDataObject other) {
  }

  public ConcreteOverridesFromAbstractDataObject(JsonObject json) {
  }

  @Override
  public ConcreteOverridesFromAbstractDataObject setOverriddenProperty(String value) {
    return this;
  }

  @Override
  public AbstractDataObjectWithProperty setAbstractProperty(String value) {
    return this;
  }
}
