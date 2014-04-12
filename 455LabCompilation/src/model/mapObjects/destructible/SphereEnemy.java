package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

public class SphereEnemy extends EnemyEntity {
    private static double radius = 6;
    private static int baseValue = 25;
    private static int baseHp = 3;

    public SphereEnemy(double x, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, radius, z);
        pointValue = baseValue;
        hp = baseHp;
        this.shapeRenderer = shapeRenderer;
    }

    public SphereEnemy(double x, double z, int pointValue, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, radius, z);
        this.pointValue = pointValue;
        hp = baseHp;
        this.shapeRenderer = shapeRenderer;
    }

    public SphereEnemy(double x, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, radius, z, color);
        pointValue = baseValue;
        hp = baseHp;
        this.shapeRenderer = shapeRenderer;
    }

    public SphereEnemy(double x, double z, int pointValue, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, radius, z, color);
        this.pointValue = pointValue;
        hp = baseHp;
        this.shapeRenderer = shapeRenderer;
    }

    public SphereEnemy(double x, double z, Vector3f color, boolean outline, ShapeRenderer shapeRenderer) {
        super(ShapeType.SPHERE, radius, x, radius, z, color, outline);
        pointValue = baseValue;
        hp = baseHp;
        this.shapeRenderer = shapeRenderer;
    }
}
