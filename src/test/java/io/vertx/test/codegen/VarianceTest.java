package io.vertx.test.codegen;

import io.vertx.codegen.Compiler;
import io.vertx.codegen.Helper;
import io.vertx.codegen.type.Variance;
import io.vertx.test.codegen.testvariance.Analyze;
import io.vertx.test.codegen.testvariance.ExtendWithVarAsCoParam;
import io.vertx.test.codegen.testvariance.ExtendWithVarAsContraParam;
import io.vertx.test.codegen.testvariance.Message;
import io.vertx.test.codegen.testvariance.MethodWithRawParameterized;
import io.vertx.test.codegen.testvariance.MethodWithVarAsContraParamInContraPos;
import io.vertx.test.codegen.testvariance.MethodWithCoType;
import io.vertx.test.codegen.testvariance.MethodWithContraType;
import io.vertx.test.codegen.testvariance.MethodWithVarAsCoParamAsCoParamInCoPos;
import io.vertx.test.codegen.testvariance.MethodWithVarAsCoParaminCoPos;
import io.vertx.test.codegen.testvariance.MethodWithInvType;
import io.vertx.test.codegen.testvariance.MethodWithStringParameterizedReturnType;
import io.vertx.test.codegen.testvariance.MethodWithStringReturnType;
import org.junit.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class VarianceTest {

  class MyProcessor extends AbstractProcessor {

    Map<String, Map<String, Set<Variance>>> varianceMap = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      return Collections.singleton(Analyze.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      for (Element elt : roundEnv.getElementsAnnotatedWith(Analyze.class)) {
        TypeParameterElement typeParameterElt = (TypeParameterElement) elt;
        Set<Variance> variances = EnumSet.noneOf(Variance.class);
        for (Variance variance : Variance.values()) {
          if (Helper.resolveSiteVariance(typeParameterElt, variance)) {
            variances.add(variance);
          }
        }
        TypeElement typeElt = (TypeElement) typeParameterElt.getGenericElement();
        String typeName = typeElt.getQualifiedName().toString();
        Map<String, Set<Variance>> nested = varianceMap.get(typeName);
        if (nested == null) {
          varianceMap.put(typeName, nested = new HashMap<>());
        }
        nested.put(typeParameterElt.getSimpleName().toString(), variances);
      }
      return true;
    }
  }

  private void assertVariance(Class<?> type, String typeParameter, Variance... variances) throws Exception {
    MyProcessor processor = new MyProcessor();
    Compiler compiler = new Compiler(processor);
    boolean compiled = compiler.compile(Arrays.asList(type));
    assertTrue(compiled);
    Map<String, Set<Variance>> nested = processor.varianceMap.get(type.getName());
    assertEquals(new HashSet<>(Arrays.asList(variances)), nested.get(typeParameter));
  }

  @Test
  public void testPositions() throws Exception {
    assertVariance(MethodWithCoType.class, "T", Variance.COVARIANT);
    assertVariance(MethodWithVarAsCoParaminCoPos.class, "T", Variance.COVARIANT);
    assertVariance(MethodWithVarAsCoParamAsCoParamInCoPos.class, "T", Variance.COVARIANT);
    assertVariance(MethodWithVarAsContraParamInContraPos.class, "T", Variance.COVARIANT);
    assertVariance(MethodWithInvType.class, "T");
    assertVariance(MethodWithContraType.class, "T", Variance.CONTRAVARIANT);
  }

  @Test
  public void testVarious() throws Exception {
    assertVariance(MethodWithRawParameterized.class, "T", Variance.values());
    assertVariance(MethodWithStringReturnType.class, "T", Variance.values());
    assertVariance(MethodWithStringParameterizedReturnType.class, "T", Variance.values());
  }

  @Test
  public void testMessage() throws Exception {
    assertVariance(Message.class, "T", Variance.COVARIANT);
  }

  @Test
  public void testExtends() throws Exception {
    assertVariance(ExtendWithVarAsCoParam.class, "T", Variance.COVARIANT);
    assertVariance(ExtendWithVarAsContraParam.class, "T", Variance.CONTRAVARIANT);
  }
}
