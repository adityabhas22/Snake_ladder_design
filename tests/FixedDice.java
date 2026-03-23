import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dice that returns a fixed sequence of values. For deterministic tests.
 */
public class FixedDice implements Dice {
    private final Iterator<Integer> values;

    public FixedDice(int... values) {
        List<Integer> list = new ArrayList<>();
        for (int v : values) list.add(v);
        this.values = list.iterator();
    }

    @Override
    public int roll() {
        if (!values.hasNext()) {
            throw new IllegalStateException("No more dice values");
        }
        return values.next();
    }
}
