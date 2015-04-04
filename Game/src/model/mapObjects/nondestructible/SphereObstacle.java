package model.mapObjects.nondestructible;

import model.Colors;
import model.handlers.TextureHandler;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class SphereObstacle extends NonDestructibleObject {
    private static double defaultRadius = 5;

        public SphereObstacle(double x, double y, double z, TextureHandler textureHandler) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, Colors.LIGHT_BLUE, false);
        this.textureHandler = textureHandler;
    }

    public SphereObstacle(double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.SPHERE, defaultRadius, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.SPHERE, radius, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public SphereObstacle(double radius, double x, double y, double z, Vector3f color, boolean outline, TextureHandler textureHandler) {
        super(ShapeType.SPHERE, radius, x, y, z, color, outline);
        this.textureHandler = textureHandler;
    }
}
