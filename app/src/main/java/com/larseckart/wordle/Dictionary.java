package com.larseckart.wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionary {

  private final List<String> words;

  private Map<String, Long> letterDistribution;

  public Dictionary() throws IOException {
    this(Paths.get("words_5_letters.txt"));
  }

  public Dictionary(Path path) throws IOException {
    words = Files.readAllLines(path);
    letterDistribution = getLetterDistribution();
  }

  String nextGuess(Solution solution) {
    List<String> possibleWords = getPossibleWords(solution);
    return possibleWords.stream()
        .sorted(Comparator.comparing(this::calculateLetterDistributionCountForWord).reversed())
        .findFirst()
        .orElseGet(() -> "pling");
  }

  public List<String> getPossibleWords(Solution solution) {
    return words.stream()
        .filter(w -> !solution.containsOnlyLettersThatMightBePartOfSolution(w))
        .filter(solution::hasCorrectLettersAtSameLocation)
        .filter(solution::hasPresentLetterButInDifferentPlace)
        .toList();
  }

  public long calculateLetterDistributionCountForWord(String word) {
    return Arrays.stream(word.split("")).distinct().mapToLong(letterDistribution::get).sum();
  }

  public Map<String, Long> getLetterDistribution() {
    return words.stream()
        .flatMap(w -> Arrays.stream(w.split("")))
        .collect(Collectors.groupingBy(l -> l, Collectors.counting()));
  }
}
