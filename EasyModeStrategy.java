public class EasyModeStrategy implements MakeMoveStrategy {
    @Override
    public MoveOutcome makeMove(Player player, Board board, Dice dice) {
        int roll = dice.roll();
        int from = player.getPosition();
        int tentative = from + roll;

        if (tentative >= board.getLastCell()) {
            player.moveTo(board.getLastCell());
            return MoveOutcome.WON;
        }

        int destination = board.applyJump(tentative);
        player.moveTo(destination);
        return MoveOutcome.NORMAL;
    }
}
