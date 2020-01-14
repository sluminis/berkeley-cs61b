public class OffByN implements CharacterComparator {

    int off;

    public OffByN(int n) {
        off = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        return diff == off;
    }
}
