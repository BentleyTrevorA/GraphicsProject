package DataRepresentations;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Line
{
    private int x0, y0, x1, y1;                      // Line Coordinates
    private float z;                                 // Z Value for incrementing
    private float dz;                                // Z slope
    private int normalizedDy, normalizedDx;          // Used in for loops so that x or y move in the right direction (- or +)
    private int yMod, xMod;                          // Used for incrementing x or y inside loop so it moves in right direction (- or +)
    private float changeInYPer1X, changeInXPer1Y;    // Used to determine when to increment second variable
    private float r, g, b;                           // Values for r,g,b values for line color change from point a to b
    private float rStep, gStep, bStep;               // Increments of r,g,b values for line color change from point a to b
    private Set<PointData> points;                   // List of points that constitute a line

    public Set<PointData> plotLineData(Vector4f p1, Vector4f p2, Vector4f startColor, Vector4f endColor) {
        return plotLineData(p1, p2, startColor, endColor, new HashSet<PointData>());
    }

    public Set<PointData> plotLineData(Vector4f p1, Vector4f p2, Vector4f startColor, Vector4f endColor, Set<PointData> points) {
        this.points = points;
        x0 = (int)p1.x;
        y0 = (int)p1.y;
        x1 = (int)p2.x;
        y1 = (int)p2.y;
        z = p1.z;

        int dx = x1 - x0;
        int dy = y1 - y0;
        if (dy != 0) {
            normalizedDy = (dy / Math.abs(dy));
        }
        if (dx != 0) {
            normalizedDx = (dx / Math.abs(dx));
        }

        r = startColor.x;
        g = startColor.y;
        b = startColor.z;

        if(Math.abs(dx) > Math.abs(dy)){
            int steps = Math.abs(dx) - 1;
            setRGBSteps(startColor, endColor, steps);
            setZSteps(p1.z, p2.z, steps);
        }
        else {
            int steps = Math.abs(dy) - 1;
            setRGBSteps(startColor, endColor, steps);
            setZSteps(p1.z, p2.z, steps);
        }

        // Rise over run (how much y changes for each 1 pixel increment of x)
        changeInYPer1X = (float) dy / (float) dx;

        // Run over rise (how much x changes for each 1 pixel increment of y)
        changeInXPer1Y = (float) dx / (float) dy;

        yMod = 1;
        if (x0 > x1) {
            yMod = -1;
        }

        xMod = 1;
        if (y0 > y1) {
            xMod = -1;
        }

        // Plot line
        if (dx == 0) {
            verticalLine();
        }
        else {
            if (isSmallSlope()) {
                smallSlopeLine();
            }
            else {
                steepSlopeLine();
            }
        }
        return this.points;
    }

    private void setRGBSteps(Vector4f startColor, Vector4f endColor, int numSteps) {
        rStep = (endColor.x - startColor.x) / numSteps;
        gStep = (endColor.y - startColor.y) / numSteps;
        bStep = (endColor.z - startColor.z) / numSteps;
    }

    private void setZSteps(float startZ, float endZ, int numSteps) {
        dz = (endZ - startZ) / (numSteps + 1);
    }

    private boolean isSmallSlope() {
        return (changeInYPer1X <= 1 && changeInYPer1X >= -1);
    }

    private boolean isPositiveSlope(boolean isSmallSlope) {
        if (isSmallSlope) {
            return (changeInYPer1X >= 0);
        }
        return (changeInXPer1Y >= 0);
    }

    // Straight up and down [-INFINITY] OR [INFINITY]
    private void verticalLine() {
        for (int y = y0; y != y1; y += normalizedDy) {
            plot(x0, y, z);
            z += dz;
        }
        plot(x1, y1, z);
    }

    // Slope -1 to 1
    private void smallSlopeLine() {
        float accumulation = 0;
        boolean positiveSlope = isPositiveSlope(true);
        int y = y0;

        for (int x = x0; x != x1; x += normalizedDx) {
            plot(x, y, z);
            accumulation += changeInYPer1X;
            if (accumulation >= 0.5 && positiveSlope) {
                y += yMod;
                accumulation--;
            }
            else if (accumulation <= -0.5 && !positiveSlope) {
                y -= yMod;
                accumulation++;
            }
            z += dz;
        }
        plot(x1, y, z);
    }

    // [-INFINITY < slope < -1] OR [1 < slope < INFINITY]
    private void steepSlopeLine() {
        float accumulation = 0;
        boolean positiveSlope = isPositiveSlope(false);
        int x = x0;

        for (int y = y0; y != y1; y += normalizedDy) {
            plot(x, y, z);
            accumulation += changeInXPer1Y;
            if (accumulation >= 0.5 && positiveSlope) {
                x += xMod;
                accumulation--;
            }
            else if (accumulation <= -0.5 && !positiveSlope) {
                x -= xMod;
                accumulation++;
            }
            z += dz;
        }
        plot(x, y1, z);
    }

    private void plot(int x, int y, float z) {
        //System.out.println("(" + x + "," + y + ") - R:" + r + " G:" + g + " B:" + b);
//        controller.setPixel(x, y, r, g, b);
        points.add(new PointData(x, y, z, r, g, b));

        // Increment Color
        r += rStep;
        g += gStep;
        b += bStep;
    }
}