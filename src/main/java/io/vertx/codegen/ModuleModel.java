package io.vertx.codegen;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeMirrorFactory;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleModel implements Model {

  private final PackageElement element;
  private final ModuleInfo info;
  private final List<AnnotationValueInfo> annotationValueInfos;

  public ModuleModel(Elements elementUtils, Types typeUtils, PackageElement element) {
    String modulePackage = element.getQualifiedName().toString();
    ModuleGen annotation = element.getAnnotation(ModuleGen.class);
    String moduleName = annotation.name();
    if (moduleName.isEmpty()) {
      throw new GenException(element, "A module name cannot be empty");
    }
    try {
      Case.KEBAB.parse(moduleName);
    } catch (IllegalArgumentException e) {
      throw new GenException(element, "Module name '" + moduleName + "' does not follow the snake case format (dash separated name)");
    }
    String groupPackage = annotation.groupPackage();
    if (groupPackage.equals("")) {
      groupPackage = modulePackage;
    } else if (!modulePackage.startsWith(groupPackage)) {
      throw new GenException(element, "A module package (" + modulePackage + ") must be prefixed by the group package (" + groupPackage + ")");
    }
    try {
      Case.QUALIFIED.parse(groupPackage);
    } catch (Exception e) {
      throw new GenException(element, "Invalid group package name " + groupPackage);
    }
    ModuleInfo info = new ModuleInfo(modulePackage, moduleName, groupPackage);
    AnnotationValueInfoFactory annotationFactory = new AnnotationValueInfoFactory(new TypeMirrorFactory(elementUtils, typeUtils, element));
    List<AnnotationValueInfo> annotationValueInfos = element
      .getAnnotationMirrors()
      .stream()
      .map(annotationFactory::processAnnotation)
      .collect(Collectors.toList());
    List<DeclaredType> a = elementUtils
      .getAllAnnotationMirrors(element)
      .stream()
      .filter(am -> am.getAnnotationType().toString().equals(ModuleGen.class.getName()))
      .flatMap(am -> am
        .getElementValues()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().getSimpleName().toString().equals("codecs"))
        .flatMap(e -> ((List<AnnotationValue>)e.getValue().getValue())
          .stream()
          .map(dt -> (DeclaredType)dt.getValue()))
      ).collect(Collectors.toList());
    a.forEach(dt -> {
      isLegalJsonCodec(elementUtils, typeUtils, dt);
    });
    this.element = element;
    this.info = info;
    this.annotationValueInfos = annotationValueInfos;
  }

  private void isLegalJsonCodec(Elements elementUtils, Types typeUtils, DeclaredType codecType) {
    TypeElement codecDeclaredElement = (TypeElement) codecType.asElement();
    if (elementUtils
      .getAllMembers(codecDeclaredElement)
      .stream()
      .noneMatch(e ->
        e.getKind() == ElementKind.FIELD &&
          e.getModifiers().containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)) &&
          e.getSimpleName().contentEquals("INSTANCE") &&
          typeUtils.isSameType(e.asType(), codecType)
      )) {
      throw new GenException(codecDeclaredElement, "The json codec " + codecType.toString() + " must have a public static final INSTANCE field of the codec type");
    }
    TypeElement codecElt = elementUtils.getTypeElement("io.vertx.core.spi.json.JsonCodec");
    TypeParameterElement jsonElt = codecElt.getTypeParameters().get(1);
    TypeMirror jsonType = Helper.resolveTypeParameter(typeUtils, codecType, jsonElt);
    if (jsonType != null && !isLegalJsonType(jsonType)) {
      throw new GenException(
        codecType.asElement(),
        "The specified json type in codec " + codecType.toString() + " is not a valid json type. Allowed types are java.lang.Boolean, java.lang.Number, java.lang.String and BOXED_PRIMITIVE"
      );
    }
  }

  private static boolean isLegalJsonType(TypeMirror type) {
    ClassKind kind = ClassKind.getKind(type.toString(), type.getAnnotation(DataObject.class) != null, type.getAnnotation(VertxGen.class) != null);
    return kind.json ||
      type.toString().equals("java.lang.Boolean") ||
      type.toString().equals("java.lang.Number") ||
      kind == ClassKind.STRING;
  }

  public String getName() {
    return info.getName();
  }

  public String translateFqn(String name) {
    return info.translatePackageName(name);
  }

  @Override
  public boolean process() {
    return false;
  }

  @Override
  public String getKind() {
    return "module";
  }

  @Override
  public Element getElement() {
    return element;
  }

  @Override
  public String getFqn() {
    return info.getPackageName();
  }

  public List<AnnotationValueInfo> getAnnotations() {
    return annotationValueInfos;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("fqn", info.getPackageName());
    vars.put("name", info.getName());
    vars.put("module", getModule());
    vars.put("annotations", getAnnotations());
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return info;
  }
}
