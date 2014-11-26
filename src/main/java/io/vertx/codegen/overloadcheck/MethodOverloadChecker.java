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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodOverloadChecker {

  private final Map<String, Map<String, String>> typeMappingsMap = new HashMap<>();

  public MethodOverloadChecker() {
    loadTypeMappings();
  }

  public void checkAmbiguous(List<MethodInfo> meths) {
    checkAmbiguousSimple(convert(meths));
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
        for (Map.Entry<String, Map<String, String>> mappingEntry: typeMappingsMap.entrySet()) {
          checkMethodList(mappingEntry.getKey(), list, mappingEntry.getValue());
        }
      }
    }
  }

  // We convert to simpler types - this makes it much easier to test
  private List<SimpleMethod> convert(List<MethodInfo> meths) {
    List<SimpleMethod> simpleMethods = new ArrayList<>(meths.size());
    for (MethodInfo meth: meths) {
      List<SimpleParam> simpleParams = new ArrayList<>();
      for (ParamInfo param: meth.getParams()) {
        simpleParams.add(new SimpleParam(param.getName(), param.getType().getKind(), param.getType().getName()));
      }
      simpleMethods.add(new SimpleMethod(meth.getName(), simpleParams));
    }
    return simpleMethods;
  }

  private void checkMethodList(String targetLang, List<SimpleMethod> meths, Map<String, String> typeMapping) {
    List<List<String>> jsParamTypesList = new ArrayList<>();
    for (SimpleMethod meth: meths) {
      // For each meth, convert it to the param types it would have in JS
      List<String> jsParamTypes = convertToJSParamTypes(meth, typeMapping);
      jsParamTypesList.add(jsParamTypes);
    }
    // Now check if we have any two which are equal
    int index1 = 0;
    for (List<String> paramTypes: jsParamTypesList) {
      int index2 = 0;
      for (List<String> paramTypesToCompare: jsParamTypesList) {
        if (index1 != index2) {
          boolean matched = true;
          for (int i = 0; i < paramTypes.size(); i++) {
            String paramType = paramTypes.get(i);
            String paramTypeToCompare = paramTypesToCompare.get(i);
            if (!(paramType.equals(paramTypeToCompare) || paramType.equals("ALL") || paramTypeToCompare.equals("ALL"))) {
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
  
  private List<String> convertToJSParamTypes(SimpleMethod meth, Map<String, String> typeMapping) {
    List<String> jsParamTypes = new ArrayList<>();
    for (SimpleParam param: meth.params) {
      String jsType = typeMapping.get(param.classKind.toString());
      if (jsType == null) {
        // Try with type name appended
        String lhs = param.classKind.toString() + "." + param.typeName;
        jsType = typeMapping.get(lhs);
        if (jsType == null) {
          throw new IllegalStateException("No type mapping found for param type " + lhs);
        }
      }

      jsParamTypes.add(jsType);
    }
    return jsParamTypes;
  }

  private void loadTypeMappings() {
    try (InputStream is = MethodOverloadChecker.class.getClassLoader().getResourceAsStream("lang-type-mapping.properties")) {
      Properties props = new Properties();
      props.load(is);
      for (Map.Entry<Object, Object> entry: props.entrySet()) {
        String lhs = (String)entry.getKey();
        String rhs = (String)entry.getValue();
        int pos = lhs.indexOf('.');
        String lang = lhs.substring(0, pos);
        String key = lhs.substring(pos + 1);
        Map<String, String> typeMapping = typeMappingsMap.get(lang);
        if (typeMapping == null) {
          typeMapping = new HashMap<>();
          typeMappingsMap.put(lang, typeMapping);
        }
        typeMapping.put(key, rhs);
      }

    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }


}
