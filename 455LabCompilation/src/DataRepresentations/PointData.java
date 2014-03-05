package DataRepresentations;

import org.lwjgl.util.vector.Vector4f;

import java.awt.*;

public class PointData implements Comparable
{
    public int x, y;
    public float r, g, b, z;

    public PointData(int x, int y, float z, Vector4f color) {
        this.x = x;
        this.y = y;
        this.z = z;
        r = color.x;
        g = color.y;
        b = color.z;
    }

    public PointData(int x, int y, float z, float r, float g, float b) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int compareTo(Object o) {
        PointData other = (PointData)o;
        if((this.y - other.y) != 0)
            return this.y - other.y;
        else
            return this.x - other.x;
    }

    public boolean equals(Object o) {
        if(!(o instanceof PointData))
            return false;
        else {
            PointData other = (PointData)o;
            return ((x == other.x) && (y == other.y));
        }
    }
}
