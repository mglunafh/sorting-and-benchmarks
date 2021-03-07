package org.example;

import java.util.Arrays;
import lombok.Getter;

@Getter
public class BenchmarkStrategyRun {
  private final String strategyLabel;
  private final int iterations;
  private final long[] timings;
  private final double mean;
  private final double median;

  public BenchmarkStrategyRun(String strategyLabel, int iterations, long[] timings) {
    this.strategyLabel = strategyLabel;
    this.iterations = iterations;
    this.timings = timings;

    // drop two first runs since they are usually contaminated with JVM start-ups and other issues
    long[] tempTimings = new long[timings.length - 2];
    System.arraycopy(timings, 2, tempTimings, 0, timings.length - 2);
    Arrays.sort(tempTimings);

    this.median = tempTimings[tempTimings.length / 2  + 1];

    double mean = 0;
    for (int i = 0; i < tempTimings.length; i++) {
      mean += (tempTimings[i] - mean) / (i + 1);
    }

    this.mean = mean;
  }
}
