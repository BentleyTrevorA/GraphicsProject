package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class PyramidObstacle extends NonDestructibleObject {
    private static double defaultScale = 25;

    public PyramidObstacle(double x, double y, double z) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, Colors.GREEN, false);
    }

    public PyramidObstacle(double x, double y, double z, Vector3f color) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, color, false);
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, false);
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, outline);
    }
}
