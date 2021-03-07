package org.example;

import java.util.Random;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArraySupplier {

  private static final int MAX_INT = 22500;

  @Getter
  private final String label;
  private final Supplier<int[]> supplier;

  public int[] getArray() {
    return supplier.get();
  }

  public static ArraySupplier randomArraySupplier(long seed, int size) {
    return new ArraySupplier("Array with uniformly distributed values",
        () -> randomArray(seed, size));
  }

  public static ArraySupplier sortedArraySupplier(int size) {
    return new ArraySupplier("Array sorted in ascending order",
        () -> sortedArray(size));
  }

  public static ArraySupplier inverseSortedArraySupplier(int size) {
    return new ArraySupplier("Array sorted in descending order",
        () -> inverseSortedArray(size));
  }

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
