package pl.mperor.lab.java.design.pattern.structural.flyweight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.design.pattern.structural.flyweight.terrain.Terrain;

import java.util.Arrays;


public class GameWorldTest {

    @Test
    public void testCreatedGameWorldContainsOnlyTerrainTilesWithTheSameTexture() {
        GameWorld gw = new GameWorld(20, 20);
        System.out.println(gw);
        Terrain firstTile = gw.getTiles(0, 0);

        Assertions.assertTrue(Arrays.stream(Terrain.values())
                        .map(Terrain::getTerrainTexture)
                        .anyMatch(texture -> texture == firstTile.getTerrainTexture()),
                "The same instance is used for the terrain texture!");
    }

}