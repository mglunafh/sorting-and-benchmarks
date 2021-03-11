package org.example.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

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
    System.out.printf("Sorts to use: %s%n", jArgs.getSorts().toString());
  }

  @Getter
  public static class BenchmarkArgs {

    @Parameter(names = "-n", description = "Size of array. Default is 1000.")
    private int size = 1000;

    @Parameter(names = "-i", description = "Number of iterations. Should be more than 2.",
        validateWith = IterationValidator.class)
    private int iterations = 2;

    @Parameter(names = "--sort", description = "Sort types to use separated by comma.",
        converter = SortConverter.class, validateWith = SortValidator.class, required = true)
    private List<String> sorts = Collections.emptyList();

    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help = false;
  }
}
