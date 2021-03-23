package org.example.cli;

import com.beust.jcommander.IStringConverter;
import org.example.ArraySupplier;

public class ArrayTypeConverter implements IStringConverter<ArraySupplier.Type> {

  @Override
  public ArraySupplier.Type convert(String value) {
    return ArraySupplier.Type.fromString(value).orElseThrow(
        () -> new IllegalArgumentException("Somehow unknown array type was passed: " + value));
  }
}
