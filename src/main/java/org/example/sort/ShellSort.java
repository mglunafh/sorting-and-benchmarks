package org.example.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShellSort implements AbstractSort {


  @Override
  public String getName() {
    return "SHELL SORT";
  }

  @Override
  public void sortArray(int[] arr, int from, int to) {
    assert from < to : String.format("Start (%d) is greater than end (%d)", from, to);
    assert from >= 0 : String.format("Start (%d) is a negative number", from);
    assert to <= arr.length : String
        .format("Stop (%d) is greater than arr length (%d)", to, arr.length);

    List<Integer> constantShifts = getConstants(to - from);

    for (int shift : constantShifts) {
      for (int i = from + shift; i < to; i++) {
        int temp = arr[i];
        for (int j = i; j >= from + shift; j -= shift) {
          if (arr[j - shift] > temp) {
            arr[j] = arr[j - shift];
          } else {
            arr[j] = temp;
            break;
          }
        }
      }
    }
  }

  /**
   * Generates Pratt gap sequence in descending order. OEIS A003586 sequence, 3-smooth numbers,
   * numbers of the form 2^i*3^j with i, j >= 0.
   *
   * @param size boundary
   * @return Array of 3-sooth numbers bounded by size/2
   */
  private static List<Integer> getConstants(int size) {
    if (size < 10) {
      return Collections.singletonList(1);
    }

    List<Integer> store = new ArrayList<>();
    int boundary = size / 2;
    for (int i = 1; i <= boundary; i *= 2) {
      for (int j = 1; j <= boundary; j *= 3) {
        int temp = i * j;
        if (temp < boundary) {
          store.add(temp);
        } else {
          break;
        }
      }
    }
    store.sort(Comparator.reverseOrder());
    return store;
  }
}
