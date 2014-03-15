package Controllers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class Lab4Controller extends LabController {
    public void init() {
        drawMode = 1;
    }

    // This method is called to "resize" the viewport to match the screen.
    // When you first start, have it be in perspective mode.
    public void resizeGL() {
        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        doGLViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    // This is called every frame, and should be responsible for keyboard updates.
    public void updateKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            // OpenGL implementation
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            drawMode = 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            // my implementation
            drawMode = 2;
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doOpenGLFrustrum();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyGLFrustrum();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doOpenGLPerspective();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
//                doMyViewportTransformation();
                render();
            }
        }
    }

    /* ****************************
     *      OPENGL Functions
     * ****************************/
    public void doOpenGLFrustrum() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(-0.1, 0.1, -0.1 * 480 / 640, 0.1 * 480 / 640, 0.1, 10);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-0.4f, -0.6f, -1);
        glVertex3f(0.4f, -0.6f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(-0.4f, -0.1f, -1);
        glVertex3f(-0.4f, -0.6f, -1);
        glColor3f(1, 0, 1);
        glVertex3f(-0.4f, -0.1f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(0.3f, 0, -2);
        glVertex3f(0.3f, 0, -2);
        glVertex3f(-0.3f, 0, -2);
        glVertex3f(-0.4f, -0.1f, -1);
        glEnd();
    }

    public void doOpenGLPerspective() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(90f, (640f) / 480f, .1f, 10f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-0.4f, -0.6f, -1);
        glVertex3f(0.4f, -0.6f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(-0.4f, -0.1f, -1);
        glVertex3f(-0.4f, -0.6f, -1);
        glColor3f(1, 0, 1);
        glVertex3f(-0.4f, -0.1f, -1);
        glVertex3f(0.4f, -0.1f, -1);
        glVertex3f(0.3f, 0, -2);
        glVertex3f(0.3f, 0, -2);
        glVertex3f(-0.3f, 0, -2);
        glVertex3f(-0.4f, -0.1f, -1);
        glEnd();
    }

    /* ****************************
     *         My Functions
     * ****************************/
    public void doMyGLFrustrum() {
        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLFrustumD(-0.1, 0.1, -0.1 * 480 / 640, 0.1 * 480 / 640, 0.1, 10);
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(-0.4f, -0.6f, -1);
        doGLVertex3f(0.4f, -0.6f, -1);
        doGLVertex3f(0.4f, -0.1f, -1);
        doGLVertex3f(0.4f, -0.1f, -1);
        doGLVertex3f(-0.4f, -0.1f, -1);
        doGLVertex3f(-0.4f, -0.6f, -1);
        doGLColor3f(1, 0, 1);
        doGLVertex3f(-0.4f, -0.1f, -1);
        doGLVertex3f(0.4f, -0.1f, -1);
        doGLVertex3f(0.3f, 0, -2);
        doGLVertex3f(0.3f, 0, -2);
        doGLVertex3f(-0.3f, 0, -2);
        doGLVertex3f(-0.4f, -0.1f, -1);
        doGLEnd();
    }
 }
