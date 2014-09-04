package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithGetterMethods {

  boolean isA();
  boolean isAb();
  boolean isABC();
  boolean isABCd();
  boolean isAbCde();

  String getB();
  String getBc();
  String getBCD();
  String getBCDe();
  String getBcDef();

  void isC();
  void isCd();
  void isCDE();
  void isCDEf();
  void isCdEfg();

  void getD();
  void getDe();
  void getDEF();
  void getDEFg();
  void getDeFgh();

}
