package com.larseckart.wordle;

import com.spun.util.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Solution {

  private final Set<String> wrongOnes = new HashSet<>();
  private final List<Tuple<Integer, String>> present = new ArrayList<>();
  private final List<Tuple<Integer, String>> correct = new ArrayList<>();

  public Solution() {
  }

  public void set(int position, String letter) {
    letter = letter.toLowerCase();
    this.correct.add(new Tuple<>(position, letter));
  }

  public void not(String letter) {
    String l = letter.toLowerCase();
    if (Stream.concat(present.stream(), correct.stream()).anyMatch(t -> t.getSecond().equals(l))) {
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
        e -> word.contains(e.getSecond()) && word.charAt(e.getFirst()) != e.getSecond().charAt(0));
  }

  boolean hasCorrectLettersAtSameLocation(String word) {
    return correct.stream().allMatch(
        e -> word.charAt(e.getFirst()) == e.getSecond().charAt(0));
  }

  boolean containsNotLettersThatAreNotPartOfSolution(String word) {
    return wrongOnes.stream().anyMatch(word::contains);
  }
}
