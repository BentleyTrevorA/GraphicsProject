package model.mapObjects;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.FloatBuffer;

public abstract class MapObject {
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
                ShapeRenderer.drawCube(scale, x, y, z, color, outline);
                break;
            case PYRAMID:
                ShapeRenderer.drawPyramid(scale, x, y, z, color, outline);
                break;
            case SPHERE:
                int slicesAndStacks = (int)(scale * scale);
                ShapeRenderer.drawSphere(scale, x, y, z, slicesAndStacks, slicesAndStacks, color);
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
        Vector4f position = new Vector4f((float)x, (float)y, (float)z, 1);

        position = inverseTranslate(position);
        position = inverseRotate(position);
        position = inverseScale(position);

        // For now, ignore y value since it should always hit with how it is setup currently
        switch(type) {
            case CUBE:
                boolean withinX = (position.x >= 0 && position.x <= 1);
                boolean withinZ = (position.z >= 0 && position.z <= 1);
                return withinX && withinZ;
            case PYRAMID:
                break;
            case SPHERE:
                double distFromCenter = position.x * position.x + position.y * position.y + position.z * position.z;
                return (distFromCenter <= scale * scale);
            case PLANE:
                break;
            default:
                break;
        }

        return false;
    }

    private Vector4f inverseTranslate(Vector4f position) {
        Matrix4f inverseTranslate = new Matrix4f();

        FloatBuffer inverseTranslation = BufferUtils.createFloatBuffer(16);
        inverseTranslation
                .put(1).put(0).put(0).put(0)
                .put(0).put(1).put(0).put(0)
                .put(0).put(0).put(1).put(0)
                .put((float)-x).put((float)-y).put((float)-z).put(1).flip();

        inverseTranslate.load(inverseTranslation);

        return Matrix4f.transform(inverseTranslate, position, position);
    }

    private Vector4f inverseScale(Vector4f position) {
        Matrix4f inverseScaleMatrix = new Matrix4f();
        FloatBuffer inverseScaleBuffer = BufferUtils.createFloatBuffer(16);
        float inverseScale = (float)(1/scale);

        inverseScaleBuffer
                .put(inverseScale).put(0).put(0).put(0)
                .put(0).put(inverseScale).put(0).put(0)
                .put(0).put(0).put(inverseScale).put(0)
                .put(0).put(0).put(0).put(1).flip();

        inverseScaleMatrix.load(inverseScaleBuffer);

        return Matrix4f.transform(inverseScaleMatrix, position, position);
    }

    // TODO: Later if rotate objects
    private Vector4f inverseRotate(Vector4f position) {
//        float angleRadians = (float) Math.toRadians(angDegrees);
//        float cosine = (float) Math.cos(angleRadians);
//        float sine = (float) Math.sin(angleRadians);
//        float[] rotate = {
//                1, 0, 0, 0,
//                0, cosine, sine, 0,
//                0, -1 * sine, cosine, 0,
//                0, 0, 0, 1};
        return position;
    }

    // get type

    public String toString() {
        String output = "Type: " + type;
        output += "\nX: " + x;
        output += "\nY: " + y;
        output += "\nZ: " + z;
        output += "\nScale: " + scale;
        output += "\nRotation: " + rotation;
        output += "\nColor: (" + color.x + ", " + color.y + ", " + color.z + ")";
        output += "\nOutline Drawn: " + output;

        return output;
    }
}
