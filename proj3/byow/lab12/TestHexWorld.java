package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestHexWorld {

    @Test
    public void testHexagonAnchors() {
        List<Position> expected = new ArrayList<>();
        expected.add(new Position(0, 6));
        expected.add(new Position(0, 12));
        expected.add(new Position(0, 18));
        expected.add(new Position(5, 3));
        expected.add(new Position(5, 9));
        expected.add(new Position(5, 15));
        expected.add(new Position(5, 21));
        expected.add(new Position(10, 0));
        expected.add(new Position(10, 6));
        expected.add(new Position(10, 12));
        expected.add(new Position(10, 18));
        expected.add(new Position(10, 24));
        expected.add(new Position(15, 3));
        expected.add(new Position(15, 9));
        expected.add(new Position(15, 15));
        expected.add(new Position(15, 21));
        expected.add(new Position(20, 6));
        expected.add(new Position(20, 12));
        expected.add(new Position(20, 18));

        List<Position> actual = HexWorld.hexagonAnchors(3);

        assertEquals(expected, actual);
    }
}
