package com.larseckart.wordle;

import java.util.ArrayList;
import java.util.List;

class Solution {

  private final char[] chars = new char[5];
  private final List<String> wrongOnes = new ArrayList<>();
  private final List<String> notRightYet = new ArrayList<>();

  public Solution() {
    this.chars[0] = '*';
    this.chars[1] = '*';
    this.chars[2] = '*';
    this.chars[3] = '*';
    this.chars[4] = '*';
  }

  public void add(int position, String c) {
    this.chars[position] = c.toLowerCase().charAt(0);
  }

  public void not(String letter) {
    wrongOnes.add(letter);
  }

  public void almost(int i, String letter) {
    notRightYet.add(letter);
  }

  boolean mightBe(String word) {
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '*') {
        continue;
      }
      if (chars[i] != word.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  boolean definitelyNot(String word) {
    for (String wrongOne : wrongOnes) {
      if (word.contains(wrongOne)) {
        return true;
      }
    }
    return false;
  }


  @Override
  public String toString() {
    return new String(chars);
  }
}
