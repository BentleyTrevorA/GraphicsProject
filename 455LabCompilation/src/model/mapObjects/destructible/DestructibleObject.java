package model.mapObjects.destructible;

import model.mapObjects.MapObject;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public abstract class DestructibleObject extends MapObject {
    public DestructibleObject(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
    }
}
