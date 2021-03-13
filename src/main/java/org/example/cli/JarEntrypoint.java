package org.example.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.example.ArraySupplier;
import org.example.BenchmarkArrayRun;
import org.example.Experiment;
import org.example.PrettyPrinter;
import org.example.sort.AbstractSort;

public class JarEntrypoint {

  public static void main(String[] args) {
    BenchmarkArgs jArgs = new BenchmarkArgs();
    JCommander helloCmd = JCommander.newBuilder()
        .addObject(jArgs)
        .build();

    try {
      helloCmd.parse(args);
    } catch (ParameterException ex) {
      System.out.println("Could not parse parameters since an error occurred:");
      System.out.println("  " + ex.getMessage());
      return;
    }
    if (jArgs.isHelp()) {
      helloCmd.usage();
      return;
    }

    System.out.printf("Number of elements: %d%n", jArgs.getSize());
    System.out.printf("Number of iterations: %d%n", jArgs.getIterations());
    String sortsToUse = jArgs.getSorts().stream().map(AbstractSort::getName)
        .collect(Collectors.joining(", "));
    System.out.printf("Sorts to use: %s%n", sortsToUse);

    Experiment experiment = new Experiment(jArgs.getSize(), jArgs.getIterations())
        .addSuppliers(jArgs.getArraySuppliers())
        .add(jArgs.getSorts());
    experiment.run();

    PrettyPrinter prettyPrinter = new PrettyPrinter();
    List<BenchmarkArrayRun> arrayBenchmarks = experiment.getArrayBenchmarks();
    prettyPrinter.showResults(arrayBenchmarks);
  }

  @Getter
  public static class BenchmarkArgs {

    @Parameter(names = {"-h", "--help"}, description = "Prints this help message.", help = true,
        order = 0)
    private boolean help = false;

    @Parameter(names = "-n", description = "Size of array.")
    private int size = 1000;

    @Parameter(names = "-i", description = "Number of iterations. Should be greater than 3.",
        validateWith = IterationValidator.class)
    private int iterations = 3;

    @Parameter(names = "--sort", description = "Sort types to use separated by comma."
        + " Currently supported: bubble, insertion, selection, shell, standard",
        converter = SortConverter.class, validateWith = SortValidator.class, required = true)
    private List<AbstractSort> sorts = Collections.emptyList();

    @Parameter(names = "--arrays", description = "Types of array separated by comma, on which "
        + "sorting algorithms are run. Currently supported: random, sorted, inverse_sorted",
        converter = ArrayTypeConverter.class, validateWith = ArrayTypeValidator.class, required = true)
    private List<ArraySupplier> arraySuppliers = Collections.emptyList();
  }
}
