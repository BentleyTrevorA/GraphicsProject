package model.renderers;

import model.Colors;
import model.HouseModel;
import model.Line3D;
import model.WireFrame;
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
    public String imgLocation = "img/textures/";
    public Map<Integer, Texture> textures;

    public static final int ENEMY_HP_6 = 0;
    public static final int ENEMY_HP_5 = 1;
    public static final int ENEMY_HP_4 = 2;
    public static final int ENEMY_HP_3 = 3;
    public static final int ENEMY_HP_2 = 4;
    public static final int ENEMY_HP_1 = 5;
    public static final int STONE_1 = 6;
    public static final int STONE_2 = 7;
    public static final int STONE_3 = 8;
    public static final int STONE_4 = 9;

    public ShapeRenderer() {
        loadTextures();
    }

    public void loadTextures() {
        try {
            textures = new HashMap<Integer, Texture>();

            textures.put(ENEMY_HP_6, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar1.png")));
            textures.put(ENEMY_HP_5, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar2.png")));
            textures.put(ENEMY_HP_4, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar3.png")));
            textures.put(ENEMY_HP_3, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar4.png")));
            textures.put(ENEMY_HP_2, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar5.png")));
            textures.put(ENEMY_HP_1, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar6.png")));

            textures.put(STONE_1, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone1.png")));
            textures.put(STONE_2, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone2.png")));
            textures.put(STONE_3, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone3.png")));
            textures.put(STONE_4, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone4.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawHouse(Vector3f houseColor, Vector3f outlineColor) {
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
    public void drawQuad(double sX, double sY, double sZ,
                                double tX, double tY, double tZ,
                                double angle, int rX, int rY, int rZ,
                                Vector3f color, boolean normalize) {
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

    public void drawWalls(double width, double height, Vector3f color) {
        drawQuad(width, height, 1, -width / 2, 0, -width / 2, 0, 0, 1, 0, color, true); // Front
        drawQuad(width, height, 1, width / 2, 0, width / 2, 180, 0, 1, 0, color, true); // Back
        drawQuad(width, height, 1, width / 2, 0, -width / 2, -90, 0, 1, 0, color, true); // Right
        drawQuad(width, height, 1, -width / 2, 0, width / 2, 90, 0, 1, 0, color, true); // Left
    }

    // Draws floor with normals
    public void drawFloorTiles(double tileSize, int numTilesInOneDirection) {
        for (int x = 0; x < numTilesInOneDirection + 1; x++) {
            for (int z = 0; z < numTilesInOneDirection + 1; z++) {
                Vector3f tileColor;
                if ((x + z) % 2 == 0)
                    tileColor = Colors.WHITE;
                else
                    tileColor = Colors.BLACK;

                glDisable(GL_LIGHTING);
                drawQuad(tileSize, tileSize, 1, tileSize * x, 0, tileSize * z, -90, 1, 0, 0, tileColor, false);
                drawQuad(tileSize, tileSize, 1, tileSize * x, 0, tileSize * -z, -90, 1, 0, 0, tileColor, false); // Reflect across z axis
                drawQuad(tileSize, tileSize, 1, tileSize * -x, 0, tileSize * z, -90, 1, 0, 0, tileColor, false); // Reflect across x axis
                drawQuad(tileSize, tileSize, 1, tileSize * -x, 0, tileSize * -z, -90, 1, 0, 0, tileColor, false); // Reflect across x & z axis
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
    public void drawPyramid(double scale, double x, double y, double z, Vector3f color, boolean outline) {
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

    public void drawCube(MapObject object) {
        if(object.getTextureNumber() > 0) {
            drawCubeWithTexture(object);
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

    public void drawCubeWithTexture(MapObject object) {
        Texture texture = textures.get(object.getTextureNumber());

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

    public void drawCubeOutline(boolean outline) {
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

    public void drawSphere(double radius, double x, double y, double z, int slices, int stacks, Vector3f color) {
        glColor3f(color.x, color.y, color.z);
        glPushMatrix();
        glTranslated(x, y, z);
        Sphere s = new Sphere();
        s.draw((float) radius, slices, stacks);
        glPopMatrix();
    }
}
