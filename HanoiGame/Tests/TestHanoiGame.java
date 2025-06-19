package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import types.HanoiGame;

public class TestHanoiGame {

    private static final String EOL = System.lineSeparator();

    @Test
    void testGameStartNonTerminated() {
        HanoiGame game = new HanoiGame(3, 3);
        assertFalse(game.isTerminated());
    }

    @Test
    void testWinningGame() {
        HanoiGame game = new HanoiGame(3, 3);

        game.play(0, 2);
        game.play(0, 1);
        game.play(2, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 2);
        game.play(0, 2);

        assertTrue(game.isTerminated());
    }

    @Test
    void testToStringOutput() {
        HanoiGame game = new HanoiGame(3, 3);

        game.play(0, 1);
        game.play(0, 2);
        String output = game.toString();
        String expected = "Moves: 2" + EOL +
                "  |    |    |  " + EOL +
                "  |    |    |  " + EOL +
                "  C    A    B  " + EOL +
                "---------------";

        assertEquals(expected, output);
    }

    @Test
    void testWinningGameToStringMoves() {
        HanoiGame game = new HanoiGame(3, 3);

        String output = game.toString();
        String expected = "Moves: 0" + EOL +
                "  A    |    |  " + EOL +
                "  B    |    |  " + EOL +
                "  C    |    |  " + EOL +
                "---------------";

        assertEquals(expected, output);
    }

    @Test
    void testWinningGameToString() {
        HanoiGame game = new HanoiGame(3, 3);

        game.play(0, 2);
        game.play(0, 1);
        game.play(2, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 2);
        game.play(0, 2);
        String output = game.toString();
        String expected = "Moves: 7" + EOL +
                "  |    |    A  " + EOL +
                "  |    |    B  " + EOL +
                "  |    |    C  " + EOL +
                "---------------";
        assertEquals(expected, output);
    }

}
