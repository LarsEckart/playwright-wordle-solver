package com.larseckart.wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Dictionary {

  private final List<String> words;

  public Dictionary() throws IOException {
    Path path = Paths.get("words_5_letters.txt");
    words = Files.readAllLines(path);
  }

  String nextGuess(Solution solution) {
    String s = words.stream()
        .filter(w -> !solution.containsNotLettersThatAreNotPartOfSolution(w))
        .filter(solution::hasCorrectLettersAtSameLocation)
        .filter(solution::hasPresentLetterInDifferentPlace)
        .findAny().orElseGet(() -> "pling");
    words.remove(s);
    return s;
  }

}
