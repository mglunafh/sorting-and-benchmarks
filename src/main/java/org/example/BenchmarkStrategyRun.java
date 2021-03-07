package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BenchmarkStrategyRun {
  private final String strategyLabel;
  private final int iterations;
  private final long[] timings;
}
