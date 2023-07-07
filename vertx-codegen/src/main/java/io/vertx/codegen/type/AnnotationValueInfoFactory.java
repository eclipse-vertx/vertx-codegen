package io.vertx.codegen.type;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public class AnnotationValueInfoFactory {

  private final Elements elementUtils;
  private final TypeMirrorFactory typeFactory;

  public AnnotationValueInfoFactory(TypeMirrorFactory typeMirrorFactory) {
    this.elementUtils = typeMirrorFactory.elementUtils;
    this.typeFactory = typeMirrorFactory;
  }

  public AnnotationValueInfo processAnnotation(AnnotationMirror annotation) {

    String fqn = ((TypeElement) annotation.getAnnotationType().asElement()).getQualifiedName().toString();
    AnnotationValueInfo owner = new AnnotationValueInfo(fqn);
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
      if (((List) realValue).isEmpty()) {
        realValue = Collections.emptyList();
      } else if (((List) realValue).get(0) instanceof AnnotationMirror) {
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
