package model.handlers;

import camera.Camera;
import driver.LWJGLSandbox;
import game.GameController;
import model.Colors;
import model.Model;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class HudVisualHandler {
    private static final int WIDTH = LWJGLSandbox.DISPLAY_WIDTH;
    private static final int HEIGHT = LWJGLSandbox.DISPLAY_HEIGHT;

    private TextureHandler textureHandler;
    private boolean drawingMiniMap = false;

    public HudVisualHandler(TextureHandler textureHandler) {
        this.textureHandler = textureHandler;
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
        ShapeRenderer.drawQuad(400 * percentLife, 20, 1, 295, HEIGHT - 25, 0, 0, 0, 0, 0, color, false,
                textureHandler.getTexture(TextureHandler.NO_TEXTURE));
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

    public void drawMiniMap(Camera camera) {
        drawingMiniMap = true;
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glRotated(-90, 1, 0, 0);

//        glRotated(camera.rotateAngle, 0f, 1.0f, 0f);
//        glOrtho(camera.xPos - 50, camera.xPos + 50,
//                camera.zPos - 50, camera.zPos + 50,
//                Model.MIN_MAP_COORDINATE, Model.MAX_MAP_COORDINATE);
//        glTranslated(33, 10, 160);
//        glScaled(.06, 1, .32);

        glOrtho(0, LWJGLSandbox.DISPLAY_WIDTH, -150, LWJGLSandbox.DISPLAY_HEIGHT, Model.MIN_MAP_COORDINATE, Model.MAX_MAP_COORDINATE);
//        glTranslated(715 + GameController.a, 10, 115 + GameController.b);
        glTranslated(810, 10, 145);
//        glScaled(.50 + GameController.c, 1, .35 + GameController.d);
        glScaled(.55, 1, .4);

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    public void cleanupDrawingMiniMap() {
        if(drawingMiniMap) {
            glMatrixMode(GL_MODELVIEW);
            glPopMatrix();
            // Pop orthographic matrix off of stack
            glMatrixMode(GL_PROJECTION);
            glPopMatrix();

            drawingMiniMap = false;
        }
    }
}
