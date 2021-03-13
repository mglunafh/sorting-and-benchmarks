package org.example;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Utils {

  public static void checkSorting(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i - 1] > arr[i]) {
        throw new RuntimeException("Array is not sorted, look at the position " + i);
      }
    }
  }

  public static Stats getStats(long[] array) {
    if (array.length == 1) {
      return new Stats(array[0], array[0]);
    }
    int size = array.length;
    Arrays.sort(array);
    long median = size % 2 == 0
        ? (array[size / 2 - 1] + array[size / 2]) / 2
        : array[size / 2];
    double mean = 0;
    for (int i = 0; i < array.length; i++) {
      mean += (array[i] - mean) / (i + 1);
    }

    return new Stats(median, Math.round(mean));
  }

  @Getter
  @RequiredArgsConstructor
  public static class Stats {

    private final double median;
    private final double mean;
  }
}
