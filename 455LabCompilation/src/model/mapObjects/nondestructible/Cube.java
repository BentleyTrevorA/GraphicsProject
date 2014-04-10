package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class Cube extends NonDestructibleObject{
    private static double defaultScale = 15;

    public Cube(double x, double y, double z) {
        super(MapObjectType.CUBE, defaultScale, x, y, z, Colors.YELLOW, false);
    }

    public Cube(double x, double y, double z, Vector3f color) {
        super(MapObjectType.CUBE, defaultScale, x, y, z, color, false);
    }

    public Cube(double scale, double x, double y, double z, Vector3f color) {
        super(MapObjectType.CUBE, scale, x, y, z, color, false);
    }

    public Cube(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(MapObjectType.CUBE, scale, x, y, z, color, outline);
    }
}
