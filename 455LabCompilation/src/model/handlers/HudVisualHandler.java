package model.handlers;

import driver.LWJGLSandbox;
import game.GameController;
import model.Colors;
import model.renderers.ShapeRenderer;
import model.renderers.TextRenderer;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class HudVisualHandler {
    private static final int WIDTH = LWJGLSandbox.DISPLAY_WIDTH;
    private static final int HEIGHT = LWJGLSandbox.DISPLAY_HEIGHT;

    private ShapeRenderer shapeRenderer;

    public HudVisualHandler(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void drawLife(float percentLife) {
        Vector3f color = determineHpColor(percentLife);

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glDisable(GL_LIGHTING);
        shapeRenderer.drawQuad(400 * percentLife, 20, 1, 295, HEIGHT - 25, 0, 0, 0, 0, 0, color, false, ShapeRenderer.NO_TEXTURE);
        glEnable(GL_LIGHTING);
        glPopMatrix();

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
    }

    private Vector3f determineHpColor(float percentLife) {
        if(percentLife > .8f) {
            return Colors.BLUE;
        }
        if(percentLife > .6f) {
            return Colors.GREEN;
        }
        if(percentLife > .4f) {
            return Colors.YELLOW;
        }
        if(percentLife > .2f) {
            return Colors.ORANGE;
        }
        return Colors.RED;
    }
}
