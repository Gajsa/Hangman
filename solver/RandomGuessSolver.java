package solver;

import java.util. * ;

/**
 * Random guessing strategy for Hangman. (task A)
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class RandomGuessSolver extends HangmanSolver {

    //Set dictionary to keep the whole given Dictionary
    private Set < String > dictionary = null;

    //Set guessedLetters to hold all the letters which are already guessed
    private Set < Character > guessedLetters = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public RandomGuessSolver(Set < String > dictionary) {
        this.dictionary = dictionary;
        guessedLetters = new HashSet < >();
    } // end of RandomGuessSolver()


    /**
     * Method to start a new game
     *
     * @param wordLengths Length of words we are guessing for.
     * @param maxIncorrectGuesses Maximum number of incorrect guesses we are allowed.
     */
    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        System.out.println("New Game Has Started");

        // calculates and stores length of the word
        String totalWordLength = "";

        for (int wordLength: wordLengths) {
            totalWordLength += wordLength + " ";
        }
        System.out.println("Word Length is: " + totalWordLength);
        System.out.println("Total Number of guesses: " + maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
    } // end of newGame()


    /**
     * Method to guess a random letter
     *
     * @return letter method have guessed
     */
    @Override
    public char makeGuess() {
        //generating a random letter
        Random random = new Random();
        char letter = (char)('a' + random.nextInt(27));
        if (letter == '{') {
            letter = '\'';
        }

        // to check if the letter is already guessed and if yes, get random letter again
        while (guessedLetters.contains(letter)) {
            letter = (char)('a' + random.nextInt(27));
            if (letter == '{') {
                letter = '\'';
            }
        }

        // saving guessed letter
        guessedLetters.add(letter);

        // return the letter to make new guess
        return letter;
    } // end of makeGuess()


    /**
     * Method need not to be implemented for random guess strategy
     *
     * @param c
     * @param bGuess True if the character guessed is in one or more of the words, otherwise false.
     * @param lPositions
     */
    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList < ArrayList < Integer >> lPositions) {
        // No Implementation required.

    } // end of guessFeedback()

} // end of class RandomGuessSolver
