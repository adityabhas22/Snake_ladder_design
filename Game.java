import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {
    private final Board board;
    private final Dice dice;
    private final MakeMoveStrategy strategy;
    private final List<Player> allPlayers;
    private Queue<Player> activePlayers;
    private final List<Player> winners;

    public Game(Board board, List<Player> players, Dice dice) {
        this(board, players, dice, new StandardModeStrategy());
    }

    public Game(Board board, List<Player> players, Dice dice, MakeMoveStrategy strategy) {
        this.board = board;
        this.dice = dice;
        this.strategy = strategy;
        this.allPlayers = new ArrayList<>(players);
        this.activePlayers = new LinkedList<>(players);
        this.winners = new ArrayList<>();
    }

    public void play() {
        while (activePlayers.size() >= 2) {
            Player current = activePlayers.poll();
            MoveOutcome outcome = makeTurn(current);
            switch (outcome) {
                case WON:
                    winners.add(current);
                    break;
                case ELIMINATED:
                    break;
                case NORMAL:
                    activePlayers.add(current);
                    break;
            }
        }
        endGame();
    }

    public MoveOutcome makeTurn(Player player) {
        return strategy.makeMove(player, board, dice);
    }

    public void endGame() {
        System.out.println("Game ended. Winners (in order): " + winners);
    }

    //Additional
    public List<Player> getWinners() {
        return new ArrayList<>(winners);
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(allPlayers);
    }
}
