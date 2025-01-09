package pl.mperor.lab.java.design.pattern.structural.flyweight;

import pl.mperor.lab.java.design.pattern.structural.flyweight.terrain.Terrain;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GameWorld {

    private final int width;
    private final int height;
    private final Terrain[][] tiles;

    public GameWorld(int width, int height) {
        assert width >= 0;
        assert height >= 0;

        this.width = width;
        this.height = height;
        this.tiles = generateTiles();
    }

    private Terrain[][] generateTiles() {
        Terrain[][] tiles = new Terrain[width][height];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Terrain[] terrains = Terrain.values();

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = terrains[random.nextInt(terrains.length)];
            }
        }

        return tiles;
    }

    public Terrain getTiles(int x, int y) {
        assert x >= 0 && x < tiles.length ;
        assert y >= 0 && y < tiles[0].length;

        return tiles[x][y];
    }

    @Override
    public String toString() {
        return Arrays.stream(tiles)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
