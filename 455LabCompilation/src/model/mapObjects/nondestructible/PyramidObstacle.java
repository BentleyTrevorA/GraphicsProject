package model.mapObjects.nondestructible;

import model.Colors;
import model.handlers.TextureHandler;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class PyramidObstacle extends NonDestructibleObject {
    private static double defaultScale = 25;

    public PyramidObstacle(double x, double y, double z, TextureHandler textureHandler) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, Colors.GREEN, false);
        this.textureHandler = textureHandler;
    }

    public PyramidObstacle(double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline, TextureHandler textureHandler) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, outline);
        this.textureHandler = textureHandler;
    }
}
