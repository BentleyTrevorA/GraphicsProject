package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

public class SphereObstacle extends NonDestructibleObject {
    private static double defaultRadius = 5;

    public SphereObstacle(double x, double y, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, Colors.LIGHT_BLUE, false);
        this.shapeRenderer = shapeRenderer;
    }

    public SphereObstacle(double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, y, z, color, outline);
        this.shapeRenderer = shapeRenderer;
    }
}
