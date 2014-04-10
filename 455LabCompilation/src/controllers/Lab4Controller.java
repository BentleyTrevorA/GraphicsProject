package controllers;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Lab4Controller extends LabController {
    private float x, y, z;

    public void init() {
        drawMode = 2;
        x = 0;
        y = 3;
        z = -10;
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
                doMyGLPerspective();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_LIGHTING_BIT);
                doOpenGLGouraud();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyGLGouraud();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            x--;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
            x++;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            y--;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            y++;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            z--;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            z++;
            drawExperimentGouraud();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doLinearFog();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyLinearFog();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doExponentialFog();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyExponentialFog();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doExponentialSquaredFog();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyExponentialSquaredFog();
                render();
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
            if (drawMode == 1) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                doOpenGLSpecular();
            } else {
                doGLClearColor(0, 0, 0, 0);
                doGLClear(GL_COLOR_BUFFER_BIT);
                doGLClear(GL_DEPTH_BUFFER_BIT);
                doMyGLSpecular();
                render();
            }
        }
    }

    private void drawExperimentGouraud() {
        if (drawMode == 1) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_LIGHTING_BIT);
        } else {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLClear(GL_DEPTH_BUFFER_BIT);
//            System.out.println("(" + x + ", " + y + ", " + z + ")");
            doMyGLGouraudExperiment(x, y, z);
            render();
        }
    }

    /* ****************************
     *      OPENGL Functions
     * ****************************/
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

    //http://sjbaker.org/steve/omniv/opengl_lighting.html
    public void doOpenGLGouraud() {
        openGlDrawingSetup();
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * 4);
        temp.order(ByteOrder.LITTLE_ENDIAN);

        glEnable(GL_NORMALIZE);
        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHT0);
        float[] diffuse_color = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
        float[] position = {0, 3, -10, 1};


        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color).flip());
        glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color).flip());
        glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position).flip());
        glColor3f(1, 0, 0);
        float dp = (float) (Math.PI / 16); // 16 picked arbitrarily ; try other numbers too

        glBegin(GL_TRIANGLES);
        for (float theta = 0; theta < 2 * Math.PI; theta += dp) {
            for (float phi = 0; phi < Math.PI; phi += dp) {
                glNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                glNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                glNormal3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
            }
        }
        glEnd();
        glDisable(GL_LIGHTING);
    }

    public void doOpenGLSpecular() {
        openGlDrawingSetup();
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * 4);
        temp.order(ByteOrder.LITTLE_ENDIAN);

        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHT0);
        float[] diffuse_color = {.7f, .7f, .7f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
        float[] specular_color = {0, 1, 0, 1};
        float[] position = {1, 0, -10, 1};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color).flip());
        glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color).flip());
        glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer) temp.asFloatBuffer().put(specular_color).flip());
        glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position).flip());

        float[] specular_surface_color = {0.0f, 1.0f, 0.9f, 1};
        glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, (FloatBuffer) temp.asFloatBuffer().put(specular_surface_color).flip());
        glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 1);

        glColor3f(1, 0, 0);
        float dp = (float) (Math.PI / 16); // 16 picked arbitrarily ; try other numbers too

        glBegin(GL_TRIANGLES);
        for (float theta = 0; theta < 2 * Math.PI; theta += dp) {
            for (float phi = 0; phi < Math.PI; phi += dp) {
                glNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                glNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                glNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                glNormal3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
                glVertex3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
            }
        }
        glEnd();
        glDisable(GL_LIGHTING);
    }

    public void doLinearFog() {
        glEnable(GL_FOG);

        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(0).put(1).put(0).put(1).flip();
        glFog(GL_FOG_COLOR, fogColor);

        glFogf(GL_FOG_MODE, GL_LINEAR);
        glFogf(GL_FOG_START, 0.4f);
        glFogf(GL_FOG_END, 1);
        glFogf(GL_FOG_DENSITY, 1);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glColor3f(1, 0, 1);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(-0.3f, 0.5f, -0.99f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glEnd();

        glDisable(GL_FOG);
    }

    public void doExponentialFog() {
        glEnable(GL_FOG);

        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(0).put(1).put(0).put(1).flip();
        glFog(GL_FOG_COLOR, fogColor);

        glFogf(GL_FOG_MODE, GL_EXP);
        glFogf(GL_FOG_START, 0.4f);
        glFogf(GL_FOG_END, 1);
        glFogf(GL_FOG_DENSITY, 1);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glColor3f(1, 0, 1);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(-0.3f, 0.5f, -0.99f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glEnd();

        glDisable(GL_FOG);
    }

    public void doExponentialSquaredFog() {
        glEnable(GL_FOG);

        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(0).put(1).put(0).put(1).flip();
        glFog(GL_FOG_COLOR, fogColor);

        glFogf(GL_FOG_MODE, GL_EXP2);
        glFogf(GL_FOG_START, 0.4f);
        glFogf(GL_FOG_END, 1);
        glFogf(GL_FOG_DENSITY, 1);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glBegin(GL_TRIANGLES);
        glColor3f(0, 0, 1);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.6f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(-0.4f, -0.6f, -0.1f);
        glColor3f(1, 0, 1);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glVertex3f(0.4f, -0.1f, -0.1f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(0.3f, 0.5f, -0.99f);
        glVertex3f(-0.3f, 0.5f, -0.99f);
        glVertex3f(-0.4f, -0.1f, -0.1f);
        glEnd();

        glDisable(GL_FOG);
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

    public void doMyGLPerspective() {
        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGluPerspective(90f, (640f) / 480f, .1f, 10f);
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

    public void doMyGLGouraud() {
        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLEnable(GL_NORMALIZE);
        doGLEnable(GL_LIGHTING);
        doGLEnable(GL_COLOR_MATERIAL);
        doGLEnable(GL_LIGHT0);
        float[] diffuse_color = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
        float[] position = {0, 3, -10, 1};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        doGLLight(GL_LIGHT0, GL_DIFFUSE, diffuse_color);
        doGLLight(GL_LIGHT0, GL_AMBIENT, ambient_color);
        doGLLight(GL_LIGHT0, GL_POSITION, position);
        doGLColor3f(1, 0, 0);
        float dp = (float) (Math.PI / 16); // 16 picked arbitrarily ; try other numbers too

        doGLBegin(GL_TRIANGLES);
        for (float theta = 0; theta < 2 * Math.PI; theta += dp) {
            for (float phi = 0; phi < Math.PI; phi += dp) {
                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
            }
        }
        doGLEnd();
        doGLDisable(GL_LIGHTING);
        doGLDisable(GL_NORMALIZE);
        doGLDisable(GL_COLOR_MATERIAL);
        doGLDisable(GL_LIGHT0);
    }

    public void doMyGLGouraudExperiment(float x, float y, float z) {
        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLEnable(GL_NORMALIZE);
        doGLEnable(GL_LIGHTING);
//        doGLEnable(GL_COLOR_MATERIAL);
        doGLEnable(GL_LIGHT0);
        float[] diffuse_color = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
        float[] position = {x, y, z, 1};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        doGLLight(GL_LIGHT0, GL_DIFFUSE, diffuse_color);
        doGLLight(GL_LIGHT0, GL_AMBIENT, ambient_color);
        doGLLight(GL_LIGHT0, GL_POSITION, position);
        doGLColor3f(1, 0, 0);
        float dp = (float) (Math.PI / 16); // 16 picked arbitrarily ; try other numbers too

        doGLBegin(GL_TRIANGLES);
        for (float theta = 0; theta < 2 * Math.PI; theta += dp) {
            for (float phi = 0; phi < Math.PI; phi += dp) {
                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
            }
        }
        doGLEnd();
        doGLDisable(GL_LIGHTING);
        doGLDisable(GL_NORMALIZE);
        doGLDisable(GL_COLOR_MATERIAL);
        doGLDisable(GL_LIGHT0);
    }

    public void doMyLinearFog() {
        doGLEnable(GL_FOG);

        float[] fog_color = {0, 1, 0, 1};
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * 4);
        doGLFog(GL_FOG_COLOR, fog_color);

        doGLFogf(GL_FOG_MODE, GL_LINEAR);
        doGLFogf(GL_FOG_START, 0.4f);
        doGLFogf(GL_FOG_END, 1);
        doGLFogf(GL_FOG_DENSITY, 1);

        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(-0.4f, -0.6f, -0.1f);
        doGLVertex3f(0.4f, -0.6f, -0.1f);
        doGLVertex3f(0.4f, -0.1f, -0.1f);
        doGLVertex3f(0.4f, -0.1f, -0.1f);
        doGLVertex3f(-0.4f, -0.1f, -0.1f);
        doGLVertex3f(-0.4f, -0.6f, -0.1f);
        doGLColor3f(1, 0, 1);
        doGLVertex3f(-0.4f, -0.1f, -0.1f);
        doGLVertex3f(0.4f, -0.1f, -0.1f);
        doGLVertex3f(0.3f, 0.5f, -0.99f);
        doGLVertex3f(0.3f, 0.5f, -0.99f);
        doGLVertex3f(-0.3f, 0.5f, -0.99f);
        doGLVertex3f(-0.4f, -0.1f, -0.1f);
        doGLEnd();

        doGLDisable(GL_FOG);
    }

    public void doMyExponentialFog() {
        doGLEnable(GL_FOG);
        float[] fog_color = {0, 1, 0, 1};
        doGLFog(GL_FOG_COLOR, fog_color);

        doGLFogf(GL_FOG_MODE, GL_EXP);
        doGLFogf(GL_FOG_START, 0.4f);
        doGLFogf(GL_FOG_END, 1);
        doGLFogf(GL_FOG_DENSITY, 1);

        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(-0.4, -0.6, -0.1);
        doGLVertex3f(0.4, -0.6, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLVertex3f(-0.4, -0.6, -0.1);
        doGLColor3f(1, 0, 1);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(0.3, 0.5, -0.99);
        doGLVertex3f(0.3, 0.5, -0.99);
        doGLVertex3f(-0.3, 0.5, -0.99);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLEnd();

        doGLDisable(GL_FOG);
    }

    public void doMyExponentialSquaredFog() {
        doGLEnable(GL_FOG);

        float[] fog_color = {0, 1, 0, 1};
        doGLFog(GL_FOG_COLOR, fog_color);

        doGLFogf(GL_FOG_MODE, GL_EXP2);
        doGLFogf(GL_FOG_START, 0.4f);
        doGLFogf(GL_FOG_END, 1);
        doGLFogf(GL_FOG_DENSITY, 1);

        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLBegin(GL_TRIANGLES);
        doGLColor3f(0, 0, 1);
        doGLVertex3f(-0.4, -0.6, -0.1);
        doGLVertex3f(0.4, -0.6, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLVertex3f(-0.4, -0.6, -0.1);
        doGLColor3f(1, 0, 1);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLVertex3f(0.4, -0.1, -0.1);
        doGLVertex3f(0.3, 0.5, -0.99);
        doGLVertex3f(0.3, 0.5, -0.99);
        doGLVertex3f(-0.3, 0.5, -0.99);
        doGLVertex3f(-0.4, -0.1, -0.1);
        doGLEnd();

        doGLDisable(GL_FOG);
    }

    public void doMyGLSpecular() {
        doGLMatrixMode(GL_PROJECTION);
        doGLLoadIdentity();
        doGLMatrixMode(GL_MODELVIEW);
        doGLLoadIdentity();

        doGLEnable(GL_LIGHTING);
        doGLEnable(GL_COLOR_MATERIAL);
        doGLEnable(GL_LIGHT0);
        float[] diffuse_color = {.7f, .7f, .7f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
        float[] specular_color = {0, 1, 0, 1};
        float[] position = {1, 0, -10, 1};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        doGLLight(GL_LIGHT0, GL_DIFFUSE, diffuse_color);
        doGLLight(GL_LIGHT0, GL_AMBIENT, ambient_color);
        doGLLight(GL_LIGHT0, GL_SPECULAR, specular_color);
        doGLLight(GL_LIGHT0, GL_POSITION, position);

        float[] specular_surface_color = {0.0f, 1.0f, 0.9f, 1};
        doGLMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, specular_surface_color);
        doGLMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 1);
        doGLColor3f(1, 0, 0);
        float dp = (float) (Math.PI / 16); // 16 picked arbitrarily ; try other numbers too

        doGLBegin(GL_TRIANGLES);
        for (float theta = 0; theta < 2 * Math.PI; theta += dp) {
            for (float phi = 0; phi < Math.PI; phi += dp) {
                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta + dp) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi)),
                        (float) Math.cos(phi),
                        (float) (Math.sin(theta) * Math.sin(phi)));

                doGLNormal3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta + dp) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta + dp) * Math.sin(phi + dp)));

                doGLNormal3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
                doGLVertex3f((float) (Math.cos(theta) * Math.sin(phi + dp)),
                        (float) Math.cos(phi + dp),
                        (float) (Math.sin(theta) * Math.sin(phi + dp)));
            }
        }
        doGLEnd();
        doGLDisable(GL_LIGHTING);
        doGLDisable(GL_NORMALIZE);
        doGLDisable(GL_COLOR_MATERIAL);
        doGLDisable(GL_LIGHT0);

        float[] spec = {0, 0, 0, 0};
        doGLLight(GL_LIGHT0, GL_SPECULAR, spec);
    }
}
