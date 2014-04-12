package model.handlers;

import driver.LWJGLSandbox;
import game.GameController;
import model.Colors;
import model.renderers.TextRenderer;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class HudTextHandler {
    private static final int WIDTH = LWJGLSandbox.DISPLAY_WIDTH;
    private static final int HEIGHT = LWJGLSandbox.DISPLAY_HEIGHT;
    private Vector3f color = Colors.WHITE;

    public void setTextColor(Vector3f color) {
        this.color = color;
    }

    /**
     * Draw shot count on all the walls
     */
    public void drawShotsRemaining(int shotsRemaining) {
        drawTextToScreen("Shots Remaining: " + shotsRemaining, 1, HEIGHT - 15);
    }

    public void drawPoints(int points) {
        drawTextToScreen("Points: " + points, 1, HEIGHT - 30);
    }

    public void drawNumEnemies(int numEnemies) {
        drawTextToScreen("Enemies Remaining: " + numEnemies, WIDTH - 175, HEIGHT - 15);
    }

    public void drawHpRemaining(int hp, int maxHp) {
        drawTextToScreen("HP: " + hp + "/" + maxHp, 300, HEIGHT - 35);
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
