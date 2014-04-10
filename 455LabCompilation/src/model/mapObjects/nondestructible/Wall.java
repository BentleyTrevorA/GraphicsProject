package model.mapObjects.nondestructible;

import model.Colors;
import model.mapObjects.MapObjectType;
import org.lwjgl.util.vector.Vector3f;

public class Wall extends NonDestructibleObject{
    private static double defaultScaleX = 15;
    private static double defaultScaleY = 15;
    private static double defaultScaleZ = 15;
    private double scaleX, scaleY, scaleZ;

    public Wall(double x, double y, double z) {
        super(MapObjectType.CUBE, 0, x, y, z, Colors.BLUE, false);
    }
    
    public void render() {
        // TODO: Call ModelRenderer using scale values
    }
}
