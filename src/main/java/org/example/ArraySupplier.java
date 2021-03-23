package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


@RequiredArgsConstructor
public class ArraySupplier {

  @RequiredArgsConstructor
  public enum Type {
    RANDOM("Array with uniformly distributed values"),
    SORTED("Array sorted in ascending order"),
    INVERSE_SORTED("Array sorted in descending order"),
    MAINLY_SORTED("Array generally sorted in ascending order");

    @Getter
    final String label;

    private static final Map<String, Type> values = new HashMap<>();

    static {
      for (Type c : values()) {
        values.put(c.name().toLowerCase(), c);
      }
    }

    public static Optional<Type> fromString(String value) {
      return Optional.ofNullable(values.get(value));
    }
  }

  private static final int LARGE_BOUNDARY = 100_000;
  private static final long SEED = 1349;

  @Getter
  private final String label;
  private final Supplier<int[]> supplier;
  private final int size;

  public int[] getArray() {
    return supplier.get();
  }

  public static ArraySupplier fromType(Type type, int size) {

    switch (type) {
      case RANDOM:
        return new ArraySupplier(type.label, () -> randomArray(SEED, size), size);
      case SORTED:
        return new ArraySupplier(type.label, () -> sortedArray(size), size);
      case INVERSE_SORTED:
        return new ArraySupplier(type.label, () -> inverseSortedArray(size), size);
      case MAINLY_SORTED:
        return new ArraySupplier(type.label, () -> mainlySortedArray(SEED, size), size);
      default:
        throw new NotImplementedException();
    }
  }

  public static int[] randomArray(long seed, int size) {
    int[] arr = new int[size];

    Random random = new Random(seed);
    for (int i = 0; i < size; i++) {
      arr[i] = random.nextInt(LARGE_BOUNDARY);
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

  public static int[] mainlySortedArray(long seed, int size) {
    int[] arr = new int[size];
    Random random = new Random(seed);
    int boundary = size < 20 ? 5 : size >> 7;
    for (int i = 0; i < size; i++) {
      arr[i] = i - size / 2 + random.nextInt(boundary);
    }
    return arr;
  }
}
