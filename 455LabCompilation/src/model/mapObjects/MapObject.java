package model.mapObjects;

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

public class MapObject {
    protected double scale;       // Scale of object
    protected double rotation;    // Rotation of object TODO: Future addition
    protected double x, y, z;     // Position of object
    protected Vector3f color;     // Color of object
    protected boolean outline;    // Draw outline of object
    protected MapObjectType type; // Type of object

    public MapObject(MapObjectType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        this.type = type;
        this.scale = scale;
        rotation = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.outline = outline;
    }

    public void render() {
        switch(type) {
            case CUBE:
                ModelRenderer.drawCube(scale, x, y, z, color, outline);
                break;
            case PYRAMID:
                ModelRenderer.drawPyramid(scale, x, y, z, color, outline);
                break;
            case SPHERE:
                int slicesAndStacks = (int)(scale * scale);
                ModelRenderer.drawSphere(scale, x, y, z, slicesAndStacks, slicesAndStacks, color);
                break;
            case PLANE:
                break;
            default:
                break;
        }
    }

    // Move x

    // Move y

    // Move z

    /**
     * Given the coordinates of an object, determine if that object is colliding
     * with this one
     *
     * @param x - x position of other object
     * @param y - y position of other object
     * @param z - z position of other object
     * @return - if the (x, y, z) position is colliding with this MapObject
     */
    public boolean isCollidingWith(double x, double y, double z) {
        switch(type) {
            case CUBE:
                break;
            case PYRAMID:
                break;
            case SPHERE:
                break;
            case PLANE:
                break;
            default:
                break;
        }

        return false;
    }

    // get type
}
