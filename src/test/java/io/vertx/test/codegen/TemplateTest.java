package io.vertx.test.codegen;

import io.vertx.codegen.Model;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.Template;
import org.junit.Test;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.io.StandardOutputStream;

import javax.lang.model.element.Element;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
    public ModuleInfo getModule() {
      throw new UnsupportedOperationException();
    }
  };

  @Test
  public void testIncludeNamedResolve() throws Exception {
    Template template = new Template(getClass().getResource("testtemplates/include_named_resolve.templ"));
    String output = template.render(NULL_MODEL);
    assertEquals("hollahello", output);
  }

  @Test
  public void testIncludeNamedNotFound() throws Exception {
    Template template = new Template(getClass().getResource("testtemplates/include_named_not_found.templ"));
    try {
      template.render(NULL_MODEL);
      fail("Was expecting to fail");
    } catch (TemplateError ignore) {
    }
  }

}
