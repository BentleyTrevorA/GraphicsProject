package model.mapObjects.destructible;

import model.Colors;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public abstract class EnemyEntity extends Entity {
    protected static Vector3f defaultColor = Colors.RED;
    protected static int defaultPointValue = 10;
    private static double defaultSpeed = .3;

    protected int pointValue;

    public EnemyEntity(ShapeType type, double scale, double x, double z) {
        super(type, scale, x, 0, z, defaultColor, false);
        speed = getRandomSpeed(defaultSpeed);
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z) {
        super(type, scale, x, y, z, defaultColor, false);
        speed = getRandomSpeed(defaultSpeed);
    }

    public EnemyEntity(ShapeType type, double scale, double x, double z, Vector3f color) {
        super(type, scale, x, 0, z, color, false);
        speed = getRandomSpeed(defaultSpeed);
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z, Vector3f color) {
        super(type, scale, x, y, z, color, false);
        speed = getRandomSpeed(defaultSpeed);
    }

    public EnemyEntity(ShapeType type, double scale, double x, double z, Vector3f color, boolean outline) {
        super(type, scale, x, 0, z, color, outline);
        speed = getRandomSpeed(defaultSpeed);
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
        speed = getRandomSpeed(defaultSpeed);
    }

    public void setPointValue(int value) {
        this.pointValue = value;
    }

    public int getPointValue() {
        return pointValue;
    }

    public double getRandomSpeed(double topSpeed) {
        double value = Math.random();
        while(value < .2) {
            value = Math.random();
        }
        return value * topSpeed;
    }

    protected void updatePointValue() {
        pointValue = defaultPointValue * hp;
    }
}
