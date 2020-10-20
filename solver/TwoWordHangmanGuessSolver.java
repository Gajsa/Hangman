package solver;

import java.util.*;

/**
 * Guessing strategy for two word Hangman. (task C)
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class TwoWordHangmanGuessSolver extends HangmanSolver {

    //ArrayList dictionary to keep the whole given Dictionary
    ArrayList<HashSet<String>> dictionary = null;

    //Set guessedLetters to hold all the letters which are already guessed
    private Set<Character> guessedLetters = null;

    //wordLen records length of the given word(s)
    private int[] wordLen;

    //letterCountHashMap to record the letter and count of the words they would be present in
    HashMap<Character, Integer> letterCountHashMap = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public TwoWordHangmanGuessSolver(Set<String> dictionary) {
        this.dictionary = new ArrayList<HashSet<String>>();
        for (int a = 0; a < 2; a++) {
            HashSet<String> dict = new HashSet<>(dictionary);
            this.dictionary.add(dict);
        }
        guessedLetters = new HashSet<>();
        letterCountHashMap = new HashMap<>();
        //Initialing HashMap with character frequencies
        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);
    } // end of TwoWordHangmanGuessSolver()


    /**
     * Method to start new game
     *
     * @param wordLengths         Length of words we are guessing for.
     * @param maxIncorrectGuesses Maximum number of incorrect guesses we are allowed.
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        System.out.println("New Game Has Started");

        // totalWordLength stores length of the word to be guessed
        String totalWordLength = "";

        // calculating length of the word provided
        for (int wordLength : wordLengths) {
            totalWordLength += wordLength + " ";
        }
        System.out.println("Word(s) Lengths are: " + totalWordLength);
        System.out.println("Total Number of guesses: " + maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        wordLen = wordLengths;
    } // end of newGame()


    /**
     * Method to make a guess
     *
     * @return letter method have guessed
     */
    @Override
    public char makeGuess() {
        // Iterating through every word in dictionary and removing those which don't have required length
        for (int a = 0; a < 2; a++) {
            for (Iterator<String> i = dictionary.get(a).iterator(); i.hasNext(); ) {
                String w = i.next();
                if (w.length() != wordLen[a]) {
                    i.remove();
                }
            }
        }

        // adding word(s) whose length is equal to length of word to guess
        ArrayList<String> words = new ArrayList<String>();
        for (int a = 0; a < 2; a++) {
            for (Iterator<String> i = dictionary.get(a).iterator(); i.hasNext(); ) {
                String w = i.next();
                if (!words.contains(w)) {
                    words.add(w);
                }
            }
        }

        // initializing HashMap
        letterCountHashMap = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);

        // recording number of times a letter is present into the hashmap
        for (String w : words) {
            for (Character c : letterCountHashMap.keySet()) {
                if (w.contains(c + "")) {
                    letterCountHashMap.replace(c, letterCountHashMap.get(c) + 1);
                }
            }
        }

        char letter = '\0';
        int count = 0;

        // To get the next letter which have highest count and is not guessed yet
        for (char c : letterCountHashMap.keySet()) {
            if ((letterCountHashMap.get(c) > count) && !guessedLetters.contains(c)) {
                letter = c;
                count = letterCountHashMap.get(c);
            }
        }

        //Record letter in guessLetter before returning
        guessedLetters.add(letter);
        return letter;

    } // end of makeGuess()


    /**
     * @param c
     * @param bGuess True if the character guessed is in one or more of the words, otherwise false.
     * @param lPositions
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
        String cStr = c + "";

        // If letter is guessed right
        if (bGuess) {
            for (int numWords = 0; numWords < 2; numWords++) {
                if (lPositions.get(numWords) != null) {

                    // ArrayList of all words to be removed
                    ArrayList<String> removeWords = new ArrayList<>();

                    // Iterate through dictionary
                    for (String word : dictionary.get(numWords)) {
                        for (Integer pos : lPositions.get(numWords)) {

                            // Adding words to removeWords ArrayList which do not have guessed letter at the position
                            if (word.charAt(pos) != cStr.charAt(0)) {
                                removeWords.add(word);
                                break;
                            }
                        }
                    }
                    // Removing all words in ArrayList removeWords from dictionary
                    dictionary.get(numWords).removeAll(removeWords);

                    removeWords.clear();

                    // Iterate through dictionary
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
            //removing all the words which contains incorrectly guessed letter
            for (int numWords = 0; numWords < 2; numWords++) {
                dictionary.get(numWords).removeIf(w -> w.contains(cStr));
            }
        }
    } // end of guessFeedback()

} // end of class TwoWordHangmanGuessSolver
