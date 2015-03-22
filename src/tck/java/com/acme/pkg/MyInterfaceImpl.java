package com.acme.pkg;

import com.acme.pkg.sub.SubInterface;
import com.acme.pkg.sub.SubInterfaceImpl;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class MyInterfaceImpl implements MyInterface {

  @Override
  public SubInterface sub() {
    return new SubInterfaceImpl();
  }

}
