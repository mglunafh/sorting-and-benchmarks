package org.example;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.IntFunction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArraySupplier {

  public enum Type {
    RANDOM, SORTED, INVERSE_SORTED;

    private static Map<String, ArraySupplier> values = new TreeMap<>();

    static {
      values.put(RANDOM.name().toLowerCase(), randomArraySupplier(SEED));
      values.put(SORTED.name().toLowerCase(), sortedArraySupplier());
      values.put(INVERSE_SORTED.name().toLowerCase(), inverseSortedArraySupplier());
    }

    public static Optional<ArraySupplier> fromString(String value) {
      return Optional.ofNullable(values.get(value));
    }
  }

  private static final int MAX_INT = 22500;
  private static final long SEED = 1349;

  @Getter
  private final String label;
  private final IntFunction<int[]> supplier;

  public int[] getArray(int size) {
    return supplier.apply(size);
  }

  public static ArraySupplier randomArraySupplier(long seed) {
    return new ArraySupplier("Array with uniformly distributed values",
        size -> randomArray(seed, size));
  }

  public static ArraySupplier sortedArraySupplier() {
    return new ArraySupplier("Array sorted in ascending order",
        ArraySupplier::sortedArray);
  }

  public static ArraySupplier inverseSortedArraySupplier() {
    return new ArraySupplier("Array sorted in descending order",
        ArraySupplier::inverseSortedArray);
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
