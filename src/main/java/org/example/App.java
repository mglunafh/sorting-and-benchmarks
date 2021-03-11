package org.example;


import java.util.List;
import org.example.sort.BubbleSort;
import org.example.sort.InsertionSort;
import org.example.sort.SelectionSort;
import org.example.sort.ShellSort;
import org.example.sort.StandardSort;

public class App {

  private static final long SEED = 150;
  private static final int ITERATIONS = 7;

  public static void main(String[] args) {
    System.out.printf("Seed value: %d%n", SEED);
    int size = 30000;

    int count = Runtime.getRuntime().availableProcessors();
    System.out.printf("Number of cores: %d%n", count);
    System.out.printf("Number of elements: %d%n", size);
    System.out.printf("Number of iterations: %d%n", ITERATIONS);

    Experiment experiment = new Experiment(ITERATIONS)
        .add(ArraySupplier.randomArraySupplier(SEED, size))
        .add(ArraySupplier.sortedArraySupplier(size))
        .add(ArraySupplier.inverseSortedArraySupplier(size))
        .add(new BubbleSort())
        .add(new StandardSort())
        .add(new ShellSort())
        .add(new InsertionSort())
        .add(new SelectionSort());

    experiment.run();

    PrettyPrinter prettyPrinter = new PrettyPrinter();
    List<BenchmarkArrayRun> arrayBenchmarks = experiment.getArrayBenchmarks();
    prettyPrinter.showResults(arrayBenchmarks);
  }
}
