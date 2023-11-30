package io.vertx.test.codegen;

import io.vertx.codegen.format.CamelCase;
import io.vertx.codegen.format.Case;
import io.vertx.codegen.format.KebabCase;
import io.vertx.codegen.format.LowerCamelCase;
import io.vertx.codegen.format.QualifiedCase;
import io.vertx.codegen.format.SnakeCase;
import org.junit.Assert;
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
        QualifiedCase.INSTANCE.parse(test);
        Assert.fail("Was expecting " + test + " to be rejected");
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
        KebabCase.INSTANCE.parse(test);
        Assert.fail("Was expecting " + test + " to be rejected");
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
        SnakeCase.INSTANCE.parse(test);
        Assert.fail("Was expecting " + test + " to be rejected");
      } catch (Exception ignore) {
      }
    }
  }

  @Test
  public void testConversion() {
    Assert.assertEquals("foo-bar-juu", CamelCase.INSTANCE.to(KebabCase.INSTANCE, "FooBarJuu"));
    Assert.assertEquals("foo_bar_juu", CamelCase.INSTANCE.to(SnakeCase.INSTANCE, "FooBarJuu"));
    Assert.assertEquals("FooBarJuu", SnakeCase.INSTANCE.to(CamelCase.INSTANCE, "foo_bar_juu"));
    Assert.assertEquals("FooBarJuu", KebabCase.INSTANCE.to(CamelCase.INSTANCE, "foo-bar-juu"));
  }

  private void formatCamelCase(String expected, String... atoms) {
    assertCase(CamelCase.INSTANCE, expected, atoms);
  }

  private void formatLowerCamelCase(String expected, String... atoms) {
    assertCase(LowerCamelCase.INSTANCE, expected, atoms);
  }

  private void formatQualifiedCase(String expected, String... atoms) {
    assertCase(QualifiedCase.INSTANCE, expected, atoms);
  }

  private void formatSnakeCase(String expected, String... atoms) {
    assertCase(SnakeCase.INSTANCE, expected, atoms);
  }

  private void formatKebabCase(String expected, String... atoms) {
    assertCase(KebabCase.INSTANCE, expected, atoms);
  }

  private void parseSnakeCase(String s, String... expected) {
    parseCase(SnakeCase.INSTANCE, s, expected);
  }

  private void parseCamelCase(String s, String... expected) {
    parseCase(CamelCase.INSTANCE, s, expected);
  }

  private void parseQualifiedCase(String s, String... expected) {
    parseCase(QualifiedCase.INSTANCE, s, expected);
  }

  private void parseKebabCase(String s, String... expected) {
    parseCase(KebabCase.INSTANCE, s, expected);
  }

  private void assertCase(Case _case, String expected, String... atoms) {
    Assert.assertEquals(expected, _case.format(Arrays.asList(atoms)));
  }

  private void parseCase(Case _case, String s, String... expected) {
    Assert.assertEquals(Arrays.asList(expected), _case.parse(s));
  }
}
