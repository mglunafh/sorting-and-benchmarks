package org.example;

import java.util.Arrays;
import java.util.Locale;

public class StandardSort implements SortingStrategy {

  @Override
  public String getName() {
    return "standard sort".toUpperCase(Locale.ROOT);
  }

  @Override
  public void sortArray(int[] arr, int start, int stop) {
    Arrays.sort(arr, start, stop);
  }
}
