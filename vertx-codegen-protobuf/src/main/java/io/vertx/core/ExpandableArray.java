package io.vertx.core;

import java.util.Arrays;

public class ExpandableArray {
  private int[] data;
  private int size;

  public ExpandableArray(int initialCapacity) {
    data = new int[initialCapacity];
    size = 0;
  }

  public int get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    return data[index];
  }

  public void add(int v) {
    ensureCapacity(size + 1);
    data[size++] = v;
  }

  private void ensureCapacity(int minCapacity) {
    if (minCapacity - data.length > 0) {
      grow(minCapacity);
    }
  }

  private void grow(int minCapacity) {
    int oldCapacity = data.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1); // grow by 1.5 times
    if (newCapacity - minCapacity < 0) {
      newCapacity = minCapacity;
    }

    // handle overflow
    if (newCapacity < 0) {
      throw new OutOfMemoryError();
    }
    data = Arrays.copyOf(data, newCapacity);
  }
}
