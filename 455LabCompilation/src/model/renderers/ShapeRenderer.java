package model.renderers;

import model.Colors;
import model.HouseModel;
import model.Line3D;
import model.WireFrame;
import model.handlers.TextureHandler;
import model.mapObjects.MapObject;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3d;

public class ShapeRenderer {

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

    /**
     * @param sX    - scale in x
     * @param sY    - scale in y
     * @param sZ    - scale in z
     * @param tX    - translate x
     * @param tY    - translate y
     * @param tZ    - translate z
     * @param angle - rotation angle
     * @param rX    - rotation about x
     * @param rY    - rotation about y
     * @param rZ    - rotation about z
     * @param color - color
     */
    public static void drawQuad(double sX, double sY, double sZ,
                                double tX, double tY, double tZ,
                                double angle, int rX, int rY, int rZ,
                                Vector3f color, boolean normalize,
                                Texture texture) {
        // Textured
        if(texture != null) {
            glColor3f(1, 1, 1);
            glPushMatrix();

            glTranslated(tX, tY, tZ);
            glRotated(angle, rX, rY, rZ);
            glScaled(sX, sY, sZ);

            glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
            glEnable(GL_TEXTURE_2D);
            if (normalize) {
                glBegin(GL_QUADS);
                {
                    glNormal3d(-1, -1, -1);
                    glTexCoord2d(0, texture.getHeight());
                    glVertex3d(0, 0, 0);

                    glNormal3d(1, -1, -1);
                    glTexCoord2d(texture.getWidth(), texture.getHeight());
                    glVertex3d(1, 0, 0);

                    glNormal3d(1, 1, -1);
                    glTexCoord2d(texture.getWidth(), 0);
                    glVertex3d(1, 1, 0);

                    glNormal3d(-1, 1, -1);
                    glTexCoord2d(0, 0);
                    glVertex3d(0, 1, 0);
                }
            } else {
                glBegin(GL_QUADS);
                {
                    glTexCoord2d(0, texture.getHeight());
                    glVertex3d(0, 0, 0);
                    glTexCoord2d(texture.getWidth(), texture.getHeight());
                    glVertex3d(1, 0, 0);
                    glTexCoord2d(texture.getWidth(), 0);
                    glVertex3d(1, 1, 0);
                    glTexCoord2d(0, 0);
                    glVertex3d(0, 1, 0);
                }
            }
            glEnd();
            glDisable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, 0); // Unbind texture
            glPopMatrix();
        }
        else { // No Texture
            glColor3f(color.x, color.y, color.z);
            glPushMatrix();

            glTranslated(tX, tY, tZ);
            glRotated(angle, rX, rY, rZ);
            glScaled(sX, sY, sZ);

            if (normalize) {
                glBegin(GL_QUADS);
                {
                    glNormal3d(-1, -1, -1);
                    glVertex3d(0, 0, 0);

                    glNormal3d(1, -1, -1);
                    glVertex3d(1, 0, 0);

                    glNormal3d(1, 1, -1);
                    glVertex3d(1, 1, 0);

                    glNormal3d(-1, 1, -1);
                    glVertex3d(0, 1, 0);
                }
            } else {
                glBegin(GL_QUADS);
                {
                    glVertex3d(0, 0, 0);
                    glVertex3d(1, 0, 0);
                    glVertex3d(1, 1, 0);
                    glVertex3d(0, 1, 0);
                }
            }
            glEnd();
            glPopMatrix();
        }
    }

    public static void drawWalls(double width, double height, Vector3f color, Texture texture) {
        drawQuad(width, height, 1, -width / 2, 0, -width / 2, 0, 0, 1, 0, color, true, texture); // Front
        drawQuad(width, height, 1, width / 2, 0, width / 2, 180, 0, 1, 0, color, true, texture); // Back
        drawQuad(width, height, 1, width / 2, 0, -width / 2, -90, 0, 1, 0, color, true, texture); // Right
        drawQuad(width, height, 1, -width / 2, 0, width / 2, 90, 0, 1, 0, color, true, texture); // Left
    }

    // Draws floor with normals
    public static void drawFloorTiles(double tileSize, int numTilesInOneDirection, Texture texture1, Texture texture2) {
        for (int x = 0; x < numTilesInOneDirection + 1; x++) {
            for (int z = 0; z < numTilesInOneDirection + 1; z++) {
                Vector3f tileColor;
                Texture texture;
                if ((x + z) % 2 == 0) {
                    tileColor = Colors.WHITE;
                    texture = texture1;
                }
                else {
                    tileColor = Colors.BLACK;
                    texture = texture2;
                }

                glDisable(GL_LIGHTING);
                drawQuad(tileSize, tileSize, 1, tileSize * x, 0, tileSize * z, -90, 1, 0, 0, tileColor, false, texture);
                drawQuad(tileSize, tileSize, 1, tileSize * x, 0, tileSize * -z, -90, 1, 0, 0, tileColor, false, texture); // Reflect across z axis
                drawQuad(tileSize, tileSize, 1, tileSize * -x, 0, tileSize * z, -90, 1, 0, 0, tileColor, false, texture); // Reflect across x axis
                drawQuad(tileSize, tileSize, 1, tileSize * -x, 0, tileSize * -z, -90, 1, 0, 0, tileColor, false, texture); // Reflect across x & z axis
                glEnable(GL_LIGHTING);
            }
        }
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

    public static void drawCube(MapObject object) {
        Texture texture = object.getTexture();
        if(texture != null) {
            drawCubeWithTexture(object, texture);
            return;
        }

        Vector3f color = object.getColor();
        double x = object.getX();
        double y = object.getY();
        double z = object.getZ();
        double scale = object.getScale();
        boolean outline = object.getOutlined();

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

        drawCubeOutline(outline);

        glPopMatrix();
    }

    public static void drawCubeWithTexture(MapObject object, Texture texture) {

        double x = object.getX();
        double y = object.getY();
        double z = object.getZ();
        double scale = object.getScale();
        boolean outline = object.getOutlined();

        glColor3f(1, 1, 1);

        glPushMatrix();
        glTranslated(x, y, z);
        glScaled(scale, scale, scale);

        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        {
            // For greater shading variation, Each vertex points out in all 3 directions
            // if vertex value is 0, normal is -1, if value is 1, normal is 1
            // Front
            glNormal3d(-1, -1, 1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(0, 0, 1); // BL

            glNormal3d(1, -1, 1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(1, 0, 1); // BR

            glNormal3d(1, 1, 1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(1, 1, 1); // TR

            glNormal3d(-1, 1, 1);
            glTexCoord2d(0, 0);
            glVertex3d(0, 1, 1); // TL

            // Right
            glNormal3d(1, -1, 1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(1, 0, 1);

            glNormal3d(1, -1, -1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(1, 0, 0);

            glNormal3d(1, 1, -1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(1, 1, 0);

            glNormal3d(1, 1, 1);
            glTexCoord2d(0, 0);
            glVertex3d(1, 1, 1);

            // Back
            glNormal3d(1, -1, -1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(1, 0, 0);

            glNormal3d(-1, -1, -1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(0, 0, 0);

            glNormal3d(-1, 1, -1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(0, 1, 0);

            glNormal3d(1, 1, -1);
            glTexCoord2d(0, 0);
            glVertex3d(1, 1, 0);

            // Left
            glNormal3d(-1, -1, -1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(0, 0, 0);

            glNormal3d(-1, -1, 1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(0, 0, 1);

            glNormal3d(-1, 1, 1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(0, 1, 1);

            glNormal3d(-1, 1, -1);
            glTexCoord2d(0, 0);
            glVertex3d(0, 1, 0);

            // Bottom
            glNormal3d(-1, -1, -1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(0, 0, 0);

            glNormal3d(1, -1, -1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(1, 0, 0);

            glNormal3d(1, -1, 1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(1, 0, 1);

            glNormal3d(-1, -1, 1);
            glTexCoord2d(0, 0);
            glVertex3d(0, 0, 1);

            // Top
            glNormal3d(-1, 1, -1);
            glTexCoord2d(0, texture.getHeight());
            glVertex3d(0, 1, 1);

            glNormal3d(-1, 1, 1);
            glTexCoord2d(texture.getWidth(), texture.getHeight());
            glVertex3d(1, 1, 1);

            glNormal3d(1, 1, 1);
            glTexCoord2d(texture.getWidth(), 0);
            glVertex3d(1, 1, 0);

            glNormal3d(1, 1, -1);
            glTexCoord2d(0, 0);
            glVertex3d(0, 1, 0);
        }
        glEnd();
        glDisable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0); // Unbind texture

        drawCubeOutline(outline);
        glPopMatrix();
    }

    public static void drawCubeOutline(boolean outline) {
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
