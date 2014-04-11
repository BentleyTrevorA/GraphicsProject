package model.renderers;

import driver.LWJGLSandbox;
import game.GameController;
import model.Colors;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class TextHandler {
    private static final int WIDTH = LWJGLSandbox.DISPLAY_WIDTH;
    private static final int HEIGHT = LWJGLSandbox.DISPLAY_HEIGHT;
    private Vector3f color = Colors.WHITE;

    public void setTextColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Draw shot count on all the walls
     */
    public void drawShotsRemaining(int maxShots, int shotsTaken) {
        drawTextToScreen("Shots Remaining: " + (maxShots - shotsTaken), 1, HEIGHT - 10);
    }

    public void drawPoints(int points) {
        drawTextToScreen("Points: " + points, 1, HEIGHT - 25);
    }

    public void drawNumEnemies(int numEnemies) {
        drawTextToScreen("Enemies: " + numEnemies, WIDTH - 100, HEIGHT - 10);
    }

    private void drawTextToScreen(String text, int xPos, int yPos) {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glDisable(GL_LIGHTING);
        glColor3f(color.x, color.y, color.z);

        TextRenderer.drawString(text, xPos, yPos, 0);
        glEnable(GL_LIGHTING);
        glPopMatrix();

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
    }
}
