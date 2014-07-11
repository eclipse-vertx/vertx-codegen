package io.vertx.codegen;

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Helper {
  public static String decapitaliseFirstLetter(String str) {
    if (str.length() == 0) {
      return str;
    } else {
      return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
  }

  public static String convertCamelCaseToUnderscores(String str) {
    StringBuilder sb = new StringBuilder();
    boolean lastLowerCase = false;
    for (int i = 0; i < str.length(); i++) {
      char chr = str.charAt(i);
      if (Character.isUpperCase(chr)) {
        if (lastLowerCase) {
          sb.append('_');
        }
        sb.append(Character.toLowerCase(chr));
        lastLowerCase = false;
      } else {
        sb.append(chr);
        lastLowerCase = true;
      }
    }
    return sb.toString();
  }

  public static String getSimpleName(String type) {
    return type.substring(type.lastIndexOf('.') + 1);
  }

  public static String getGenericType(String type) {
    int pos = type.indexOf("<");
    if (pos >= 0) {
      String genericType = type.substring(pos + 1, type.lastIndexOf(">"));
      return genericType;
    } else {
      return null;
    }
  }

  public static String getNonGenericType(String type) {
    int pos = type.indexOf("<");
    if (pos >= 0) {
      String nonGenericType = type.substring(0, pos);
      return nonGenericType;
    } else {
      return type;
    }
  }

  public static boolean isBasicType(String type) {
    switch (type) {
      case "void":
      case "byte":
      case "short":
      case "int":
      case "long":
      case "float":
      case "double":
      case "boolean":
      case "char":
      case "java.lang.String":
      case "java.lang.Byte":
      case "java.lang.Short":
      case "java.lang.Integer":
      case "java.lang.Long":
      case "java.lang.Float":
      case "java.lang.Double":
      case "java.lang.Boolean":
      case "java.lang.Character":
        return true;
      default:
        return false;
    }
  }

  public static boolean isVertxGenType(String type) {
    try {
      Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(type);
      return clazz.getAnnotation(VertxGen.class) != null;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  public static String indentString(String str, String indent) {
    StringBuilder sb = new StringBuilder(indent);
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      sb.append(ch);
      if (ch == '\n' && i != str.length() - 1) {
        sb.append(indent);
      }
    }
    return sb.toString();
  }

  public static String ind(int spaces) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < spaces; i++) {
      sb.append(' ');
    }
    return sb.toString();
  }

  public static String getJavadocTag(String comment, String tagName) {
    int pos = comment.indexOf(tagName);
    int endPos = comment.indexOf("\n", pos);
    String tag = comment.substring(pos + tagName.length() + 1, endPos);
    System.out.println("tag is:" + tag + ":");
    return tag;
  }

  public static String removeTags(String comment) {
    // we remove everything from the first tag to the end of the comment -
    // tags MUST be at the end of the comment
    int pos = comment.indexOf('@');
    if (pos == -1) {
      return comment;
    }
    if (pos > 0) {
      String beforePos = comment.substring(0, pos);
      int prevReturn = beforePos.lastIndexOf('\n');
      if (prevReturn != -1) {
        pos = prevReturn;
      } else {
        pos = 0;
      }
    }
    return comment.substring(0, pos);
  }

}
