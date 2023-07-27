package io.vertx.core;

import org.junit.Assert;
import org.junit.Test;

public class ExpandableArrayTest {

  @Test
  public void testAddAndGet() {
    ExpandableIntArray arr = new ExpandableIntArray(2);
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(5);
    arr.add(6);

    Assert.assertEquals(1, arr.get(0));
    Assert.assertEquals(2, arr.get(1));
    Assert.assertEquals(3, arr.get(2));
    Assert.assertEquals(4, arr.get(3));
    Assert.assertEquals(5, arr.get(4));
    Assert.assertEquals(6, arr.get(5));
  }

  @Test
  public void testSet() {
    ExpandableIntArray arr = new ExpandableIntArray(2);
    arr.add(1);
    arr.add(2);
    arr.set(1, 20);
    Assert.assertEquals(20, arr.get(1));
    Assert.assertEquals(1, arr.get(0));
  }

  @Test
  public void testSetAutoExpand() {
    ExpandableIntArray arr = new ExpandableIntArray(2);
    arr.add(1);
    arr.set(5, 50);
    Assert.assertEquals(50, arr.get(5));
    arr.add(2);
    arr.add(3);
    Assert.assertEquals(2, arr.get(6));
    Assert.assertEquals(3, arr.get(7));
  }

  @Test
  public void testGetOutOfBounds() {
    ExpandableIntArray arr = new ExpandableIntArray(2);
    arr.add(1);
    Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
      arr.get(5);
    });
  }

  @Test
  public void testSetNegativeIndex() {
    ExpandableIntArray arr = new ExpandableIntArray(2);
    arr.add(1);
    Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
      arr.set(-1, 10);
    });
  }

  @Test(expected = OutOfMemoryError.class)
  public void testOutOfMemory() {
    new ExpandableIntArray(Integer.MAX_VALUE);
  }
}
