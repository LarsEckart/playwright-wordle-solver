package com.larseckart.wordle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class Solution {

  private final Set<String> wrongOnes = new HashSet<>();
  private final List<Tuple<Integer, String>> present = new ArrayList<>();
  private final Set<Tuple<Integer, String>> correct = new HashSet<>();

  public Solution() {
  }

  public void set(int position, String letter) {
    letter = letter.toLowerCase();
    this.correct.add(new Tuple<>(position, letter));
  }

  public void not(String letter) {
    String l = letter.toLowerCase();
    if (Stream.concat(present.stream(), correct.stream()).anyMatch(t -> t.second().equals(l))) {
      return;
    }
    wrongOnes.add(letter);
  }

  public void almost(int position, String letter) {
    letter = letter.toLowerCase();
    present.add(new Tuple<>(position, letter));
  }

  public boolean hasPresentLetterButInDifferentPlace(String word) {
    return present.stream().allMatch(
        e -> word.contains(e.second()) && word.charAt(e.first()) != e.second().charAt(0));
  }

  boolean hasCorrectLettersAtSameLocation(String word) {
    return correct.stream().allMatch(
        e -> word.charAt(e.first()) == e.second().charAt(0));
  }

  boolean containsOnlyLettersThatMightBePartOfSolution(String word) {
    return wrongOnes.stream().anyMatch(wrongLetter -> word.contains(wrongLetter));
  }
}
