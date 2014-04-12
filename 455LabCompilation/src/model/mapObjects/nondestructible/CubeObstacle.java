package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

public class CubeObstacle extends NonDestructibleObject {
    private static double defaultScale = 15;

    public CubeObstacle(double x, double y, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, defaultScale, x, y, z, Colors.YELLOW, false);
        this.shapeRenderer = shapeRenderer;
    }

    public CubeObstacle(double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, defaultScale, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public CubeObstacle(double scale, double x, double y, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, scale, x, y, z, Colors.YELLOW, false);
        this.shapeRenderer = shapeRenderer;
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, scale, x, y, z, color, false);
        this.shapeRenderer = shapeRenderer;
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color, boolean outline, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, scale, x, y, z, color, outline);
        this.shapeRenderer = shapeRenderer;
    }
}
