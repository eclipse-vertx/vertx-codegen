package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface EnumValueAnnotation {

  TestEnum value();

  TestEnum[] array();

  TestEnum defaultValue() default TestEnum.TEST;
}
