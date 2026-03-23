public class StandardModeStrategy implements MakeMoveStrategy {
    @Override
    public MoveOutcome makeMove(Player player, Board board, Dice dice) {
        int roll = dice.roll();
        int to = board.resolveMove(player.getPosition(), roll);
        player.moveTo(to);
        return board.isWinningCell(to) ? MoveOutcome.WON : MoveOutcome.NORMAL;
    }
}
