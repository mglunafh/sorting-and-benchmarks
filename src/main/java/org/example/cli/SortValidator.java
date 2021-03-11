package org.example.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.example.sort.SortingStrategy;

public class SortValidator implements IParameterValidator {

  @Override
  public void validate(String name, String value) throws ParameterException {

    String[] sortValues = value.split(",");
    for (String sort : sortValues) {
      SortingStrategy.fromString(sort).orElseThrow(() ->
          new ParameterException("Unrecognized option for sorting strategy: " + sort));
    }
  }
}
