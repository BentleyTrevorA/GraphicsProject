package model.mapObjects.destructible;

import model.Colors;
import model.mapObjects.MapObjectType;
import model.mapObjects.destructible.DestructibleObject;
import org.lwjgl.util.vector.Vector3f;

public class EnemyEntity extends Entity {
    protected static Vector3f defaultColor = Colors.RED;

    public EnemyEntity(MapObjectType type, double scale, double x, double z) {
        super(type, scale, x, 0, z, defaultColor, false);
    }

    public EnemyEntity(MapObjectType type, double scale, double x, double z, Vector3f color) {
        super(type, scale, x, 0, z, color, false);
    }

    public EnemyEntity(MapObjectType type, double scale, double x, double z, Vector3f color, boolean outline) {
        super(type, scale, x, 0, z, color, outline);
    }

    public EnemyEntity(MapObjectType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
    }
}
