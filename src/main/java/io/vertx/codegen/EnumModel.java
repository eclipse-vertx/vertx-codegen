package io.vertx.codegen;

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.type.TypeMirrorFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A processed enum.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumModel implements Model {

  private final Doc.Factory docFactory;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected final TypeElement modelElt;
  protected final TypeMirrorFactory typeMirrorFactory;
  protected EnumTypeInfo type;
  private final AnnotationValueInfoFactory annotationValueInfoFactory;
  private Doc doc;
  private List<EnumValueInfo> values;
  private List<AnnotationValueInfo> annotations;
  private boolean processed;
  private boolean deprecated;
  private Text deprecatedDesc;

  public EnumModel(ProcessingEnvironment env, TypeElement modelElt) {
    this.typeUtils = env.getTypeUtils();
    this.elementUtils = env.getElementUtils();
    this.typeMirrorFactory = new TypeMirrorFactory(elementUtils, typeUtils, elementUtils.getPackageOf(modelElt));
    this.docFactory = new Doc.Factory(env.getMessager(), elementUtils, typeUtils, typeMirrorFactory, modelElt);
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(typeMirrorFactory);
    this.deprecated = modelElt.getAnnotation(Deprecated.class) != null;
  }

  public boolean process() {
    if (!processed) {
      if (modelElt.getKind() != ElementKind.ENUM) {
        throw new GenException(modelElt, "@VertxGen can only be used with interfaces or enums" + modelElt.asType().toString());
      }
      doc = docFactory.createDoc(modelElt);
      if (doc != null) {
        doc.getBlockTags().stream().filter(tag -> tag.getName().equals("deprecated")).findFirst().ifPresent(tag ->
          deprecatedDesc = new Text(Helper.normalizeWhitespaces(tag.getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt))
        ); 
      }
      type = (EnumTypeInfo) typeMirrorFactory.create(modelElt.asType());
      Helper.checkUnderModule(this, "@VertxGen");
      values = elementUtils.
        getAllMembers(modelElt).
        stream().
        filter(elt -> elt.getKind() == ElementKind.ENUM_CONSTANT).
        flatMap(Helper.cast(VariableElement.class)).
        map(elt -> {
          Doc doc = docFactory.createDoc(elt);
          Text enumItemDeprecatedDesc = null;
          if (doc != null) {
            Optional<Tag> methodDeprecatedTag = doc.
                getBlockTags().
                stream().
                filter(tag -> tag.getName().equals("deprecated")).
                findFirst();
            if (methodDeprecatedTag.isPresent()) {
              enumItemDeprecatedDesc = new Text(Helper.normalizeWhitespaces(methodDeprecatedTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt));
            }
          }
          return new EnumValueInfo(elt.getSimpleName().toString(), doc, elt.getAnnotation(Deprecated.class) != null, enumItemDeprecatedDesc);
        }).
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

  /**
   * @return {@code true} if the class has a {@code @Deprecated} annotation
   */
  public boolean isDeprecated() {
    return deprecated;
  }
  /**
   * @return the description of deprecated
   */
  public Text getDeprecatedDesc() {
    return deprecatedDesc;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("type", getType());
    vars.put("doc", doc);
    vars.put("values", values);
    vars.put("deprecated", deprecated);
    vars.put("deprecatedDesc", getDeprecatedDesc());
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return type.getModule();
  }
}
