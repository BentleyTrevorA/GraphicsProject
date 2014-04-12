package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class CubeObstacle extends NonDestructibleObject {
    private static double defaultScale = 15;

    public CubeObstacle(double x, double y, double z) {
        super(ShapeType.CUBE, defaultScale, x, y, z, Colors.YELLOW, false);
    }

    public CubeObstacle(double x, double y, double z, Vector3f color) {
        super(ShapeType.CUBE, defaultScale, x, y, z, color, false);
    }

    public CubeObstacle(double scale, double x, double y, double z) {
        super(ShapeType.CUBE, scale, x, y, z, Colors.YELLOW, false);
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color) {
        super(ShapeType.CUBE, scale, x, y, z, color, false);
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(ShapeType.CUBE, scale, x, y, z, color, outline);
    }
}
