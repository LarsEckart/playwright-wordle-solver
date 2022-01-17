package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

  public static void main(String[] args) throws Exception {

    Path path = Paths.get("words_5_letters.txt");
    List<String> words = Files.readAllLines(path);
    String guess = words.get(new Random(5).nextInt(words.size()));

    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.firefox()
          .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
      Page page = browser.newPage();
      page.navigate("https://www.powerlanguage.co.uk/wordle/");
      page.click("game-modal path");
      Solution solution = new Solution();

      enterGuess(page, guess);

      for (int i = 0; i < 5; i++) {
        ElementHandle elementHandle1 = page.querySelector("game-tile >> nth=" + i);
        String letter = elementHandle1.getAttribute("letter");
        String evaluation = elementHandle1.getAttribute("evaluation"); // correct | absent | present
        if ("correct".equals(evaluation)) {
          solution.add(i, letter.charAt(0));
        }
        if ("absent".equals(evaluation)) {
          solution.not(letter);
        }
        if ("present".equals(evaluation)) {
          solution.almost(letter);
        }
      }

      // Pause on the following line.
      page.pause();
    }
  }

  private static class Solution {

    private final char[] chars = new char[5];
    private final List<String> wrongOnes = new ArrayList<>();
    private final List<String> notRightYet = new ArrayList<>();

    public Solution() {
      this.chars[0] = '*';
      this.chars[1] = '*';
      this.chars[2] = '*';
      this.chars[3] = '*';
      this.chars[4] = '*';
    }

    public void add(int position, char c) {
      this.chars[position] = c;
    }

    public String toString() {
      return new String(chars);
    }

    public void not(String letter) {
      wrongOnes.add(letter);
    }

    public void almost(String letter) {
      notRightYet.add(letter);
    }
  }

  private static void enterGuess(Page page, String guess) {
    char[] chars = guess.toCharArray();
    page.click("button:has-text(\"" + chars[0] + "\")");
    page.click("button:has-text(\"" + chars[1] + "\")");
    page.click("button:has-text(\"" + chars[2] + "\")");
    page.click("button:has-text(\"" + chars[3] + "\")");
    page.click("button:has-text(\"" + chars[4] + "\")");

    page.click("text=enter");
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {

    }
  }
}
