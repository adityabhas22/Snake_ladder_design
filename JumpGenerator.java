import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Places one jump (snake or ladder) onto the board.
 * Same contract for both; implementations differ only in valid start/end ranges.
 */
public interface JumpGenerator {

    /**
     * Attempts to place exactly one jump. Updates {@code jumps} and {@code usedStarts} if successful.
     *
     * @param lastCell   last cell number (n * n)
     * @param usedStarts cells that are already start of a jump (not modified by this method if false returned)
     * @param jumps      map to add start -> end (caller's map is modified on success)
     * @param random     source of randomness
     * @return true if a jump was placed, false if no valid placement found (e.g. board too full)
     */
    boolean placeOne(int lastCell, Set<Integer> usedStarts, Map<Integer, Integer> jumps, Random random);
}
