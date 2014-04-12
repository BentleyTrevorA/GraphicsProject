package model.mapObjects.destructible;

import camera.Camera;
import model.Colors;
import model.Model;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

import java.util.Calendar;

public class Shot extends DestructibleObject {
    public double dx, dy, dz;
    protected static double radius = 1;
    public static Vector3f color = Colors.PINK;

    public int damage = 1;        // Damage shot does to enemies

    private int timeOfBirth;      // Time that the shot was born (seconds)
    private int secToLive = 10;   // Time (seconds) that shot will live before dying

    public boolean alive;         // Shot is alive and moving around the map still

//    protected double scale;       // Scale of object
//    protected double rotation;    // Rotation of object
//    protected double x, y, z;     // Position of object
//    protected Vector3f color;     // Color of object
//    protected boolean outline;    // Draw outline of object
//    protected ShapeType type; // Type of object

    public Shot(double x, double y, double z, double dx, double dy, double dz) {
        super (ShapeType.SPHERE, radius, x, y, z, color, false);
        //ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        alive = true;
    }

    public Shot(Camera camera) {
        super(ShapeType.SPHERE, radius,
                camera.xPos + 4 * Math.sin(Math.toRadians(camera.rotateAngle)),
                camera.yPos,
                camera.zPos - 4 * Math.cos(Math.toRadians(camera.rotateAngle)),
                color, false);

        // Set speed of ball to be faster than the player so if they are running it's not weird
        dx = 2 * Math.sin(Math.toRadians(camera.rotateAngle));
        dy = 0;
        dz = -2 * Math.cos(Math.toRadians(camera.rotateAngle));

        alive = true;
        timeOfBirth = getCurrentTimeInSeconds();
    }

    public void updatePosition() {
        x += dx;
        y += dy;
        z += dz;
    }

    // Assumes board is always a square field
    public boolean isOutsideGameField() {
        return x <= Model.MIN_MAP_COORDINATE || x >= Model.MAX_MAP_COORDINATE
                || z <= Model.MIN_MAP_COORDINATE || z >= Model.MAX_MAP_COORDINATE;
    }

    // Checks if lifespan of shot is over
    public void checkShotLife() {
        if (getCurrentTimeInSeconds() - timeOfBirth >= secToLive)
            kill();
    }

    public void kill() {
        alive = false;
    }

    public boolean isDead() {
        return !alive;
    }

    private int getCurrentTimeInSeconds() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
}
