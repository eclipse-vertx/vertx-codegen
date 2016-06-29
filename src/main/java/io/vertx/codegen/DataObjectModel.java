package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.TypeMirrorFactory;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.core.json.JsonObject;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class DataObjectModel implements Model {

  private final Elements elementUtils;
  private final Types typeUtils;
  private final Doc.Factory docFactory;
  private final TypeMirrorFactory typeFactory;
  private final TypeElement modelElt;
  private boolean processed = false;
  private boolean concrete;
  private boolean isClass;
  private boolean generateConverter;
  private boolean inheritConverter;
  private final Map<String, PropertyInfo> propertyMap = new LinkedHashMap<>();
  private final Set<ClassTypeInfo> superTypes = new LinkedHashSet<>();
  private ClassTypeInfo superType;
  private final Set<ClassTypeInfo> abstractSuperTypes = new LinkedHashSet<>();
  private final Set<ClassTypeInfo> importedTypes = new LinkedHashSet<>();
  private ClassTypeInfo type;
  private Doc doc;
  private boolean jsonifiable;

  public DataObjectModel(Elements elementUtils, Types typeUtils, TypeElement modelElt, Messager messager) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils);
    this.docFactory = new Doc.Factory(messager, elementUtils, typeUtils, typeFactory, modelElt);
    this.modelElt = modelElt;
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

  public ClassTypeInfo getType() {
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

  public boolean isJsonifiable() {
    return jsonifiable;
  }

  public boolean getInheritConverter() {
    return inheritConverter;
  }

  @Override
  public Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("type", type);
    vars.put("doc", doc);
    vars.put("generateConverter", generateConverter);
    vars.put("inheritConverter", inheritConverter);
    vars.put("concrete", concrete);
    vars.put("isClass", isClass);
    vars.put("properties", propertyMap.values());
    vars.put("importedTypes", importedTypes);
    vars.put("superTypes", superTypes);
    vars.put("superType", superType);
    vars.put("abstractSuperTypes", abstractSuperTypes);
    vars.put("jsonifiable", jsonifiable);
    return vars;
  }

  boolean process() {
    if (!processed) {
      if (modelElt.getKind() == ElementKind.INTERFACE || modelElt.getKind() == ElementKind.CLASS) {
        traverse();
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
    this.inheritConverter = ann.inheritConverter();
    this.isClass = modelElt.getKind() == ElementKind.CLASS;
    this.concrete = isClass && !modelElt.getModifiers().contains(Modifier.ABSTRACT);
    try {
      this.type = (ClassTypeInfo) typeFactory.create(modelElt.asType());
    } catch (ClassCastException e) {
      throw new GenException(modelElt, "Data object must be a plain java class with no type parameters");
    }
    Helper.checkUnderModule(this, "@VertxGen");
    doc = docFactory.createDoc(modelElt);

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

    int result = 0;
    List<ExecutableElement> methodsElt = new ArrayList<>();
    for (Element enclosedElt : elementUtils.getAllMembers(modelElt)) {
      switch (enclosedElt.getKind()) {
        case CONSTRUCTOR:
          ExecutableElement constrElt = (ExecutableElement) enclosedElt;
          result |= processConstructor(constrElt);
          break;
        case METHOD: {
          ExecutableElement methodElt = (ExecutableElement) enclosedElt;
          if (methodElt.getSimpleName().toString().equals("toJson") &&
              methodElt.getParameters().isEmpty() &&
              typeFactory.create(methodElt.getReturnType()).getKind() == ClassKind.JSON_OBJECT) {
            jsonifiable = true;
          }
          if (methodElt.getAnnotation(GenIgnore.class) == null) {
            methodsElt.add(methodElt);
          }
          break;
        }
      }
    }

    processMethods(methodsElt);

    boolean hasJsonConstructor = (result & 2) == 2;

    if (concrete && !hasJsonConstructor) {
      throw new GenException(modelElt, "Data object " + modelElt + " class does not have a constructor " + modelElt.getSimpleName() + "(" + JsonObject.class.getSimpleName() + ")");
    }

    // Sort the properties so we do have a consistent order
    ArrayList<PropertyInfo> props = new ArrayList<>(propertyMap.values());
    Collections.sort(props, (p1, p2) -> p1.name.compareTo(p2.name));
    propertyMap.clear();
    props.forEach(prop -> propertyMap.put(prop.name, prop));
  }

  private void processImportedTypes() {
    for (PropertyInfo property : propertyMap.values()) {
      property.type.collectImports(importedTypes);
    }
    importedTypes.addAll(superTypes.stream().collect(toList()));
    for (Iterator<ClassTypeInfo> i = importedTypes.iterator();i.hasNext();) {
      ClassTypeInfo importedType = i.next();
      if (importedType.getPackageName().equals(type.getPackageName())) {
        i.remove();
      }
    }
  }

  private int processConstructor(ExecutableElement constrElt) {
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
              return 2;
            }
          }
        }
      }
    }

    return 0;
  }

  private void processMethods(List<ExecutableElement> methodsElt) {

    Map<String, ExecutableElement> getters = new HashMap<>();
    Map<String, ExecutableElement> setters = new HashMap<>();
    Map<String, ExecutableElement> adders = new HashMap<>();

    while (methodsElt.size() > 0) {
      ExecutableElement methodElt = methodsElt.remove(0);
      if (((TypeElement)methodElt.getEnclosingElement()).getQualifiedName().toString().equals("java.lang.Object")) {
        continue;
      }
      String methodName = methodElt.getSimpleName().toString();
      if (methodName.startsWith("get") && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3)) && methodElt.getParameters().isEmpty() && methodElt.getReturnType().getKind() != TypeKind.VOID) {
        String name = Helper.normalizePropertyName(methodName.substring(3));
        getters.put(name, methodElt);
      } else if (methodName.startsWith("is") && methodName.length() > 2 && Character.isUpperCase(methodName.charAt(2)) && methodElt.getParameters().isEmpty() && methodElt.getReturnType().getKind() != TypeKind.VOID) {
        String name = Helper.normalizePropertyName(methodName.substring(2));
        getters.put(name, methodElt);
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
          TypeMirror t= methodElt.getParameters().get(0).asType();
          if (numParams == 1 || (numParams == 2 && t.getKind() == TypeKind.DECLARED &&
              ((TypeElement)((DeclaredType)t).asElement()).getQualifiedName().toString().equals("java.lang.String"))) {
            adders.put(name, methodElt);
          }
        } else {
          if (numParams == 1) {
            setters.put(name, methodElt);
          }
        }
      }
    }

    Set<String> names = new HashSet<>();
    names.addAll(getters.keySet());
    names.addAll(setters.keySet());
    names.addAll(adders.keySet());

    for (String name : names) {
      processMethod(name, getters.get(name), setters.get(name), adders.get(name));
    }


  }

  private void processMethod(String name, ExecutableElement getterElt, ExecutableElement setterElt, ExecutableElement adderElt) {

    PropertyKind propKind = null;
    TypeInfo propType = null;
    TypeMirror propTypeMirror = null;

    //
    if (setterElt != null) {
      VariableElement paramElt = setterElt.getParameters().get(0);
      propTypeMirror = paramElt.asType();
      propType = typeFactory.create(propTypeMirror);
      propKind = PropertyKind.forType(propType.getKind());
      switch (propKind) {
        case LIST:
        case SET:
          propType = ((ParameterizedTypeInfo) propType).getArgs().get(0);
          propTypeMirror = ((DeclaredType)propTypeMirror).getTypeArguments().get(0);
          break;
        case MAP:
          propType = ((ParameterizedTypeInfo) propType).getArgs().get(1);
          propTypeMirror = ((DeclaredType)propTypeMirror).getTypeArguments().get(1);
          break;
      }
    }

    //
    if (getterElt != null) {
      TypeMirror getterTypeMirror = getterElt.getReturnType();
      TypeInfo getterType = typeFactory.create(getterTypeMirror);
      PropertyKind getterKind = PropertyKind.forType(getterType.getKind());
      switch (getterKind) {
        case LIST:
        case SET:
          getterType = ((ParameterizedTypeInfo) getterType).getArgs().get(0);
          getterTypeMirror = ((DeclaredType)getterTypeMirror).getTypeArguments().get(0);
          break;
        case MAP:
          getterType = ((ParameterizedTypeInfo) getterType).getArgs().get(1);
          getterTypeMirror = ((DeclaredType)getterTypeMirror).getTypeArguments().get(1);
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
        Element propTypeElt = typeUtils.asElement(propTypeMirror);
        jsonifiable = propTypeElt.getAnnotation(DataObject.class) == null ||
            Helper.isJsonifiable(elementUtils, typeUtils, (TypeElement)propTypeElt);
        break;
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

    PropertyInfo property = new PropertyInfo(declared, name, doc, propType,
        setterElt != null ? setterElt.getSimpleName().toString() : null,
        adderElt != null ? adderElt.getSimpleName().toString() : null,
        getterElt != null ? getterElt.getSimpleName().toString() : null,
        propKind, jsonifiable);
    propertyMap.put(property.name, property);
  }



}
