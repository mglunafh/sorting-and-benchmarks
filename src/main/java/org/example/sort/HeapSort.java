package org.example.sort;


public class HeapSort implements AbstractSort {

  @Override
  public String getName() {
    return "HEAP SORT";
  }

  @Override
  public void sortArray(int[] arr, int start, int stop) {
    assert start < stop : String.format("Start (%d) is greater than end (%d)", start, stop);
    assert start >= 0 : String.format("Start (%d) is a negative number", start);
    assert stop <= arr.length : String.format("Stop (%d) is greater than arr length (%d)", stop, arr.length);

    for (int i = stop; i > start; i--) {
      for (int j = start + 1; j < i; j++) {
        emerge(arr, start, j);
      }
      swap(arr, start, i - 1);
    }
  }

  private void emerge(int[] arr, int start, int index) {
    int tempIndex = index;
    while (tempIndex > start) {
      int parentInd = start + (tempIndex - start - 1) / 2;
      if (arr[tempIndex] > arr[parentInd]) {
        swap(arr, tempIndex, parentInd);
      }
      tempIndex = parentInd;
    }
  }
}
