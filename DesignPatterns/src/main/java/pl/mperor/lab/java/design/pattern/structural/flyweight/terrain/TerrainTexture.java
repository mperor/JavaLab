package pl.mperor.lab.java.design.pattern.structural.flyweight.terrain;

public sealed interface TerrainTexture permits GrassTexture, SandTexture, WaterTexture {
}
