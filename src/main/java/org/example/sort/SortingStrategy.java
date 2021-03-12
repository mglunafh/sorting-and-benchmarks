package org.example.sort;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import lombok.Getter;

public enum SortingStrategy {
  BUBBLE(new BubbleSort()),
  INSERTION(new InsertionSort()),
  SELECTION(new SelectionSort()),
  SHELL(new ShellSort()),
  STANDARD(new StandardSort());

  SortingStrategy(AbstractSort sort) {
    this.sort = sort;
  }

  private static Map<String, SortingStrategy> values = new TreeMap<>();

  static {
    values.put(BUBBLE.name().toLowerCase(), BUBBLE);
    values.put(INSERTION.name().toLowerCase(), INSERTION);
    values.put(SELECTION.name().toLowerCase(), SELECTION);
    values.put(SHELL.name().toLowerCase(), SHELL);
    values.put(STANDARD.name().toLowerCase(), STANDARD);
  }

  public static Optional<SortingStrategy> fromString(String value) {
    return Optional.ofNullable(values.get(value));
  }

  @Getter
  private final AbstractSort sort;
}
