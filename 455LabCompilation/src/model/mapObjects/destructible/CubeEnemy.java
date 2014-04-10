package model.mapObjects.destructible;

import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class CubeEnemy extends EnemyEntity {
    private static double cubeScale = 10;

    public CubeEnemy(double x, double z) {
        super(MapObjectType.CUBE, cubeScale, x, z);
    }

    public CubeEnemy(double x, double z, Vector3f color) {
        super(MapObjectType.CUBE, cubeScale, x, z, color);
    }

    public CubeEnemy(double x, double z, Vector3f color, boolean outline) {
        super(MapObjectType.CUBE, cubeScale, x, z, color, outline);
    }
}
