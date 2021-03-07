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
  private final List<BenchmarkArrayRun> arrayBenchmarks = new ArrayList<>();

  private final int iterations;
  private final int arraySize;

  public Experiment add(ArraySupplier supplier) {
    suppliers.add(supplier);
    return this;
  }

  public Experiment add(SortingStrategy strategy) {
    strategies.add(strategy);
    return this;
  }

  public void run() {
    int count = Runtime.getRuntime().availableProcessors();
    System.out.printf("Number of cores: %d%n", count);
    System.out.printf("Number of elements: %d%n", arraySize);
    System.out.printf("Number of iterations: %d%n", iterations);

    for (ArraySupplier supplier : suppliers) {
      int[] arr = supplier.getArray();
      ArrayList<BenchmarkStrategyRun> strategyBenchmarks = new ArrayList<>();
      for (SortingStrategy strategy : strategies) {
        strategyBenchmarks.add(runBenchmark(strategy, arr));
      }
      arrayBenchmarks.add(new BenchmarkArrayRun(supplier.getLabel(), strategyBenchmarks));
    }
  }

  public void showResults() {
    for (BenchmarkArrayRun benchmark: arrayBenchmarks) {
      printBenchmarks(benchmark);
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

  private static void printTimings(BenchmarkStrategyRun run) {
    System.out.println("===========");
    System.out.println(run.strategyLabel);
    for (int i = 0; i < run.timings.length; i++) {
      System.out.printf("Run %d: %dms%n", i, run.timings[i]);
    }
    System.out.println("===========");
  }

  private static void printBenchmarks(BenchmarkArrayRun arrayRun) {

    List<BenchmarkStrategyRun> runs = arrayRun.getBenchmarks();
    if (runs.isEmpty()) {
      return;
    }
    System.out.println();
    System.out.println(arrayRun.getArrayLabel());

    int[] columnWidths = new int[runs.size() + 1];

    BenchmarkStrategyRun firstRun = runs.get(0);
    String lastNumber = String.valueOf(firstRun.iterations);
    int width = lastNumber.length();
    String runFormatter = "Run %" + width + "d ";

    int firstColumnWidth = String.format(runFormatter, 0).length();
    columnWidths[0] = firstColumnWidth;
    for (int i = 0; i < runs.size(); i++) {
      columnWidths[i + 1] = runs.get(i).strategyLabel.length();
    }

    StringBuilder rowSb = new StringBuilder();
    int tableLength = columnWidths.length;
    for (int n: columnWidths) {
      tableLength += n;
    }
    repeat(rowSb, '-', tableLength);
    String horizontalLine = rowSb.toString();

    System.out.println(horizontalLine);
    rowSb = new StringBuilder();
    repeat(rowSb, ' ', firstColumnWidth).append('|');
    for (BenchmarkStrategyRun run : runs) {
      rowSb.append(run.strategyLabel).append('|');
    }
    System.out.println(rowSb.toString());
    System.out.println(horizontalLine);

    for (int i = 0; i < firstRun.iterations; i++) {
      rowSb = new StringBuilder();
      String firstVal = String.format(runFormatter, i + 1);
      rowSb.append(firstVal).append('|');

      for (int runI = 0; runI < runs.size(); runI++) {
        BenchmarkStrategyRun run = runs.get(runI);
        int curWidth = columnWidths[runI + 1];
        String cellFormatter = "%" + (curWidth - 3) + "d ms";
        String cellValue = String.format(cellFormatter, run.timings[i]);
        rowSb.append(cellValue).append('|');
      }
      System.out.println(rowSb.toString());
    }
    System.out.println(horizontalLine);
  }

  private static StringBuilder repeat(StringBuilder sb, char ch, int n) {
    for (int i = 0; i < n; i++) {
      sb.append(ch);
    }
    return sb;
  }

  @RequiredArgsConstructor
  static class BenchmarkStrategyRun {
    private final String strategyLabel;
    private final int iterations;
    private final long[] timings;
  }

  @Getter
  @RequiredArgsConstructor
  static class BenchmarkArrayRun {
    private final String arrayLabel;
    private final List<BenchmarkStrategyRun> benchmarks;
  }
}
