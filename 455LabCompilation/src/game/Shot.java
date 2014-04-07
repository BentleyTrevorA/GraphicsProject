package game;

public class Shot {
    public double x, y, z, dx, dy, dz;
    public double size = 1;
    public int slices = 16;
    public int stacks = 14;

    public Shot(double x, double y, double z, double dx, double dy, double dz)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public void updatePosition()
    {
        x += dx;
        y += dy;
        z += dz;
    }

    // Assumes board is always a square field
    public boolean isOutsizeGameField(double min, double max)
    {
        return x <= min || x >= max || z <= min || z >= max;
    }
}
