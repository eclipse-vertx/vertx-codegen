package io.vertx.codegen.generators.mvel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.codegen.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Stream;

public class MvelCodeGeneratorLoader implements GeneratorLoader {

  private final static ObjectMapper mapper = new ObjectMapper();

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
      try (Scanner scanner = new Scanner(descriptor.openStream(), "UTF-8").useDelimiter("\\A")) {
        String s = scanner.next();
        ObjectNode obj = (ObjectNode) mapper.readTree(s);
        String name = obj.get("name").asText();
        ArrayNode generatorsCfg = (ArrayNode) obj.get("generators");
        for (JsonNode generator : generatorsCfg) {
          Set<String> kinds = new HashSet<>();
          if(generator.get("kind").isArray()) {
            generator.get("kind").forEach(v -> kinds.add(v.asText()));
          }
          else {
            kinds.add(generator.get("kind").asText());
          }
          JsonNode templateFilenameNode = generator.get("templateFilename");
          if (templateFilenameNode == null) {
            templateFilenameNode = generator.get("templateFileName");
          }
          String templateFilename = templateFilenameNode.asText();
          JsonNode filenameNode = generator.get("filename");
          if (filenameNode == null) {
            filenameNode = generator.get("fileName");
          }
          String filename = filenameNode.asText();
          boolean incremental = generator.has("incremental") && generator.get("incremental").asBoolean();
          if (!templates.contains(templateFilename)) {
            templates.add(templateFilename);
            MvelCodeGenerator gen = new MvelCodeGenerator();
            gen.name = name;
            gen.kinds = kinds;
            gen.incremental = incremental;
            gen.filename = filename;
            gen.templateFilename = templateFilename;
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
}
