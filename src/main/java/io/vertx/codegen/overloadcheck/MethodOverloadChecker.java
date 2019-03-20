/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.codegen.overloadcheck;

import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodOverloadChecker {

  public final static MethodOverloadChecker INSTANCE;

  static {
    MethodOverloadChecker checker = new MethodOverloadChecker(new Properties());
    try {
      checker = new MethodOverloadChecker(); // Might fail
    } catch (Exception e) {
      e.printStackTrace();
    }
    INSTANCE = checker;
  }

  private final Map<String, Map<String, Set<String>>> typeMappingsMap = new HashMap<>();

  public MethodOverloadChecker() {
    loadTypeMappings();
  }

  public MethodOverloadChecker(Properties props) {
    loadTypeMappings(props);
  }

  public void checkAmbiguous(Stream<MethodInfo> meths) {
    checkAmbiguousSimple(convert(meths).collect(Collectors.toList()));
  }

  public void checkAmbiguousSimple(List<SimpleMethod> meths) {
    List<SimpleMethod> methods = new ArrayList<>(meths);
    Map<Integer, List<SimpleMethod>> byNumParams = new HashMap<>();
    for (SimpleMethod meth: methods) {
      int numParams = meth.params.size();
      List<SimpleMethod> list = byNumParams.get(numParams);
      if (list == null) {
        list = new ArrayList<>();
        byNumParams.put(numParams, list);
      }
      list.add(meth);
    }
    for (Map.Entry<Integer, List<SimpleMethod>> entry: byNumParams.entrySet()) {
      List<SimpleMethod> list = entry.getValue();
      if (list.size() == 1) {
        // Ignore - no overloaded methods
      } else {
        for (Map.Entry<String, Map<String, Set<String>>> mappingEntry: typeMappingsMap.entrySet()) {
          checkMethodList(mappingEntry.getKey(), list, mappingEntry.getValue());
        }
      }
    }
  }

  // We convert to simpler types - this makes it much easier to test
  private Stream<SimpleMethod> convert(Stream<MethodInfo> meths) {
    return meths.map(meth -> {
      List<SimpleParam> simpleParams = new ArrayList<>();
      for (ParamInfo param: meth.getParams()) {
        TypeInfo type = (param.getType() instanceof DataObjectTypeInfo) ? ((DataObjectTypeInfo) param.getType()).getTargetJsonType() : param.getType();
        simpleParams.add(new SimpleParam(param.getName(), type.getKind(), param.isNullable(), type.getName()));
      }
      return new SimpleMethod(meth.getName(), simpleParams);
    });
  }

  private void checkMethodList(String targetLang, List<SimpleMethod> meths, Map<String, Set<String>> typeMapping) {
    List<List<SimpleType>> paramsTypesList = new ArrayList<>();
    for (SimpleMethod meth: meths) {
      // For each meth, convert it to the param types it would have in each lang
      List<SimpleType> paramTypes = convertToLangParamTypes(meth, typeMapping);
      paramsTypesList.add(paramTypes);
    }
    // Now check if we have any two which are equal
    int index1 = 0;
    for (List<SimpleType> paramTypes: paramsTypesList) {
      int index2 = 0;
      for (List<SimpleType> paramTypesToCompare: paramsTypesList) {
        if (index1 != index2) {
          boolean matched = true;
          for (int i = 0; i < paramTypes.size(); i++) {
            SimpleType paramType = paramTypes.get(i);
            SimpleType paramTypeToCompare = paramTypesToCompare.get(i);
            if (!(paramType.matches(paramTypeToCompare))) {
              matched = false;
              break;
            }
          }
          if (matched) {
            SimpleMethod clashing1 = meths.get(index1);
            SimpleMethod clashing2 = meths.get(index2);
            String msg = "Failed to generate because it would be impossible in target language " + targetLang +
              " at runtime to resolve which of the following overloaded methods to call in the Java API: " + clashing1 +
              " and " + clashing2;
            throw new IllegalArgumentException(msg);
          }
        }
        index2++;
      }
      index1++;
    }
  }

  private List<SimpleType> convertToLangParamTypes(SimpleMethod meth, Map<String, Set<String>> typeMapping) {
    List<SimpleType> langParamTypes = new ArrayList<>();
    for (SimpleParam param: meth.params) {
      Set<String> langType = typeMapping.get(param.classKind.toString());
      if (langType == null) {
        // Try with type name appended
        String lhs = param.classKind.toString() + "." + param.typeName;
        langType = typeMapping.get(lhs);
        if (langType == null) {
          throw new IllegalStateException("No type mapping found for param type " + lhs);
        }
      }
      langParamTypes.add(new SimpleType(langType, param.nullable));
    }
    return langParamTypes;
  }

  private void loadTypeMappings() {
    try (InputStream is = MethodOverloadChecker.class.getClassLoader().getResourceAsStream("lang-type-mapping.properties")) {
      Properties props = new Properties();
      props.load(is);
      loadTypeMappings(props);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private void loadTypeMappings(Properties props) {
    for (Map.Entry<Object, Object> entry: props.entrySet()) {
      String lhs = (String)entry.getKey();
      String rhs = (String)entry.getValue();
      int pos = lhs.indexOf('.');
      String lang = lhs.substring(0, pos);
      String key = lhs.substring(pos + 1);
      Map<String, Set<String>> typeMapping = typeMappingsMap.get(lang);
      if (typeMapping == null) {
        typeMapping = new HashMap<>();
        typeMappingsMap.put(lang, typeMapping);
      }
      Set<String> types = new HashSet<>(Arrays.asList(rhs.split("\\s*,\\s*")));;
      typeMapping.put(key, types);
    }
  }
}
