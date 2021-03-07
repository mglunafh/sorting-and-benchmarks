package org.example;

import java.util.Random;

public class ArraySupplier {

  private static final int MAX_INT = 22500;

  public static int[] randomArray(long seed, int size) {
    int[] arr = new int[size];

    Random random = new Random(seed);
    for (int i = 0; i < size; i++) {
      arr[i] = random.nextInt(MAX_INT);
    }
    return arr;
  }

  public static int[] sortedArray(int size) {
    int[] arr = new int[size];

    for (int i = 0; i < size; i++) {
      arr[i] = i;
    }
    return arr;
  }

  public static int[] inverseSortedArray(int size) {
    int[] arr = new int[size];

    for (int i = 0; i < size; i++) {
      arr[i] = size - i - 1;
    }
    return arr;
  }
}
