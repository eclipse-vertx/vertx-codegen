package io.vertx.test.codegen;

import io.vertx.codegen.Case;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class CaseTest {

  @Test
  public void testFormatCamelCase() {
    formatCamelCase("", "");
    formatCamelCase("Foo", "foo");
    formatCamelCase("Foo", "Foo");
    formatCamelCase("FOO", "FOO");
    formatCamelCase("FooBar", "Foo", "Bar");
    formatCamelCase("FooBar", "Foo", "bar");
    formatCamelCase("FooBar", "Foo", "", "Bar");
    formatCamelCase("FOOBar", "FOO", "", "Bar");
  }

  @Test
  public void testParseCamelCase() {
    parseCamelCase("");
    parseCamelCase("Foo", "Foo");
    parseCamelCase("FooBar", "Foo", "Bar");
    parseCamelCase("FooBarJuu", "Foo", "Bar", "Juu");
    parseCamelCase("URL", "URL");
    parseCamelCase("URLDecoder", "URL", "Decoder");
    parseCamelCase("testSomething", "test", "Something");
    parseCamelCase("testURL", "test", "URL");
    parseCamelCase("test123", "test123");
  }

  @Test
  public void testFormatLowerCamelCase() {
    formatLowerCamelCase("", "");
    formatLowerCamelCase("foo", "foo");
    formatLowerCamelCase("foo", "Foo");
    formatLowerCamelCase("foo", "FOO");
    formatLowerCamelCase("fooBar", "Foo", "Bar");
    formatLowerCamelCase("fooBar", "Foo", "bar");
    formatLowerCamelCase("fooBar", "Foo", "", "Bar");
    formatLowerCamelCase("fooBar", "FOO", "", "Bar");
    formatLowerCamelCase("fooBarJuu", "Foo", "Bar", "Juu");
  }

  @Test
  public void testParseLowerCamelCase() {
    parseLowerCamelCase("");
    parseLowerCamelCase("foo", "foo");
    parseLowerCamelCase("fooBar", "foo", "Bar");
    parseLowerCamelCase("fooBarJuu", "foo", "Bar", "Juu");
    parseLowerCamelCase("URL", "URL");
    parseLowerCamelCase("URLDecoder", "URL", "Decoder");
    parseLowerCamelCase("testSomething", "test", "Something");
    parseLowerCamelCase("testURL", "test", "URL");
    parseLowerCamelCase("test123", "test123");
  }

  @Test
  public void testFormatQualifiedCase() {
    formatQualifiedCase("", "");
    formatQualifiedCase("foo", "foo");
    formatQualifiedCase("Foo", "Foo");
    formatQualifiedCase("foo.bar", "foo", "bar");
    formatQualifiedCase("foo.Bar", "foo", "Bar");
    formatQualifiedCase("foo.bar", "foo", "", "bar");
  }

  @Test
  public void testParseQualifiedCase() {
    parseQualifiedCase("");
    parseQualifiedCase("foo", "foo");
    parseQualifiedCase("foo.bar", "foo", "bar");
    parseQualifiedCase("foo.bar.juu", "foo", "bar", "juu");
    for (String test : Arrays.asList(".", ".foo", "foo.", "foo..bar")) {
      try {
        Case.QUALIFIED.parse(test);
        fail("Was expecting " + test + " to be rejected");
      } catch (Exception ignore) {
      }
    }
  }

  @Test
  public void testFormatKebabCase() {
    formatKebabCase("", "");
    formatKebabCase("foo", "foo");
    formatKebabCase("foo", "Foo");
    formatKebabCase("foo-bar", "Foo", "Bar");
    formatKebabCase("foo-bar", "Foo", "bar");
    formatKebabCase("foo-bar", "Foo", "", "Bar");
  }

  @Test
  public void testParseKebabCase() {
    parseKebabCase("");
    parseKebabCase("foo", "foo");
    parseKebabCase("foo-bar", "foo", "bar");
    parseKebabCase("foo-bar-juu", "foo", "bar", "juu");
    for (String test : Arrays.asList("-", "-foo", "foo-", "foo--bar")) {
      try {
        Case.KEBAB.parse(test);
        fail("Was expecting " + test + " to be rejected");
      } catch (Exception ignore) {
      }
    }
  }

  @Test
  public void testFormatSnakeCase() {
    formatSnakeCase("", "");
    formatSnakeCase("foo", "foo");
    formatSnakeCase("foo", "Foo");
    formatSnakeCase("foo_bar", "Foo", "Bar");
    formatSnakeCase("foo_bar", "Foo", "bar");
    formatSnakeCase("foo_bar", "Foo", "", "Bar");
  }

  @Test
  public void testParseSnakeCase() {
    parseSnakeCase("");
    parseSnakeCase("foo", "foo");
    parseSnakeCase("foo_bar", "foo", "bar");
    parseSnakeCase("foo_bar_juu", "foo", "bar", "juu");
    for (String test : Arrays.asList("_", "_foo", "foo_", "foo__bar")) {
      try {
        Case.SNAKE.parse(test);
        fail("Was expecting " + test + " to be rejected");
      } catch (Exception ignore) {
      }
    }
  }

  @Test
  public void testConversion() {
    assertEquals("foo-bar-juu", Case.CAMEL.to(Case.KEBAB, "FooBarJuu"));
    assertEquals("foo_bar_juu", Case.CAMEL.to(Case.SNAKE, "FooBarJuu"));
    assertEquals("FooBarJuu", Case.SNAKE.to(Case.CAMEL, "foo_bar_juu"));
    assertEquals("FooBarJuu", Case.KEBAB.to(Case.CAMEL, "foo-bar-juu"));
  }

  private void formatCamelCase(String expected, String... atoms) {
    assertCase(Case.CAMEL, expected, atoms);
  }

  private void formatLowerCamelCase(String expected, String... atoms) {
    assertCase(Case.LOWER_CAMEL, expected, atoms);
  }

  private void formatQualifiedCase(String expected, String... atoms) {
    assertCase(Case.QUALIFIED, expected, atoms);
  }

  private void formatSnakeCase(String expected, String... atoms) {
    assertCase(Case.SNAKE, expected, atoms);
  }

  private void formatKebabCase(String expected, String... atoms) {
    assertCase(Case.KEBAB, expected, atoms);
  }

  private void parseSnakeCase(String s, String... expected) {
    parseCase(Case.SNAKE, s, expected);
  }

  private void parseCamelCase(String s, String... expected) {
    parseCase(Case.CAMEL, s, expected);
  }

  private void parseLowerCamelCase(String s, String... expected) {
    parseCase(Case.LOWER_CAMEL, s, expected);
  }

  private void parseQualifiedCase(String s, String... expected) {
    parseCase(Case.QUALIFIED, s, expected);
  }

  private void parseKebabCase(String s, String... expected) {
    parseCase(Case.KEBAB, s, expected);
  }

  private void assertCase(Case _case, String expected, String... atoms) {
    assertEquals(expected, _case.format(Arrays.asList(atoms)));
  }

  private void parseCase(Case _case, String s, String... expected) {
    assertEquals(Arrays.asList(expected), _case.parse(s));
  }
}
