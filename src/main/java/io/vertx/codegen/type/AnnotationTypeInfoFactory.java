package io.vertx.codegen.type;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public class AnnotationTypeInfoFactory {

  private final Elements elementUtils;
  private final TypeMirrorFactory typeFactory;

  public AnnotationTypeInfoFactory(Elements elementUtils, Types typeUtils) {
    this.elementUtils = elementUtils;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils);
  }

  public AnnotationTypeInfo processAnnotation(AnnotationMirror annotation) {

    String fqn = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName().toString();
    AnnotationTypeInfo owner = new AnnotationTypeInfo(fqn);
    Map<? extends ExecutableElement, ? extends AnnotationValue> valueMap = elementUtils.getElementValuesWithDefaults(annotation);
    for (ExecutableElement valueElt : valueMap.keySet().stream().filter(e -> e.getKind().equals(ElementKind.METHOD)).collect(Collectors.toSet())) {
      owner.putMember(valueElt.getSimpleName().toString(), processAnnotationMemberValue(valueMap.get(valueElt)));
    }
    return owner;
  }

  @SuppressWarnings("unchecked")
  private Object processAnnotationMemberValue(AnnotationValue value) {
    Object realValue = value.getValue();

    if (realValue instanceof VariableElement) {
      realValue = ((VariableElement) realValue).getSimpleName().toString();
    } else if (realValue instanceof AnnotationMirror) {
      realValue = processAnnotation((AnnotationMirror) realValue);
    } else if (realValue instanceof TypeMirror) {
      realValue = typeFactory.create((TypeMirror) realValue);
    } else if (realValue instanceof List) {
      realValue = ((List<AnnotationValue>) realValue).stream().map(AnnotationValue::getValue).collect(Collectors.toList());
      if (((List) realValue).get(0) instanceof AnnotationMirror) {
        realValue = ((List<AnnotationMirror>) realValue).stream().map(this::processAnnotation).collect(Collectors.toList());
      } else if (((List) realValue).get(0) instanceof TypeMirror) {
        realValue = ((List<TypeMirror>) realValue).stream().map(typeFactory::create).collect(Collectors.toList());
      } else if (((List) realValue).get(0) instanceof VariableElement) {
        realValue = ((List<VariableElement>) realValue).stream().map(v -> v.getSimpleName().toString()).collect(Collectors.toList());
      }
    }

    return realValue;
  }

}
