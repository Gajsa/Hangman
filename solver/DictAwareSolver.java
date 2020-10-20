package solver;

import java.util.*;
import java.lang.System;
import solver.HangmanSolver;

/**
 * Dictionary aware guessing strategy for Hangman. (task B) You'll need to
 * complete the implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class DictAwareSolver extends HangmanSolver {

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
    public DictAwareSolver(Set<String> dictionary) {
        // Implement me!
        this.dictionary = dictionary;
        guessedLetters = new HashSet<>();
        map = new HashMap<>();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
    } // end of DictAwareSolver()

    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        // Implement me!
        System.out.println("New Game Has Started");
        String s = "";
        for (int wordLength : wordLengths) {
            s += wordLength + " ";
        }
        System.out.println("Word(s) Lengths are: " + s);
        System.out.println("Total Number of guesses: " + maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
        wordLen = wordLengths[0];
    } // end of newGame()

    @Override
    public char makeGuess() {
        // Implement me!
        for (Iterator<String> i = dictionary.iterator(); i.hasNext();) {
            String w = i.next();
            if(w.length() != wordLen){
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
            if((map.get(c) > max) && !guessedLetters.contains(c)){
                letter = c;
                max = map.get(c);
            }
        }
        if(!guessedLetters.contains(letter)){
            guessedLetters.add(letter);
        }
        return letter;
    } // end of makeGuess()

    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList< ArrayList<Integer>> lPositions) {
      //  Implement me!
        if(bGuess){
            ArrayList<Integer> positions = lPositions.get(0);
            for (Iterator<String> i = dictionary.iterator(); i.hasNext();){
                String w = i.next();
                for (Integer pos : positions) {
                    if(w.charAt(pos) != c){
                        i.remove();
                        break;
                    }
                }
            }
            for (Iterator<String> i = dictionary.iterator(); i.hasNext();){
                String w = i.next();
                if(w.contains(c+"")){
                    ArrayList<Integer> pos2 = new ArrayList<>();
                    for(int a = 0; a < w.length(); a++) {
                        if(w.charAt(a) == c){
                            pos2.add(a);
                        }
                    }
                    if(!pos2.equals(positions)){
                        i.remove();
                    }
                }
            }
        }
        else{
            for (Iterator<String> i = dictionary.iterator(); i.hasNext();){
                String w = i.next();
                if(w.contains(c+"")){
                    i.remove();
                }
            }
        }
    } // end of guessFeedback()

} // end of class DictAwareSolver

