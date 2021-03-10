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

    Arrays.sort(array);
    long median = array[array.length / 2 + 1];
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
