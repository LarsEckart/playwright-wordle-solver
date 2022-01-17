package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class App {

  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.firefox()
          .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
      Page page = browser.newPage();
      page.navigate("https://www.powerlanguage.co.uk/wordle/");
      page.click("game-modal path");

      enterGuess(page, "world");

      for (int i = 0; i < 5; i++) {
        ElementHandle elementHandle1 = page.querySelector("game-tile >> nth=" + i);
        String letter = elementHandle1.getAttribute("letter");
        String evaluation = elementHandle1.getAttribute("evaluation");
      }

      // Pause on the following line.
      page.pause();
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
  }
}
