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

  Template(String name) {
    // Read the template file from the classpath
    InputStream source = getClass().getClassLoader().getResourceAsStream(name);
    if (source == null) {
      throw new IllegalStateException("Can't find template file on classpath: " + name);
    }
    this.name = name;
    this.compiled = loadCompiled(name, source);
  }

  Template(String name, InputStream source) {
    this.name = name;
    this.compiled = loadCompiled(name, source);
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
    Map<String, Object> vars = new HashMap<>();
    vars.put("importedTypes", model.getImportedTypes());
    vars.put("concrete", model.isConcrete());
    vars.put("ifacePackageName", model.getIfacePackageName());
    vars.put("ifaceSimpleName", model.getIfaceSimpleName());
    vars.put("ifaceFQCN", model.getIfaceFQCN());
    vars.put("ifaceComment", model.getIfaceComment());
    vars.put("helper", new Helper());
    vars.put("methods", model.getMethods());
    vars.put("referencedTypes", model.getReferencedTypes());
    vars.put("superTypes", model.getSuperTypes());
    vars.put("concreteSuperTypes", model.getConcreteSuperTypes());
    vars.put("abstractSuperTypes", model.getAbstractSuperTypes());
    vars.put("squashedMethods", model.getSquashedMethods().values());
    vars.put("methodsByName", model.getMethodMap());
    vars.put("referencedOptionsTypes", model.getReferencedOptionsTypes());

    // Useful for testing the type class kind, allows to do type.kind == CLASS_API instead of type.kind.name() == "API"
    for (ClassKind classKind : ClassKind.values()) {
      vars.put("CLASS_" + classKind.name(), classKind);
    }

    // Useful for testing the method kind, allows to do method.kind == METHOD_HANDLER instead of method.kind.name() == "HANDLER"
    for (MethodKind methodKind : MethodKind.values()) {
      vars.put("METHOD_" + methodKind.name(), methodKind);
    }

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
    if (!outputFile.getParentFile().exists()) {
      outputFile.getParentFile().mkdirs();
    }
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outputFile))) {
      outStream.print(output);
      outStream.flush();
    }
  }
}
