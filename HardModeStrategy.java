import java.util.HashMap;
import java.util.Map;

public class HardModeStrategy implements MakeMoveStrategy {
    private static final int ELIMINATION_ROLL = 6;
    private static final int MAX_CONSECUTIVE = 3;

    private final Map<String, Integer> consecutiveCount = new HashMap<>();

    @Override
    public MoveOutcome makeMove(Player player, Board board, Dice dice) {
        int roll = dice.roll();
        String playerId = player.getId();

        if (roll == ELIMINATION_ROLL) {
            int count = consecutiveCount.getOrDefault(playerId, 0) + 1;
            if (count >= MAX_CONSECUTIVE) {
                consecutiveCount.remove(playerId);
                return MoveOutcome.ELIMINATED;
            }
            consecutiveCount.put(playerId, count);
        } else {
            consecutiveCount.remove(playerId);
        }

        // Exact finish: overshoot means stay
        int from = player.getPosition();
        int tentative = from + roll;
        if (tentative > board.getLastCell()) {
            return MoveOutcome.NORMAL;
        }

        int destination = board.applyJump(tentative);
        player.moveTo(destination);

        if (board.isWinningCell(destination)) {
            consecutiveCount.remove(playerId);
            return MoveOutcome.WON;
        }

        return MoveOutcome.NORMAL;
    }
}
