package io.vertx.codegen;

import io.vertx.codegen.annotations.GenIgnore;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class OptionsModel implements Model {

  private final Elements elementUtils;
  private final Types typeUtils;
  private final TypeInfo.Factory typeFactory;
  private final TypeElement modelElt;
  private boolean processed = false;
  private final Map<String, PropertyInfo> propertyMap = new LinkedHashMap<>();
  private final Set<TypeInfo.Class> importedTypes = new HashSet<>();
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

  public Set<TypeInfo.Class> getImportedTypes() {
    return importedTypes;
  }

  @Override
  public Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("type", type);
    vars.put("properties", propertyMap.values());
    vars.put("importedTypes", importedTypes);
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    return vars;
  }

  public Map<String, PropertyInfo> getPropertyMap() {
    return propertyMap;
  }

  boolean process() {
    if (!processed) {
      if (modelElt.getKind() == ElementKind.INTERFACE) {
        for (Element memberElt : elementUtils.getAllMembers((TypeElement) modelElt)) {
          if (memberElt.getKind() == ElementKind.METHOD) {
            if (memberElt.getSimpleName().toString().equals("optionsFromJson")) {
              if (memberElt.getModifiers().contains(Modifier.STATIC)) {
                // TODO should probably also test that the method returns the right options type and
                // takes JsonObject as a parameter
                traverse();
                processImportedTypes();
                processed = true;
                return true;
              }
            }
          }
        }
        throw new GenException(modelElt, "Options " + modelElt + " class does not have a static factory method called optionsFromJson");
      } else {
        throw new GenException(modelElt, "Options " + modelElt + " must be an interface not a class");
      }
    }
    return false;
  }

  private void traverse() {
    TypeInfo type = typeFactory.create(modelElt.asType());
    if (!(type instanceof TypeInfo.Class)) {
      throw new GenException(modelElt, "Options must be a plain java class with no type parameters");
    }
    this.type = (TypeInfo.Class) type;
    for (Element enclosedElt : modelElt.getEnclosedElements()) {
      switch (enclosedElt.getKind()) {
        case METHOD: {
          ExecutableElement methodElt = (ExecutableElement) enclosedElt;
          if (!methodElt.getModifiers().contains(Modifier.STATIC) && methodElt.getAnnotation(GenIgnore.class) == null) {
            processMethod(methodElt);
          }
          break;
        }
      }
    }
  }

  private void processImportedTypes() {
    for (PropertyInfo property : propertyMap.values()) {
      property.type.collectImports(importedTypes);
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
          if (!methodElt.getReturnType().equals(modelElt.asType())) {
            throw new GenException(methodElt, "Setter must be fluent and return itself instead of " + methodElt.getReturnType());
          }
          VariableElement parameterElt = parameters.get(0);
          TypeInfo type = typeFactory.create(parameterElt.asType());
          boolean array;
          if ("add".equals(prefix)) {
            if (name.endsWith("s")) {
              throw new GenException(methodElt, "Option adder name must not terminate with 's' char");
            } else {
              name += "s";
            }
            array = true;
          } else {
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
          PropertyInfo property = new PropertyInfo(name, type, array);
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
