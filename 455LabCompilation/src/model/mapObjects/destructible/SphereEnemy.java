package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class SphereEnemy extends EnemyEntity {
    private static double radius = 6;
    private static int baseValue = 25;
    private static int baseHp = 3;

    public SphereEnemy(double x, double z) {
        super(ShapeType.SPHERE, radius, x, radius, z);
        pointValue = baseValue;
        hp = baseHp;
    }

    public SphereEnemy(double x, double z, int pointValue) {
        super(ShapeType.SPHERE, radius, x, radius, z);
        this.pointValue = pointValue;
        hp = baseHp;
    }

    public SphereEnemy(double x, double z, Vector3f color) {
        super(ShapeType.SPHERE, radius, x, radius, z, color);
        pointValue = baseValue;
        hp = baseHp;
    }

    public SphereEnemy(double x, double z, int pointValue, Vector3f color) {
        super(ShapeType.SPHERE, radius, x, radius, z, color);
        this.pointValue = pointValue;
        hp = baseHp;
    }

    public SphereEnemy(double x, double z, Vector3f color, boolean outline) {
        super(ShapeType.SPHERE, radius, x, radius, z, color, outline);
        pointValue = baseValue;
        hp = baseHp;
    }
}
