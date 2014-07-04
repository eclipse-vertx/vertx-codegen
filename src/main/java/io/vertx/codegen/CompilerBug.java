package io.vertx.codegen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
import org.mvel2.templates.TemplateRuntime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
https://jira.codehaus.org/browse/MVEL-266
 */
public class CompilerBug {

  public static void main(String[] args) {
    String templ =
      "@declare{'someTempl'}\n" +
      "@if{obj.foo}x@end{}\n" +
      "@end{}\n" +
      "@foreach{obj : objs}\n" +
      "@includeNamed{'someTempl'}\n" +
      "@end{}\n" +
      "@foreach{obj : objs}\n" +
      "@includeNamed{'someTempl'}\n" +
      "@end{}";
    ArrayList<MyObj> objs = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      objs.add(new MyObj());
    }
    Map<String, Object> vars = new HashMap<>();
    vars.put("objs", objs);
    TemplateRuntime.eval(templ, vars);
  }

  public static class MyObj {
    public boolean isFoo() {
      return true;
    }
  }
}
