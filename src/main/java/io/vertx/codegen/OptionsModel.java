package io.vertx.codegen;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Options;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsModel implements Model {

  private final Elements elementUtils;
  private final Types typeUtils;
  private final TypeInfo.Factory typeFactory;
  private final TypeElement modelElt;
  private boolean processed = false;
  private boolean concrete;
  private final Map<String, PropertyInfo> propertyMap = new LinkedHashMap<>();
  private final Set<TypeInfo.Class> superTypes = new LinkedHashSet<>();
  private final Set<TypeInfo.Class> concreteSuperTypes = new LinkedHashSet<>();
  private final Set<TypeInfo.Class> abstractSuperTypes = new LinkedHashSet<>();
  private final Set<TypeInfo.Class> importedTypes = new LinkedHashSet<>();
  private TypeInfo.Class type;

  public OptionsModel(Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.typeFactory = new TypeInfo.Factory(elementUtils, typeUtils);
    this.modelElt = modelElt;
  }

  @Override
  public String getKind() {
    return "options";
  }

  @Override
  public Element getElement() {
    return modelElt;
  }

  @Override
  public String getFqn() {
    return type.getName();
  }

  public boolean isAbstract() {
    return !concrete;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public Set<TypeInfo.Class> getImportedTypes() {
    return importedTypes;
  }

  public Map<String, PropertyInfo> getPropertyMap() {
    return propertyMap;
  }

  public Set<TypeInfo.Class> getConcreteSuperTypes() {
    return concreteSuperTypes;
  }

  public Set<TypeInfo.Class> getAbstractSuperTypes() {
    return abstractSuperTypes;
  }

  public Set<TypeInfo.Class> getSuperTypes() {
    return superTypes;
  }

  @Override
  public Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("type", type);
    vars.put("concrete", concrete);
    vars.put("properties", propertyMap.values());
    vars.put("importedTypes", importedTypes);
    vars.put("superTypes", superTypes);
    vars.put("concreteSuperTypes", concreteSuperTypes);
    vars.put("abstractSuperTypes", abstractSuperTypes);
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    return vars;
  }

  boolean process() {
    if (!processed) {
      if (modelElt.getKind() == ElementKind.INTERFACE) {
        traverse();
        processImportedTypes();
        processed = true;
        return true;
      } else {
        throw new GenException(modelElt, "Options " + modelElt + " must be an interface not a class");
      }
    }
    return false;
  }

  private void traverse() {
    this.concrete = modelElt.getAnnotation(Options.class).concrete();
    try {
      this.type = (TypeInfo.Class) typeFactory.create(modelElt.asType());
    } catch (ClassCastException e) {
      throw new GenException(modelElt, "Options must be a plain java class with no type parameters");
    }
    for (TypeMirror superTM : modelElt.getInterfaces()) {
      if (((DeclaredType) superTM).asElement().getAnnotation(Options.class) != null) {
        TypeInfo.Class superType = (TypeInfo.Class) typeFactory.create(superTM);
        if (((DeclaredType) superTM).asElement().getAnnotation(Options.class).concrete()) {
          if (!concrete) {
            throw new GenException(modelElt, "Abstract options cannot inherit concrete options");
          } else if (concreteSuperTypes.size() > 0) {
            throw new GenException(modelElt, "Concrete options cannot inherit at most one concrete options");
          }
          concreteSuperTypes.add(superType);
        } else {
          abstractSuperTypes.add(superType);
        }
        superTypes.add(superType);
      }
    }
    boolean hasOptionsFromJson = false;
    for (Element enclosedElt : elementUtils.getAllMembers(modelElt)) {
      switch (enclosedElt.getKind()) {
        case METHOD: {
          ExecutableElement methodElt = (ExecutableElement) enclosedElt;
          Element ownerElt = methodElt.getEnclosingElement();
          TypeElement objectElt = elementUtils.getTypeElement("java.lang.Object");
          if (methodElt.getAnnotation(GenIgnore.class) == null && !ownerElt.equals(objectElt)) {
            if (methodElt.getModifiers().contains(Modifier.STATIC)) {
              if (methodElt.getSimpleName().toString().equals("optionsFromJson")) {
                hasOptionsFromJson = true;
              }
            } else {
              processMethod(methodElt);
            }
          }
          break;
        }
      }
    }
    if (concrete && !hasOptionsFromJson) {
      throw new GenException(modelElt, "Options " + modelElt + " class does not have a static factory method called optionsFromJson");
    }
  }

  private void processImportedTypes() {
    for (PropertyInfo property : propertyMap.values()) {
      property.type.collectImports(importedTypes);
    }
    importedTypes.addAll(superTypes.stream().collect(Collectors.toList()));
    for (Iterator<TypeInfo.Class> i = importedTypes.iterator();i.hasNext();) {
      TypeInfo.Class importedType = i.next();
      if (importedType.getPackageName().equals(type.getPackageName())) {
        i.remove();
      }
    }
  }

  private void processMethod(ExecutableElement methodElt) {
    String methodName = methodElt.getSimpleName().toString();
    if (methodName.startsWith("is") && methodName.length() > 2) {
      List<? extends VariableElement> parameters = methodElt.getParameters();
      if (parameters.size() > 0) {
        throw new GenException(methodElt, "Invalid getter with arguments " + parameters);
      }
      return;
    } else if (methodName.length() > 3) {
      String prefix = methodName.substring(0, 3);
      String name = Helper.normalizePropertyName(methodName.substring(3));
      List<? extends VariableElement> parameters = methodElt.getParameters();
      switch (prefix) {
        case "add":
        case "set": {
          if (parameters.size() != 1) {
            throw new GenException(methodElt, "Setter with incorrect number of arguments " + parameters);
          }
          VariableElement parameterElt = parameters.get(0);
          TypeInfo type = typeFactory.create(parameterElt.asType());
          boolean array;
          boolean adder;
          if ("add".equals(prefix)) {
            if (name.endsWith("s")) {
              throw new GenException(methodElt, "Option adder name must not terminate with 's' char");
            } else {
              name += "s";
            }
            array = true;
            adder = true;
          } else {
            adder = false;
            if (type.getKind() == ClassKind.LIST) {
              type = ((TypeInfo.Parameterized) type).getArgs().get(0);
              array = true;
            } else {
              array = false;
            }
          }
          switch (type.getKind()) {
            case PRIMITIVE:
            case BOXED_PRIMITIVE:
            case STRING:
            case OPTIONS:
            case API:
            case JSON_OBJECT:
              break;
            default:
              throw new GenException(parameterElt, "Invalid adder/setter type " + type);
          }

          boolean declared;
          Element ownerElt = methodElt.getEnclosingElement();
          if (ownerElt.equals(modelElt)) {
            // Handle the case where this methods overrides from another options
            declared = true;
            for (TypeMirror superTM : modelElt.getInterfaces()) {
              DeclaredType superDT = (DeclaredType) superTM;
              if (superDT.asElement().getAnnotation(Options.class) != null) {
                for (Element foo : elementUtils.getAllMembers((TypeElement) superDT.asElement())) {
                  if (foo instanceof ExecutableElement) {
                    if (elementUtils.overrides(methodElt, (ExecutableElement) foo, modelElt)) {
                      declared = false;
                    }
                  }
                }
              }
            }
          } else {
            declared = ownerElt.getAnnotation(Options.class) == null;
          }

          PropertyInfo property = new PropertyInfo(declared, name, type, methodName, array, adder);
          if (propertyMap.containsKey(property.name)) {
            //
          }
          propertyMap.put(property.name, property);
          return;
        }
        case "get": {
          if (parameters.size() > 0) {
            throw new GenException(methodElt, "Invalid getter with arguments " + parameters);
          }
          return;
        }
      }
    }
    throw new GenException(methodElt, "Method " + methodElt + " not supported");
  }
}
