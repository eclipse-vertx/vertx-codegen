package io.vertx.test.codegen.generator.gen1;

import io.vertx.codegen.processor.EnumValueInfo;
import io.vertx.codegen.processor.Generator;
import io.vertx.codegen.processor.MethodInfo;
import io.vertx.codegen.processor.Model;
import io.vertx.codegen.processor.ParamInfo;
import io.vertx.codegen.processor.PropertyInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.processor.type.TypeInfo;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class TestGenerator extends Generator<Model>{
  public TestGenerator(){
    name = "testgen1";
    kinds = new HashSet<>(Arrays.asList("dataObject", "class", "enum", "module"));
  }

  @Override
  public Collection<Class<? extends Annotation>> annotations() {
    return Arrays.asList(DataObject.class, ModuleGen.class, VertxGen.class);
  }

  @Override
  public String filename(Model model) {
    return model.getFqn().replace('.', '_') + ".properties";
  }

  private String format(Map<String, Object> vars) {
    StringBuilder sb = new StringBuilder();
    vars.forEach((k, v) -> {
      if (v instanceof Iterable<?>) {
        Iterable<?> list = (Iterable<?>) v;
        Iterator<?> it = list.iterator();
        if (it.hasNext()) {
          Object elt = it.next();
          if (elt instanceof TypeInfo) {
            Map<String, TypeInfo> map = new java.util.TreeMap<>();
            Iterable<TypeInfo> types = (Iterable<TypeInfo>) list;
            for (TypeInfo type : types) {
              map.put(type.getName(), type);
            }
            sb.append(k).append('=').append(map.values()).append('\n');
            return;
          } else if (elt instanceof PropertyInfo) {
            Iterable<PropertyInfo> props = (Iterable<PropertyInfo>) list;
            props.forEach(prop -> {
              sb.append("property.").append(prop.getName()).append("=").append(prop.getType()).append("\n");
            });
            return;
          } else if (elt instanceof MethodInfo) {
            Iterable<MethodInfo> methods = (Iterable<MethodInfo>) list;
            methods.forEach(method -> {
              sb.append("method.").append(method.getName()).append(method.getParams().stream().map(ParamInfo::getName).collect(Collectors.joining(",", "(", ")"))).append("=").append(method.getReturnType()).append("\n");
            });
            return;
          } else if (elt instanceof EnumValueInfo) {
            Iterable<EnumValueInfo> values = (Iterable<EnumValueInfo>) list;
            sb.append("values=");
            values.forEach(prop -> {
              sb.append(prop.getIdentifier());
              sb.append(",");
            });
            sb.setCharAt(sb.length() - 1, '\n');
            return;
          }
        }
      }
      sb.append(k).append('=').append(v).append("\n");
    });
    return sb.toString();
  }

  @Override
  public String render(Model model, int index, int size, Map<String, Object> session) {
    Map<String, Object> vars = model.getVars();
    return format(vars);
  }
}
