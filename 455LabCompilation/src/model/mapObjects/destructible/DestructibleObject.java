package model.mapObjects.destructible;

import model.mapObjects.MapObject;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class DestructibleObject extends MapObject {
    public DestructibleObject(MapObjectType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
    }
}
