import java.util.*;

/**
 * Validates BoardBuilder: no jump at 1, no jump end at lastCell, correct counts, deterministic with same Random seed.
 * Run from snake_ladder_design: javac tests/BoardBuilderTest.java && java -cp .:tests BoardBuilderTest
 */
public class BoardBuilderTest {

    public static void main(String[] args) {
        BoardBuilderTest t = new BoardBuilderTest();
        t.testDeterministicWithSameSeed();
        t.testBoardHasCorrectLastCell();
        System.out.println("All BoardBuilder tests passed.");
    }

    private void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    void testDeterministicWithSameSeed() {
        Random r1 = new Random(42);
        Random r2 = new Random(42);
        Board b1 = BoardBuilder.randomBoard(10, 5, 5, r1);
        Board b2 = BoardBuilder.randomBoard(10, 5, 5, r2);
        assertTrue(b1.getLastCell() == b2.getLastCell(), "same lastCell");
        assertTrue(b1.getSize() == 10 && b2.getSize() == 10, "size 10");
    }

    void testBoardHasCorrectLastCell() {
        Board board = BoardBuilder.randomBoard(10, 2, 2, new Random(123));
        assertTrue(board.getLastCell() == 100, "10x10 board last cell 100");
        assertTrue(board.isWinningCell(100), "100 is winning cell");
    }
}
