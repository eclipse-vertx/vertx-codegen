package io.vertx.codegen.type;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.tree.JCTree;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
class TreeTypeInternal implements TypeUse.TypeInternal {

  public static final TypeUse.TypeInternalProvider PROVIDER = new TypeUse.TypeInternalProvider() {
    public TypeUse.TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int paramIndex) {
      Trees trees = Trees.instance(env);
      if (trees == null) {
        return null;
      }
      MethodTree tree = trees.getTree(methodElt);
      if (tree == null) {
        return null;
      }
      Tree type = tree.getParameters().get(paramIndex).getType();
      return new TreeTypeInternal(type, isNullable(type));
    }
    public TypeUse.TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
      Trees trees = Trees.instance(env);
      if (trees == null) {
        return null;
      }
      JCTree.JCMethodDecl tree = (JCTree.JCMethodDecl) trees.getTree(methodElt);
      if (tree == null) {
        return null;
      }
      JCTree type = tree.getReturnType();
      boolean nullable = isNullable(type);
      if (!nullable) {
        JCTree.JCModifiers mods = tree.mods;
        for (JCTree.JCAnnotation jca : mods.annotations) {
          if (jca.type.toString().equals(TypeUse.NULLABLE)) {
            nullable = true;
            break;
          }
        }
      }
      return new TreeTypeInternal(type, nullable);
    }

  };

  private final Tree type;
  private final boolean nullable;

  private TreeTypeInternal(Tree type, boolean nullable) {
    this.type = type;
    this.nullable = nullable;
  }

  public boolean isNullable() {
    return nullable;
  }

  @Override
  public String rawName() {
    if (type instanceof ParameterizedTypeTree) {
      ParameterizedTypeTree parameterizedType = (ParameterizedTypeTree) type;
      return ((TypeElement)((JCTree.JCTypeApply) parameterizedType).type.asElement()).getQualifiedName().toString();
    }
    return null;
  }

  public TypeUse.TypeInternal getArgAt(int index) {
    ParameterizedTypeTree parameterizedType = (ParameterizedTypeTree) type;
    Tree type = parameterizedType.getTypeArguments().get(index);
    return new TreeTypeInternal(type, isNullable(type));
  }

  private static boolean isNullable(Tree type) {
    TypeMirror typeMirror = ((JCTree)type).type;
    for (AnnotationMirror mirror : typeMirror.getAnnotationMirrors()) {
      if (mirror.getAnnotationType().toString().equals(TypeUse.NULLABLE)) {
        return true;
      }
    }
    if (type instanceof JCTree.JCTypeApply) {
      JCTree.JCExpression expr = ((JCTree.JCTypeApply) type).clazz;
      if (expr instanceof AnnotatedTypeTree) {
        AnnotatedTypeTree tree2 = (AnnotatedTypeTree) expr;
        for (AnnotationTree mirror : tree2.getAnnotations()) {
          if (mirror instanceof JCTree.JCAnnotation) {
            JCTree.JCAnnotation jca = (JCTree.JCAnnotation) mirror;
            if (jca.type.toString().equals(TypeUse.NULLABLE)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
}
