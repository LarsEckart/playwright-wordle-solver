package com.larseckart.wordle;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class AppWithTestcontainers {

    public static void main(String[] args) throws Exception {
        try (GenericContainer<?> chrome = new GenericContainer<>(DockerImageName.parse("browserless/chrome:latest"));
             Playwright playwright = Playwright.create()) {
            chrome.withExposedPorts(3000).waitingFor(Wait.forHttp("/")).start();
            Browser browser = playwright.chromium().connectOverCDP("ws://localhost:" + chrome.getFirstMappedPort());
            WordlePage wordlePage = new WordlePage(browser);

            Dictionary dictionary = new Dictionary();
            Solution solution = new Solution();

            wordlePage.open();
            wordlePage.closeInstructions();

            for (int i = 1; i < 7; i++) {
                String guess = dictionary.nextGuess(solution);
                System.out.printf("guessing %s from  %s possible words\n", guess, dictionary.getPossibleWords(solution).size());
                wordlePage.enterGuess(guess);
                wordlePage.evaluateGuess(solution, i);
                if (wordlePage.wasCorrect(i)) {
                    wordlePage.closeStatistics();
                    break;
                }
            }
        }
    }
}
