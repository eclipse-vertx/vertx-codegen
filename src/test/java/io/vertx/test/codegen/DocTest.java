package io.vertx.test.codegen;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.Generator;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Token;
import io.vertx.test.codegen.doc.FooEnum;
import io.vertx.test.codegen.doc.LinkLabel;
import io.vertx.test.codegen.doc.LinkToEnum;
import io.vertx.test.codegen.doc.LinkToMethodInSameType;
import io.vertx.test.codegen.doc.LinkToSameType;
import org.junit.Test;

import javax.lang.model.element.ElementKind;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DocTest {

  @Test
  public void testTags() {
    assertTag("foo");
    assertTag("foo bar");
    assertTag("foo  bar");
    assertTag("foo\nbar");
    assertTag("foo \n bar");
    assertTag(" foo");
    assertTag("foo ");
  }

  private void assertTag(String value) {
    Tag tag = new Tag("foo", value);
    assertEquals(value, tag.getValue());
  }

  @Test
  public void testCommentParser() {
    assertComment("first", "first", null);
    assertComment("first\n\nbody", "first", "body");
    assertComment("first @tag", "first @tag", null);
    assertComment("first\n@tag1 value1", "first", null, new Tag("tag1", "value1"));
    assertComment("first\n@tag1 line1\nline2", "first", null, new Tag("tag1", "line1\nline2"));
    assertComment("first\n@tag1 value1\n@tag2 value2", "first", null, new Tag("tag1", "value1"), new Tag("tag2", "value2"));
    assertComment("@tag1 value", "", null, new Tag("tag1", "value"));
    assertComment("\n@tag1 value", "", null, new Tag("tag1", "value"));
    assertComment("@tag1 value1\n@tag2 value2", "", null, new Tag("tag1", "value1"), new Tag("tag2", "value2"));
  }

  private void assertComment(String text, String expectedFirstSentence, String expectedBody, Tag... expectedBlockTags) {
    Doc comment = Doc.create(text);
    assertEquals(expectedFirstSentence, comment.getFirstSentence().getValue());
    assertEquals(expectedBody, comment.getBody() != null ? comment.getBody().getValue() : null);
    assertEquals(Arrays.asList(expectedBlockTags), comment.getBlockTags());
  }

  @Test
  public void testText() {
    List<Token> events = Token.tokenize("abc");
    assertEquals(1, events.size());
    assertTrue(events.get(0).isText());
    assertEquals("abc", events.get(0).getValue());
  }

  @Test
  public void testTag1() {
    List<Token> events = Token.tokenize("{@def}");
    assertEquals(1, events.size());
    assertTrue(events.get(0).isInlineTag());
    assertEquals("{@def}", events.get(0).getValue());
    assertEquals("def", ((Token.InlineTag)events.get(0)).getTag().getName());
    assertEquals("", ((Token.InlineTag)events.get(0)).getTag().getValue());
  }

  @Test
  public void testTag2() {
    List<Token> events = Token.tokenize("{@def ghi}");
    assertEquals(1, events.size());
    assertTrue(events.get(0).isInlineTag());
    assertEquals("{@def ghi}", events.get(0).getValue());
    assertEquals("def", ((Token.InlineTag)events.get(0)).getTag().getName());
    assertEquals(" ghi", ((Token.InlineTag)events.get(0)).getTag().getValue());
  }

  @Test
  public void testTag3() {
    List<Token> events = Token.tokenize("{@def\nghi}");
    assertEquals(1, events.size());
    assertTrue(events.get(0).isInlineTag());
    assertEquals("{@def\nghi}", events.get(0).getValue());
    assertEquals("def", ((Token.InlineTag)events.get(0)).getTag().getName());
    assertEquals("\nghi", ((Token.InlineTag)events.get(0)).getTag().getValue());
  }

  @Test
  public void testSequence() {
    List<Token> events = Token.tokenize("abc{@def}\nghi{@jkl mno}\n");
    assertEquals(6, events.size());
    assertTrue(events.get(0).isText());
    assertEquals("abc", events.get(0).getValue());
    assertTrue(events.get(1).isInlineTag());
    assertEquals("{@def}", events.get(1).getValue());
    assertEquals("def", ((Token.InlineTag)events.get(1)).getTag().getName());
    assertEquals("", ((Token.InlineTag)events.get(1)).getTag().getValue());
    assertTrue(events.get(2).isLineBreak());
    assertEquals("\n", events.get(2).getValue());
    assertTrue(events.get(3).isText());
    assertEquals("ghi", events.get(3).getValue());
    assertTrue(events.get(4).isInlineTag());
    assertEquals("{@jkl mno}", events.get(4).getValue());
    assertEquals("jkl", ((Token.InlineTag)events.get(4)).getTag().getName());
    assertEquals(" mno", ((Token.InlineTag)events.get(4)).getTag().getValue());
    assertTrue(events.get(5).isLineBreak());
    assertEquals("\n", events.get(5).getValue());
  }

  @Test
  public void testLinkToMethodInSameType() throws Exception {
    ClassModel model = new Generator().generateClass(LinkToMethodInSameType.class);
    MethodInfo method = model.getMethodMap().get("m").get(0);
    Doc doc = method.getDoc();
    List<Token> tokens = doc.getTokens();
    assertEquals(4, tokens.size());
    for (Token token : tokens) {
      Tag.Link link = (Tag.Link) ((Token.InlineTag) token).getTag();
      assertEquals(ElementKind.METHOD, link.getTargetElement().getKind());
      assertEquals("method(java.lang.String,int)", link.getTargetElement().toString());
    }
  }

  @Test
  public void testLinkToSameType() throws Exception {
    ClassModel model = new Generator().generateClass(LinkToSameType.class);
    MethodInfo method = model.getMethodMap().get("m").get(0);
    Doc doc = method.getDoc();
    List<Token> tokens = doc.getTokens();
    assertEquals(2, tokens.size());
    for (Token token : tokens) {
      Tag.Link link = (Tag.Link) ((Token.InlineTag) token).getTag();
      assertEquals(LinkToSameType.class.getName(), link.getTargetElement().toString());
    }
  }

  @Test
  public void testLinkToEnum() throws Exception {
    ClassModel model = new Generator().generateClass(LinkToEnum.class);
    MethodInfo method = model.getMethodMap().get("m").get(0);
    Doc doc = method.getDoc();
    List<Token> tokens = doc.getTokens();
    assertEquals(2, tokens.size());
    for (Token token : tokens) {
      Tag.Link link = (Tag.Link) ((Token.InlineTag) token).getTag();
      assertEquals(FooEnum.class.getName(), link.getTargetElement().toString());
    }
  }

  @Test
  public void testLinkLabel() throws Exception {
    ClassModel model = new Generator().generateClass(LinkLabel.class);
    MethodInfo method = model.getMethodMap().get("m").get(0);
    Doc doc = method.getDoc();
    List<Token> tokens = doc.getTokens();
    String[] expectedLabels = {"","   "," the label value"};
    assertEquals(expectedLabels.length, tokens.size());
    for (int i = 0;i < tokens.size();i++) {
      Tag.Link link = (Tag.Link) ((Token.InlineTag) tokens.get(i)).getTag();
      assertEquals("" + i,expectedLabels[i], link.getLabel());
    }
  }

  @Test
  public void testTagParam() {
    assertTagParam("param_name param_desc", "param_name", "param_desc");
    assertTagParam("param_name", "param_name", null);
    assertTagParam("param_name ", "param_name", null);
    assertTagParam("param_name  ", "param_name", null);
    assertTagParam("param_name  param_desc", "param_name", "param_desc");
    assertTagParam(" param_name param_desc", "param_name", "param_desc");
    assertTagParam("param_name\nparam_desc", "param_name", "param_desc");
    assertTagParam("param_name param_\ndesc", "param_name", "param_ desc");
    assertTagParam("param_name param_desc ", "param_name", "param_desc");
    assertTagParam("param_name param desc", "param_name", "param desc");
    assertTagParam(")param_name param desc", null, null);
  }

  private void assertTagParam(String comment, String expectedName, String expectedDescription) {
    Tag.Param param = new Tag.Param("param", comment);
    assertEquals(expectedName, param.getParamName());
    assertEquals(expectedDescription, param.getParamDescription());
  }
}
