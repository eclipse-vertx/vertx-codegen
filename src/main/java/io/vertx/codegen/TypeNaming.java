package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
abstract class TypeNaming {

  abstract String getClassName(String packageName, String simpleName);

  static TypeNaming QUALIFIED = new TypeNaming() {
    @Override
    String getClassName(String packageName, String simpleName) {
      return packageName.length() == 0 ? simpleName : packageName + "." + simpleName;
    }
  };

  static TypeNaming SIMPLE = new TypeNaming() {
    @Override
    String getClassName(String packageName, String simpleName) {
      return simpleName;
    }
  };

}
