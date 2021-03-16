package io.vertx.test.codegen.testapi.jsonmapper;

import io.vertx.codegen.annotations.VertxGen;

/**MyEnumWithCustomConstructor doc*/
@VertxGen
public enum MyEnumWithCustomConstructor {
  
  /**DEV doc*/
  DEV("dev", "development"), 
  
  /**ITEST doc*/
  ITEST("itest", "integration-test");

  public static MyEnumWithCustomConstructor of(String pName) {
    for (MyEnumWithCustomConstructor item : MyEnumWithCustomConstructor.values()) {
      if (item.names[0].equalsIgnoreCase(pName) || item.names[1].equalsIgnoreCase(pName)
          || pName.equalsIgnoreCase(item.name())) {
        return item;
      }
    }
    return DEV;
  }

  private String[] names = new String[2];

  MyEnumWithCustomConstructor(String pShortName, String pLongName) {
    names[0] = pShortName;
    names[1] = pLongName;
  }

  public String getLongName() {
    return names[1];
  }

  public String getShortName() {
    return names[0];
  }

}
