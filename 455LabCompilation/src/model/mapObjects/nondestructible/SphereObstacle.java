package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class SphereObstacle extends NonDestructibleObject {
    private static double defaultRadius = 5;

    public SphereObstacle(double x, double y, double z) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, Colors.LIGHT_BLUE, false);
    }

    public SphereObstacle(double x, double y, double z, Vector3f color) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, color, false);
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color) {
        super(ShapeType.SPHERE, radius, x, y, z, color, false);
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline) {
        super(ShapeType.SPHERE, radius, x, y, z, color, outline);
    }
}
