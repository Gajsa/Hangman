package solver;

import java.util.*;

/**
 * Random guessing strategy for Hangman. (task A) You'll need to complete the
 * implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver {

    private Set<String> dictionary = null;
    private Set<Character> guessedLetters = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public RandomGuessSolver(Set<String> dictionary) {
        // Implement me!
        this.dictionary = dictionary;
        guessedLetters = new HashSet<>();
    } // end of RandomGuessSolver()

    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        // Implement me!
        System.out.println("New Game Has Started");
        String  s = "";
        for (int wordLength : wordLengths) {
            s += wordLength+" ";
        }
        System.out.println("Word(s) Lengths are: "+s);
        System.out.println("Total Number of guesses: "+maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
    } // end of newGame()

    @Override
    public char makeGuess() {
        // Implement me!
        //generating a random letter
        Random r = new Random();
        char letter = (char) ('a' + r.nextInt(27));
        if(letter == '{'){
            letter = '\'';
        }
        while(guessedLetters.contains(letter)){
            letter = (char) ('a' + r.nextInt(27));
            if(letter == '{'){
                letter = '\'';
                
            }
        }
        guessedLetters.add(letter);
        // TODO: This is a placeholder, replace with appropriate return value.
        return letter;
    } // end of makeGuess()

    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList<ArrayList<Integer>> lPositions) {
        // Implement me!
        /*if(bGuess){
            System.out.println("Letter "+c+" is found at the following positions: ");
            for (int a = 0; a < lPositions.size(); a++) {
                System.out.println(lPositions.get(a).toString());
            }
        }
        else{
            System.out.println("Letter "+c+" is not found!");
        }*/
    } // end of guessFeedback()

} // end of class RandomGuessSolver
