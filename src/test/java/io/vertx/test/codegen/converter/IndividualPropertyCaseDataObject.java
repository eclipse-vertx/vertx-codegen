package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.DataObjectProperty;
import io.vertx.codegen.type.CaseFormat;
import io.vertx.core.json.JsonObject;

/**
 * @author Richard Gomez
 */
@DataObject(generateConverter = true)
public class IndividualPropertyCaseDataObject {

  @DataObjectProperty(caseFormat = CaseFormat.LOWER_CAMEL)
  private String lowerCamelCase;
  @DataObjectProperty(caseFormat = CaseFormat.UPPER_CAMEL)
  private String upperCamelCase;
  @DataObjectProperty(caseFormat = CaseFormat.KEBAB)
  private String kebabCase;
  @DataObjectProperty(caseFormat = CaseFormat.SNAKE)
  private String snakeCase;

  public IndividualPropertyCaseDataObject() {
  }

  public IndividualPropertyCaseDataObject(IndividualPropertyCaseDataObject copy) {
  }

  public IndividualPropertyCaseDataObject(JsonObject json) {
  }

  public String getLowerCamelCase() {
    return lowerCamelCase;
  }

  public void setLowerCamelCase(String lowerCamelCase) {
    this.lowerCamelCase = lowerCamelCase;
  }

  public String getUpperCamelCase() {
    return upperCamelCase;
  }

  public void setUpperCamelCase(String upperCamelCase) {
    this.upperCamelCase = upperCamelCase;
  }

  public String getKebabCase() {
    return kebabCase;
  }

  public void setKebabCase(String kebabCase) {
    this.kebabCase = kebabCase;
  }

  public String getSnakeCase() {
    return snakeCase;
  }

  public void setSnakeCase(String snakeCase) {
    this.snakeCase = snakeCase;
  }

}
