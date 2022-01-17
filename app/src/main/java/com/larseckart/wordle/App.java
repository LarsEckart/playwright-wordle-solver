package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
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

      page.click("button:has-text(\"w\")");
      page.click("button:has-text(\"o\")");
      page.click("button:has-text(\"r\")");
      page.click("button:has-text(\"l\")");
      page.click("button:has-text(\"d\")");

      page.click("text=enter");

      // Pause on the following line.
      page.pause();
    }
  }
}
