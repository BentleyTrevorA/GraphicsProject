package model.mapObjects;

import model.renderers.ShapeRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.FloatBuffer;

public abstract class MapObject {
    protected double scale;          // Scale of object
    protected int slices, stacks;    // For Spheres
    protected double rotation;       // Rotation of object
    protected double x, y, z;        // Position of object
    protected Vector3f color;        // Color of object
    protected boolean outline;       // Draw outline of object
    protected ShapeType type;        // Type of object

    // Collision Variables
    public static final int X_PLANE = 0;
    public static final int Y_PLANE = 1;
    public static final int Z_PLANE = 2;
    protected Vector4f collisionPoint; // point used for checking collisions

    public MapObject(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        this.type = type;
        this.scale = scale;

        slices = (int)(scale * scale);

        if(slices < 16)
            slices = 16;

        stacks = slices;
        rotation = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.outline = outline;
    }

    public void render() {
        switch (type) {
            case CUBE:
                ShapeRenderer.drawCube(scale, x, y, z, color, outline);
                break;
            case PYRAMID:
                ShapeRenderer.drawPyramid(scale, x, y, z, color, outline);
                break;
            case SPHERE:
                ShapeRenderer.drawSphere(scale, this.x, this.y, this.z, slices, stacks, color);
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

    public void setColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Given the coordinates of an object, determine if that object is colliding
     * with this one
     *
     * @param object - MapObject to text against
     * @return - if the (x, y, z) position is colliding with this MapObject
     */
    public boolean isCollidingWith(MapObject object) {
        collisionPoint = new Vector4f((float) object.x, (float) object.y, (float) object.z, 1);

        collisionPoint = inverseTranslate(collisionPoint);
        collisionPoint = inverseRotate(collisionPoint);
        collisionPoint = inverseScale(collisionPoint);

        // For now, ignore y value since it should always hit with how it is setup currently
        switch (type) {
            case CUBE:
                boolean withinX = (collisionPoint.x >= 0 && collisionPoint.x <= 1);
                boolean withinZ = (collisionPoint.z >= 0 && collisionPoint.z <= 1);

                return (withinX && withinZ);
            case PYRAMID:
                // TODO: Collision detection for pyramid
                break;
            case SPHERE:
                double distFromCenter = collisionPoint.x * collisionPoint.x + collisionPoint.y * collisionPoint.y + collisionPoint.z * collisionPoint.z;
                return (distFromCenter <= scale * scale);
            case PLANE:
                break;
            default:
                break;
        }

        return false;
    }

    public int getCollisionPlane(double dx, double dz) {
        // TODO: Figure out this logic for balls bouncing off right (Maybe, or just forget it)
        if (collisionPoint.z > collisionPoint.x && dz < 0 || collisionPoint.z < collisionPoint.x && dz > 0)
            return Z_PLANE;
        else
            return X_PLANE;
    }

    private Vector4f inverseTranslate(Vector4f position) {
        Matrix4f inverseTranslate = new Matrix4f();

        FloatBuffer inverseTranslation = BufferUtils.createFloatBuffer(16);
        inverseTranslation
                .put(1).put(0).put(0).put(0)
                .put(0).put(1).put(0).put(0)
                .put(0).put(0).put(1).put(0)
                .put((float) -x).put((float) -y).put((float) -z).put(1).flip();

        inverseTranslate.load(inverseTranslation);

        return Matrix4f.transform(inverseTranslate, position, position);
    }

    private Vector4f inverseScale(Vector4f position) {
        Matrix4f inverseScaleMatrix = new Matrix4f();
        FloatBuffer inverseScaleBuffer = BufferUtils.createFloatBuffer(16);
        float inverseScale = (float) (1 / scale);

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

    public ShapeType getType() {
        return type;
    }

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
