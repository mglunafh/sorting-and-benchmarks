package org.example;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Utils {

  public static Stats getStats(long[] array) {

    Arrays.sort(array);
    long median = array[array.length / 2  + 1];
    double mean = 0;
    for (int i = 0; i < array.length; i++) {
      mean += (array[i] - mean) / (i + 1);
    }

    return new Stats(median, mean);
  }

  @Getter
  @RequiredArgsConstructor
  public static class Stats {
    private final double median;
    private final double mean;
  }
}
