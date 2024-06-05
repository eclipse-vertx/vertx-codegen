package io.vertx.codegen.processor.type;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ParameterizedTypeInfo extends TypeInfo {

  final ClassTypeInfo raw;
  final boolean nullable;
  final List<TypeInfo> args;

  public ParameterizedTypeInfo(ClassTypeInfo raw, boolean nullable, List<TypeInfo> args) {
    this.raw = raw;
    this.nullable = nullable;
    this.args = args;
  }

  @Override
  public TypeInfo getErased() {
    return new ParameterizedTypeInfo(raw, nullable, args.stream().map(TypeInfo::getErased).collect(Collectors.toList()));
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  public ClassTypeInfo getRaw() {
    return raw;
  }

  /**
   * @return the type arguments
   */
  public List<TypeInfo> getArgs() {
    return args;
  }

  /**
   * @param index the type argument index
   * @return a specific type argument
   */
  public TypeInfo getArg(int index) {
    return args.get(index);
  }

  @Override
  public ClassKind getKind() {
    return raw.getKind();
  }

  @Override
  public void collectImports(Collection<ClassTypeInfo> imports) {
    raw.collectImports(imports);
    args.stream().forEach(a -> a.collectImports(imports));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ParameterizedTypeInfo) {
      ParameterizedTypeInfo that = (ParameterizedTypeInfo) obj;
      return raw.equals(that.raw) && args.equals(that.args);
    }
    return false;
  }

  @Override
  public String format(boolean qualified) {
    StringBuilder buf = new StringBuilder(raw.format(qualified)).append('<');
    for (int i = 0; i < args.size(); i++) {
      TypeInfo typeArgument = args.get(i);
      if (i > 0) {
        buf.append(',');
      }
      buf.append(typeArgument.format(qualified));
    }
    buf.append('>');
    return buf.toString();
  }

  @Override
  public String translateName(TypeNameTranslator translator) {
    StringBuilder buf = new StringBuilder(raw.translateName(translator)).append('<');
    for (int i = 0; i < args.size(); i++) {
      TypeInfo typeArgument = args.get(i);
      if (i > 0) {
        buf.append(',');
      }
      buf.append(typeArgument.translateName(translator));
    }
    buf.append('>');
    return buf.toString();
  }

  @Override
  public boolean isParameterized() {
    return true;
  }
}
