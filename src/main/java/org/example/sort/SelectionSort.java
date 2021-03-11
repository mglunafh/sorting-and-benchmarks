package org.example.sort;

public class SelectionSort implements AbstractSort {

  @Override
  public String getName() {
    return "SELECTION SORT";
  }

  @Override
  public void sortArray(int[] arr, int start, int stop) {
    assert start < stop : String.format("Start (%d) is greater than end (%d)", start, stop);
    assert start >= 0 : String.format("Start (%d) is a negative number", start);
    assert stop <= arr.length : String.format("Stop (%d) is greater than arr length (%d)", stop, arr.length);

    for (int i = start; i < stop; i++) {
      int tempIndex = i;
      for (int j = i + 1; j < stop; j++) {
        if (arr[tempIndex] > arr[j]) {
          tempIndex = j;
        }
      }
      int temp = arr[i];
      arr[i] = arr[tempIndex];
      arr[tempIndex] = temp;
    }
  }
}
