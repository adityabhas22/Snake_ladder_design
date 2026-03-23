import java.util.Collections;
import java.util.Map;

public class HardModeStrategyTest {

    public static void main(String[] args) {
        HardModeStrategyTest t = new HardModeStrategyTest();
        t.testNormalMove();
        t.testExactWin();
        t.testOvershootStays();
        t.testSingleSixOk();
        t.testThreeConsecutiveSixesEliminated();
        t.testNonSixResetsCounter();
        t.testCounterPerPlayer();
        t.testEliminatedPlayerNotMoved();
        System.out.println("All HardModeStrategy tests passed.");
    }

    private void assertEq(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) {
            throw new AssertionError(msg + " expected " + expected + " got " + actual);
        }
    }

    void testNormalMove() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        HardModeStrategy strategy = new HardModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(4));
        assertEq(4, p.getPosition(), "should move to 4");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }

    void testExactWin() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        p.moveTo(97);
        HardModeStrategy strategy = new HardModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(3));
        assertEq(100, p.getPosition(), "exact roll should win");
        assertEq(MoveOutcome.WON, outcome, "should be WON");
    }

    void testOvershootStays() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        p.moveTo(98);
        HardModeStrategy strategy = new HardModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(5));
        assertEq(98, p.getPosition(), "overshoot should stay");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }

    void testSingleSixOk() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        HardModeStrategy strategy = new HardModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(6, p.getPosition(), "single 6 should move normally");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }

    void testThreeConsecutiveSixesEliminated() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        HardModeStrategy strategy = new HardModeStrategy();

        // Roll 1: 6 -> moves to 6
        MoveOutcome o1 = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(MoveOutcome.NORMAL, o1, "first 6 should be NORMAL");
        assertEq(6, p.getPosition(), "should be at 6");

        // Roll 2: 6 -> moves to 12
        MoveOutcome o2 = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(MoveOutcome.NORMAL, o2, "second 6 should be NORMAL");
        assertEq(12, p.getPosition(), "should be at 12");

        // Roll 3: 6 -> ELIMINATED, not moved
        MoveOutcome o3 = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(MoveOutcome.ELIMINATED, o3, "third consecutive 6 should ELIMINATE");
        assertEq(12, p.getPosition(), "should stay at 12 after elimination");
    }

    void testNonSixResetsCounter() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        HardModeStrategy strategy = new HardModeStrategy();

        // Two 6s then a 3 (resets counter)
        strategy.makeMove(p, board, new FixedDice(6));  // pos 6
        strategy.makeMove(p, board, new FixedDice(6));  // pos 12
        strategy.makeMove(p, board, new FixedDice(3));  // pos 15, resets counter

        // Now two more 6s should be fine
        MoveOutcome o4 = strategy.makeMove(p, board, new FixedDice(6));  // pos 21
        assertEq(MoveOutcome.NORMAL, o4, "first 6 after reset should be NORMAL");

        MoveOutcome o5 = strategy.makeMove(p, board, new FixedDice(6));  // pos 27
        assertEq(MoveOutcome.NORMAL, o5, "second 6 after reset should be NORMAL");

        // Third 6 -> eliminated
        MoveOutcome o6 = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(MoveOutcome.ELIMINATED, o6, "third 6 after reset should ELIMINATE");
    }

    void testCounterPerPlayer() {
        Board board = new Board(10, Collections.emptyMap());
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        HardModeStrategy strategy = new HardModeStrategy();

        // P1 rolls 6, P2 rolls 6, P1 rolls 6, P2 rolls 6, P1 rolls 6
        strategy.makeMove(p1, board, new FixedDice(6));  // P1: 1 six
        strategy.makeMove(p2, board, new FixedDice(6));  // P2: 1 six
        strategy.makeMove(p1, board, new FixedDice(6));  // P1: 2 sixes
        strategy.makeMove(p2, board, new FixedDice(6));  // P2: 2 sixes

        MoveOutcome p1o = strategy.makeMove(p1, board, new FixedDice(6));  // P1: 3 sixes
        assertEq(MoveOutcome.ELIMINATED, p1o, "P1 should be eliminated after 3 sixes");

        // P2 only has 2 consecutive sixes, next 6 should eliminate
        MoveOutcome p2o = strategy.makeMove(p2, board, new FixedDice(6));
        assertEq(MoveOutcome.ELIMINATED, p2o, "P2 should be eliminated after 3 sixes");
    }

    void testEliminatedPlayerNotMoved() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        p.moveTo(10);
        HardModeStrategy strategy = new HardModeStrategy();

        strategy.makeMove(p, board, new FixedDice(6));  // pos 16
        strategy.makeMove(p, board, new FixedDice(6));  // pos 22
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(6)); // eliminated

        assertEq(MoveOutcome.ELIMINATED, outcome, "should be ELIMINATED");
        assertEq(22, p.getPosition(), "position should not change on elimination");
    }
}
