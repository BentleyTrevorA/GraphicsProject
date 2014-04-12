package model.mapObjects.destructible;

import model.Colors;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public abstract class EnemyEntity extends Entity {
    protected static Vector3f defaultColor = Colors.RED;
    protected int pointValue = 10;
    private double defaultSpeed = .3;

    public EnemyEntity(ShapeType type, double scale, double x, double z) {
        super(type, scale, x, 0, z, defaultColor, false);
        speed = defaultSpeed;
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z) {
        super(type, scale, x, y, z, defaultColor, false);
        speed = defaultSpeed;
    }

    public EnemyEntity(ShapeType type, double scale, double x, double z, Vector3f color) {
        super(type, scale, x, 0, z, color, false);
        speed = defaultSpeed;
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z, Vector3f color) {
        super(type, scale, x, y, z, color, false);
        speed = defaultSpeed;
    }

    public EnemyEntity(ShapeType type, double scale, double x, double z, Vector3f color, boolean outline) {
        super(type, scale, x, 0, z, color, outline);
        speed = defaultSpeed;
    }

    public EnemyEntity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
        speed = defaultSpeed;
    }

    public int getPointValue() {
        return pointValue;
    }
}
