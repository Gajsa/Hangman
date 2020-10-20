package solver;

import java.util.*;

/**
 * Guessing strategy for Wheel of Fortune Hangman variant. (task D) You'll need
 * to complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class WheelOfFortuneGuessSolver extends HangmanSolver {

    private Set<String> dictionary = null;
    private Set<Character> guessedLetters = null;
    private int wordLen = 0;
    HashMap<Character, Integer> map = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public WheelOfFortuneGuessSolver(Set<String> dictionary) {
        // Implement me!
        this.dictionary = dictionary;
        guessedLetters = new HashSet<>();
        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char)a, 0);
        }
        map.put('\'', 0);
    } // end of WheelOfFortuneGuessSolver()

    @Override
    public void newGame(int[] wordLengths, int maxTries) {
        // Implement me!
        System.out.println("New Game Has Started");
        String  s = "";
        for (int wordLength : wordLengths) {
            s += wordLength+" ";
        }
        System.out.println("Word(s) Lengths are: "+s);
        System.out.println("Total Number of guesses: "+maxTries);
        System.out.println("------------------------------------------------");
        wordLen = wordLengths[0];
    } // end of setWordLength()

    @Override
    public char makeGuess() {
        // Implement me!
        ArrayList<String> words = new ArrayList<>();
        for (String w: dictionary) {
            if(w.length() == wordLen){
                words.add(w);
            }
        }


        for (String w: words) {
            for (Character c: map.keySet()) {
                if(w.contains(c+"")){
                    map.replace(c, map.get(c)+1);
                }
            }
        }
        int max = Collections.max(map.values());
        char letter = '\0';
        for(Character c: map.keySet()){
            if(map.get(c) == max){
                letter = c;
            }
        }
        map.remove(letter);
        if(!guessedLetters.contains(letter)){
            guessedLetters.add(letter);
        }
        // TODO: This is a placeholder, replace with appropriate return value.
        return letter;
    } // end of makeGuess()

    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer>> lPositions) {
        // Implement me!
    } // end of guessFeedback()

} // end of class WheelOfFortuneGuessSolver
