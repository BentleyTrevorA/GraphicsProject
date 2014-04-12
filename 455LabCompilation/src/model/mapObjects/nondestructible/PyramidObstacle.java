package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

public class PyramidObstacle extends NonDestructibleObject {
    private static double defaultScale = 25;

    public PyramidObstacle(double x, double y, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, Colors.GREEN, false);
        this.shapeRenderer = shapeRenderer;
    }

    public PyramidObstacle(double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.PYRAMID, defaultScale, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public PyramidObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline, ShapeRenderer shapeRenderer) {
        super(ShapeType.PYRAMID, radius, x, y, z, color, outline);
        this.shapeRenderer = shapeRenderer;
    }
}
