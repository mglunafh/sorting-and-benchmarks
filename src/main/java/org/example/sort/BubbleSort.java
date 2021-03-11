package org.example.sort;

import java.util.Locale;

public class BubbleSort implements AbstractSort {

  @Override
  public String getName() {
    return "Bubble sort".toUpperCase(Locale.ROOT);
  }

  @Override
  public void sortArray(int[] arr, int start, int stop) {
    assert start < stop : String.format("Start (%d) is greater than end (%d)", start, stop);
    assert start >= 0 : String.format("Start (%d) is a negative number", start);
    assert stop <= arr.length : String.format("Stop (%d) is greater than arr length (%d)", stop, arr.length);

    for (int i = start; i < stop; i++) {
      for (int j = start + 1; j < stop - i; j++) {
        if (arr[j - 1] > arr[j]) {
          int temp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = temp;
        }
      }
    }
  }
}
