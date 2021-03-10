package org.example;

public class InsertionSort implements SortingStrategy {

  @Override
  public String getName() {
    return "INSERTION SORT";
  }

  @Override
  public void sortArray(int[] arr, int start, int stop) {
    assert start < stop : String.format("Start (%d) is greater than end (%d)", start, stop);
    assert start >= 0 : String.format("Start (%d) is a negative number", start);
    assert stop <= arr.length : String.format("Stop (%d) is greater than arr length (%d)", stop, arr.length);

    for (int i = start + 1; i < stop; i++) {
      for (int j = i; j > start; j--) {
        if (arr[j] < arr[j - 1]) {
          int temp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = temp;
        }
      }
    }
  }
}
