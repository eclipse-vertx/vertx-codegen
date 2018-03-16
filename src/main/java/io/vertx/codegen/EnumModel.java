package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.type.TypeMirrorFactory;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A processed enum.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumModel implements Model  {

  private final Doc.Factory docFactory;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected final TypeElement modelElt;
  protected EnumTypeInfo type;
  private final AnnotationValueInfoFactory annotationValueInfoFactory;
  private Doc doc;
  private List<EnumValueInfo> values;
  private List<AnnotationValueInfo> annotations;
  private boolean processed;

  public EnumModel(Messager messager, Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    this.docFactory = new Doc.Factory(messager, elementUtils, typeUtils, new TypeMirrorFactory(elementUtils, typeUtils), modelElt);
    this.typeUtils = typeUtils;
    this.elementUtils = elementUtils;
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(new TypeMirrorFactory(elementUtils, typeUtils));
  }

  boolean process() {
    if (!processed) {
      if (modelElt.getKind() != ElementKind.ENUM) {
        throw new GenException(modelElt, "@VertxGen can only be used with interfaces or enums" + modelElt.asType().toString());
      }
      doc = docFactory.createDoc(modelElt);
      type = (EnumTypeInfo) new TypeMirrorFactory(elementUtils, typeUtils).create(modelElt.asType());
      Helper.checkUnderModule(this, "@VertxGen");
      values = elementUtils.
          getAllMembers(modelElt).
          stream().
          filter(elt -> elt.getKind() == ElementKind.ENUM_CONSTANT).
          flatMap(Helper.cast(VariableElement.class)).
          map(elt -> new EnumValueInfo(elt.getSimpleName().toString(), docFactory.createDoc(elt))).
          collect(Collectors.toList());
      if (values.isEmpty()) {
        throw new GenException(modelElt, "No empty enums");
      }
      processed = true;
      return true;
    } else {
      return false;
    }
  }

  private void processTypeAnnotations() {
    this.annotations = elementUtils.getAllAnnotationMirrors(modelElt).stream().map(annotationValueInfoFactory::processAnnotation).collect(Collectors.toList());
  }

  /**
   * @return the type of this enum model
   */
  public EnumTypeInfo getType() {
    return type;
  }

  /**
   * @return the possible enum values
   */
  public List<EnumValueInfo> getValues() {
    return values;
  }

  /**
   * @return the enum doc
   */
  public Doc getDoc() {
    return doc;
  }

  @Override
  public String getKind() {
    return "enum";
  }

  @Override
  public Element getElement() {
    return modelElt;
  }

  @Override
  public String getFqn() {
    return modelElt.getQualifiedName().toString();
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("type", getType());
    vars.put("doc", doc);
    vars.put("values", values);
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return type.getModule();
  }
}
