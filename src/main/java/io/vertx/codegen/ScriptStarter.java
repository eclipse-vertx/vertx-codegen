package io.vertx.codegen;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ScriptStarter {
  public static void main(String[] args) {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("nashorn");
    try {
      engine.eval("load('src/javascript/starter.js');");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
