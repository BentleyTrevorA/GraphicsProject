package game;

import camera.Camera;
import model.Model;

import java.util.Calendar;

public class Shot {
    public double x, y, z, dx, dy, dz;
    public double size = 1;
    public int slices = 16;
    public int stacks = 14;

    public int damage = 1;       // Damage shot does to enemies

    public int timeOfBirth;      // Time that the shot was born (seconds)
    public int secToLive = 10;   // Time (seconds) that shot will live before dying

    public boolean alive = true; // Shot is alive and moving around the map still

    // TODO: Extend MapObject
//    protected double scale;       // Scale of object
//    protected double rotation;    // Rotation of object
//    protected double x, y, z;     // Position of object
//    protected Vector3f color;     // Color of object
//    protected boolean outline;    // Draw outline of object
//    protected MapObjectType type; // Type of object

    public Shot(double x, double y, double z, double dx, double dy, double dz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public Shot(Camera camera) {
        x = camera.xPos + 4 * Math.sin(Math.toRadians(camera.rotateAngle));
        y = camera.yPos;
        z = camera.zPos - 4 * Math.cos(Math.toRadians(camera.rotateAngle));

        // Set speed of ball to be faster than the player so if they are running it's not weird
        dx = 2 * Math.sin(Math.toRadians(camera.rotateAngle));
        dy = 0;
        dz = -2 * Math.cos(Math.toRadians(camera.rotateAngle));

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
        if(getCurrentTimeInSeconds() - timeOfBirth >= secToLive)
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
