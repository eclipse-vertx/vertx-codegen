package io.vertx.core;

import org.junit.Assert;
import org.junit.Test;

public class ExpandableArrayTest {

  @Test
  public void testAddAndGet() {
    ExpandableArray array = new ExpandableArray(2);
    array.add(1);
    array.add(2);
    array.add(3);
    array.add(4);
    array.add(5);
    array.add(6);

    Assert.assertEquals(1, array.get(0));
    Assert.assertEquals(2, array.get(1));
    Assert.assertEquals(3, array.get(2));
    Assert.assertEquals(4, array.get(3));
    Assert.assertEquals(5, array.get(4));
    Assert.assertEquals(6, array.get(5));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetOutOfBounds() {
    ExpandableArray array = new ExpandableArray(2);
    array.add(1);
    array.get(1);
  }

  @Test(expected = OutOfMemoryError.class)
  public void testOutOfMemory() {
    ExpandableArray array = new ExpandableArray(Integer.MAX_VALUE);
  }
}
