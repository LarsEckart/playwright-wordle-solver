package com.larseckart.wordle;

public class App {

    public static void main(String[] args) throws Exception {
        Dictionary dictionary = new Dictionary();
        Solution solution = new Solution();

        try (WordlePage wordlePage = new WordlePage()) {
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
