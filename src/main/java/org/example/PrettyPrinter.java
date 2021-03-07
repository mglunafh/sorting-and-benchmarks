package org.example;

import java.util.List;

public class PrettyPrinter {

  public void showResults(List<BenchmarkArrayRun> arrayBenchmarks) {
    for (BenchmarkArrayRun benchmark: arrayBenchmarks) {
      printBenchmarks(benchmark);
    }
  }

  private static void printTimings(BenchmarkStrategyRun run) {
    System.out.println("===========");
    System.out.println(run.getStrategyLabel());
    long[] timings = run.getTimings();
    for (int i = 0; i < timings.length; i++) {
      System.out.printf("Run %d: %dms%n", i, timings[i]);
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
    String lastNumber = String.valueOf(firstRun.getIterations());
    int width = lastNumber.length();
    String runFormatter = "Run %" + width + "d ";

    int firstColumnWidth = String.format(runFormatter, 0).length();
    columnWidths[0] = firstColumnWidth;
    for (int i = 0; i < runs.size(); i++) {
      columnWidths[i + 1] = runs.get(i).getStrategyLabel().length();
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
      rowSb.append(run.getStrategyLabel()).append('|');
    }
    System.out.println(rowSb.toString());
    System.out.println(horizontalLine);

    for (int i = 0; i < firstRun.getIterations(); i++) {
      rowSb = new StringBuilder();
      String firstVal = String.format(runFormatter, i + 1);
      rowSb.append(firstVal).append('|');

      for (int runI = 0; runI < runs.size(); runI++) {
        BenchmarkStrategyRun run = runs.get(runI);
        long[] timings = run.getTimings();
        int curWidth = columnWidths[runI + 1];
        String cellFormatter = "%" + (curWidth - 3) + "d ms";
        String cellValue = String.format(cellFormatter, timings[i]);
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
}
