package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.sort.AbstractSort;

@RequiredArgsConstructor
public class Experiment {

  private final List<ArraySupplier> suppliers = new ArrayList<>();
  private final List<AbstractSort> strategies = new ArrayList<>();
  @Getter
  private final List<BenchmarkArrayRun> arrayBenchmarks = new ArrayList<>();

  private final int iterations;

  public Experiment add(ArraySupplier supplier) {
    suppliers.add(supplier);
    return this;
  }

  public Experiment add(AbstractSort strategy) {
    strategies.add(strategy);
    return this;
  }

  public void run() {
    for (ArraySupplier supplier : suppliers) {
      int[] arr = supplier.getArray();
      ArrayList<BenchmarkStrategyRun> strategyBenchmarks = new ArrayList<>();
      for (AbstractSort strategy : strategies) {
        strategyBenchmarks.add(runBenchmark(strategy, arr));
      }
      arrayBenchmarks.add(new BenchmarkArrayRun(supplier.getLabel(), strategyBenchmarks));
    }
  }

  private BenchmarkStrategyRun runBenchmark(AbstractSort strategy, int[] array) {
    long[] timings = new long[iterations];
    for (int i = 0; i < iterations; i++) {
      int[] tempArray = Arrays.copyOf(array, array.length);
      timings[i] = getTimingOfSortArray(strategy, tempArray);
    }
    return new BenchmarkStrategyRun(strategy.getName(), iterations, timings);
  }

  private static long getTimingOfSortArray(AbstractSort strategy, int[] array) {
    Instant before = Instant.now();
    strategy.sortArray(array);
    Instant after = Instant.now();

    Duration between = Duration.between(before, after);
    Utils.checkSorting(array);
    return between.toMillis();
  }
}
