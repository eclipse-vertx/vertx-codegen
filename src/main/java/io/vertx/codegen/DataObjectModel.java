package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.type.*;
import io.vertx.core.json.JsonObject;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.time.Instant;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectModel implements Model {

  private final Elements elementUtils;
  private final Types typeUtils;
  private final Doc.Factory docFactory;
  private final TypeMirrorFactory typeFactory;
  private final TypeElement modelElt;
  // ----------------
  private final Map<String, PropertyInfo> propertyMap = new LinkedHashMap<>();
  private final Set<ClassTypeInfo> superTypes = new LinkedHashSet<>();
  private final Set<ClassTypeInfo> abstractSuperTypes = new LinkedHashSet<>();
  private final Set<ClassTypeInfo> importedTypes = new LinkedHashSet<>();
  // ----------------
  private final AnnotationValueInfoFactory annotationValueInfoFactory;
  private boolean processed;
  private boolean concrete;
  private boolean isClass;
  // ----------------
  private boolean generateConverter;
  private boolean inheritConverter;
  private boolean publicConverter;
  private int constructors;
  // ----------------
  private boolean deprecated;
  private Text deprecatedDesc;
  private ClassTypeInfo superType;
  private DataObjectTypeInfo type;
  private Doc doc;
  private boolean hasToJsonMethod;
  private boolean hasDecodeStaticMethod;
  private List<AnnotationValueInfo> annotations;

  public DataObjectModel(ProcessingEnvironment env, TypeElement modelElt) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils, elementUtils.getPackageOf(modelElt));
    this.docFactory = new Doc.Factory(env.getMessager(), elementUtils, typeUtils, typeFactory, modelElt);
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(typeFactory);
    this.deprecated = modelElt.getAnnotation(Deprecated.class) != null;
  }

  @Override
  public String getKind() {
    return "dataObject";
  }

  @Override
  public Element getElement() {
    return modelElt;
  }

  @Override
  public String getFqn() {
    return type.getName();
  }

  public DataObjectTypeInfo getType() {
    return type;
  }

  public Doc getDoc() {
    return doc;
  }

  public boolean isAbstract() {
    return !concrete;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public Set<ClassTypeInfo> getImportedTypes() {
    return importedTypes;
  }

  public Map<String, PropertyInfo> getPropertyMap() {
    return propertyMap;
  }

  @Override
  public List<AnnotationValueInfo> getAnnotations() {
    return annotations;
  }

  public ClassTypeInfo getSuperType() {
    return superType;
  }

  public Set<ClassTypeInfo> getAbstractSuperTypes() {
    return abstractSuperTypes;
  }

  public Set<ClassTypeInfo> getSuperTypes() {
    return superTypes;
  }

  public ModuleInfo getModule() {
    return type.getRaw().getModule();
  }

  public boolean isClass() {
    return isClass;
  }

  public boolean getGenerateConverter() {
    return generateConverter;
  }

  public boolean getInheritConverter() {
    return inheritConverter;
  }

  public boolean isPublicConverter() {
    return publicConverter;
  }

  public boolean isEncodable() { return type.hasJsonEncoder(); }

  public boolean isDecodable() { return type.hasJsonDecoder(); }

  public boolean hasToJsonMethod() { return hasToJsonMethod; }

  public boolean hasEmptyConstructor() {
    return (constructors & 1) == 1;
  }

  public boolean hasJsonConstructor() { return (constructors & 2) == 2; }

  public boolean hasDecodeStaticMethod() {
    return hasDecodeStaticMethod;
  }

  /**
   * @return {@code true} if the class has a {@code @Deprecated} annotation
   */
  public boolean isDeprecated() {
    return deprecated;
  }
  /**
   * @return the description of deprecated
   */
  public Text getDeprecatedDesc() {
    return deprecatedDesc;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("type", type);
    vars.put("doc", doc);
    vars.put("generateConverter", generateConverter);
    vars.put("inheritConverter", inheritConverter);
    vars.put("publicConverter", publicConverter);
    vars.put("concrete", concrete);
    vars.put("isClass", isClass);
    vars.put("properties", propertyMap.values());
    vars.put("importedTypes", importedTypes);
    vars.put("superTypes", superTypes);
    vars.put("superType", superType);
    vars.put("abstractSuperTypes", abstractSuperTypes);
    vars.put("hasToJsonMethod", hasToJsonMethod);
    vars.put("hasEmptyConstructor", hasEmptyConstructor());
    vars.put("hasJsonConstructor", hasJsonConstructor());
    vars.put("encodable", isEncodable());
    vars.put("decodable", isDecodable());
    vars.put("deprecated", deprecated);
    vars.put("deprecatedDesc", getDeprecatedDesc());
    return vars;
  }

  public boolean process() {
    if (!processed) {
      if (modelElt.getKind() == ElementKind.INTERFACE || modelElt.getKind() == ElementKind.CLASS) {
        traverse();
        processTypeAnnotations();
        processImportedTypes();
        processed = true;
        return true;
      } else {
        throw new GenException(modelElt, "Data object " + modelElt + " must be an interface or a class");
      }
    }
    return false;
  }

  private void traverse() {
    DataObject ann = modelElt.getAnnotation(DataObject.class);
    this.generateConverter = ann.generateConverter();
    this.publicConverter = ann.publicConverter();
    this.inheritConverter = ann.inheritConverter();
    this.isClass = modelElt.getKind() == ElementKind.CLASS;
    this.concrete = isClass && !modelElt.getModifiers().contains(Modifier.ABSTRACT);
    try {
      this.type = (DataObjectTypeInfo) typeFactory.create(modelElt.asType());
    } catch (ClassCastException e) {
      throw new GenException(modelElt, "Data object must be a plain java class with no type parameters");
    }
    Helper.checkUnderModule(this, "@VertxGen");
    doc = docFactory.createDoc(modelElt);
    if (doc != null)
      doc.getBlockTags().stream().filter(tag -> tag.getName().equals("deprecated")).findFirst().ifPresent(tag ->
        deprecatedDesc = new Text(Helper.normalizeWhitespaces(tag.getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt))
      );
    if (getModule() == null) {
      throw new GenException(modelElt, "Data object must have an ancestor package annotated with @ModuleGen");
    }

    modelElt.getInterfaces().stream()
      .filter(superTM -> superTM instanceof DeclaredType && ((DeclaredType) superTM).asElement().getAnnotation(DataObject.class) != null)
      .map(e -> (ClassTypeInfo) typeFactory.create(e)).forEach(abstractSuperTypes::add);

    superTypes.addAll(abstractSuperTypes);

    TypeMirror superClass = modelElt.getSuperclass();
    if (superClass instanceof DeclaredType && ((DeclaredType) superClass).asElement().getAnnotation(DataObject.class) != null) {
      superType = (ClassTypeInfo) typeFactory.create(superClass);
      superTypes.add(superType);
    }

    List<ExecutableElement> methodsElt = new ArrayList<>();
    for (Element enclosedElt : elementUtils.getAllMembers(modelElt)) {
      switch (enclosedElt.getKind()) {
        case CONSTRUCTOR:
          ExecutableElement constrElt = (ExecutableElement) enclosedElt;
          processConstructor(constrElt);
          break;
        case METHOD: {
          ExecutableElement methodElt = (ExecutableElement) enclosedElt;
          if (methodElt.getSimpleName().toString().equals("toJson") &&
            methodElt.getParameters().isEmpty() &&
            typeFactory.create(methodElt.getReturnType()).getKind() == ClassKind.JSON_OBJECT) {
            hasToJsonMethod = true;
          }
          if (methodElt.getSimpleName().contentEquals("decode") &&
            methodElt.getModifiers().containsAll(Arrays.asList(Modifier.STATIC, Modifier.PUBLIC)) &&
            methodElt.getParameters().size() == 1 &&
            typeFactory.create(methodElt.getParameters().get(0).asType()).getKind() == ClassKind.JSON_OBJECT &&
            typeUtils.isSameType(methodElt.getReturnType(), this.modelElt.asType())) {
            hasDecodeStaticMethod = true;
          }
          if (methodElt.getAnnotation(GenIgnore.class) == null) {
            methodsElt.add(methodElt);
          }
          break;
        }
      }
    }

    processMethods(methodsElt);

    // Sort the properties so we do have a consistent order
    ArrayList<PropertyInfo> props = new ArrayList<>(propertyMap.values());
    Collections.sort(props, (p1, p2) -> p1.name.compareTo(p2.name));
    propertyMap.clear();
    props.forEach(prop -> propertyMap.put(prop.name, prop));
  }

  private void processTypeAnnotations() {
    this.annotations = elementUtils.getAllAnnotationMirrors(modelElt).stream().map(annotationValueInfoFactory::processAnnotation).collect(Collectors.toList());
  }

  private void processImportedTypes() {
    for (PropertyInfo property : propertyMap.values()) {
      property.type.collectImports(importedTypes);
    }
    importedTypes.addAll(superTypes.stream().collect(toList()));
    for (Iterator<ClassTypeInfo> i = importedTypes.iterator(); i.hasNext(); ) {
      ClassTypeInfo importedType = i.next();
      if (importedType.getPackageName().equals(type.getPackageName())) {
        i.remove();
      }
    }
  }

  private void processConstructor(ExecutableElement constrElt) {
    if (constrElt.getModifiers().contains(Modifier.PUBLIC)) {
      Element ownerElt = constrElt.getEnclosingElement();
      if (ownerElt.equals(modelElt)) {
        List<? extends VariableElement> parameters = constrElt.getParameters();
        int size = parameters.size();
        if (size == 1) {
          TypeInfo ti = typeFactory.create(parameters.get(0).asType());
          if (ti instanceof ClassTypeInfo) {
            ClassTypeInfo cl = (ClassTypeInfo) ti;
            if (cl.getKind() == ClassKind.JSON_OBJECT) {
              constructors |= 2;
            }
          }
        } else if (size == 0) {
          constructors |= 1;
        }
      }
    }
  }



  @SuppressWarnings("unchecked")
  private void processMethods(List<ExecutableElement> methodsElt) {

    Map<String, ExecutableElement> getters = new HashMap<>();
    Map<String, ExecutableElement> setters = new HashMap<>();
    Map<String, ExecutableElement> adders = new HashMap<>();
    Map<String, List<AnnotationMirror>> annotations = new HashMap<>();

    BiFunction<List<AnnotationMirror>, List<AnnotationMirror>, List<AnnotationMirror>> merger = (a, b) -> {
      if (b.size() > 0) {
        if (!(a instanceof ArrayList)) {
          a = new ArrayList<>(a);
        }
        a.addAll(b);
      }
      return a;
    };

    while (methodsElt.size() > 0) {
      ExecutableElement methodElt = methodsElt.remove(0);
      if (((TypeElement) methodElt.getEnclosingElement()).getQualifiedName().toString().equals("java.lang.Object") ||
        methodElt.getModifiers().contains(Modifier.STATIC) ||
        !methodElt.getModifiers().contains(Modifier.PUBLIC)) {
        continue;
      }
      String methodName = methodElt.getSimpleName().toString();
      if (methodName.startsWith("get") && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3)) && methodElt.getParameters().isEmpty() && methodElt.getReturnType().getKind() != TypeKind.VOID) {
        String name = Helper.normalizePropertyName(methodName.substring(3));
        getters.put(name, methodElt);
        annotations.merge(name, (List<AnnotationMirror>) elementUtils.getAllAnnotationMirrors(methodElt), merger);
      } else if (methodName.startsWith("is") && methodName.length() > 2 && Character.isUpperCase(methodName.charAt(2)) && methodElt.getParameters().isEmpty() && methodElt.getReturnType().getKind() != TypeKind.VOID) {
        String name = Helper.normalizePropertyName(methodName.substring(2));
        getters.put(name, methodElt);
        annotations.merge(name, (List<AnnotationMirror>) elementUtils.getAllAnnotationMirrors(methodElt), merger);
      } else if ((methodName.startsWith("set") || methodName.startsWith("add")) && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3))) {
        String prefix = methodName.substring(0, 3);
        String name = Helper.normalizePropertyName(methodName.substring(3));
        int numParams = methodElt.getParameters().size();
        if ("add".equals(prefix)) {
          if (name.endsWith("s")) {
            throw new GenException(methodElt, "Option adder name must not terminate with 's' char");
          } else {
            name += "s";
          }
          TypeMirror t = methodElt.getParameters().get(0).asType();
          if (numParams == 1 || (numParams == 2 && t.getKind() == TypeKind.DECLARED &&
            ((TypeElement) ((DeclaredType) t).asElement()).getQualifiedName().toString().equals("java.lang.String"))) {
            adders.put(name, methodElt);
            annotations.merge(name, (List<AnnotationMirror>) elementUtils.getAllAnnotationMirrors(methodElt), merger);
          }
        } else {
          if (numParams == 1) {
            setters.put(name, methodElt);
            annotations.merge(name, (List<AnnotationMirror>) elementUtils.getAllAnnotationMirrors(methodElt), merger);
          }
        }
      }
    }

    Set<String> names = new HashSet<>();
    names.addAll(getters.keySet());
    names.addAll(setters.keySet());
    names.addAll(adders.keySet());

    for (String name : names) {
      //Check annotations on field
      List<? extends AnnotationMirror> list = getElement().getEnclosedElements().stream()
        .filter(e -> e.getKind().equals(ElementKind.FIELD) && e.getSimpleName().toString().equals(name))
        .flatMap(e -> elementUtils.getAllAnnotationMirrors(e).stream()).collect(Collectors.toList());
      if (list.size() > 0) {
        annotations.merge(name, (List<AnnotationMirror>) list, merger);
      }
    }
    for (String name : names) {
      processMethod(name, getters.get(name), setters.get(name), adders.get(name), annotations.get(name));
    }
  }

  private void processMethod(String name, ExecutableElement getterElt, ExecutableElement setterElt, ExecutableElement adderElt, List<AnnotationMirror> annotationMirrors) {

    PropertyKind propKind = null;
    TypeInfo propType = null;
    TypeMirror propTypeMirror = null;
    boolean propertyDeprecated = false;

    //
    if (setterElt != null) {
      VariableElement paramElt = setterElt.getParameters().get(0);
      propTypeMirror = paramElt.asType();
      propType = typeFactory.create(propTypeMirror);
      propKind = PropertyKind.forType(propType.getKind());
      propertyDeprecated |= setterElt.getAnnotation(Deprecated.class) != null;
      switch (propKind) {
        case LIST:
        case SET:
          propType = ((ParameterizedTypeInfo) propType).getArgs().get(0);
          propTypeMirror = ((DeclaredType) propTypeMirror).getTypeArguments().get(0);
          break;
        case MAP:
          propType = ((ParameterizedTypeInfo) propType).getArgs().get(1);
          propTypeMirror = ((DeclaredType) propTypeMirror).getTypeArguments().get(1);
          break;
      }
    }

    //
    if (getterElt != null) {
      TypeMirror getterTypeMirror = getterElt.getReturnType();
      TypeInfo getterType = typeFactory.create(getterTypeMirror);
      PropertyKind getterKind = PropertyKind.forType(getterType.getKind());
      propertyDeprecated |= getterElt.getAnnotation(Deprecated.class) != null;
      switch (getterKind) {
        case LIST:
        case SET:
          getterType = ((ParameterizedTypeInfo) getterType).getArgs().get(0);
          getterTypeMirror = ((DeclaredType) getterTypeMirror).getTypeArguments().get(0);
          break;
        case MAP:
          getterType = ((ParameterizedTypeInfo) getterType).getArgs().get(1);
          getterTypeMirror = ((DeclaredType) getterTypeMirror).getTypeArguments().get(1);
          break;
      }
      if (propType != null) {
        if (propKind != getterKind) {
          throw new GenException(getterElt, name + " getter " + getterKind + " does not match the setter " + propKind);
        }
        if (!getterType.equals(propType)) {
          throw new GenException(getterElt, name + " getter type " + getterType + " does not match the setter type " + propType);
        }
      } else {
        propTypeMirror = getterTypeMirror;
        propType = getterType;
        propKind = getterKind;
      }
    }

    //
    if (adderElt != null) {
      propertyDeprecated |= adderElt.getAnnotation(Deprecated.class) != null;
      switch (adderElt.getParameters().size()) {
        case 1: {
          VariableElement paramElt = adderElt.getParameters().get(0);
          TypeMirror adderTypeMirror = paramElt.asType();
          TypeInfo adderType = typeFactory.create(adderTypeMirror);
          if (propTypeMirror != null) {
            if (propKind != PropertyKind.LIST && propKind != PropertyKind.SET) {
              throw new GenException(adderElt, name + "adder does not correspond to non list/set");
            }
            if (!adderType.equals(propType)) {
              throw new GenException(adderElt, name + " adder type " + adderType + "  does not match the property type " + propType);
            }
          } else {
            propTypeMirror = adderTypeMirror;
            propType = adderType;
            propKind = PropertyKind.LIST;
          }
          break;
        }
        case 2: {
          VariableElement paramElt = adderElt.getParameters().get(1);
          TypeMirror adderTypeMirror = paramElt.asType();
          TypeInfo adderType = typeFactory.create(adderTypeMirror);
          if (propTypeMirror != null) {
            if (propKind != PropertyKind.MAP) {
              throw new GenException(adderElt, name + "adder does not correspond to non map");
            }
            if (!adderType.equals(propType)) {
              throw new GenException(adderElt, name + " adder type " + adderType + "  does not match the property type " + propType);
            }
          } else {
            propTypeMirror = adderTypeMirror;
            propType = adderType;
            propKind = PropertyKind.MAP;
          }
          break;
        }
      }
    }

    //
    boolean jsonifiable;
    switch (propType.getKind()) {
      case OBJECT:
        if (propKind == PropertyKind.VALUE) {
          return;
        }
      case PRIMITIVE:
      case BOXED_PRIMITIVE:
      case STRING:
      case API:
      case JSON_OBJECT:
      case JSON_ARRAY:
      case ENUM:
        jsonifiable = true;
        break;
      case DATA_OBJECT:
        jsonifiable = ((DataObjectTypeInfo)propType).hasJsonEncoder();
        break;
      case OTHER:
        if (propType.getName().equals(Instant.class.getName())) {
          jsonifiable = true;
          break;
        }
        return;
      default:
        return;
    }

    boolean declared = false;
    Doc doc = null;
    for (ExecutableElement methodElt : Arrays.asList(setterElt, adderElt, getterElt)) {
      if (methodElt != null) {

        // A stream that list all overriden methods from super types
        // the boolean control whether or not we want to filter only annotated
        // data objects
        Function<Boolean, Stream<ExecutableElement>> overridenMeths = (annotated) -> {
          Set<DeclaredType> ancestorTypes = Helper.resolveAncestorTypes(modelElt, true, true);
          return ancestorTypes.
            stream().
            map(DeclaredType::asElement).
            filter(elt -> !annotated || elt.getAnnotation(DataObject.class) != null).
            flatMap(Helper.cast(TypeElement.class)).
            flatMap(elt -> elementUtils.getAllMembers(elt).stream()).
            flatMap(Helper.instanceOf(ExecutableElement.class)).
            filter(executableElt -> executableElt.getKind() == ElementKind.METHOD && elementUtils.overrides(methodElt, executableElt, modelElt));
        };

        //
        if (doc == null) {
          doc = docFactory.createDoc(methodElt);
          if (doc == null) {
            Optional<Doc> first = overridenMeths.apply(false).
              map(docFactory::createDoc).
              filter(d -> d != null).
              findFirst();
            doc = first.orElse(null);
          }
        }

        //
        if (!declared) {
          Element ownerElt = methodElt.getEnclosingElement();
          if (ownerElt.equals(modelElt)) {
            Object[] arr = overridenMeths.apply(true).limit(1).filter(elt -> !elt.getModifiers().contains(Modifier.ABSTRACT)).toArray();
            // Handle the case where this methods overrides from another data object
            declared = arr.length == 0;
          } else {
            declared = ownerElt.getAnnotation(DataObject.class) == null;
          }
        }
      }
    }

    List<AnnotationValueInfo> annotationValueInfos = new ArrayList<>();

    if (annotationMirrors != null) {
      annotationMirrors.stream().map(annotationValueInfoFactory::processAnnotation).forEach(annotationValueInfos::add);
    }

    Text propertyDeprecatedDesc = null;
    if (doc != null) {
      Optional<Tag> methodDeprecatedTag = doc.
          getBlockTags().
          stream().
          filter(tag -> tag.getName().equals("deprecated")).
          findFirst();
      if (methodDeprecatedTag.isPresent()) {
        propertyDeprecatedDesc = new Text(Helper.normalizeWhitespaces(methodDeprecatedTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt));
      }
    }

    PropertyInfo property = new PropertyInfo(declared, name, doc, propType,
      setterElt != null ? setterElt.getSimpleName().toString() : null,
      adderElt != null ? adderElt.getSimpleName().toString() : null,
      getterElt != null ? getterElt.getSimpleName().toString() : null,
      annotationValueInfos, propKind, jsonifiable, propertyDeprecated, propertyDeprecatedDesc);
    propertyMap.put(property.name, property);
  }

}
