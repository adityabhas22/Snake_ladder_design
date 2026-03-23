import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Places one jump (snake or ladder) onto the board.
 * Direction controlled by constructor: goesDown=true for snakes, false for ladders.
 */
public class RandomJumpGenerator implements JumpGenerator {

    private static final int MAX_ATTEMPTS = 500;
    private final boolean goesDown;

    public RandomJumpGenerator(boolean goesDown) {
        this.goesDown = goesDown;
    }

    public static RandomJumpGenerator snake()  { return new RandomJumpGenerator(true);  }
    public static RandomJumpGenerator ladder() { return new RandomJumpGenerator(false); }

    @Override
    public boolean placeOne(int lastCell, Set<Integer> usedStarts,
                            Map<Integer, Integer> jumps, Random random) {
        int minBoard = goesDown ? 3 : 2;
        if (lastCell < minBoard) return false;

        int startMin = goesDown ? 2 : 1;
        int startMax = lastCell - 1;
        int startRange = startMax - startMin + 1;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int start = startMin + random.nextInt(startRange);
            if (usedStarts.contains(start)) continue;

            int end;
            if (goesDown) {
                int tailMax = start - 1;
                if (tailMax < 1) continue;
                end = 1 + random.nextInt(tailMax);
            } else {
                int endMin = start + 1;
                if (endMin > lastCell) continue;
                end = endMin + random.nextInt(lastCell - endMin + 1);
            }

            if (usedStarts.contains(end)) continue;
            jumps.put(start, end);
            usedStarts.add(start);
            return true;
        }
        return false;
    }
}
