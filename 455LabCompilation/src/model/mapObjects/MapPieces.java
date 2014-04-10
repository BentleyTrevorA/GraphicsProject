package model.mapObjects;

import model.Colors;
import model.HouseModel;
import model.Line3D;
import model.WireFrame;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class MapPieces {

    private static WireFrame model = new HouseModel();

    public static void drawHouse(Vector3f houseColor, Vector3f outlineColor) {
        glColor3f(houseColor.x, houseColor.y, houseColor.z);
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

        glColor3f(outlineColor.x, outlineColor.y, outlineColor.z);
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

    public static void drawWalls(int scale, int numTilesInOneDirection, Vector3f color) {
        int cornerCoord = scale * numTilesInOneDirection;
        int heightScale = 4;
        glColor3f(color.x, color.y, color.z);
        glBegin(GL_QUADS);
        {
            // Front
            glVertex3d(-cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, -cornerCoord);

            // Right
            glVertex3d(cornerCoord, 0, -cornerCoord);
            glVertex3d(cornerCoord, 0, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, -cornerCoord);

            // Back
            glVertex3d(cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, cornerCoord);
            glVertex3d(cornerCoord, heightScale * scale, cornerCoord);

            // Left
            glVertex3d(-cornerCoord, 0, cornerCoord);
            glVertex3d(-cornerCoord, 0, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, -cornerCoord);
            glVertex3d(-cornerCoord, heightScale * scale, cornerCoord);
        }
        glEnd();
    }

    public static void drawFloor(int scale, int numTiles) {
        for (int x = 0; x < numTiles + 1; x++) {
            for (int z = 0; z < numTiles + 1; z++) {
                Vector3f tileColor;
                if ((x + z) % 2 == 0)
                    tileColor = Colors.WHITE;
                else
                    tileColor = Colors.BLACK;


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

    private static void drawFloorTile(double scale, double x, double y, double z, Vector3f color) {
        glColor3f(color.x, color.y, color.z);

        glPushMatrix();
        glTranslated(x, y, z);
        glScaled(scale, scale, scale);

        glBegin(GL_QUADS);
        {
            glVertex3d(0, 0, 0);
            glVertex3d(0, 0, 1);
            glVertex3d(1, 0, 1);
            glVertex3d(1, 0, 0);
        }
        glEnd();

        glPopMatrix();
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
    public static void drawPyramid(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        double halfScale = .5;
        double height = Math.sqrt(halfScale * halfScale + halfScale * halfScale);
        glColor3f(color.x, color.y, color.z);

        glPushMatrix();
        glTranslated(x, y, z);
        glScaled(scale, scale, scale);

        // Be sure to be CCW
        glBegin(GL_TRIANGLES);
        {
            // Back face
            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(0, 1, 0);
            glVertex3d(halfScale, height, halfScale);

            // Right face
            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(0, 1, 0);
            glVertex3d(halfScale, height, halfScale);

            // Front face
            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);

            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(0, 1, 0);
            glVertex3d(halfScale, height, halfScale);

            // Left face
            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);

            glNormal3d(0, 1, 0);
            glVertex3d(halfScale, height, halfScale);
        }
        glEnd();

        glBegin(GL_QUADS);
        {
            // Bottom face
            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);
        }
        glEnd();

        if (outline) {
            glColor3f(0, 0, 0);
            glBegin(GL_LINES);
            {
                glVertex3d(0, 0, 0);
                glVertex3d(1, 0, 0);

                glVertex3d(1, 0, 0);
                glVertex3d(1, 0, 1);

                glVertex3d(1, 0, 1);
                glVertex3d(0, 0, 1);

                glVertex3d(0, 0, 1);
                glVertex3d(0, 0, 0);

                glVertex3d(0, 0, 0);
                glVertex3d(halfScale, height, halfScale);

                glVertex3d(1, 0, 0);
                glVertex3d(halfScale, height, halfScale);

                glVertex3d(1, 0, 1);
                glVertex3d(halfScale, height, halfScale);

                glVertex3d(0, 0, 1);
                glVertex3d(halfScale, height, halfScale);
            }
            glEnd();
        }
        glPopMatrix();
    }

    public static void drawCube(double scale, double x, double y, double z, Vector3f color, boolean outline) {
        glColor3f(color.x, color.y, color.z);

        glPushMatrix();
        glTranslated(x, y, z);
        glScaled(scale, scale, scale);

        glBegin(GL_QUADS);
        {
            // For greater shading variation, Each vertex points out in all 3 directions
            // if vertex value is 0, normal is -1, if value is 1, normal is 1
            // Front
            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);

            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(1, 1, 1);
            glVertex3d(1, 1, 1);

            glNormal3d(-1, 1, 1);
            glVertex3d(0, 1, 1);

            // Right
            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(1, 1, -1);
            glVertex3d(1, 1, 0);

            glNormal3d(1, 1, 1);
            glVertex3d(1, 1, 1);

            // Back
            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(-1, 1, -1);
            glVertex3d(0, 1, 0);

            glNormal3d(1, 1, -1);
            glVertex3d(1, 1, 0);

            // Left
            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);

            glNormal3d(-1, 1, 1);
            glVertex3d(0, 1, 1);

            glNormal3d(-1, 1, -1);
            glVertex3d(0, 1, 0);

            // Bottom
            glNormal3d(-1, -1, -1);
            glVertex3d(0, 0, 0);

            glNormal3d(1, -1, -1);
            glVertex3d(1, 0, 0);

            glNormal3d(1, -1, 1);
            glVertex3d(1, 0, 1);

            glNormal3d(-1, -1, 1);
            glVertex3d(0, 0, 1);

            // Top
            glNormal3d(-1, 1, -1);
            glVertex3d(0, 1, 0);

            glNormal3d(-1, 1, 1);
            glVertex3d(0, 1, 1);

            glNormal3d(1, 1, 1);
            glVertex3d(1, 1, 1);

            glNormal3d(1, 1, -1);
            glVertex3d(1, 1, 0);
        }
        glEnd();

        if (outline) {
            glColor3f(0, 0, 0);
            glBegin(GL_LINES);
            {
                // Bottom
                glVertex3d(0, 0, 0);
                glVertex3d(1, 0, 0);

                glVertex3d(1, 0, 0);
                glVertex3d(1, 0, 1);

                glVertex3d(1, 0, 1);
                glVertex3d(0, 0, 1);

                glVertex3d(0, 0, 1);
                glVertex3d(0, 0, 0);

                // Top
                glVertex3d(0, 1, 0);
                glVertex3d(1, 1, 0);

                glVertex3d(1, 1, 0);
                glVertex3d(1, 1, 1);

                glVertex3d(1, 1, 1);
                glVertex3d(0, 1, 1);

                glVertex3d(0, 1, 1);
                glVertex3d(0, 1, 0);

                // Sides
                glVertex3d(0, 0, 0);
                glVertex3d(0, 1, 0);

                glVertex3d(1, 0, 0);
                glVertex3d(1, 1, 0);

                glVertex3d(1, 0, 1);
                glVertex3d(1, 1, 1);

                glVertex3d(0, 0, 1);
                glVertex3d(0, 1, 1);
            }
            glEnd();
        }

        glPopMatrix();
    }

    public static void drawSphere(double radius, double x, double y, double z, int slices, int stacks, Vector3f color) {
        glColor3f(color.x, color.y, color.z);
        glPushMatrix();
        glTranslated(x, y, z);
        Sphere s = new Sphere();
        s.draw((float) radius, slices, stacks);
        glPopMatrix();
    }
}
