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
  HEAP(new HeapSort()),
  STANDARD(new StandardSort());

  SortingStrategy(AbstractSort sort) {
    this.sort = sort;
  }

  private static Map<String, SortingStrategy> values = new TreeMap<>();

  static {
    for (SortingStrategy strategy : SortingStrategy.values()) {
      values.put(strategy.name().toLowerCase(), strategy);
    }
  }

  public static Optional<SortingStrategy> fromString(String value) {
    return Optional.ofNullable(values.get(value));
  }

  @Getter
  private final AbstractSort sort;
}
