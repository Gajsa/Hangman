package solver;

import java.util.*;

/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D)
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver {


    HashSet<String> dictionaryBackup = null;

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
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {
        this.dictionaryBackup = new HashSet<>(dictionary);

        guessedLetters = new HashSet<>();
        letterCountHashMap = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);
    } // end of WheelOfFortuneGuessSolver()


    /**
     *
     * @param wordLengths Length of words we are guessing for.
     * @param maxTries
     */
    @Override
    public void newGame(int[] wordLengths, int maxTries) {
        System.out.println("New Game Has Started");

        wordLen = wordLengths;

        this.dictionary = new ArrayList<HashSet<String>>();
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            HashSet<String> dict = new HashSet<>(this.dictionaryBackup);
            this.dictionary.add(dict);
        }

        // totalWordLength stores length of the word to be guessed
        String totalWordLength = "";

        // calculating length of the word(s) provided
        for (int wordLength : wordLengths) {
            totalWordLength += wordLength + " ";
        }
        System.out.println("Word(s) Lengths are: " + totalWordLength);

        System.out.println("Total Number of guesses: " + maxTries);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
    } // end of setWordLength()

    /**
     * Method to make a guess
     *
     * @return letter method have guessed
     */
    @Override
    public char makeGuess() {
        // Iterating through every word in dictionary and removing those which don't have required length
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            for (Iterator<String> i = dictionary.get(numWords).iterator(); i.hasNext(); ) {
                String w = i.next();
                if (w.length() != wordLen[numWords]) {
                    i.remove();
                }
            }
        }

        // words ArrayList to record the letters in current dictionary
        ArrayList<String> words = new ArrayList<String>();
        for (int numWords = 0; numWords < wordLen.length; numWords++) {
            for (Iterator<String> i = dictionary.get(numWords).iterator(); i.hasNext(); ) {
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

        // Records the letter that is to be guessed
        char letter = '\0';

        // Records the count of currently guessed letter
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
     *
     * @param c
     * @param bGuess True if the character guessed is in one or more of the words, otherwise false.
     * @param lPositions
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
        String cStr = c + "";

        // If letter is guessed right
        if (bGuess) {
            for (int numWords = 0; numWords < wordLen.length; numWords++) {
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

                        // If word contains letter which is previously guessed
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
