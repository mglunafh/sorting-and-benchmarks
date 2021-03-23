package org.example;


import java.util.Arrays;
import java.util.List;
import org.example.ArraySupplier.Type;
import org.example.sort.BubbleSort;
import org.example.sort.HeapSort;
import org.example.sort.InsertionSort;
import org.example.sort.SelectionSort;
import org.example.sort.ShellSort;
import org.example.sort.StandardSort;

public class App {

  private static final int ITERATIONS = 5;

  public static void main(String[] args) {
    int size = 10000;
    System.out.printf("Number of elements: %d%n", size);
    System.out.printf("Number of iterations: %d%n", ITERATIONS);

    ArraySupplier[] suppliers = new ArraySupplier[]{
        ArraySupplier.fromType(Type.SORTED, size),
        ArraySupplier.fromType(Type.MAINLY_SORTED, size),
        ArraySupplier.fromType(Type.RANDOM, size),
        ArraySupplier.fromType(Type.INVERSE_SORTED, size)
    };

    Experiment experiment = new Experiment(ITERATIONS)
        .addSuppliers(Arrays.asList(suppliers))
        .add(new BubbleSort())
        .add(new StandardSort())
        .add(new ShellSort())
        .add(new InsertionSort())
        .add(new SelectionSort())
        .add(new HeapSort());

    experiment.run();

    PrettyPrinter prettyPrinter = new PrettyPrinter();
    List<BenchmarkArrayRun> arrayBenchmarks = experiment.getArrayBenchmarks();
    prettyPrinter.showResults(arrayBenchmarks);
  }
}
