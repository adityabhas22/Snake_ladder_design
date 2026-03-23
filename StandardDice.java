import java.util.Random;

public class StandardDice implements Dice {
    private final int faces;
    private final Random random;

    public StandardDice(int faces, Random random) {
        this.faces = faces;
        this.random = random;
    }

    /**
     * Convenience constructor with a default Random instance.
     */
    public StandardDice(int faces) {
        this(faces, new Random());
    }

    @Override
    public int roll() {
        return 1 + random.nextInt(faces);
    }
}
