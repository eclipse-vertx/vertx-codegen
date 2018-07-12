package io.vertx.codegen.generators.mvel;

import io.vertx.codegen.Model;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.generators.mvel.Template;
import org.junit.Test;
import org.mvel2.templates.TemplateError;

import javax.lang.model.element.Element;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TemplateTest {

  Model NULL_MODEL = new Model() {
    public String getKind() {
      throw new UnsupportedOperationException();
    }

    public Element getElement() {
      throw new UnsupportedOperationException();
    }

    public String getFqn() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean process() {
      throw new UnsupportedOperationException();
    }

    @Override
    public ModuleInfo getModule() {
      throw new UnsupportedOperationException();
    }
  };

  @Test
  public void testIncludeNamedResolve() {
    Template template = new Template(getClass().getResource("testtemplates/include_named_resolve.templ"));
    String output = template.render(NULL_MODEL);
    assertEquals("hollahello", output);
  }

  @Test
  public void testIncludeNamedNotFound() {
    Template template = new Template(getClass().getResource("testtemplates/include_named_not_found.templ"));
    try {
      template.render(NULL_MODEL);
      fail("Was expecting to fail");
    } catch (TemplateError ignore) {
    }
  }

}
