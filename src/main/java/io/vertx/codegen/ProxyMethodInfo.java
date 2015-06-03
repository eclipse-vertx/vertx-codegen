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

import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Text;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ProxyMethodInfo extends MethodInfo {

  private final boolean proxyIgnore;
  private final boolean proxyClose;

  public ProxyMethodInfo(Set<TypeInfo.Class> ownerTypes, String name, MethodKind kind, TypeInfo returnType, Text returnDescription, boolean fluent,
                         boolean cacheReturn, List<ParamInfo> params, String comment, Doc doc, boolean staticMethod,
                         List<TypeParamInfo.Method> typeParams, boolean proxyIgnore, boolean proxyClose) {


    super(ownerTypes, name, kind, returnType, returnDescription, fluent, cacheReturn, params, comment, doc, staticMethod, typeParams);
    this.proxyIgnore = proxyIgnore;
    this.proxyClose = proxyClose;
  }

  public boolean isProxyIgnore() {
    return proxyIgnore;
  }

  public boolean isProxyClose() {
    return proxyClose;
  }
}
