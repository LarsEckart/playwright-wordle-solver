package com.larseckart.wordle;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class SolutionTest {

  private final Solution solution = new Solution();

  @Test
  void applesauce() throws IOException {
    Dictionary dictionary = new Dictionary(Paths.get("src/main/resources/words_5_letters.txt"));
    Solution solution = new Solution();

    solution.not("a");
    solution.not("r");
    solution.set(2, "o");
    solution.not("s");
    solution.not("e");

    solution.not("t");
    solution.not("h");
    solution.set(2, "o");
    solution.set(3, "l");
    solution.not("i");

    solution.not("d");
    solution.not("o");
    solution.set(2, "o");
    solution.set(3, "l");
    solution.not("y");


    assertThat(dictionary.getPossibleWords(solution).contains("knoll")).isTrue();
  }

  @Test
  void almostWithDuplicates() {

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

    assertThat(solution.containsOnlyLettersThatMightBePartOfSolution("graph")).isTrue();
    assertThat(solution.containsOnlyLettersThatMightBePartOfSolution("apple")).isFalse();
  }

  @Test
  void containsNotLettersThatAreNotPartOfSolution2() {
    solution.not("a");
    solution.not("p");

    assertThat(solution.containsOnlyLettersThatMightBePartOfSolution("graph")).isTrue();
    assertThat(solution.containsOnlyLettersThatMightBePartOfSolution("apple")).isTrue();
  }
}
