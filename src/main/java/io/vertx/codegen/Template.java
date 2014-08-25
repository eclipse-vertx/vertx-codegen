package io.vertx.codegen;

import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
class Template {

  private final String name;
  private final CompiledTemplate compiled;
  private final HashMap<String, String> options;

  Template(String name) {
    // Read the template file from the classpath
    InputStream source = getClass().getClassLoader().getResourceAsStream(name);
    if (source == null) {
      throw new IllegalStateException("Can't find template file on classpath: " + name);
    }
    this.name = name;
    this.compiled = loadCompiled(name, source);
    this.options = new HashMap<>();
  }

  Template(String name, InputStream source) {
    this.name = name;
    this.compiled = loadCompiled(name, source);
    this.options = new HashMap<>();
  }

  public void setOptions(Map<String, String> options) {
    this.options.clear();
    this.options.putAll(options);
  }

  private static CompiledTemplate loadCompiled(String name, InputStream source) {
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
    template = template.replace("\n", "").replace("\\n", "\n").replace("\t", "");

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

  String getName() {
    return name;
  }

  void apply(Model model, String outputFileName) throws Exception {
    apply(model, new File(outputFileName));
  }

  void apply(Model model, File outputFile) throws Exception {
    String output = render(model);
    if (!outputFile.getParentFile().exists()) {
      outputFile.getParentFile().mkdirs();
    }
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outputFile))) {
      outStream.print(output);
      outStream.flush();
    }
  }

  String render(Model model) {
    Map<String, Object> vars = model.getVars();

    // Options
    vars.put("options", options);

    ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
    String output;
    try {
      // Be sure to have mvel classloader as parent during evaluation as it will need mvel classes
      // when generating code
      Thread.currentThread().setContextClassLoader(TemplateRuntime.class.getClassLoader());
      output = (String) TemplateRuntime.execute(compiled, null, new MapVariableResolverFactory(vars));
    } finally {
      Thread.currentThread().setContextClassLoader(currentCL);
    }
    return output;
  }
}
