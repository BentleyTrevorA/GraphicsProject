package model.mapObjects.nondestructible;

import model.mapObjects.MapObject;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public abstract class NonDestructibleObject extends MapObject {
    public NonDestructibleObject(MapObjectType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
    }
}
