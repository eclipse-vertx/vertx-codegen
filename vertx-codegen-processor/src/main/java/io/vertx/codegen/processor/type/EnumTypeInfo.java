package io.vertx.codegen.processor.type;

import io.vertx.codegen.processor.ModuleInfo;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class EnumTypeInfo extends ClassTypeInfo {

  final List<String> values;
  final boolean gen;

  public EnumTypeInfo(String fqcn, boolean gen, List<String> values,
		  ModuleInfo module, boolean nullable, DataObjectInfo dataObject) {
    super(ClassKind.ENUM, fqcn, module, nullable, Collections.emptyList(), false, dataObject);

    this.gen = gen;
    this.values = values;
  }

  /**
   * @return true if the type is a generated type
   */
  public boolean isGen() {
    return gen;
  }

  /**
   * @return the enum possible values
   */
  public List<String> getValues() {
    return values;
  }
}
