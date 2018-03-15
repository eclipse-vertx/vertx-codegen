package io.vertx.codegen.type;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import io.vertx.codegen.type.TypeUse.TypeInternal;

class MirrorTypeInternal implements TypeInternal {

  public static final TypeUse.TypeInternalProvider PROVIDER = new TypeUse.TypeInternalProvider() {
    @Override
    public TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int index) {
      return new MirrorTypeInternal(methodElt.getParameters().get(index).asType());
    }

    @Override
    public TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
      return new MirrorTypeInternal(methodElt.getReturnType());
    }
  };

  final TypeMirror mirror;

  MirrorTypeInternal(TypeMirror mirror) {
    this.mirror = mirror;
  }

  @Override
  public String rawName() {
    if (mirror.getKind() == TypeKind.DECLARED) {
      return ((TypeElement)((DeclaredType)mirror).asElement()).getQualifiedName().toString();
    } else {
      return null;
    }
  }

  @Override
  public boolean isNullable() {
    for (AnnotationMirror annotation : mirror.getAnnotationMirrors()) {
      DeclaredType annotationType = annotation.getAnnotationType();
      TypeElement annotationTypeElt = (TypeElement) annotationType.asElement();
      if (annotationTypeElt.getQualifiedName().toString().equals(TypeUse.NULLABLE)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public TypeInternal getArgAt(int index) {
    List<? extends TypeMirror> args = ((DeclaredType) mirror).getTypeArguments();
    return new MirrorTypeInternal(args.get(index));
  }
}
