import java.util.*;

/**
 * Builds a board with n snakes and n ladders using pluggable JumpGenerators.
 * No large candidate lists; generators use O(1) space per attempt.
 */
public class BoardBuilder {

    /**
     * Creates a board of size n x n with snakeCount snakes and ladderCount ladders.
     * Uses the given Random for reproducibility in tests.
     */
    public static Board randomBoard(int n, int snakeCount, int ladderCount, Random random) {
        int lastCell = n * n;
        if (lastCell < 2) {
            throw new IllegalArgumentException("Board must have at least 2 cells");
        }

        Set<Integer> usedStarts = new HashSet<>();
        Map<Integer, Integer> jumps = new HashMap<>();
        JumpGenerator snakeGenerator = RandomJumpGenerator.snake();
        JumpGenerator ladderGenerator = RandomJumpGenerator.ladder();

        for (int i = 0; i < snakeCount; i++) {
            snakeGenerator.placeOne(lastCell, usedStarts, jumps, random);
        }
        for (int i = 0; i < ladderCount; i++) {
            ladderGenerator.placeOne(lastCell, usedStarts, jumps, random);
        }

        return new Board(n, jumps);
    }

    /**
     * Convenience: n snakes and n ladders on n x n board.
     */
    public static Board randomBoard(int n, Random random) {
        return randomBoard(n, n, n, random);
    }
}
