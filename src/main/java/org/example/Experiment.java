package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Experiment {

  private final List<ArraySupplier> suppliers = new ArrayList<>();
  private final List<SortingStrategy> strategies = new ArrayList<>();
  @Getter
  private final List<BenchmarkArrayRun> arrayBenchmarks = new ArrayList<>();

  private final int iterations;

  public Experiment add(ArraySupplier supplier) {
    suppliers.add(supplier);
    return this;
  }

  public Experiment add(SortingStrategy strategy) {
    strategies.add(strategy);
    return this;
  }

  public void run() {
    for (ArraySupplier supplier : suppliers) {
      int[] arr = supplier.getArray();
      ArrayList<BenchmarkStrategyRun> strategyBenchmarks = new ArrayList<>();
      for (SortingStrategy strategy : strategies) {
        strategyBenchmarks.add(runBenchmark(strategy, arr));
      }
      arrayBenchmarks.add(new BenchmarkArrayRun(supplier.getLabel(), strategyBenchmarks));
    }
  }

  private BenchmarkStrategyRun runBenchmark(SortingStrategy strategy, int[] array) {
    long[] timings = new long[iterations];
    for (int i = 0; i < iterations; i++) {
      int[] tempArray = Arrays.copyOf(array, array.length);
      timings[i] = getTimingOfSortArray(strategy, tempArray);
    }
    return new BenchmarkStrategyRun(strategy.getName(), iterations, timings);
  }

  private static long getTimingOfSortArray(SortingStrategy strategy, int[] array) {
//    System.out.println(Arrays.toString(array));

    Instant before = Instant.now();
    strategy.sortArray(array);
    Instant after = Instant.now();

//    System.out.println(Arrays.toString(array));
    Duration between = Duration.between(before, after);
    checkSorting(array);
    return between.toMillis();
  }

  private static void checkSorting(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i - 1] > arr[i]) {
        throw new RuntimeException("Array is not sorted, look at the position " + i);
      }
    }
  }
}
