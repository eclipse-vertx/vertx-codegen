package io.vertx.codegen.generators.mvel;

import io.vertx.codegen.Case;
import io.vertx.codegen.MethodKind;
import io.vertx.codegen.Model;
import io.vertx.codegen.type.ClassKind;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
class Template {

  private final String baseURI;
  public final String name;
  private final CompiledTemplate compiled;
  private final HashMap<String, String> options = new HashMap<>();

  Template(URL url) {
    String file = url.getFile();
    this.name = file.substring(file.lastIndexOf('/') + 1);
    try {
      this.baseURI = url.toURI().toString();
      this.compiled = loadCompiled(url);
    } catch (URISyntaxException e) {
      throw new TemplateError("Could not load template from template " + url, e);
    }
  }

  Template(String name) {
    this(resolveURL(name));
  }

  void setOptions(Map<String, String> options) {
    this.options.clear();
    this.options.putAll(options);
  }

  // A global template cache because loading a template is expensive
  private static final Map<URL, CompiledTemplate> templateCache = new HashMap<>();

  /**
   * Load a template given its {@code templateURL}.
   *
   * @param templateURL the template url
   * @return the compiled template
   */
  private static CompiledTemplate loadCompiled(URL templateURL) {
    CompiledTemplate template = templateCache.get(templateURL);
    if (template == null) {
      InputStream is = null;
      try {
        is = templateURL.openStream();
      } catch (IOException e) {
        throw new TemplateError("Could not load template from template " + templateURL, e);
      }
      template = loadCompiled(is);
      templateCache.put(templateURL, template);
    }
    return template;
  }

  /**
   * Load a template given its {@code source}.
   *
   * @param source the template source
   * @return the compiled template
   */
  private static CompiledTemplate loadCompiled(InputStream source) {
    // Load the template
    String template;
    try (Scanner scanner = new Scanner(source, "UTF-8").useDelimiter("\\A")) {
      template = scanner.next();
    }
    // MVEL preserves all whitespace therefore, so we can have readable templates we remove all line breaks
    // and replace all occurrences of "\n" with a line break
    // "\n" signifies we want an actual line break in the output
    // We use actual tab characters in the template so we can see indentation, but we strip these out
    // before parsing.
    // So use tabs for indentation that YOU want to see in the template but won't be in the final output
    // And use spaces for indentation that WILL be in the final output
    template = template.replace("\n", "").replace("\r", "").replace("\\n", System.getProperty("line.separator")).replace("\t", "");

    // Be sure to have mvel classloader as parent during evaluation as it will need mvel classes
    // when generating code
    ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader(TemplateRuntime.class.getClassLoader());
    try {
      return TemplateCompiler.compileTemplate(template);
    } finally {
      Thread.currentThread().setContextClassLoader(currentCL);
    }
  }

  private static URL resolveURL(String name) {
    // Read the template file from the classpath
    URL url = Template.class.getClassLoader().getResource(name);
    if (url == null) {
      throw new IllegalArgumentException("Can't find template file on classpath: " + name);
    }
    return url;
  }

  public String render(Model model) {
    return render(model, Collections.emptyMap());
  }

  public String render(Model model, Map<String, Object> vars) {
    vars = new HashMap<>(vars);
    vars.put("options", options);
    vars.put("skipFile", false);
    vars.putAll(model.getVars());
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    vars.putAll(Case.vars());

    TemplateRegistry registry = new SimpleTemplateRegistry() {
      @Override
      public CompiledTemplate getNamedTemplate(String name) {
        try {
          return super.getNamedTemplate(name);
        } catch (TemplateError err) {
          // Load error try to resolve from base uri
          try {
            URL url;
            if (name.startsWith("/")) {
              url = resolveURL(name.substring(1));
            } else {
              url = new URL(new URL(baseURI), name);
            }
            CompiledTemplate compiledTemplate = loadCompiled(url);
            addNamedTemplate(name, compiledTemplate);
            return compiledTemplate;
          } catch (Exception ex) {
            throw new TemplateError("Could not load template " + name + " from template " + baseURI, ex);
          }
        }
      }
    };

    ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
    try {
      // Be sure to have mvel classloader as parent during evaluation as it will need mvel classes
      // when generating code
      Thread.currentThread().setContextClassLoader(TemplateRuntime.class.getClassLoader());
      TemplateRuntime runtime = new TemplateRuntime(compiled.getTemplate(), registry, compiled.getRoot(), ".");
      String blah = (String) runtime.execute(new StringBuilder(), null, new MapVariableResolverFactory(vars));
      if (Boolean.TRUE.equals(vars.get("skipFile"))) {
        return null;
      }
      return blah;
    } finally {
      Thread.currentThread().setContextClassLoader(currentCL);
    }
  }
}
