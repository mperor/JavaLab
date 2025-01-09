package pl.mperor.lab.java.design.pattern.structural.flyweight.terrain;

public enum Terrain {

    WATER("W", new WaterTexture(), 1),
    SAND("S", new SandTexture(), 2),
    GRASS("G", new GrassTexture(), 3);

    private final String name;
    private final TerrainTexture terrainTexture;
    private final int movementCost;

    Terrain(String name, TerrainTexture terrainTexture, int movementCost) {
        this.name = name;
        this.terrainTexture = terrainTexture;
        this.movementCost = movementCost;
    }

    public TerrainTexture getTerrainTexture() {
        return terrainTexture;
    }

    public int getMovementCost() {
        return movementCost;
    }

    @Override
    public String toString() {
        return name;
    }
}
