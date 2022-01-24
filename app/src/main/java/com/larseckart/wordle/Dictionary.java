package com.larseckart.wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionary {

  private final List<String> words;

  public Dictionary() throws IOException {
    Path path = Paths.get("words_5_letters.txt");
    words = Files.readAllLines(path);
  }

  public Dictionary(Path path) throws IOException {
    words = Files.readAllLines(path);
  }

  String nextGuess(Solution solution) {
    return getPossibleWords(solution).stream()
        .findAny()
        .orElseGet(() -> "pling");
  }

  public List<String> getPossibleWords(Solution solution) {
    return words.stream()
        .filter(w -> !solution.containsNotLettersThatAreNotPartOfSolution(w))
        .filter(solution::hasCorrectLettersAtSameLocation)
        .filter(solution::hasPresentLetterButInDifferentPlace).toList();
  }

  public Map<String, Long> getLetterDistribution() {

    return words.stream()
        .flatMap(w -> Arrays.stream(w.split("")))
        .collect(Collectors.groupingBy(l -> l, Collectors.counting()));
  }
}
