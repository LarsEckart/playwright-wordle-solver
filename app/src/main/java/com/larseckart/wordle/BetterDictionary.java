package com.larseckart.wordle;

import java.io.IOException;

public class BetterDictionary extends Dictionary {

  private int invocations = 0;
  private String[] goodWords = {
      "arose",
      "until",
      "chawk",
      "womby"
  };


  public BetterDictionary() throws IOException {
  }

  @Override
  String nextGuess(Solution solution) {
    if (invocations < 4) {
      return goodWords[invocations++];
    }
    return super.nextGuess(solution);
  }
}
