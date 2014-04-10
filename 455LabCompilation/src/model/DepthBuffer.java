package model;

import static org.lwjgl.opengl.GL11.*;

public class DepthBuffer {

    public DepthBuffer()
    {}

    public void enableDepthBuffering() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // sets background to black
        glClearDepth(1.0f); // clear depth buffer
        glEnable(GL_CULL_FACE); // Enable back face culling
        glEnable(GL_DEPTH_TEST); // Enables depth testing
        glDepthFunc(GL_LEQUAL); // sets the type of test to use for depth testing
        glDepthRange(0, 1);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
}
