package dataRepresentations;

import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Quad extends ConvexShape {
    private Vector4f point1, point2, point3, point4;
    private Vector4f p1Color, p2Color, p3Color, p4Color;
    private Line line;

    public Quad() {
        line = new Line();
    }

    public void addPoint(Vector4f point, Vector4f color) {
        if (point1 == null) {
            point1 = point;
            p1Color = color;
        } else if (point2 == null) {
            point2 = point;
            p2Color = color;
        } else if (point3 == null) {
            point3 = point;
            p3Color = color;
        } else {
            point4 = point;
            p4Color = color;
        }
    }

    // Returns triangle points sorted by Y value, then X value
    protected ArrayList<PointData> calculateSortedFramePoints() {
        if (point1 != null && point2 != null && point3 != null) {
            // Use line algorithm code to get edges of triangle
            Set<PointData> points = line.plotLineData(point1, point2, p1Color, p2Color);
            points = line.plotLineData(point2, point3, p2Color, p3Color, points);
            points = line.plotLineData(point3, point4, p3Color, p4Color, points);
            points = line.plotLineData(point4, point1, p4Color, p1Color, points);

            // Sort by y Value
            ArrayList<PointData> sortedPointsList = new ArrayList<PointData>(points);
            Collections.sort(sortedPointsList);

            return sortedPointsList;
        } else
            return null;
    }
}
