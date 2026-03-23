public class Player {
    private final String id;
    private int position;

    public Player(String id) {
        this.id = id;
        this.position = 0;
    }

    public String getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void moveTo(int cell) {
        this.position = cell;
    }
}
