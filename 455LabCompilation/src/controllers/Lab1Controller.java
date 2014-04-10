package controllers;

import org.lwjgl.opengl.Display;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.lwjgl.opengl.GL11.*;

public class Lab1Controller extends LabController {
    private ByteBuffer raster;

    public void init() {
        raster = ByteBuffer.allocateDirect(640 * 480 * 4 * 3);
        raster.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < (640 * 480 * 3); i++) {
            float value = ((float) i) / (640 * 480 * 3);
            raster.putFloat(value);
        }
        raster.rewind(); // Put pointer at the front again
    }

    // This method is called to "resize" the viewport to match the screen.
    // When you first start, have it be in perspective mode.
    public void resizeGL() {
        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void update() {
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        // Set the clear color
        glClearColor(1, 0, 0, 1);
        // Clear the screen to the clear color (i.e. if the clear color is red , the screen turns red );
        glClear(GL_COLOR_BUFFER_BIT);
        if (drawMode == 2) {
            // Save the old state so that you can set it back after you draw\
            boolean depthWasEnabled = glIsEnabled(GL_DEPTH_TEST);
            glDisable(GL_DEPTH_TEST);
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();
            glMatrixMode(GL_PROJECTION);
            glPushMatrix();
            glLoadIdentity();
            // Draw the array of pixels ( This is where you draw the values you have stored in the array 'raster ')
            glRasterPos2f(-1, -1);
            glDrawPixels(640, 480, GL_RGB, GL_FLOAT, raster);
            // Set the state back to what it was
            glPopMatrix();
            glMatrixMode(GL_MODELVIEW);
            glPopMatrix();

            if (depthWasEnabled)
                glEnable(GL_DEPTH_TEST);
        }
        glFlush();
    }
}

