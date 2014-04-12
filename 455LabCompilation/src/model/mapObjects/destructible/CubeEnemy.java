package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class CubeEnemy extends EnemyEntity {
    private static double cubeScale = 10;

    public CubeEnemy(double x, double z) {
        super(ShapeType.CUBE, cubeScale, x, z);
    }

    public CubeEnemy(double x, double z, int pointValue) {
        super(ShapeType.CUBE, cubeScale, x, z);
        this.pointValue = pointValue;
    }

    public CubeEnemy(double x, double z, Vector3f color) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
    }

    public CubeEnemy(double x, double z, int pointValue, Vector3f color) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        this.pointValue = pointValue;
    }

    public CubeEnemy(double x, double z, Vector3f color, boolean outline) {
        super(ShapeType.CUBE, cubeScale, x, z, color, outline);
    }
}
