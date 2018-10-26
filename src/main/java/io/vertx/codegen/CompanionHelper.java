package io.vertx.codegen;

import io.vertx.codegen.annotations.GenIgnore;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class CompanionHelper {

  public static Optional<TypeElement> getCompanion(Element rootElt) {
    List<? extends Element> enclosedElements = rootElt.getEnclosedElements();

    Map<TypeMirror, ? extends Element> companionCandidates = enclosedElements
      .stream()
      .filter((Element companionCandidate) -> companionCandidate.getKind() == CLASS)
      .collect(toMap(Element::asType, Function.identity()));

    Set<TypeMirror> fieldCompanionCandidate = enclosedElements
      .stream()
      .filter((Element elt) -> elt.getKind() == FIELD && elt.getModifiers().containsAll(asList(STATIC, FINAL)))
      .map(Element::asType).collect(toSet());

    companionCandidates.keySet().retainAll(fieldCompanionCandidate);

    if (companionCandidates.size() > 1) {
      throw new RuntimeException();
    }
    Iterator<? extends Element> iter = companionCandidates.values().iterator();
    if (iter.hasNext()) {
      return Optional.of(iter.next())
        .map(TypeElement.class::cast)
        .filter(elt -> elt.getAnnotation(GenIgnore.class) == null);
    }
    return Optional.empty();
  }

}
