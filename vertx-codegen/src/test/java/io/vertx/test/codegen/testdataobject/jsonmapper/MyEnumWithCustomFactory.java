package io.vertx.test.codegen.testdataobject.jsonmapper;

public enum MyEnumWithCustomFactory {
  DEV("dev", "development"), ITEST("itest", "integration-test");

  public static MyEnumWithCustomFactory of(String pName) {
    for (MyEnumWithCustomFactory item : MyEnumWithCustomFactory.values()) {
      if (item.names[0].equalsIgnoreCase(pName) || item.names[1].equalsIgnoreCase(pName)
          || pName.equalsIgnoreCase(item.name())) {
        return item;
      }
    }
    return DEV;
  }

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

}
