package org.example.sort;

public interface AbstractSort {

  String getName();

  void sortArray(int[] arr, int start, int stop);

  default void sortArray(int[] arr) {
    sortArray(arr, 0, arr.length);
  }
}
