package model;

import org.lwjgl.util.vector.Vector3f;

import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class MapPieces {

    private WireFrame model = new HouseModel();

    public void drawHouse() {
        glBegin(GL_POLYGON);
        {
            Iterator<Line3D> iter = model.getLines();
            while (iter.hasNext()) {
                Line3D line = iter.next();
                float startX = (float) line.start.x;
                float startY = (float) line.start.y;
                float startZ = (float) line.start.z;

                float endX = (float) line.end.x;
                float endY = (float) line.end.y;
                float endZ = (float) line.end.z;

                glVertex3f(startX, startY, startZ);
                glVertex3f(endX, endY, endZ);
            }
        }
        glEnd();

        glColor3f(0, 0, 1);
        glBegin(GL_LINES);
        {
            Iterator<Line3D> iter = model.getLines();
            while (iter.hasNext()) {
                Line3D line = iter.next();
                float startX = (float) line.start.x;
                float startY = (float) line.start.y;
                float startZ = (float) line.start.z;

                float endX = (float) line.end.x;
                float endY = (float) line.end.y;
                float endZ = (float) line.end.z;

                glVertex3f(startX, startY, startZ);
                glVertex3f(endX, endY, endZ);
            }
        }
        glEnd();
    }

    public void drawWalls(int scale, int numTilesInOneDirection) {
        int cornerCoord = scale * numTilesInOneDirection;
        int heightScale = 4;
        glBegin(GL_QUADS);
        {
            // Front
            glColor3f(1, 0, 0);
            glVertex3d(-cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, -cornerCoord);

            // Right
            glColor3f(1, 1, 0);
            glVertex3d(cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, 0, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, -cornerCoord);

            // Back
            glColor3f(0, 0, 1);
            glVertex3d(cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, cornerCoord);

            // Left
            glColor3f(0, 1, 1);
            glVertex3d(-cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, 0, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, cornerCoord);
        }
        glEnd();
    }

    public void drawFloor(int scale, int numTiles) {
        for (int x = 0; x < numTiles + 1; x++) {
            for (int z = 0; z < numTiles + 1; z++) {
                Vector3f tileColor;
                if ((x + z) % 2 == 0)
                    tileColor = new Vector3f(1, 1, 1);
                else
                    tileColor = new Vector3f(0, 0, 0);


                drawFloorTile(scale, scale * x, 0, scale * z, tileColor);
                // Reflect across z axis
                drawFloorTile(scale, scale * x, 0, scale * -z, tileColor);
                // Reflect across x axis
                drawFloorTile(scale, scale * -x, 0, scale * z, tileColor);
                // Reflect across x and z axis
                drawFloorTile(scale, scale * -x, 0, scale * -z, tileColor);
            }
        }
    }

    public void drawFloorTile(double scale, double x, double y, double z, Vector3f color) {
        glColor3f(color.x, color.y, color.z);

        glBegin(GL_QUADS);
        {
            glVertex3d(x, y, z);
            glVertex3d(x, y, z + scale);
            glVertex3d(scale + x, y, z + scale);
            glVertex3d(scale + x, y, z);
        }
        glEnd();
    }

    /**
     * Draw a basic pyramid shape
     *
     * @param scale   - Scale of shape
     * @param x       - x offset
     * @param y       - y offset
     * @param z       - z offset
     * @param outline - draw the shape outline
     */
    public void drawPyramid(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        double halfScale = .5 * scale;
        double height = Math.sqrt(halfScale * halfScale + halfScale * halfScale);

        glColor3f(color.x, color.y, color.z);
        // Be sure to be CCW
//        glEnable(GL_NORMALIZE);
//        glEnable(GL_DEPTH_TEST);
        glBegin(GL_TRIANGLES);
        {
            glColor3f(0, 0, 1);
            glVertex3d(scale + x, y, z);
            glVertex3d(x, y, z);
            glVertex3d(halfScale + x, height + y, halfScale + z);

            glColor3f(0, 1, 0);
            glVertex3d(scale + x, y, scale + z);
            glVertex3d(scale + x, y, z);
            glVertex3d(halfScale + x, height + y, halfScale + z);

            glColor3f(1, 0, 0);
            glVertex3d(x, y, scale + z);
            glVertex3d(scale + x, y, scale + z);
            glVertex3d(halfScale + x, height + y, halfScale + z);

            glColor3f(0, 1, 1);
            glVertex3d(x, y, z);
            glVertex3d(x, y, scale + z);
            glVertex3d(halfScale + x, height + y, halfScale + z);
        }
        glEnd();

        if (outline) {
            glColor3f(0, 0, 0);
            glBegin(GL_LINES);
            {
                glVertex3d(x, y, z);
                glVertex3d(scale + x, y, z);

                glVertex3d(scale + x, y, z);
                glVertex3d(scale + x, y, scale + z);

                glVertex3d(scale + x, y, scale + z);
                glVertex3d(x, y, scale + z);

                glVertex3d(x, y, scale + z);
                glVertex3d(x, y, z);

                glVertex3d(x, y, z);
                glVertex3d(halfScale + x, height + y, halfScale + z);

                glVertex3d(scale + x, y, z);
                glVertex3d(halfScale + x, height + y, halfScale + z);

                glVertex3d(scale + x, y, scale + z);
                glVertex3d(halfScale + x, height + y, halfScale + z);

                glVertex3d(x, y, scale + z);
                glVertex3d(halfScale + x, height + y, halfScale + z);
            }
            glEnd();
        }
    }

    public void drawCube(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        glColor3f(color.x, color.y, color.z);
        glBegin(GL_QUADS);
        {
            // Front
            glVertex3d(x, y, scale + z);
            glVertex3d(scale + x, y, scale + z);
            glVertex3d(scale + x, scale + y, scale + z);
            glVertex3d(x, scale + y, scale + z);

            // Right
            glVertex3d(scale + x, y, scale + z);
            glVertex3d(scale + x, y, z);
            glVertex3d(scale + x, scale + y, z);
            glVertex3d(scale + x, scale + y, scale + z);

            // Back
            glVertex3d(scale + x, y, z);
            glVertex3d(x, y, z);
            glVertex3d(x, scale + y, z);
            glVertex3d(scale + x, scale + y, z);

            // Left
            glVertex3d(x, y, z);
            glVertex3d(x, y, scale + z);
            glVertex3d(x, scale + y, scale + z);
            glVertex3d(x, scale + y, z);

            // Bottom
            glVertex3d(x, y, z);
            glVertex3d(scale + x, y, z);
            glVertex3d(scale + x, y, scale + z);
            glVertex3d(x, y, scale + z);

            // Top
            glVertex3d(x, scale + y, z);
            glVertex3d(x, scale + y, scale + z);
            glVertex3d(scale + x, scale + y, scale + z);
            glVertex3d(scale + x, scale + y, z);
        }
        glEnd();

        if(outline) {
            glColor3f(0, 0, 0);
            glBegin(GL_LINES);
            {
                // Bottom
                glVertex3d(x, y, z);
                glVertex3d(scale + x, y, z);

                glVertex3d(scale + x, y, z);
                glVertex3d(scale + x, y, scale + z);

                glVertex3d(scale + x, y, scale + z);
                glVertex3d(x, y, scale + z);

                glVertex3d(x, y, scale + z);
                glVertex3d(x, y, z);

                // Top
                glVertex3d(x, scale + y, z);
                glVertex3d(scale + x, scale + y, z);

                glVertex3d(scale + x, scale + y, z);
                glVertex3d(scale + x, scale + y, scale + z);

                glVertex3d(scale + x, scale + y, scale + z);
                glVertex3d(x, scale + y, scale + z);

                glVertex3d(x, scale + y, scale + z);
                glVertex3d(x, scale + y, z);

                // Sides
                glVertex3d(x, y, z);
                glVertex3d(x, scale + y, z);

                glVertex3d(scale + x, y, z);
                glVertex3d(scale + x, scale + y, z);

                glVertex3d(scale + x, y, scale + z);
                glVertex3d(scale + x, scale + y, scale + z);

                glVertex3d(x, y, scale + z);
                glVertex3d(x, scale + y, scale + z);
            }
            glEnd();
        }
    }
}
