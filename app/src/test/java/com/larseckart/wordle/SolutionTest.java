package com.larseckart.wordle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SolutionTest {

  private final Solution solution = new Solution();

  @Test
  void returnsTrueWhenFirstLetterMatches() {
    solution.add(0, "P");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsFalseWhenFirstLetterNotMatches() {
    solution.add(0, "P");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenSecondLetterNotMatches() {
    solution.add(1, "a");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsFalseWhenThirdLetterNotMatches() {
    solution.add(2, "n");

    assertThat(solution.mightBe("solar")).isFalse();
  }

  @Test
  void returnsTrueWhenSecondLetterMatches() {
    solution.add(1, "a");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenThirdLetterMatches() {
    solution.add(2, "n");

    assertThat(solution.mightBe("panda")).isTrue();
  }

  @Test
  void returnsTrueWhenTwoLetterMatches() {
    solution.add(0, "g");
    solution.add(1, "r");
    solution.add(2, "a");
    solution.add(3, "p");
//    solution.add(4, "e");

    assertThat(solution.mightBe("graph")).isTrue();
  }

  @Test
  void definitelyNot() {
    solution.not("g");

    assertThat(solution.definitelyNot("graph")).isTrue();
  }
}
