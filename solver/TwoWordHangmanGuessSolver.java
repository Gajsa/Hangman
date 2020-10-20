package solver;

import java.util. * ;

/**
 * Guessing strategy for two word Hangman. (task C) You'll need to complete the
 * implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class TwoWordHangmanGuessSolver extends HangmanSolver {

    /** To keep the whole given Dictionary **/
    ArrayList < HashSet < String >> dictionary = null;

    /** Set guessedLetters to hold all the letters which are already guessed **/
    private Set < Character > guessedLetters = null;

    /** wordLen records length of the given word **/
    private int[] wordLen;

    /** letterCountHashMap to record the letter and count of the words they would be present in **/
    HashMap < Character, Integer > letterCountHashMap = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public TwoWordHangmanGuessSolver(Set < String > dictionary) {

        this.dictionary = new ArrayList < HashSet < String >> ();
        for (int a = 0; a < 2; a++) {
            HashSet < String > dict = new HashSet < >(dictionary);
            this.dictionary.add(dict);
        }

        guessedLetters = new HashSet < >();
        letterCountHashMap = new HashMap < >();

        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);
    } // end of TwoWordHangmanGuessSolver()


    /**
     * Method to start new game
     *
     * @param wordLengths Length of words we are guessing for.
     * @param maxIncorrectGuesses Maximum number of incorrect guesses we are allowed.
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        System.out.println("New Game Has Started");

        // totalWordLength stores length of the word to be guessed
        String totalWordLength = "";

        // calculating length of the word provided
        for (int wordLength: wordLengths) {
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
            for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                String w = i.next();
                if (w.length() != wordLen[a]) {
                    i.remove();
                }
            }
        }

        // words ArrayList to record the letters in current dictionary
        ArrayList < String > words = new ArrayList < String > ();
        for (int a = 0; a < 2; a++) {
            for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                String w = i.next();
                if (!words.contains(w)) {
                    words.add(w);
                }
            }
        }

        // initializing HashMap
        letterCountHashMap = new HashMap < >();
        for (int a = 97; a < 123; a++) {
            letterCountHashMap.put((char) a, 0);
        }
        letterCountHashMap.put('\'', 0);

        // recording number of times a letter is present into the hashmap
        for (String w: words) {
            for (Character letter: letterCountHashMap.keySet()) {
                if (w.contains(letter + "")) {
                    letterCountHashMap.replace(letter, letterCountHashMap.get(letter) + 1);
                }
            }
        }

        // Records the letter that is to be guessed
        char letter = '\0';

        // Records the count of currently guessed letter
        int count = 0;

        // To get the next letter which have highest count and is not guessed yet
        for (char c: letterCountHashMap.keySet()) {
            if ((letterCountHashMap.get(c) > count) && !guessedLetters.contains(c)) {
                letter = c;
                count = letterCountHashMap.get(c);
            }
        }

        // Check if letter is already present, if not, add it in guessedLetters
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter);
        }
        return letter;
    } // end of makeGuess()

    /**
     *
     * @param c
     * @param bGuess True if the character guessed is in one or more of the words, otherwise false.
     * @param lPositions
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList < ArrayList < Integer >> lPositions) {
        if (bGuess) {
            for (int a = 0; a < 2; a++) {
                ArrayList < Integer > positions = lPositions.get(a);
                if (positions != null) {
                    for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                        String w = i.next();
                        for (int b = 0; b < positions.size(); b++) {
                            if (w.charAt(positions.get(b)) != c) {
                                i.remove();
                                break;
                            }
                        }
                    }
                    for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                        String w = i.next();
                        if (w.contains(c + "")) {
                            ArrayList < Integer > pos2 = new ArrayList < >();
                            for (int d = 0; d < w.length(); d++) {
                                if (w.charAt(d) == c) {
                                    pos2.add(d);
                                }
                            }
                            if (!pos2.equals(positions)) {
                                i.remove();
                            }
                        }
                    }
                } else {
                    for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                        String w = i.next();
                        if (w.contains(c + "")) {
                            i.remove();
                        }
                    }
                }
            }
        } else {
            for (int a = 0; a < 2; a++) {
                for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                    String w = i.next();
                    if (w.contains(c + "")) {
                        i.remove();
                    }
                }
            }
        }
    } // end of guessFeedback()

} // end of class TwoWordHangmanGuessSolver
