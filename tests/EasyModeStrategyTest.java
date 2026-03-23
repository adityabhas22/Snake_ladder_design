import java.util.Collections;
import java.util.Map;

public class EasyModeStrategyTest {

    public static void main(String[] args) {
        EasyModeStrategyTest t = new EasyModeStrategyTest();
        t.testNormalMove();
        t.testLenientFinishExactRoll();
        t.testLenientFinishOvershootWins();
        t.testSnakeStillApplies();
        t.testLadderStillApplies();
        System.out.println("All EasyModeStrategy tests passed.");
    }

    private void assertEq(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) {
            throw new AssertionError(msg + " expected " + expected + " got " + actual);
        }
    }

    void testNormalMove() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        EasyModeStrategy strategy = new EasyModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(5));
        assertEq(5, p.getPosition(), "should move to 5");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }

    void testLenientFinishExactRoll() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        p.moveTo(97);
        EasyModeStrategy strategy = new EasyModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(3));
        assertEq(100, p.getPosition(), "exact roll should win");
        assertEq(MoveOutcome.WON, outcome, "should be WON");
    }

    void testLenientFinishOvershootWins() {
        Board board = new Board(10, Collections.emptyMap());
        Player p = new Player("P1");
        p.moveTo(97);
        EasyModeStrategy strategy = new EasyModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(6));
        assertEq(100, p.getPosition(), "overshoot should still win in easy mode");
        assertEq(MoveOutcome.WON, outcome, "should be WON");
    }

    void testSnakeStillApplies() {
        Board board = new Board(10, Map.of(5, 2));
        Player p = new Player("P1");
        EasyModeStrategy strategy = new EasyModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(5));
        assertEq(2, p.getPosition(), "snake should still apply");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }

    void testLadderStillApplies() {
        Board board = new Board(10, Map.of(3, 22));
        Player p = new Player("P1");
        EasyModeStrategy strategy = new EasyModeStrategy();
        MoveOutcome outcome = strategy.makeMove(p, board, new FixedDice(3));
        assertEq(22, p.getPosition(), "ladder should still apply");
        assertEq(MoveOutcome.NORMAL, outcome, "should be NORMAL");
    }
}
