package org.example;

public interface SortingStrategy {

  String getName();

  void sortArray(int[] arr, int start, int stop);

  default void sortArray(int[] arr) {
    sortArray(arr, 0, arr.length);
  }
}
