package model.mapObjects.destructible;

import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class SphereEnemy extends EnemyEntity {
    private static double radius = 5;

    public SphereEnemy(double x, double z) {
        super(MapObjectType.CUBE, radius, x, z);
    }

    public SphereEnemy(double x, double z, Vector3f color) {
        super(MapObjectType.SPHERE, radius, x, z, color);
    }

    public SphereEnemy(double x, double z, Vector3f color, boolean outline) {
        super(MapObjectType.CUBE, radius, x, z, color, outline);
    }
}
