package solver;

import java.util.*;

/**
 * Dictionary aware guessing strategy for Hangman. (task B)
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver {


    //Set dictionary to keep the whole given Dictionary
    private Set<String> dictionary = null;

    //Set guessedLetters to hold all the letters which are already guessed
    private Set<Character> guessedLetters = null;

    //wordLen records length of the given word
    private int wordLen = 0;

    //letterCountHashMap to record the letter and count of the words they would be present in
    HashMap<Character, Integer> letterCountHashMap = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public DictAwareSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
        guessedLetters = new HashSet<>();
        letterCountHashMap = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);
    } // end of DictAwareSolver()

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
        System.out.println("Word Length is : " + totalWordLength);
        System.out.println("Total Number of guesses: " + maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        wordLen = wordLengths[0];
    } // end of newGame()


    /**
     * Method to make a guess
     *
     * @return letter method have guessed
     */
    @Override
    public char makeGuess() {
        // Iterating through every word in dictionary and removing those which don't have required length
        for (Iterator<String> i = dictionary.iterator(); i.hasNext(); ) {
            String word = i.next();
            if (word.length() != wordLen) {
                i.remove();
            }
        }

        // words ArrayList to record the letters in current dictionary
        ArrayList<String> words = new ArrayList<>();
        for (String w : dictionary) {
            words.add(w);
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

            // ArrayList of all words to be removed
            ArrayList<String> removeWords = new ArrayList<>();

            // Iterate through dictionary
            for (String word : dictionary) {
                for (Integer pos : lPositions.get(0)) {
                    // Adding words to removeWords ArrayList which do not have guessed letter at the position
                    if (word.charAt(pos) != cStr.charAt(0)) {
                        removeWords.add(word);
                        break;
                    }
                }
            }
            // Removing all words in ArrayList removeWords from dictionary
            dictionary.removeAll(removeWords);

            removeWords.clear();

            // Iterate through dictionary
            for (String word : dictionary) {
                // If word contains letter which is previously guessed
                if (word.contains(cStr)) {
                    ArrayList<Integer> pos2 = new ArrayList<>();
                    for (int a = 0; a < word.length(); a++) {
                        if (word.charAt(a) == cStr.charAt(0)) {
                            pos2.add(a);
                        }
                    }
                    if (!pos2.equals(lPositions.get(0))) {
                        removeWords.add(word);
                    }
                }
            }
            dictionary.removeAll(removeWords);
        } else {
            dictionary.removeIf(word -> word.contains(cStr));
        }
    } // end of guessFeedback()

} // end of class DictAwareSolver
