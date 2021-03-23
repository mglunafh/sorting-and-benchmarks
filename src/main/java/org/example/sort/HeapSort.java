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

    for (int j = start + 1; j < stop; j++) {
        emerge(arr, start, j);
    }

    for (int i = stop - 1; i > start + 1; i--) {
      swap(arr, start, i);
      drown(arr, start, i);
    }
    if (arr[start] > arr[start + 1]) {
      swap(arr, start, start + 1);
    }
  }

  private void drown(int[] arr, int start,  int stop) {
    int tempIndex = start;
    while (tempIndex < (stop - start - 1) / 2) {
      int child1 = 2 * tempIndex + 1;
      int child2 = 2 * tempIndex + 2;
      if (arr[child1] > arr[child2] && arr[child1] > arr[tempIndex]) {
        swap(arr, child1, tempIndex);
        tempIndex = child1;
      }
      else if (arr[child2] >= arr[child1] && arr[child2] > arr[tempIndex]) {
        swap(arr, child2, tempIndex);
        tempIndex = child2;
      }
      else
        break;
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
