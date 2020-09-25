package byow.lab12;

public class Position {
    public int x;
    public int y;

    Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Position.class) {
            return false;
        }
        Position other = (Position) obj;
        return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
        return x*100007 + y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
