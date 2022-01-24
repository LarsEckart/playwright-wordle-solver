package com.larseckart.wordle;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class SolutionTest {

  private final Solution solution = new Solution();

  @Test
  void applesauce() throws IOException {
    Dictionary dictionary = new Dictionary(Paths.get("src/main/resources/words_5_letters.txt"));
    Solution solution = new Solution();

    solution.not("q");
    solution.not("u");
    solution.not("e");
    solution.not("r");
    solution.not("y");

    solution.not("f");
    solution.not("c");
    solution.not("a");
    solution.almost(1, "o");
    solution.set(4, "l");

    solution.not("s");
    solution.not("t");
    solution.not("o"); // !!!
    solution.almost(1, "k");

    assertThat(dictionary.getPossibleWords(solution).contains("knoll")).isTrue();
  }

  @Test
  void returnsTrueWhenFirstLetterMatches() {
    solution.set(0, "P");

    assertThat(solution.hasCorrectLettersAtSameLocation("panda")).isTrue();
  }

  @Test
  void returnsFalseWhenFirstLetterNotMatches() {
    solution.set(0, "P");

    assertThat(solution.hasCorrectLettersAtSameLocation("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenSecondLetterNotMatches() {
    solution.set(1, "a");

    assertThat(solution.hasCorrectLettersAtSameLocation("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenThirdLetterNotMatches() {
    solution.set(2, "n");

    assertThat(solution.hasCorrectLettersAtSameLocation("solar")).isFalse();
  }

  @Test
  void returnsTrueWhenSecondLetterMatches() {
    solution.set(1, "a");

    assertThat(solution.hasCorrectLettersAtSameLocation("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenThirdLetterMatches() {
    solution.set(2, "n");

    assertThat(solution.hasCorrectLettersAtSameLocation("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenTwoLetterMatches() {
    solution.set(0, "g");
    solution.set(1, "r");
    solution.set(2, "a");
    solution.set(3, "p");
//    solution.add(4, "e");

    assertThat(solution.hasCorrectLettersAtSameLocation("graph")).isTrue();
  }

  @Test
  void containsNotLettersThatAreNotPartOfSolution() {
    solution.not("g");

    assertThat(solution.containsNotLettersThatAreNotPartOfSolution("graph")).isTrue();
    assertThat(solution.containsNotLettersThatAreNotPartOfSolution("apple")).isFalse();
  }

  @Test
  void containsNotLettersThatAreNotPartOfSolution2() {
    solution.not("a");
    solution.not("p");

    assertThat(solution.containsNotLettersThatAreNotPartOfSolution("graph")).isTrue();
    assertThat(solution.containsNotLettersThatAreNotPartOfSolution("apple")).isTrue();
  }
}
