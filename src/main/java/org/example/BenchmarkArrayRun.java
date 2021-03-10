package org.example;


import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BenchmarkArrayRun {

  private final String arrayLabel;
  private final List<BenchmarkStrategyRun> benchmarks;
}
