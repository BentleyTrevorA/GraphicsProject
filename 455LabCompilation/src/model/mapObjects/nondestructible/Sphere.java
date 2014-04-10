package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class Sphere extends NonDestructibleObject {
    private static double defaultRadius = 5;

    public Sphere(double x, double y, double z) {
        super(MapObjectType.SPHERE, defaultRadius, x, y, z, Colors.LIGHT_BLUE, false);
    }

    public Sphere(double x, double y, double z, Vector3f color) {
        super(MapObjectType.SPHERE, defaultRadius, x, y, z, color, false);
    }

    public Sphere(double radius, double x, double y, double z, Vector3f color) {
        super(MapObjectType.SPHERE, radius, x, y, z, color, false);
    }

    public Sphere(double radius, double x, double y, double z, Vector3f color, boolean outline) {
        super(MapObjectType.SPHERE, radius, x, y, z, color, outline);
    }
}
