package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.ShapeType;

public class WallObstacle extends NonDestructibleObject {
    private static double defaultScaleX = 15;
    private static double defaultScaleY = 15;
    private static double defaultScaleZ = 15;
    private double scaleX, scaleY, scaleZ;

    public WallObstacle(double x, double y, double z) {
        super(ShapeType.CUBE, 0, x, y, z, Colors.BLUE, false);
    }

    public void render() {
        // TODO: Call ShapeRenderer using scale values
    }
}
