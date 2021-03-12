package org.example.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import org.example.ArraySupplier;

public class ArrayTypeValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {

      String[] arrayValues = value.split(",");
      for (String sort : arrayValues) {
        ArraySupplier.Type.fromString(sort).orElseThrow(() ->
            new ParameterException("Unrecognized option for array type: " + sort));
      }
    }
  }
