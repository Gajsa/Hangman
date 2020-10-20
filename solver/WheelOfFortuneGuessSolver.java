package solver;

import java.util.*;

/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D) You'll need
 * to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver {

    HashSet<String> dictionaryBackup = null;
    ArrayList<HashSet<String>> dictionary = null;
    private Set<Character> guessedLetters = null;
    private int[] wordLen;
    HashMap<Character,
            Integer> map = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     *                   from.
     */
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {
        // Implement me!
        this.dictionaryBackup = new HashSet<>(dictionary);

        guessedLetters = new HashSet<>();
        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
    } // end of WheelOfFortuneGuessSolver()

    @Override
    public void newGame(int[] wordLengths, int maxTries) {
        // Implement me!
        System.out.println("New Game Has Started");

        wordLen = wordLengths;

        this.dictionary = new ArrayList<HashSet<String>>();
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            HashSet<String> dict = new HashSet<>(this.dictionaryBackup);
            this.dictionary.add(dict);
        }

        String s = "";
        for (int wordLength : wordLengths) {
            s += wordLength + " ";
        }
        System.out.println("Word(s) Lengths are: " + s);

        System.out.println("Total Number of guesses: " + maxTries);
        System.out.println("------------------------------------------------");
    } // end of setWordLength()

    @Override
    public char makeGuess() {
        // Implement me!
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            for (Iterator<String> i = dictionary.get(numWords).iterator(); i.hasNext(); ) {
                String w = i.next();
                if (w.length() != wordLen[numWords]) {
                    i.remove();
                }
            }
        }
        ArrayList<String> temp = new ArrayList<String>();
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            for (Iterator<String> i = dictionary.get(numWords).iterator(); i.hasNext(); ) {
                String w = i.next();
                if (!temp.contains(w)) {
                    temp.add(w);
                }
            }
        }

        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
        for (String w : temp) {
            for (Character c : map.keySet()) {
                if (w.contains(c + "")) {
                    map.replace(c, map.get(c) + 1);
                }
            }
        }

        char letter = '\0';
        int max = 0;
        for (char c : map.keySet()) {
            if ((map.get(c) > max) && !guessedLetters.contains(c)) {
                letter = c;
                max = map.get(c);
            }
        }
        guessedLetters.add(letter);
        return letter;
    } // end of makeGuess()

    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
        String cStr = c + "";

        if (bGuess) {
            for (int numWords = 0; numWords < wordLen.length; numWords++) {
                if (lPositions.get(numWords) != null) {
                    ArrayList<String> removeWords = new ArrayList<>();
                    for (String word : dictionary.get(numWords)) {
                        for (Integer pos : lPositions.get(numWords)) {
                            if (word.charAt(pos) != cStr.charAt(0)) {
                                removeWords.add(word);
                                break;
                            }
                        }
                    }
                    dictionary.get(numWords).removeAll(removeWords);

                    removeWords.clear();
                    for (String word : dictionary.get(numWords)) {
                        if (word.contains(cStr)) {
                            ArrayList<Integer> pos2 = new ArrayList<>();
                            for (int d = 0; d < word.length(); d++) {
                                if (word.charAt(d) == cStr.charAt(0)) {
                                    pos2.add(d);
                                }
                            }
                            if (!pos2.equals(lPositions.get(numWords))) {
                                removeWords.add(word);
                            }
                        }
                    }
                    dictionary.get(numWords).removeAll(removeWords);
                } else {
                    dictionary.get(numWords).removeIf(word -> word.contains(cStr));
                }
            }
        } else {
            for (int numWords = 0; numWords < wordLen.length; numWords++) {
                dictionary.get(numWords).removeIf(w -> w.contains(cStr));
            }
        }
    } // end of guessFeedback()

} // end of class WheelOfFortuneGuessSolver
