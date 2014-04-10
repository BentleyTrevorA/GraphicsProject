package model.mapObjects;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class TextHandler {
    /**
     * Draw shot count on all the walls
     */
    public void drawShotsRemaining(double maxShots, double shotsTaken, Vector3f color) {
        glColor3f(color.x, color.y, color.z);
//        glRotated(-camera.rotateAngle, 0, 1, 0);
//        TextRenderer.drawString("Shot Remaining: " + (maxShots - shots.size()), camera.xPos -102, camera.yPos + 64, camera.zPos - 93);
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        model.TextRenderer.drawString("Shot Remaining: " + (maxShots - shotsTaken), -50, 50, -250);

        glRotatef(90, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Shot Remaining: " + (maxShots - shotsTaken), -50, 50, -250);

        glRotatef(180, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Shot Remaining: " + (maxShots - shotsTaken), -50, 50, -250);

        glRotatef(-90, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Shot Remaining: " + (maxShots - shotsTaken), -50, 50, -250);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    public void drawPoints(int points, Vector3f color) {
        glColor3f(color.x, color.y, color.z);

        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        model.TextRenderer.drawString("Points: " + points, -50, 40, -250);

        glRotatef(90, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Points: " + points, -50, 40, -250);

        glRotatef(180, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Points: " + points, -50, 40, -250);

        glRotatef(-90, 0f, 1.0f, 0f);
        model.TextRenderer.drawString("Points: " + points, -50, 40, -250);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }
}
