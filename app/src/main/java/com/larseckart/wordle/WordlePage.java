package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordlePage implements AutoCloseable {

    private Playwright playwright;
    private final Page page;

    public WordlePage() {
        playwright = Playwright.create();
        Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
        this.page = context.newPage();
    }

    public WordlePage(Browser browser) {
        BrowserContext context = browser.newContext(new Browser.NewContextOptions());
        this.page = context.newPage();
    }

    void open() {
        page.navigate("https://www.powerlanguage.co.uk/wordle/");
    }

    public void closeInstructions() {
        this.page.click("game-modal path");
        this.page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void closeStatistics() {
        page.click("game-modal svg");
        this.page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void enterGuess(String guess) {
        char[] letters = guess.toCharArray();

        page.click("button[data-key=%s]".formatted(letters[0]));
        page.click("button[data-key=%s]".formatted(letters[1]));
        page.click("button[data-key=%s]".formatted(letters[2]));
        page.click("button[data-key=%s]".formatted(letters[3]));
        page.click("button[data-key=%s]".formatted(letters[4]));

        page.click("text=enter");
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void close() {
        Path path = page.video().path();
        this.playwright.close();
        Path newPath = Paths.get(today());
        try {
            Files.move(path, path.resolveSibling(newPath));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String today() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".webm";
    }

    public void evaluateGuess(Solution solution, int row) {
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

    public boolean wasCorrect(int row) {
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
            String evaluation = elementHandle1.getAttribute("evaluation"); // correct | absent | present
            if ("correct".equals(evaluation)) {

            }
            if ("absent".equals(evaluation)) {
                return false;
            }
            if ("present".equals(evaluation)) {
                return false;
            }
        }
        return true;

    }
}
