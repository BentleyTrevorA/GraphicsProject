package game;

import camera.Camera;

public class Shot {
    public double x, y, z, dx, dy, dz;
    public double size = 1;
    public int slices = 16;
    public int stacks = 14;
    public int damage = 1;
    public int hp = 1000; // Can hit this many objects before disappearing

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
    }

    public void updatePosition() {
        x += dx;
        y += dy;
        z += dz;
    }

    // Assumes board is always a square field
    public boolean isOutsideGameField(double min, double max) {
        return x <= min || x >= max || z <= min || z >= max;
    }

    public void loseHp() {
        hp--;
    }
}
