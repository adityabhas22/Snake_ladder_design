import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size (n for n×n board): ");
        int n = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();

        System.out.print("Enter difficulty (easy/hard): ");
        String difficulty = scanner.next().trim().toLowerCase();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("P" + i));
        }

        Board board = BoardBuilder.randomBoard(n, new Random());
        Dice dice = new StandardDice(6);

        MakeMoveStrategy strategy;
        if (difficulty.equals("easy")) {
            strategy = new EasyModeStrategy();
        } else {
            strategy = new HardModeStrategy();
        }

        Game game = new Game(board, players, dice, strategy);
        game.play();
    }
}
