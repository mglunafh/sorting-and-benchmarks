package org.example.cli;

import com.beust.jcommander.IStringConverter;
import org.example.sort.AbstractSort;
import org.example.sort.SortingStrategy;

public class SortConverter implements IStringConverter<AbstractSort> {

  @Override
  public AbstractSort convert(String value) {
    return SortingStrategy.fromString(value).map(SortingStrategy::getSort).orElseThrow(
        () -> new IllegalArgumentException("Somehow unknown type of sort was passedA " + value)
    );
  }
}
