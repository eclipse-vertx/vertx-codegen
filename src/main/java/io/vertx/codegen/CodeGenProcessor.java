package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.generators.dataobjecthelper.DataObjectHelperGenLoader;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@javax.annotation.processing.SupportedOptions({"codegen.output","codegen.generators"})
@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class CodeGenProcessor extends AbstractProcessor {

  private static final int JAVA= 0, RESOURCE = 1, OTHER = 2;
  private static final String JSON_MAPPERS_PROPERTIES_PATH = "META-INF/vertx/json-mappers.properties";
  public static final Logger log = Logger.getLogger(CodeGenProcessor.class.getName());
  private File outputDirectory;
  private List<? extends Generator<?>> codeGenerators;
  private Map<String, GeneratedFile> generatedFiles = new HashMap<>();
  private Map<String, GeneratedFile> generatedResources = new HashMap<>();
  private Map<String, String> relocations = new HashMap<>();
  private Set<Class<? extends Annotation>> supportedAnnotation = new HashSet<>();
  private List<CodeGen.Converter> mappers;

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return supportedAnnotation.stream().map(Class::getName).collect(Collectors.toSet());
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    generatedFiles.clear();
    generatedResources.clear();
    supportedAnnotation = new HashSet<>(Arrays.asList(DataObject.class, VertxGen.class));
    getCodeGenerators()
      .stream()
      .flatMap(gen -> gen.annotations().stream())
      .forEach(supportedAnnotation::add);

    // Load mappers
    if (mappers == null) {
      mappers = loadJsonMappers();
    }
  }

  private Predicate<Generator> filterGenerators() {
    String generatorsOption = processingEnv.getOptions().get("codegen.generators");
    if (generatorsOption != null) {
      List<Pattern> wanted = Stream.of(generatorsOption.split(","))
        .map(String::trim)
        .map(Pattern::compile)
        .collect(Collectors.toList());
      return cg -> wanted.stream()
        .anyMatch(p -> p.matcher(cg.name).matches());
    } else {
      return null;
    }
  }

  private Collection<? extends Generator<?>> getCodeGenerators() {
    if (codeGenerators == null) {
      String outputDirectoryOption = processingEnv.getOptions().get("codegen.output");
      if (outputDirectoryOption != null) {
        outputDirectory = new File(outputDirectoryOption);
        if (!outputDirectory.exists()) {
          if (!outputDirectory.mkdirs()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " does not exist");
          }
        }
        if (!outputDirectory.isDirectory()) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Output directory " + outputDirectoryOption + " is not a directory");
        }
      }
      // load GeneratorLoader by ServiceLoader
      Stream<GeneratorLoader> serviceLoader = StreamSupport.stream(ServiceLoader.load(GeneratorLoader.class, CodeGenProcessor.class.getClassLoader()).spliterator(), false);
      Stream<GeneratorLoader> loaders = Stream.of(new DataObjectHelperGenLoader());
      Stream<Generator<?>> generators = Stream.concat(serviceLoader, loaders).flatMap(l -> l.loadGenerators(processingEnv));
      Predicate<Generator> filter = filterGenerators();
      if (filter != null) {
        generators = generators.filter(filter);
      }
      generators = generators.peek(gen -> {
        gen.load(processingEnv);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Loaded " + gen.name + " code generator");
      });
      relocations = processingEnv.getOptions()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().startsWith("codegen.output."))
        .collect(Collectors.toMap(
          e -> e.getKey().substring("codegen.output.".length()),
          Map.Entry::getValue)
        );

      codeGenerators = generators.collect(Collectors.toList());
    }
    return codeGenerators;
  }

  private static void loadJsonMappers(List<CodeGen.Converter> list, InputStream is) throws IOException {
    Properties tmp = new Properties();
    tmp.load(is);
    tmp.stringPropertyNames().forEach(name -> {
      int idx = name.lastIndexOf('.');
      if (idx != -1) {
        String type = name.substring(0, idx);
        String value = tmp.getProperty(name);
        int idx1 = value.indexOf('#');
        if (idx1 != -1) {
          String className = value.substring(0, idx1);
          String rest = value.substring(idx1 + 1);
          int idx2 = rest.indexOf('.');
          if (idx2 != -1) {
            list.add(new CodeGen.Converter(type, className, Arrays.asList(rest.substring(0, idx2), rest.substring(idx2 + 1))));
          } else {
            list.add(new CodeGen.Converter(type, className, Collections.singletonList(rest)));
          }
        }
      }
    });
  }

  private Path determineSourcePath() {
    try {
      JavaFileObject generationForPath = processingEnv.getFiler()
          .createClassFile("PathFor" + getClass().getSimpleName());
      return new File(generationForPath.toUri()).toPath().getParent();
    } catch (IOException e) {
      // Possible failure (e.g with JPMS will not accept this)
      processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Unable to determine source file path!");
      return null;
    }
  }

  private List<CodeGen.Converter> loadJsonMappers() {
    Exception exception = null;
    List<CodeGen.Converter> merged = new ArrayList<>();
    for (StandardLocation loc : StandardLocation.values()) {
      try {
        FileObject file = processingEnv.getFiler().getResource(loc, "", JSON_MAPPERS_PROPERTIES_PATH);
        try(InputStream is = file.openInputStream()) {
          try {
            loadJsonMappers(merged, is);
            exception = null;
          } catch (IOException e) {
            exception = e;
          }
        }
      } catch (Exception ignore) {
        exception = ignore;
        // Filer#getResource and openInputStream will throw IOException when not found
      }
    }
    if (exception != null) {
      try {
        Enumeration<URL> resources = getClass().getClassLoader().getResources(JSON_MAPPERS_PROPERTIES_PATH);
        while (resources.hasMoreElements()) {
          URL url = resources.nextElement();
          try (InputStream is = url.openStream()) {
            loadJsonMappers(merged, is);
            exception = null;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Loaded json-mappers.properties " + url);
          }
        }
      } catch (IOException e) {
        exception = e;
        // ignore in order to looking for the file using the source path
      }
    }
    if (exception != null) {
      Path path = determineSourcePath();
      if (path != null) {
        Path source = path.getParent().getParent().resolve("src/main/resources").resolve(JSON_MAPPERS_PROPERTIES_PATH);
        if (source.toFile().exists()) {
          try (InputStream is = source.toUri().toURL().openStream()) {
            loadJsonMappers(merged, is);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Loaded json-mappers.properties from '" + source + "'");
          } catch (IOException e) {
            log.log(Level.SEVERE, "Could not load json-mappers.properties", e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Unable to open properties file at " + source);
          }
        }
      }
    }
    return merged;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    // find elements annotated with @SuppressWarnings("codegen-enhanced-method")
    if (!roundEnv.processingOver()) {
      Collection<? extends Generator> codeGenerators = getCodeGenerators();

      if (!roundEnv.errorRaised()) {
        CodeGen codegen = new CodeGen(processingEnv);
        mappers.forEach(codegen::registerConverter);
        codegen.init(roundEnv, getClass().getClassLoader());
        Map<String, GeneratedFile> generatedClasses = new HashMap<>();

        // Generate source code
        codegen.getModels().forEach(entry -> {
          try {
            Model model = entry.getValue();
            for (Generator codeGenerator : codeGenerators) {
              if (codeGenerator.kinds.contains(model.getKind())) {
                String relativeName = codeGenerator.filename(model);
                if (relativeName != null) {
                  int kind;
                  if (relativeName.endsWith(".java") && !relativeName.contains("/")) {
                    String relocation = relocations.get(codeGenerator.name);
                    if (relocation != null) {
                      kind = OTHER;
                      relativeName = relocation + '/' +
                        relativeName.substring(0, relativeName.length() - ".java".length()).replace('.', '/') + ".java";
                    } else {
                      kind = JAVA;
                    }
                  } else if (relativeName.startsWith("resources/")) {
                    kind = RESOURCE;
                  } else {
                    kind = OTHER;
                  }
                  if (kind == JAVA) {
                    // Special handling for .java
                    String fqn = relativeName.substring(0, relativeName.length() - ".java".length());
                    // Avoid to recreate the same file (this may happen as we unzip and recompile source trees)
                    if (processingEnv.getElementUtils().getTypeElement(fqn) != null) {
                      continue;
                    }
                    List<ModelProcessing> processings = generatedClasses.computeIfAbsent(fqn, GeneratedFile::new);
                    processings.add(new ModelProcessing(model, codeGenerator));
                  } else if (kind == RESOURCE) {
                    relativeName = relativeName.substring("resources/".length());
                    List<ModelProcessing> processings = generatedResources.computeIfAbsent(relativeName, GeneratedFile::new);
                    processings.add(new ModelProcessing(model, codeGenerator));
                  } else {
                    List<ModelProcessing> processings = generatedFiles.computeIfAbsent(relativeName, GeneratedFile::new);
                    processings.add(new ModelProcessing(model, codeGenerator));
                  }
                }
              }
            }
          } catch (GenException e) {
            reportGenException(e);
          } catch (Exception e) {
            reportException(e, entry.getKey());
          }
        });

        // Generate classes
        generatedClasses.values().forEach(generated -> {
          boolean shouldWarningsBeSuppressed = false;
          try {
            String content = generated.generate();
            if (content.length() > 0) {
              JavaFileObject target = processingEnv.getFiler().createSourceFile(generated.uri);
              try (Writer writer = target.openWriter()) {
                writer.write(content);
              }
              processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated model " + generated.get(0).model.getFqn() + ": " + generated.uri);
            }
          } catch (GenException e) {
            reportGenException(e);
          } catch (Exception e) {
            reportException(e, generated.get(0).model.getElement());
          }
        });
      }
    } else {

      // Generate resources
      for (GeneratedFile generated : generatedResources.values()) {
        boolean shouldWarningsBeSuppressed = false;
        try {
          String content = generated.generate();
          if (content.length() > 0) {
            try (Writer w = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", generated.uri).openWriter()) {
              w.write(content);
            }
            boolean createSource;
            try {
              processingEnv.getFiler().getResource(StandardLocation.SOURCE_OUTPUT, "", generated.uri);
              createSource = true;
            } catch (FilerException e) {
              // SOURCE_OUTPUT == CLASS_OUTPUT
              createSource = false;
            }
            if (createSource) {
              try (Writer w = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", generated.uri).openWriter()) {
                w.write(content);
              }
            }
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated model " + generated.get(0).model.getFqn() + ": " + generated.uri);
          }
        } catch (GenException e) {
          reportGenException(e);
        } catch (Exception e) {
          reportException(e, generated.get(0).model.getElement());
        }
      }
      // Generate files
      generatedFiles.values().forEach(generated -> {
        Path path = new File(generated.uri).toPath();
        if (path.isAbsolute()) {
          // Nothing to do
        } else if (outputDirectory != null) {
          path = outputDirectory.toPath().resolve(path);
        } else {
          return;
        }
        File file = path.toFile();
        Helper.ensureParentDir(file);
        String content = generated.generate();
        if (content.length() > 0) {
          try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
          } catch (GenException e) {
            reportGenException(e);
          } catch (Exception e) {
            reportException(e, generated.get(0).model.getElement());
          }
          processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated model " + generated.get(0).model.getFqn() + ": " + generated.uri);
        }
      });
    }
    return true;
  }

  private void reportGenException(GenException e) {
    String name = e.element.toString();
    if (e.element.getKind() == ElementKind.METHOD) {
      name = e.element.getEnclosingElement() + "#" + name;
    }
    String msg = "Could not generate model for " + name + ": " + e.msg;
    log.log(Level.SEVERE, msg, e);
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e.element);
  }

  private void reportException(Exception e, Element elt) {
    String msg = "Could not generate element for " + elt + ": " + e.getMessage();
    log.log(Level.SEVERE, msg, e);
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, elt);
  }

  private static class ModelProcessing {
    final Model model;
    final Generator generator;
    public ModelProcessing(Model model, Generator generator) {
      this.model = model;
      this.generator = generator;
    }
  }

  private static class GeneratedFile extends ArrayList<ModelProcessing> {

    private final String uri;
    private final Map<String, Object> session = new HashMap<>();


    public GeneratedFile(String uri) {
      super();
      this.uri = uri;
    }

    @Override
    public boolean add(ModelProcessing modelProcessing) {
      if (!modelProcessing.generator.incremental) {
        clear();
      }
      return super.add(modelProcessing);
    }

    String generate() {
      Collections.sort(this, (o1, o2) ->
        o1.model.getElement().getSimpleName().toString().compareTo(
          o2.model.getElement().getSimpleName().toString()));
      StringBuilder buffer = new StringBuilder();
      for (int i = 0; i < size(); i++) {
        ModelProcessing processing = get(i);
        try {
          String part = processing.generator.render(processing.model, i, size(), session);
          if (part != null) {
            buffer.append(part);
          }
        } catch (GenException e) {
          throw e;
        } catch (Exception e) {
          GenException genException = new GenException(processing.model.getElement(), e.getMessage());
          genException.initCause(e);
          throw genException;
        }
      }
      return buffer.toString();
    }
  }
}
