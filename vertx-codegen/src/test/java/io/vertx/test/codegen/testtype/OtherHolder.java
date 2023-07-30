package io.vertx.test.codegen.testtype;

import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface OtherHolder<ClassTypeParam> {

  Locale classType();
  Runnable interfaceType();
  Callable genericInterface();
  Callable<Runnable> interfaceParameterizedByInterface();
  Callable<Locale> interfaceParameterizedByClass();
  Callable<ClassTypeParam> interfaceParameterizedByClassTypeParam();
  <MethodTypeParam> Callable<MethodTypeParam> interfaceParameterizedByMethodTypeParam();
  Vector genericClass();
  Vector<Runnable> classParameterizedByInterface();
  Vector<Locale> classParameterizedByClass();
  Vector<ClassTypeParam> classParameterizedByClassTypeParam();
  <MethodTypeParam> Vector<MethodTypeParam> classParameterizedByMethodTypeParam();

}
