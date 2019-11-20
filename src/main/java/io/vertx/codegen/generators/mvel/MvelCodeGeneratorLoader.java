package io.vertx.codegen.generators.mvel;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.vertx.codegen.CodeGenProcessor;
import io.vertx.codegen.Generator;
import io.vertx.codegen.GeneratorLoader;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Stream;

public class MvelCodeGeneratorLoader implements GeneratorLoader {

  private final static JsonFactory factory = new JsonFactory();

  @Override
  public Stream<Generator<?>> loadGenerators(ProcessingEnvironment processingEnv) {
    List<Generator<?>> generators = new ArrayList<>();
    Enumeration<URL> descriptors = Collections.emptyEnumeration();
    try {
      descriptors = CodeGenProcessor.class.getClassLoader().getResources("codegen.json");
    } catch (IOException ignore) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Could not load code generator descriptors");
    }
    Set<String> templates = new HashSet<>();
    while (descriptors.hasMoreElements()) {
      URL descriptor = descriptors.nextElement();
      try (JsonParser parser = factory.createParser(descriptor)) {
        List<MvelCodeGenerator> parsed = parseContent(parser);
        for (MvelCodeGenerator gen : parsed) {
          if (!templates.contains(gen.templateFilename)) {
            templates.add(gen.templateFilename);
            generators.add(gen);
          }
        }
      } catch (Exception e) {
        String msg = "Could not load code generator " + descriptor;
        CodeGenProcessor.log.log(Level.SEVERE, msg, e);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
      }
    }
    return generators.stream();
  }

  private List<MvelCodeGenerator> parseContent(JsonParser parser) throws IOException {
    List<MvelCodeGenerator> generators = null;
    String name = null;
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      String tokenName = parser.getCurrentName();
      if ("name".equals(tokenName)) {
        parser.nextToken();
        name = parser.getValueAsString();
      } else if ("generators".equals(tokenName)) {
        parser.nextToken();
        generators = parseGenerators(parser);
      }
    }
    for (MvelCodeGenerator generator : generators) {
      generator.name = name;
    }
    return generators;
  }

  private List<MvelCodeGenerator> parseGenerators(JsonParser parser) throws IOException {
    List<MvelCodeGenerator> generators = new ArrayList<>();
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      generators.add(parseGenerator(parser));
    }
    return generators;
  }

  private MvelCodeGenerator parseGenerator(JsonParser parser) throws IOException {
    MvelCodeGenerator gen = new MvelCodeGenerator();
    Set<String> kinds = new HashSet<>();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      String tokenName = parser.getCurrentName();
      if ("kind".equals(tokenName)) {
        parser.nextToken();
        if (parser.currentToken() == JsonToken.START_ARRAY) {
          while (parser.nextToken() != JsonToken.END_ARRAY) {
            kinds.add(parser.getValueAsString());
          }
        } else {
          kinds.add(parser.getValueAsString());
        }
      } else if ("incremental".equals(tokenName)) {
        parser.nextToken();
        gen.incremental = parser.getValueAsBoolean();
      } else if ("filename".equals(tokenName)) {
        parser.nextToken();
        gen.filename = parser.getValueAsString();
      } else if ("templateFilename".equals(tokenName)) {
        parser.nextToken();
        gen.templateFilename = parser.getValueAsString();
      }
    }
    gen.kinds = kinds;
    return gen;
  }
}
