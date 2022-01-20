package com.larseckart.wordle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class Solution {

  private final char[] chars = new char[5];
  private final Set<String> wrongOnes = new HashSet<>();
  private final Map<Integer, String> present = new HashMap<>();

  public Solution() {
    this.chars[0] = '*';
    this.chars[1] = '*';
    this.chars[2] = '*';
    this.chars[3] = '*';
    this.chars[4] = '*';
  }

  public void set(int position, String c) {
    this.chars[position] = c.toLowerCase().charAt(0);
  }

  public void not(String letter) {
    wrongOnes.add(letter);
  }

  public void almost(int i, String letter) {
    present.put(i, letter);
  }

  public boolean hasPresentLetterButInDifferentPlace(String word) {
    for (Entry<Integer, String> entry : present.entrySet()) {
      if (!word.contains(entry.getValue())) {
        return false;
      }
      if (word.charAt(entry.getKey()) == entry.getValue().charAt(0)) {
        return false;
      }

    }
    return true;
  }

  boolean hasCorrectLettersAtSameLocation(String word) {
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

  boolean containsNotLettersThatAreNotPartOfSolution(String word) {
    return wrongOnes.stream().anyMatch(word::contains);
  }


  @Override
  public String toString() {
    return new String(chars);
  }
}
