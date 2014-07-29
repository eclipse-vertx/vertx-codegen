package io.vertx.codegen.testmodel;

import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class TestOptionsImpl implements TestOptions {

  private String foo;
  private int bar;
  private double wibble;

  public TestOptionsImpl() {
  }

  public TestOptionsImpl(JsonObject json) {
    this.foo = json.getString("foo", null);
    this.bar = json.getInteger("bar", 0);
    this.wibble = (double)json.getNumber("wibble", 0);
  }

  public String getFoo() {
    return foo;
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }

  public int getBar() {
    return bar;
  }

  public void setBar(int bar) {
    this.bar = bar;
  }

  public double getWibble() {
    return wibble;
  }

  public void setWibble(double wibble) {
    this.wibble = wibble;
  }
}
