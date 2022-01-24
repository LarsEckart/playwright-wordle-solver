package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class App {

  public static void main(String[] args) throws Exception {

    Dictionary dictionary = new Dictionary();

    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.firefox()
          .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
      Page page = browser.newPage();
      page.navigate("https://www.powerlanguage.co.uk/wordle/");
      page.click("game-modal path");
      Solution solution = new Solution();

      page.waitForLoadState(LoadState.NETWORKIDLE);

      for (int i = 1; i < 7; i++) {
        String guess = dictionary.nextGuess(solution);
        System.out.printf("guessing %s from  %s possible words\n", guess, dictionary.getPossibleWords(solution).size());
        enterGuess(page, guess);
        evaluateGuess(page, solution, i);
      }

      // Pause on the following line.
      page.pause();
    }
  }

  private static void evaluateGuess(Page page, Solution solution, int row) {
    int start;
    switch (row) {
      default -> start = 0;
      case 2 -> start = 5;
      case 3 -> start = 10;
      case 4 -> start = 15;
      case 5 -> start = 20;
      case 6 -> start = 25;
    }
    int end = start + 5;
    for (int i = start; i < end; i++) {
      ElementHandle elementHandle1 = page.querySelector("game-tile >> nth=" + i);
      String letter = elementHandle1.getAttribute("letter");
      String evaluation = elementHandle1.getAttribute("evaluation"); // correct | absent | present
      if ("correct".equals(evaluation)) {
        solution.set(i % 5, letter);
      }
      if ("absent".equals(evaluation)) {
        solution.not(letter);
      }
      if ("present".equals(evaluation)) {
        solution.almost(i % 5, letter);
      }
    }
  }

  private static void enterGuess(Page page, String guess) {
    char[] letters = guess.toCharArray();

    page.click("button[data-key=%s]".formatted(letters[0]));
    page.click("button[data-key=%s]".formatted(letters[1]));
    page.click("button[data-key=%s]".formatted(letters[2]));
    page.click("button[data-key=%s]".formatted(letters[3]));
    page.click("button[data-key=%s]".formatted(letters[4]));

    page.click("text=enter");
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {

    }
  }
}
