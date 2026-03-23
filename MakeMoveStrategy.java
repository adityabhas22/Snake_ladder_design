public interface MakeMoveStrategy {
    MoveOutcome makeMove(Player player, Board board, Dice dice);
}
