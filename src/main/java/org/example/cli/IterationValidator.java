package org.example.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IterationValidator implements IParameterValidator {

  @Override
  public void validate(String name, String value) throws ParameterException {
    int i;
    try {
      i = Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      throw new ParameterException("Could not parse number of iterations from parameter: " + value);
    }
    if (i <= 2) {
      throw new ParameterException("Number of iterations should be more than 2.");
    }
  }
}
