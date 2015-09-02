package io.vertx.codegen;

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

  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected final TypeElement modelElt;
  protected TypeInfo.Class.Enum type;
  private List<String> values;
  private boolean processed;

  public EnumModel(Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    this.typeUtils = typeUtils;
    this.elementUtils = elementUtils;
    this.modelElt = modelElt;
  }

  boolean process() {
    if (!processed) {
      if (modelElt.getKind() != ElementKind.ENUM) {
        throw new GenException(modelElt, "@VertxGen can only be used with interfaces or enums" + modelElt.asType().toString());
      }
      type = (TypeInfo.Class.Enum) new TypeInfo.Factory(elementUtils, typeUtils).create(modelElt.asType());
      values = elementUtils.
          getAllMembers(modelElt).
          stream().
          filter(elt -> elt.getKind() == ElementKind.ENUM_CONSTANT).
          flatMap(Helper.cast(VariableElement.class)).
          map(elt -> elt.getSimpleName().toString()).
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

  /**
   * @return the type of this enum model
   */
  public TypeInfo.Class.Enum getType() {
    return type;
  }

  /**
   * @return the possible enum values
   */
  public List<String> getValues() {
    return values;
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
    Map<String, Object> vars = new HashMap<>();
    vars.put("type", getType());
    vars.put("helper", new Helper());
    vars.put("members", values);
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return type.getModule();
  }
}
