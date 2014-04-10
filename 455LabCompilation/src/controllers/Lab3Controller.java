package controllers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Lab3Controller extends LabController {
    public void init() {
        drawMode = 2;
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
                doViewportTransformation();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyViewportTransformation();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doDivideByW();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyDivideByW();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (drawMode == 1) {
                // IMPORTANT: GL_DEPTH_BUFFER_BIT must be cleared for depth to work in rendering
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doDepthTest();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyDepthTest();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doEasyPointClipping();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyEasyPointClipping();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyMatrixManipulation();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyFunkyShape();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyRotation();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyTranslation();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyScale();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyOrtho();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_APOSTROPHE)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doShear();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyShear();
                render();
            }
        }
    }

    /**
     * 1. Draw your triangles/polygons (i.e. call glBegin, glVertex3f, glEnd, etc.)
     * <p/>
     * 2. Multiply all points by the modelview and projection matrices
     * <p/>
     * 3. Divide by the w that results from step 2
     * <p/>
     * 4. Clip everything to the square from -1,-1 to 1,1 (This is by convention, and because it makes other things work out
     * nicely). However, if you are only doing point clipping, you can skip this part and do it when you plot individual pixels.
     * If you do that, you just have to check to make sure the point is in the viewport (not just the screen).
     * <p/>
     * 5. Size to your viewport. Basically, add 1 to all x and y coordinates, and then divide by two. This will put all your points
     * between 0,0 and 1,1. Then, multiply your x coordinate by the viewport width and add the x viewport min. Do the
     * same to your y coordinate with the height and y viewport min.
     * <p/>
     * 6. Use your triangle/polygon/line code as usual using the new coordinates, converting your points to integer points if your
     * code needs that.
     * <p/>
     * 7. When you set each pixel, check the z buer to see if you should set the pixel. This is essentially your clipping in z.
     */

    /* *********************************************************
    *                   OPEN GL FUNCTIONS - LAB 3
    * *********************************************************/
    protected void openGlDrawingSetup() {
        // Save the old state so that you can set it back after you draw\
        //        boolean depthWasEnabled = glIsEnabled(GL_DEPTH_TEST);
        //        glDisable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    protected void doViewportTransformation() {
        openGlDrawingSetup();

        glViewport(0, 0, 320, 240);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 1, 0);
        glVertex3f(-1, 0, 0);
        glVertex3f(0, -0.8f, 0);
        glVertex3f(0.5f, 0.8f, 0);
        glEnd();

        glViewport(320, 240, 320, 240);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-1, 0.8f, 0);
        glVertex3f(0.1f, -0.8f, 0);
        glVertex3f(0.5f, 0.8f, 0);
        glEnd();

        // Restore your viewport to the whole screen
        glViewport(0, 0, 640, 480);
    }

    protected void doDivideByW() {
        openGlDrawingSetup();

        glBegin(GL_TRIANGLES);
        glColor3f(1, 0, 0);
        glVertex4f(-1, 0.2f, 0, 1);
        glVertex4f(0, 0.8f, 0, 1);
        glVertex4f(1, 0.2f, 0, 1);
        glColor3f(1, 0, 1);
        glVertex4f(-1, -0.8f, 0, 2);
        glVertex4f(0, -0.2f, 0, 2);
        glVertex4f(1, -0.8f, 0, 2);
        glEnd();
    }

    protected void doDepthTest() {
        openGlDrawingSetup();

        glEnable(GL_DEPTH_TEST);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 1, 1);
        glVertex3f(-0.5f, 0.2f, 0.5f);
        glVertex3f(0, -0.5f, 0);
        glVertex3f(0.5f, 0.2f, -0.5f);
        glColor3f(1, 1, 0);
        glVertex3f(-0.5f, -0.2f, -0.5f);
        glVertex3f(0, 0.5f, 0);
        glVertex3f(0.5f, -0.2f, 0.5f);
        glEnd();
    }

    protected void doEasyPointClipping() {
        openGlDrawingSetup();

        glEnable(GL_DEPTH_TEST);
        glBegin(GL_TRIANGLES);
        glColor3f(0.5f, 1, 1);
        glVertex3f(0.5f, 0, 0);
        glVertex3f(0, 0.5f, -2);
        glVertex3f(0, -0.5f, 2);
        glEnd();

        glViewport(50, 50, 200, 400);
        glBegin(GL_TRIANGLES);
        glColor3f(1, 1, 0.5f);
        glVertex3f(-1.4f, -1.2f, -0.5f);
        glVertex3f(0, 1.2f, 0);
        glVertex3f(1.5f, -0.2f, 0.5f);
        glEnd();

        // Restore your viewport to the whole screen
        glViewport(0, 0, 640, 480);
    }

    protected void doShear() {
        openGlDrawingSetup();

        glLoadIdentity();
        glPushMatrix();
        shear(.1f, 0, 0, 0, 0, 0);
        glBegin(GL_TRIANGLES);
        glColor3f(0.8f, 0.5f, 1);
        glVertex3f(0.5f, 0.4f, 1);
        glVertex3f(0.8f, 0.4f, 1);
        glVertex3f(0.65f, 0.9f, 1);
        glEnd();

        glLoadIdentity();
        shear(-.1f, 0, 0, 0, 0, 0);
        glBegin(GL_TRIANGLES);
        glColor3f(1, 0, 0);
        glVertex3f(0.5f, 0.4f, 1);
        glVertex3f(0.8f, 0.4f, 1);
        glVertex3f(0.65f, 0.9f, 1);
        glEnd();

        glLoadIdentity();
        shear(0, 0, .1f, 0, 0, 0);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(0.5f, 0.4f, 1);
        glVertex3f(0.8f, 0.4f, 1);
        glVertex3f(0.65f, 0.9f, 1);
        glEnd();

        glLoadIdentity();
        shear(0, .25f, 0, 0, 0, 0);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 1, 0);
        glVertex3f(0.5f, 0.4f, 1);
        glVertex3f(0.8f, 0.4f, 1);
        glVertex3f(0.65f, 0.9f, 1);
        glEnd();

        glLoadIdentity();
        shear(0, -.5f, 0, 0, 0, 0);
        glBegin(GL_TRIANGLES);
        glColor3f(0, 1, 1);
        glVertex3f(0.5f, 0.4f, 1);
        glVertex3f(0.8f, 0.4f, 1);
        glVertex3f(0.65f, 0.9f, 1);
        glEnd();
    }

    /* *********************************************************
    *                   DRAWING FUNCTIONS
    * *********************************************************/
    protected void doMyViewportTransformation() {
        doGLLoadIdentity();
        doGLViewport(0, 0, 320, 240);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 1, 0);
        doGLVertex3f(-1, 0, 0);
        doGLVertex3f(0, -0.8f, 0);
        doGLVertex3f(0.5f, 0.8f, 0);
        doGLEnd();

        doGLViewport(320, 240, 320, 240);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(-1, 0.8f, 0);
        doGLVertex3f(0.1f, -0.8f, 0);
        doGLVertex3f(0.5f, 0.8f, 0);
        doGLEnd();

        // Restore your viewport to the whole screen
        doGLViewport(0, 0, 640, 480);
    }

    protected void doMyDivideByW() {
        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 0, 0);
        doGLVertex4f(-1, 0.2f, 0, 1);
        doGLVertex4f(0, 0.8f, 0, 1);
        doGLVertex4f(1, 0.2f, 0, 1);
        doGLColor3f(1, 0, 1);
        doGLVertex4f(-1, -0.8f, 0, 2);
        doGLVertex4f(0, -0.2f, 0, 2);
        doGLVertex4f(1, -0.8f, 0, 2);
        doGLEnd();
    }

    protected void doMyDepthTest() {
        doGLLoadIdentity();
        doGLEnable(GL_DEPTH_TEST);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 1, 1);
        doGLVertex3f(-0.5f, 0.2f, 0.5f);
        doGLVertex3f(0, -0.5f, 0);
        doGLVertex3f(0.5f, 0.2f, -0.5f);

        doGLColor3f(1, 1, 0);
        doGLVertex3f(-0.5f, -0.2f, -0.5f);
        doGLVertex3f(0, 0.5f, 0);
        doGLVertex3f(0.5f, -0.2f, 0.5f);
        doGLEnd();
    }

    protected void doMyEasyPointClipping() {
        doGLLoadIdentity();
        doGLEnable(GL_DEPTH_TEST);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.5f, 1, 1);
        doGLVertex3f(0.5f, 0, 0);
        doGLVertex3f(0, 0.5f, -2);
        doGLVertex3f(0, -0.5f, 2);
        doGLEnd();

        doGLViewport(50, 50, 200, 400);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 1, 0.5f);
        doGLVertex3f(-1.4f, -1.2f, -0.5f);
        doGLVertex3f(0, 1.2f, 0);
        doGLVertex3f(1.5f, -0.2f, 0.5f);
        doGLEnd();

        // Restore your viewport to the whole screen
        doGLViewport(0, 0, 640, 480);
    }

    protected void doMyMatrixManipulation() {
        doGLLoadIdentity();
        float[] translate = {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                -1.2f, 0.3f, 0, 1};
        float[] rotate = {
                (float) Math.cos(Math.PI / 2), (float) Math.sin(Math.PI / 2), 0, 0,
                (float) -Math.sin(Math.PI / 2), (float) Math.cos(Math.PI / 2), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};

        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.5f, 0.2f, 1);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLLoadMatrixd(translate);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.5f, 0.8f, 0.2f);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.2f, 0.6f, 1);
        doGLVertex3f(0.5f, -0.4f, 0);
        doGLVertex3f(0.8f, -0.4f, 0);
        doGLVertex3f(0.65f, -0.7f, 0);
        doGLEnd();

        doGLLoadMatrixd(rotate);
        doGLMultMatrixf(translate);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.9f, 0.2f, 0.4f);
        doGLVertex3f(0.5f, -0.4f, 0);
        doGLVertex3f(0.8f, -0.4f, 0);
        doGLVertex3f(0.65f, -0.7f, 0);
        doGLEnd();

        doGLLoadIdentity();
    }

    private void doMyFunkyShape() {
        doGLLoadIdentity();
        doGLColor3f(0, 1, 1);
        float r2 = (float) (1 / Math.sqrt(2));
        float[] mDown = {
                0, -r2, 0, 0,
                r2, 0, 0, 0,
                0, 0, 1, 0,
                0, -r2, 0, 1};
        float[] mUp = {
                0, r2, 0, 0,
                -r2, 0, 0, 0,
                0, 0, 1, 0,
                0, r2, 0, 1};
        tree(8, r2, mDown, mUp);
    }

    private void tree(int depth, float r2, float[] mDown, float[] mUp) {
        if (depth <= 0) return;

        doGLBegin(GL_LINES);
        doGLVertex2f(0, -r2);
        doGLVertex2f(0, r2);
        doGLEnd();

        doGLPushMatrix();

        doGLMultMatrixf(mDown);
        tree(depth - 1, r2, mDown, mUp);

        doGLPopMatrix();
        doGLPushMatrix();

        doGLMultMatrixf(mUp);
        tree(depth - 1, r2, mDown, mUp);

        doGLPopMatrix();
    }

    private void doMyRotation() {
        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.5f, 0.2f, 1);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLRotatef(90, 0, 0, 1);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.1f, 0.2f, 1);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

//        doGLLoadIdentity();
//        doGLRotatef(180, 1, 0, 0);
//        doGLBegin(GL_TRIANGLES);
//        doGLColor3f(0, 1, 0);
//        doGLVertex3f(0.5f, 0.1f, 0);
//        doGLVertex3f(0.8f, 0.1f, 0);
//        doGLVertex3f(0.65f, 0.4f, 0);
//        doGLEnd();
//
//        doGLLoadIdentity();
//        doGLRotatef(135, 0, 1, 0);
//        doGLBegin(GL_TRIANGLES);
//        doGLColor3f(1, 0, 0);
//        doGLVertex3f(0.5f, 0.1f, 0);
//        doGLVertex3f(0.8f, 0.1f, 0);
//        doGLVertex3f(0.65f, 0.4f, 0);
//        doGLEnd();
    }

    private void doMyTranslation() {
        doGLLoadIdentity();

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.33f, 0.77f, 0);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLTranslatef(-1, -1, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.77f, 0.2f, 0.3f);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLLoadIdentity();
    }

    private void doMyScale() {
        doGLLoadIdentity();
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.9f, 0.5f, 1);
        doGLVertex3f(0.5f, 0.4f, 0);
        doGLVertex3f(0.8f, 0.4f, 0);
        doGLVertex3f(0.65f, 0.9f, 0);
        doGLEnd();

        doGLScalef(0.8f, 0.7f, 1);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.8f, 0.7f, 0);
        doGLVertex3f(0.5f, 0.1f, 0);
        doGLVertex3f(0.8f, 0.1f, 0);
        doGLVertex3f(0.65f, 0.4f, 0);
        doGLEnd();

        doGLLoadIdentity();
    }

    private void doMyOrtho() {
        doGLLoadIdentity();
        doGLOrtho(0, 640, 0, 480, -1, 1);

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 0, 0);
        doGLVertex3f(300, 300, 0);
        doGLColor3f(0, 1, 0);
        doGLVertex3f(600, 300, 0);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(450, 410, 0);

        doGLColor3f(1, 1, 0);
        doGLVertex3f(100, 400, 0);
        doGLColor3f(0, 1, 1);
        doGLVertex3f(70, 60, 0);
        doGLColor3f(1, 0, 1);
        doGLVertex3f(350, 100, 0);
        doGLEnd();
    }

    private void doMyLookAt() {
        doGLLoadIdentity();
        doGluLookAt(320, 96, .2f, 320, 96, 0, 0, 0, 0);

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 0, 0);
        doGLVertex4f(-1, 0.2f, 0, 1);
        doGLVertex4f(0, 0.8f, 0, 1);
        doGLVertex4f(1, 0.2f, 0, 1);

        doGLColor3f(1, 0, 1);
        doGLVertex4f(-1, -0.8f, 0, 2);
        doGLVertex4f(0, -0.2f, 0, 2);
        doGLVertex4f(1, -0.8f, 0, 2);
        doGLEnd();
    }

    private void doMyShear() {
        doGLLoadIdentity();
        shear(.1f, 0, 0, 0, 0, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0.8f, 0.5f, 1);
        doGLVertex3f(0.5f, 0.4f, 1);
        doGLVertex3f(0.8f, 0.4f, 1);
        doGLVertex3f(0.65f, 0.9f, 1);
        doGLEnd();

        doGLLoadIdentity();
        shear(-.1f, 0, 0, 0, 0, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 0, 0);
        doGLVertex3f(0.5f, 0.4f, 1);
        doGLVertex3f(0.8f, 0.4f, 1);
        doGLVertex3f(0.65f, 0.9f, 1);
        doGLEnd();

        doGLLoadIdentity();
        shear(0, 0, .1f, 0, 0, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(0.5f, 0.4f, 1);
        doGLVertex3f(0.8f, 0.4f, 1);
        doGLVertex3f(0.65f, 0.9f, 1);
        doGLEnd();

        doGLLoadIdentity();
        shear(0, .25f, 0, 0, 0, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 1, 0);
        doGLVertex3f(0.5f, 0.4f, 1);
        doGLVertex3f(0.8f, 0.4f, 1);
        doGLVertex3f(0.65f, 0.9f, 1);
        doGLEnd();

        doGLLoadIdentity();
        shear(0, -.5f, 0, 0, 0, 0);
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 1, 1);
        doGLVertex3f(0.5f, 0.4f, 1);
        doGLVertex3f(0.8f, 0.4f, 1);
        doGLVertex3f(0.65f, 0.9f, 1);
        doGLEnd();

//        doGLLoadIdentity();
//        doGLBegin(GL_TRIANGLES);
//        doGLColor3f(1, 1, 1);
//        doGLVertex3f(0.5f, 0.4f, 1);
//        doGLVertex3f(0.8f, 0.4f, 1);
//        doGLVertex3f(0.65f, 0.9f, 1);
//        doGLEnd();
    }
}
