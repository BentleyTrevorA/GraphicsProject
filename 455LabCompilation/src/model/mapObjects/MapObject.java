package model.mapObjects;

import camera.Camera;
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
    protected double dx, dy, dz;     // Speed in x, y, and z respectively
    protected double speed;          // Speed a MapObject can move in x, y, and z in a single update
    protected Vector3f color;        // Color of object
    protected boolean outline;       // Draw outline of object
    protected ShapeType type;        // Type of object
    protected int texture;           // Texture of object
    protected ShapeRenderer shapeRenderer; // Draws all the shapes

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

        this.x = x;
        this.y = y;
        this.z = z;

        dx = 0;
        dy = 0;
        dz = 0;
        speed = 0;

        rotation = 0;

        this.color = color;
        this.outline = outline;
        texture = ShapeRenderer.NO_TEXTURE;
        shapeRenderer = null;
    }

    public void render() {
        if(shapeRenderer != null)
        {
            switch (type) {
                case CUBE:
                    shapeRenderer.drawCube(this);
                    break;
                case PYRAMID:
                    shapeRenderer.drawPyramid(scale, x, y, z, color, outline);
                    break;
                case SPHERE:
                    shapeRenderer.drawSphere(scale, this.x, this.y, this.z, slices, stacks, color);
                    break;
                case PLANE:
                    break;
                default:
                    break;
            }
        }
    }

    public void updatePosition() {
        x += dx;
        y += dy;
        z += dz;
    }

    public void updateTargetPosition(MapObject objectToMoveTowards) {
        updateTargetPosition(objectToMoveTowards.x, objectToMoveTowards.y, objectToMoveTowards.z);
    }

    public void updateTargetPosition(Camera camera) {
        updateTargetPosition(camera.xPos, camera.yPos, camera.zPos);
    }

    private void updateTargetPosition(double x, double y, double z) {
        double distX = x - this.x;
        double distY = y - this.y;
        double distZ = z - this.z;
        double halfScale = scale / 2;

        // Have the target be a bit farther so it will collide with it
        if(distX < 0)
            distX -= halfScale;
        else
            distX += halfScale;

        // Have the target be a bit farther so it will collide with it
        if(distZ < 0)
            distZ -= halfScale;
        else
            distZ += halfScale;

        double greaterDist;
        if(Math.abs(distX) > Math.abs(distZ))
            greaterDist = Math.abs(distX);
        else
            greaterDist = Math.abs(distZ);

        if(greaterDist == 0)
            greaterDist = 1;

        // TODO: Figure out how to always move a dist of 1 * speed - do the same for the camera movement
        dx = speed * distX / greaterDist;
        dy = 0;
        dz = speed * distZ / greaterDist;
    }

    // TODO: Handle reflections
    public void reflect(MapObject object) {
        // based off of object, reflect
        // this will apply for both enemies and for shots
    }

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
        return isCollidingWith(new Vector4f((float) object.x, (float) object.y, (float) object.z, 1));
    }

    // TODO: Need to better detect when running into player and killing them
    public boolean isCollidingWith(Vector4f collisionPoint) {
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

    /**************** GETTERS *********************/
    public double getScale() {
        return scale;
    }

    public int getSlices() {
        return slices;
    }

    public int getStacks() {
        return stacks;
    }

    public double getRotation() {
        return rotation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getDz() {
        return dz;
    }

    public double getSpeed() {
        return speed;
    }

    public Vector3f getColor() {
        return color;
    }

    public boolean getOutlined() {
        return outline;
    }

    public ShapeType getType() {
        return type;
    }

    public int getTextureNumber() {
        return texture;
    }

    /**************** SETTERS *********************/
    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void setTexture(int texture) {
        this.texture = texture;
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
