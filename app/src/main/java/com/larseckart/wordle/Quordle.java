package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.io.IOException;

public class Quordle {

  public static final String TOP_LEFT_X_PATH = "//html/body/div/div/div[2]/div/div[1]/div[1]/div[";
  public static final String TOP_RIGHT_X_PATH = "//html/body/div/div/div[2]/div/div[1]/div[2]/div[";
  public static final String BOTTOM_LEFT_X_PATH = "//html/body/div/div/div[2]/div/div[2]/div[1]/div[";
  public static final String BOTTOM_RIGHT_X_PATH = "//html/body/div/div/div[2]/div/div[2]/div[2]/div[";

  public static void main(String[] args) throws IOException {

    Dictionary dictionary = new BetterDictionary();
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
      Page page = browser.newPage();
      page.navigate("https://www.quordle.com/");
      Solution solutionTopLeft = new Solution();
      Solution solutionTopRight = new Solution();
      Solution solutionBottomLeft = new Solution();
      Solution solutionBottomRight = new Solution();

      String nextGuess;
      String[] letters;
      int row = 0;

      for (int r = 1; r < 10; r++) {
        nextGuess = dictionary.nextGuess(solutionTopLeft);
        letters = nextGuess.split("");

        page.keyboard().type(nextGuess);
        page.keyboard().press("Enter");

        row = r;
        for (int i = 1; i < 6; i++) {
          evaluateRow(page, solutionTopLeft, letters, row, i, TOP_LEFT_X_PATH);
          evaluateRow(page, solutionTopRight, letters, row, i, TOP_RIGHT_X_PATH);
          evaluateRow(page, solutionBottomLeft, letters, row, i, BOTTOM_LEFT_X_PATH);
          evaluateRow(page, solutionBottomRight, letters, row, i, BOTTOM_RIGHT_X_PATH);
        }

        if (allCorrect(page, letters, row, TOP_LEFT_X_PATH)) {
          break;
        }
      }

      for (int r = row + 1; r < 10; r++) {
        nextGuess = dictionary.nextGuess(solutionTopRight);
        letters = nextGuess.split("");

        page.keyboard().type(nextGuess);
        page.keyboard().press("Enter");

        row = r;
        for (int i = 1; i < 6; i++) {
          evaluateRow(page, solutionTopRight, letters, row, i, TOP_RIGHT_X_PATH);
          evaluateRow(page, solutionBottomLeft, letters, row, i, BOTTOM_LEFT_X_PATH);
          evaluateRow(page, solutionBottomRight, letters, row, i, BOTTOM_RIGHT_X_PATH);
        }

        if (allCorrect(page, letters, row, TOP_RIGHT_X_PATH)) {
          break;
        }
      }

      for (int r = row + 1; r < 10; r++) {
        nextGuess = dictionary.nextGuess(solutionBottomLeft);
        letters = nextGuess.split("");

        page.keyboard().type(nextGuess);
        page.keyboard().press("Enter");

        row = r;
        for (int i = 1; i < 6; i++) {
          evaluateRow(page, solutionBottomLeft, letters, row, i, BOTTOM_LEFT_X_PATH);
          evaluateRow(page, solutionBottomRight, letters, row, i, BOTTOM_RIGHT_X_PATH);
        }

        if (allCorrect(page, letters, row, BOTTOM_LEFT_X_PATH)) {
          break;
        }
      }

      for (int r = row + 1; r < 10; r++) {
        nextGuess = dictionary.nextGuess(solutionBottomRight);
        letters = nextGuess.split("");

        page.keyboard().type(nextGuess);
        page.keyboard().press("Enter");

        row = r;
        for (int i = 1; i < 6; i++) {
          evaluateRow(page, solutionBottomRight, letters, row, i, BOTTOM_RIGHT_X_PATH);
        }

        if (allCorrect(page, letters, row, BOTTOM_RIGHT_X_PATH)) {
          break;
        }
      }

      page.pause();
    }
  }

  private static void evaluateRow(Page page, Solution solution, String[] letters, int row,
      int i, String xpathStart) {
    String ariaLabel = page.locator(
        xpathStart + row + "]/div[" + i + "]").getAttribute("aria-label");
    if (ariaLabel.equals(
        "'%s' (letter %s) is correct".formatted(letters[i - 1].toUpperCase(), i))) {
      solution.set(i - 1, letters[i - 1]);
    } else if (ariaLabel.equals(
        "'%s' (letter %s) is incorrect".formatted(letters[i - 1].toUpperCase(), i))) {
      solution.not(letters[i - 1]);
    } else {
      solution.almost(i - 1, letters[i - 1]);
    }
  }

  private static boolean allCorrect(Page page, String[] letters, int row, String xpath) {
    boolean allCorrect = true;
    for (int i = 1; i < 6; i++) {
      String ariaLabel = page.locator(
              xpath + row + "]/div[" + i + "]")
          .getAttribute("aria-label");
      if (ariaLabel.equals(
          "'%s' (letter %s) is correct".formatted(letters[i - 1].toUpperCase(), i))) {
      } else if (ariaLabel.equals(
          "'%s' (letter %s) is incorrect".formatted(letters[i - 1].toUpperCase(), i))) {
        allCorrect = false;
      } else {
        allCorrect = false;
      }
    }
    return allCorrect;
  }

}
