import java.util.Collections;
import java.util.Map;

/**
 * Deterministic tests for Board move resolution and win detection.
 * Run with: javac -cp .. BoardTest.java && java -cp .:.. BoardTest
 */
public class BoardTest {

    public static void main(String[] args) {
        BoardTest t = new BoardTest();
        t.testOvershootDoesNotMove();
        t.testSnakeMovesPlayerDown();
        t.testLadderMovesPlayerUp();
        t.testExactWin();
        t.testNoJumpReturnsSameCell();
        t.testApplyJumpSingleJumpOnly();
        System.out.println("All Board tests passed.");
    }

    private void assertEq(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new AssertionError(msg + " expected " + expected + " got " + actual);
        }
    }

    private void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    void testOvershootDoesNotMove() {
        Board board = new Board(10, Collections.emptyMap());
        int from = 98;
        int roll = 5;
        int result = board.resolveMove(from, roll);
        assertEq(98, result, "overshoot: should stay at 98");
    }

    void testSnakeMovesPlayerDown() {
        Map<Integer, Integer> jumps = Map.of(14, 7);
        Board board = new Board(10, jumps);
        int result = board.resolveMove(10, 4);
        assertEq(7, result, "land on 14 (snake head) -> 7");
    }

    void testLadderMovesPlayerUp() {
        Map<Integer, Integer> jumps = Map.of(3, 22);
        Board board = new Board(10, jumps);
        int result = board.resolveMove(1, 2);
        assertEq(22, result, "land on 3 (ladder start) -> 22");
    }

    void testExactWin() {
        Board board = new Board(10, Collections.emptyMap());
        assertTrue(board.isWinningCell(100), "cell 100 is winning on 10x10");
        assertTrue(!board.isWinningCell(99), "cell 99 is not winning");
    }

    void testNoJumpReturnsSameCell() {
        Board board = new Board(10, Map.of(5, 2));
        int result = board.resolveMove(3, 1);
        assertEq(4, result, "land on 4 (no jump) -> 4");
    }

    void testApplyJumpSingleJumpOnly() {
        Map<Integer, Integer> jumps = Map.of(14, 7);
        Board board = new Board(10, jumps);
        int result = board.resolveMove(10, 4);
        assertEq(7, result, "land on 14 -> snake to 7 (single jump only)");
    }
}
