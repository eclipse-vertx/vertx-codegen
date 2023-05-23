package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;

import java.util.Objects;

// Temporary Test Object, maybe will switch to test with TestDataObject
@DataObject(generateConverter = true, protoConverter = true)
public class Address {
  private String name;
  private Float longitude;
  private Float latitude;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(name, address.name) && Objects.equals(longitude, address.longitude) && Objects.equals(latitude, address.latitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, longitude, latitude);
  }
}
