import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int size;
    private final int lastCell;
    private final Map<Integer, Integer> jumps;

    public Board(int size, Map<Integer, Integer> jumps) {
        this.size = size;
        this.lastCell = size * size;
        this.jumps = new HashMap<>(jumps);
    }

    /**
     * Resolves a move: applies overshoot rule, then any snake/ladder at landing cell.
     * Returns the final cell the player should be on.
     */
    public int resolveMove(int fromCell, int roll) {
        int tentative = fromCell + roll;
        if (tentative > lastCell) {
            return fromCell;
        }
        return applyJump(tentative);
    }
    
    /**
     * If the cell has a snake or ladder, returns the destination; otherwise returns the cell.
     */
    public int applyJump(int cell) {
        return jumps.getOrDefault(cell, cell);
    }

    public boolean isWinningCell(int cell) {
        return cell == lastCell;
    }

    public int getLastCell() {
        return lastCell;
    }

    public int getSize() {
        return size;
    }
}
