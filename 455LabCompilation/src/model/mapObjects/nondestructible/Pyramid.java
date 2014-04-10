package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class Pyramid extends NonDestructibleObject{
    private static double defaultScale = 25;

    public Pyramid(double x, double y, double z) {
        super(MapObjectType.PYRAMID, defaultScale, x, y, z, Colors.GREEN, false);
    }

    public Pyramid(double x, double y, double z, Vector3f color) {
        super(MapObjectType.PYRAMID, defaultScale, x, y, z, color, false);
    }

    public Pyramid(double radius, double x, double y, double z, Vector3f color) {
        super(MapObjectType.PYRAMID, radius, x, y, z, color, false);
    }

    public Pyramid(double radius, double x, double y, double z, Vector3f color, boolean outline) {
        super(MapObjectType.PYRAMID, radius, x, y, z, color, outline);
    }
}
