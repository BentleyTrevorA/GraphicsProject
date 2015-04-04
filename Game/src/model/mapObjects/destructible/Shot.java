package model.mapObjects.destructible;

import camera.Camera;
import model.Colors;
import model.Model;
import model.handlers.TimeHandler;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class Shot extends DestructibleObject {
    protected static double radius = 1;
    private static Vector3f color = Colors.CYAN;

    private int damage = 1;        // Damage shot does to enemies

    private int timeOfBirth;      // Time that the shot was born (seconds)
    private int secToLive = 10;   // Time (seconds) that shot will live before dying

    public boolean alive;         // Shot is alive and moving around the map still

    public Shot(double x, double y, double z, double dx, double dy, double dz) {
        super (ShapeType.SPHERE, radius, x, y, z, color, false);
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
        timeOfBirth = TimeHandler.getCurrentTimeInSeconds();
    }

    public int getDamage() {
        return damage;
    }

    // Assumes board is always a square field
    public boolean isOutsideGameField() {
        return x <= Model.MIN_MAP_COORDINATE || x >= Model.MAX_MAP_COORDINATE
                || z <= Model.MIN_MAP_COORDINATE || z >= Model.MAX_MAP_COORDINATE;
    }

    // Checks if lifespan of shot is over
    public void checkShotLife() {
        if (TimeHandler.getCurrentTimeInSeconds() - timeOfBirth >= secToLive)
            kill();
    }

    public void kill() {
        alive = false;
    }

    public boolean isDead() {
        return !alive;
    }
}
