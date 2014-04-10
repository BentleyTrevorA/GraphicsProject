package dataRepresentations;

import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.Collections;

public class BigPoint extends ConvexShape {
    Vector4f color;
    ArrayList<PointData> circlePoints;

    public BigPoint(int x, int y, int radius, Vector4f color) {
        this.color = color;
        circlePoints = new ArrayList<PointData>();

        // Calculate 1/8 of the circle
        int xOffset = radius;
        int yOffset = 0;
        double rSquared = radius * radius;
        while (yOffset <= xOffset) {
            // First Quadrant (TR)
            addToCirclePoints(x + xOffset, y + yOffset);
            addToCirclePoints(x + yOffset, y + xOffset);

            // Second Quadrant (TL)
            addToCirclePoints(x - yOffset, y + xOffset);
            addToCirclePoints(x - xOffset, y + yOffset);

            // Third Quadrant (BL)
            addToCirclePoints(x - xOffset, y - yOffset);
            addToCirclePoints(x - yOffset, y - xOffset);

            // Fourth Quadrant (BR)
            addToCirclePoints(x + yOffset, y - xOffset);
            addToCirclePoints(x + xOffset, y - yOffset);

            // Xn+1^2 = R^2 - Yn^2 - 2Yn - 1
            xOffset = (int) Math.sqrt(rSquared - (yOffset * yOffset) - (2 * yOffset) - 1);
            yOffset++;
        }
    }

    private void addToCirclePoints(int x, int y) {
        PointData point = new PointData(x, y, 1, color);
        if (!circlePoints.contains(point))
            circlePoints.add(point);
    }

    // Returns triangle points sorted by Y value, then X value
    protected ArrayList<PointData> calculateSortedFramePoints() {
        Collections.sort(circlePoints);
        return circlePoints;
    }
}
