package solver;

import java.util. * ;

/**
 * Guessing strategy for two word Hangman. (task C) You'll need to complete the
 * implementation of this.
 *
 * @author Jeffrey Chan, RMIT 2020
 */
public class TwoWordHangmanGuessSolver extends HangmanSolver {

    ArrayList < HashSet < String >> dictionary = null;
    private Set < Character > guessedLetters = null;
    private int[] wordLen;
    HashMap < Character,
            Integer > map = null;

    /**
     * Constructor.
     *
     * @param dictionary Dictionary of words that the guessed words are drawn
     * from.
     */
    public TwoWordHangmanGuessSolver(Set < String > dictionary) {
        // Implement me!
        this.dictionary = new ArrayList < HashSet < String >> ();
        for (int a = 0; a < 2; a++) {
            HashSet < String > dict = new HashSet < >(dictionary);
            this.dictionary.add(dict);
        }
        guessedLetters = new HashSet < >();
        map = new HashMap < >();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
    } // end of TwoWordHangmanGuessSolver()

    @Override
    public void newGame(int[] wordLengths, int maxIncorrectGuesses) {
        // Implement me!
        System.out.println("New Game Has Started");
        String s = "";
        for (int wordLength: wordLengths) {
            s += wordLength + " ";
        }
        System.out.println("Word(s) Lengths are: " + s);
        System.out.println("Total Number of guesses: " + maxIncorrectGuesses);
        System.out.println("------------------------------------------------");
        wordLen = wordLengths;
    } // end of newGame()

    @Override
    public char makeGuess() {
        // Implement me!
        for (int a = 0; a < 2; a++) {
            for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                String w = i.next();
                if (w.length() != wordLen[a]) {
                    i.remove();
                }
            }
        }
        ArrayList < String > temp = new ArrayList < String > ();
        for (int a = 0; a < 2; a++) {
            for (Iterator < String > i = dictionary.get(a).iterator(); i.hasNext();) {
                String w = i.next();
                if (!temp.contains(w)) {
                    temp.add(w);
                }
            }
        }
        map = new HashMap < >();
        for (int a = 97; a < 123; a++) {
            map.put((char) a, 0);
        }
        map.put('\'', 0);
        for (String w: temp) {
            for (Character c: map.keySet()) {
                if (w.contains(c + "")) {
                    map.replace(c, map.get(c) + 1);
                }
            }
        }
        char letter = '\0';
        int max = 0;
        for (char c: map.keySet()) {
            if ((map.get(c) > max) && !guessedLetters.contains(c)) {
                letter = c;
                max = map.get(c);
            }
        }
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter);
        }
        return letter;
    } // end of makeGuess()

    @Override
    public void guessFeedback(char c, Boolean bGuess, ArrayList < ArrayList < Integer >> lPositions) {
        // Implement me!
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
