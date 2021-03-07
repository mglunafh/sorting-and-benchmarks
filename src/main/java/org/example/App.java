package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;


public class App {

  private static final long SEED = 150;
  private static final int ITERATIONS = 9;

  public static void main(String[] args) {
    int size = 10000;
    int count = Runtime.getRuntime().availableProcessors();
    System.out.printf("Number of cores: %d%n", count);
    System.out.printf("Number of elements: %d%n", size);
    System.out.printf("Number of iterations: %d%n", ITERATIONS);
    System.out.printf("Seed value: %d%n", SEED);

    int[] randomArray = ArraySupplier.randomArray(SEED, size);
    int[] sortedArray = ArraySupplier.sortedArray(size);
    int[] inverseSortedArray = ArraySupplier.inverseSortedArray(size);

    SortingStrategy bubbleSort = new BubbleSort();
    SortingStrategy standardSort = new StandardSort();
    SortingStrategy selectionSort = new SelectionSort();
    SortingStrategy insertionSort = new InsertionSort();

    for (int[] arr : new int[][] {randomArray, sortedArray, inverseSortedArray}) {
      ArrayList<BenchmarkRun> benchmarkRuns = new ArrayList<>();

      benchmarkRuns.add(runBenchmark(bubbleSort, arr));
      benchmarkRuns.add(runBenchmark(selectionSort, arr));
      benchmarkRuns.add(runBenchmark(standardSort, arr));
      benchmarkRuns.add(runBenchmark(insertionSort, arr));

      printBenchmarks(benchmarkRuns);
    }
  }

  private static BenchmarkRun runBenchmark(SortingStrategy strategy, int[] array) {
    long[] timings = new long[ITERATIONS];

    for (int i = 0; i < ITERATIONS; i++) {
      int[] tempArray = Arrays.copyOf(array, array.length);
      timings[i] = getTimingOfSortArray(strategy, tempArray);
    }

    return new BenchmarkRun(strategy.getName(), ITERATIONS, timings);
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

  private static void printTimings(BenchmarkRun run) {
    System.out.println("===========");
    System.out.println(run.label);
    for (int i = 0; i < run.timings.length; i++) {
      System.out.printf("Run %d: %dms%n", i, run.timings[i]);
    }
    System.out.println("===========");
  }

  private static void printBenchmarks(List<BenchmarkRun> runs) {
    if (runs.isEmpty()) {
      return;
    }
    int[] columnWidths = new int[runs.size() + 1];

    BenchmarkRun firstRun = runs.get(0);
    String lastNumber = String.valueOf(firstRun.iterations);
    int width = lastNumber.length();
    String runFormatter = "Run %" + width + "d ";

    int firstColumnWidth = String.format(runFormatter, 0).length();
    columnWidths[0] = firstColumnWidth;
    for (int i = 0; i < runs.size(); i++) {
      columnWidths[i + 1] = runs.get(i).label.length();
    }

    StringBuilder rowSb = new StringBuilder();
    repeat(rowSb, ' ', firstColumnWidth).append('|');
    for (BenchmarkRun run : runs) {
      rowSb.append(run.label).append('|');
    }
    System.out.println(rowSb.toString());
    rowSb = new StringBuilder();
    int tableLength = columnWidths.length;
    for (int n: columnWidths) {
      tableLength += n;
    }
    repeat(rowSb, '-', tableLength);
    System.out.println(rowSb.toString());

    for (int i = 0; i < firstRun.iterations; i++) {
      rowSb = new StringBuilder();
      String firstVal = String.format(runFormatter, i + 1);
      rowSb.append(firstVal).append('|');

      for (int runI = 0; runI < runs.size(); runI++) {
        BenchmarkRun run = runs.get(runI);
        int curWidth = columnWidths[runI + 1];
        String cellFormatter = "%" + (curWidth - 3) + "d ms";
        String cellValue = String.format(cellFormatter, run.timings[i]);
        rowSb.append(cellValue).append('|');
      }

      System.out.println(rowSb.toString());
    }
  }

  private static StringBuilder repeat(StringBuilder sb, char ch, int n) {
    for (int i = 0; i < n; i++) {
      sb.append(ch);
    }
    return sb;
  }

  @RequiredArgsConstructor
  static class BenchmarkRun {
    private final String label;
    private final int iterations;
    private final long[] timings;
  }
}
