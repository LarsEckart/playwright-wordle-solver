package com.larseckart.wordle;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class DictionaryTest {

  @Test
  void getLetterDistribution() throws IOException {
    Dictionary dictionary = new Dictionary(Paths.get("src/main/resources/words_5_letters.txt"));
    Approvals.verify(dictionary.getLetterDistribution());
  }

  @Test
  void getLetterDistributionCountForWord() throws IOException {
    Dictionary dictionary = new Dictionary(Paths.get("src/main/resources/words_5_letters.txt"));
    assertThat(dictionary.calculateLetterDistributionCountForWord("pants")).isEqualTo(20921L);
    assertThat(dictionary.calculateLetterDistributionCountForWord("aaaaa")).isEqualTo(5990);
  }

}
