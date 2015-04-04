package model.mapObjects.nondestructible;

import model.Colors;
import model.handlers.TextureHandler;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class CubeObstacle extends NonDestructibleObject {
    private static double defaultScale = 15;

    public CubeObstacle(double x, double y, double z, TextureHandler textureHandler) {
        super(ShapeType.CUBE, defaultScale, x, y, z, Colors.YELLOW, false);
        this.textureHandler = textureHandler;
    }

    public CubeObstacle(double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.CUBE, defaultScale, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public CubeObstacle(double scale, double x, double y, double z, TextureHandler textureHandler) {
        super(ShapeType.CUBE, scale, x, y, z, Colors.YELLOW, false);
        this.textureHandler = textureHandler;
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.CUBE, scale, x, y, z, color, false);
        this.textureHandler = textureHandler;
    }

    public CubeObstacle(double scale, double x, double y, double z, Vector3f color, boolean outline, TextureHandler textureHandler) {
        super(ShapeType.CUBE, scale, x, y, z, color, outline);
        this.textureHandler = textureHandler;
    }
}
