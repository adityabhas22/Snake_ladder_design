import java.util.*;

/**
 * Deterministic game test: fixed dice sequence, assert winner and positions.
 * Run from snake_ladder_design: javac tests/GameTest.java tests/FixedDice.java && java -cp .:tests GameTest
 */
public class GameTest {

    public static void main(String[] args) {
        GameTest t = new GameTest();
        t.testRoundRobinWithFixedDice();
        t.testWinnerRemovedFromPlay();
        System.out.println("All Game tests passed.");
    }

    private void assertEq(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new AssertionError(msg + " expected " + expected + " got " + actual);
        }
    }

    private void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    void testRoundRobinWithFixedDice() {
        Map<Integer, Integer> jumps = new HashMap<>();
        jumps.put(10, 2);
        Board board = new Board(10, jumps);
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        FixedDice dice = new FixedDice(3, 4, 3);
        Game game = new Game(board, List.of(p1, p2), dice);
        game.makeTurn(p1);
        assertEq(3, p1.getPosition(), "P1 rolls 3 -> 3");
        game.makeTurn(p2);
        assertEq(4, p2.getPosition(), "P2 rolls 4 -> 4");
        game.makeTurn(p1);
        assertEq(6, p1.getPosition(), "P1 3+3=6");
    }

    void testWinnerRemovedFromPlay() {
        Map<Integer, Integer> jumps = Collections.emptyMap();
        Board board = new Board(10, jumps);
        Player p1 = new Player("A");
        Player p2 = new Player("B");
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 20; i++) rolls.add(10);
        FixedDice dice = new FixedDice(rolls.stream().mapToInt(x -> x).toArray());
        Game game = new Game(board, List.of(p1, p2), dice);
        game.play();
        List<Player> winners = game.getWinners();
        assertTrue(winners.size() >= 1, "at least one winner when dice always 10");
        assertTrue(board.isWinningCell(winners.get(0).getPosition()), "winner at last cell");
    }
}
