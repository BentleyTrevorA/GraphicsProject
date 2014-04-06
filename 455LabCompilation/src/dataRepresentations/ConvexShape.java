package dataRepresentations;

import controllers.LabController;

import java.util.ArrayList;

public class ConvexShape
{
    protected ArrayList<PointData> calculateSortedFramePoints() {
        return null;
    }

    public void fill(LabController controller) {
        ArrayList<PointData> sortedPointsList = calculateSortedFramePoints();

        PointData startPoint = sortedPointsList.get(0);
        PointData endPoint = sortedPointsList.get(0);
        for (int i = 0; i < sortedPointsList.size(); i++) {
            PointData point = sortedPointsList.get(i);

            // For each y find xmin and xmax
            if (point.y != startPoint.y) {
                // Calculate color gradient
                float r = startPoint.r;
                float g = startPoint.g;
                float b = startPoint.b;
                float z = startPoint.z;

                int steps = (endPoint.x - startPoint.x) - 1;
                float rStep, gStep, bStep, zStep;
                if(steps > 0) {
                    rStep = (endPoint.r - r) / steps;
                    gStep = (endPoint.g - g) / steps;
                    bStep = (endPoint.b - b) / steps;
                    zStep = (endPoint.z - z) / steps;
                }
                else {
                    rStep = 0;
                    gStep = 0;
                    bStep = 0;
                    zStep = 0;
                }

                // plot, interpolating color across row from xmin to xmax
                for (int j = startPoint.x; j <= endPoint.x; j++) {
                    controller.setPixel(j, startPoint.y, z, r, g, b);
                    r += rStep;
                    g += gStep;
                    b += bStep;
                    z += zStep;
                }

                startPoint = point;
                endPoint = startPoint;
            }
            else {
                endPoint = point;
            }
        }
    }
}
