package io.vertx.codegen.type;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.tree.JCTree;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
class TreeTypeInternal implements TypeUse.TypeInternal {

  static final TypeUse.TypeInternalProvider PROVIDER = new TypeUse.TypeInternalProvider() {
    public TypeUse.TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int paramIndex) {
      Trees trees = Trees.instance(env);
      if (trees == null) {
        return null;
      }
      MethodTree tree = trees.getTree(methodElt);
      if (tree == null) {
        return null;
      }
      return new TreeTypeInternal(tree.getParameters().get(paramIndex).getType());
    }
    public TypeUse.TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
      Trees trees = Trees.instance(env);
      if (trees == null) {
        return null;
      }
      MethodTree tree = trees.getTree(methodElt);
      if (tree == null) {
        return null;
      }
      return new TreeTypeInternal(tree.getReturnType());
    }

  };

  private final Tree type;
  private final boolean nullable;

  private TreeTypeInternal(Tree type) {
    this.type = type;
    this.nullable = isNullable(type);
  }

  public boolean isNullable() {
    return nullable;
  }

  public TypeUse.TypeInternal getArgAt(int index) {
    if (type instanceof ParameterizedTypeTree) {
      ParameterizedTypeTree parameterizedType = (ParameterizedTypeTree) type;
      return new TreeTypeInternal(parameterizedType.getTypeArguments().get(index));
    } else {
      return TypeUse.NULL_TYPE_INTERNAL;
    }
  }

  private static boolean isNullable(Tree type) {
    TypeMirror typeMirror = ((JCTree)type).type;
    if (typeMirror instanceof com.sun.tools.javac.code.Type.AnnotatedType) {
      com.sun.tools.javac.code.Type.AnnotatedType abc = (com.sun.tools.javac.code.Type.AnnotatedType) typeMirror;
      for (AnnotationMirror mirror : abc.getAnnotationMirrors()) {
        if (mirror.getAnnotationType().toString().equals(TypeUse.NULLABLE)) {
          return true;
        }
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
