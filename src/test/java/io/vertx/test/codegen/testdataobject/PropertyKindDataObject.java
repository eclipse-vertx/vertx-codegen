package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DataObject
public interface PropertyKindDataObject {
  int getInteger();
  void setInteger(int i);
  List<Integer> getIntegerList();
  void setIntegerList(List<Integer> list);
  Set<Integer> getIntegerSet();
  void setIntegerSet(Set<Integer> list);
  Map<String, String> getMap();
  void setMap(Map<String, String> list);
  void addMap(String key, String value);
}
