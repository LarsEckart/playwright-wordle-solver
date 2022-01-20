package com.larseckart.wordle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SolutionTest {

  private final Solution solution = new Solution();

  @Test
  void returnsTrueWhenFirstLetterMatches() {
    solution.set(0, "P");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsFalseWhenFirstLetterNotMatches() {
    solution.set(0, "P");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenSecondLetterNotMatches() {
    solution.set(1, "a");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenThirdLetterNotMatches() {
    solution.set(2, "n");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsTrueWhenSecondLetterMatches() {
    solution.set(1, "a");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenThirdLetterMatches() {
    solution.set(2, "n");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenTwoLetterMatches() {
    solution.set(0, "g");
    solution.set(1, "r");
    solution.set(2, "a");
    solution.set(3, "p");
//    solution.add(4, "e");

    assertThat(solution.mightBe("graph")).isTrue();
  }

  @Test
  void definitelyNot() {
    solution.not("g");

    assertThat(solution.definitelyNot("graph")).isTrue();
  }
}
