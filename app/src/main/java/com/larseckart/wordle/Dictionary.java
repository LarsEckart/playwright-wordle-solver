package com.larseckart.wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Dictionary {

  private final List<String> words;

  public Dictionary() throws IOException {
    Path path = Paths.get("words_5_letters.txt");
    words = Files.readAllLines(path);
  }

  String nextGuess(Solution solution) {
    return words.stream()
        .filter(w -> !solution.definitelyNot(w))
        .filter(solution::mightBe)
        .findAny().orElseGet(() -> "pling");
  }

}
