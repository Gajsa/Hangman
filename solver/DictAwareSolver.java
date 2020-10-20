package solver;

import java.util.*;

/**
 * Dictionary aware guessing strategy for Hangman. (task B)
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver {

    /**
     * Set dictionary to keep the whole given Dictionary
     **/
    private Set<String> dictionary = null;

    /**
     * Set guessedLetters to hold all the letters which are already guessed
     **/
    private Set<Character> guessedLetters = null;

    /**
     * wordLen records length of the given word
     **/
    private int wordLen = 0;

    /**
     *
     **/
    HashMap<Character, Integer> map = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     *                   from.
     */
    public DictAwareSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
        guessedLetters = new HashSet<>();
        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
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
        for (Iterator<String> i = dictionary.iterator(); i.hasNext(); ) {
            String w = i.next();
            if (w.length() != wordLen) {
                i.remove();
            }
        }
        ArrayList<String> words = new ArrayList<>();
        for (String w : dictionary) {
            words.add(w);
        }
        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
        for (String w : words) {
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


    /**
     * @param c
     * @param bGuess     True if the character guessed is in one or more of the words, otherwise false.
     * @param lPositions
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
        String cStr = c + "";

        if (bGuess) {
//            ArrayList<Integer> positions = lPositions.get(0);

//            for (Iterator<String> iterator = dictionary.iterator(); iterator.hasNext(); ) {
//                String word = iterator.next();
//                for (Integer pos : lPositions.get(0)) {
//                    if (word.charAt(pos) != cStr.charAt(0)) {
//                        iterator.remove();
//                        break;
//                    }
//                }
//            }

            ArrayList<String> removeWords = new ArrayList<>();
            for (String word : dictionary) {
                for (Integer pos : lPositions.get(0)) {
                    if (word.charAt(pos) != cStr.charAt(0)) {
                        removeWords.add(word);
                        break;
                    }
                }
            }
            dictionary.removeAll(removeWords);

//            for (Iterator<String> iterator = dictionary.iterator(); iterator.hasNext(); ) {
//                String word = iterator.next();
//                if (word.contains(cStr)) {
//                    ArrayList<Integer> pos2 = new ArrayList<>();
//                    for (int a = 0; a < word.length(); a++) {
//                        if (word.charAt(a) == cStr.charAt(0)) {
//                            pos2.add(a);
//                        }
//                    }
//                    if (!pos2.equals(lPositions.get(0))) {
//                        iterator.remove();
//                    }
//                }
//            }

            removeWords.clear();
            for (String word : dictionary) {
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
