package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import javafx.geometry.Pos;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2233;
    private static final Random RANDOM = new Random(SEED);

    static TETile[][] generateHexagon(int s, TETile t) {
        if (s < 1) {
            throw new IllegalArgumentException(String.format("hexagon size < 1, s=%d", s));
        }
        int w = 3*s - 2;
        int h = 2*s;

        TETile[][] piece= new TETile[w][h];

        for (int x = 0; x < w; ++x) {
            Arrays.fill(piece[x], t);
        }

        for (int x = 0; x < s; ++x) {
            for (int y = 0; y < s - x - 1; ++y) {
                piece[x][y] = Tileset.NOTHING;
                piece[w - x - 1][y] = Tileset.NOTHING;
            }
            for (int y = h - s + x + 1; y < h; ++y) {
                piece[x][y] = Tileset.NOTHING;
                piece[w - x - 1][y] = Tileset.NOTHING;
            }
        }

        return piece;
    }

    static List<Position> hexagonAnchors(int radius) {
        List<Position> anchors = new ArrayList<>();
        int diameter = 2*radius - 1;
        int columnSpacing = 2*radius - 1;
        for (int i = 1; i <= diameter; ++i) {
            int x = (i - 1)*columnSpacing;
            int y = radius*Math.abs(radius - i);
            for (int j = 0; j < diameter - Math.abs(radius - i); ++j) {
                anchors.add(new Position(x, y));
                y += 2*radius;
            }
        }
        return anchors;
    }

    static void tessellate(TETile[][] world, TETile[][] tiles, Position origin) {

        int worldWidth = world.length;
        int worldHeight = world[0].length;

        int tilesWidth = tiles.length;
        int tilesHeight = tiles[0].length;

        for (int x = origin.x; x < worldWidth && x < origin.x + tilesWidth; ++x) {
            for (int y = origin.y; y < worldHeight && y < origin.y + tilesHeight; ++y) {
                TETile tile = tiles[x - origin.x][y - origin.y];
                if (!tile.description().equals("nothing"))
                    world[x][y] = tile;
            }
        }
    }

    static void generateWorld(TETile[][] world, int s, int radius) {
        for (Position position : hexagonAnchors(radius)) {
            tessellate(world, generateHexagon(s, randomTile()), position);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {

        int radius = 3;
        int s = 4;

        int diameter = 2*radius - 1;
        int w = diameter * diameter + s;
        int h = diameter * 2 * radius;
        TETile[][] world = new TETile[w][h];

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        TERenderer ter = new TERenderer();
        ter.initialize(w, h);


        generateWorld(world, s, radius);

        ter.renderFrame(world);
    }
}
