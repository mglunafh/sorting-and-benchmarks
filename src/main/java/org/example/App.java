package org.example;


import java.util.List;

public class App {

  private static final long SEED = 150;
  private static final int ITERATIONS = 7;

  public static void main(String[] args) {
    System.out.printf("Seed value: %d%n", SEED);
    int size = 15000;

    Experiment experiment = new Experiment(ITERATIONS, size)
        .add(ArraySupplier.randomArraySupplier(SEED, size))
        .add(ArraySupplier.sortedArraySupplier(size))
        .add(ArraySupplier.inverseSortedArraySupplier(size))
        .add(new BubbleSort())
        .add(new StandardSort())
        .add(new InsertionSort())
        .add(new SelectionSort());

    experiment.run();

    PrettyPrinter prettyPrinter = new PrettyPrinter();
    List<BenchmarkArrayRun> arrayBenchmarks = experiment.getArrayBenchmarks();
    prettyPrinter.showResults(arrayBenchmarks);
  }
}
