package controllers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class Lab2Controller extends LabController {
    public void init() {
        currentColor = new Vector4f(1, 1, 1, 1);
        clearColor = new Vector4f(0, 0, 0, 0);

        // For simplicity of execution
        drawMode = 2;
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

    //This is called every frame, and should be responsible for keyboard updates.
    public void updateKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            // OpenGL implementation
            drawMode = 1;
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            // my implementation
            drawMode = 2;
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            doGLClearColor(0, 1, 1, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLPointSize(0);
            doGLDrawPoints();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawLines();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawTriangles();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawLineStrip();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawLineLoop();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawTriangleStrip();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawTriangleFan();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawQuads();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawQuadStrip();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawPolygon();
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_APOSTROPHE)) {
            doGLClearColor(0, 0, 0, 0);
            doGLClear(GL_COLOR_BUFFER_BIT);
            doGLDrawBigPoints();
            render();
        }
    }

    /* *********************************************************
    *                   DRAWING FUNCTIONS
    * *********************************************************/
    private void doGLDrawPoints() {
        doGLBegin(GL_POINTS);
        for (float theta = 0, radius = 60.0f; radius > 1.0; theta += 0.1, radius -= 0.3) {
            doGLColor3f(radius / 60.0f, 0.3f, 1 - (radius / 60.0f));
            doGLVertex2i((int) (200 + radius * Math.cos(theta)), (int) (200 + radius * Math.sin(theta)));
        }
        glEnd();
    }

    private void doGLDrawLines() {
        doGLBegin(GL_LINES);
        for (int i = 0; i <= 8; i++) {
            doGLColor3f(1, 0, 0);
            doGLVertex2i(200, 200);
            doGLVertex2i(200 + 10 * i, 280);
            doGLColor3f(0, 1, 0);
            doGLVertex2i(200, 200);
            doGLColor3f(0, 1, 1);
            doGLVertex2i(200 - 10 * i, 280);
            doGLVertex2i(200, 200);
            doGLVertex2i(280, 200 + 10 * i);
            doGLVertex2i(200, 200);
            doGLVertex2i(280, 200 - 10 * i);
        }
        doGLEnd();
    }

    private void doGLDrawLineStrip() {
        doGLBegin(GL_LINE_STRIP);
        doGLColor3f(0.42f, 0.27f, 0.11f);
        doGLVertex2i(250, 30);
        doGLVertex2i(270, 60);
        doGLVertex2i(270, 210);
        doGLColor3f(0.04f, 0.70f, 0.02f);
        doGLVertex2i(230, 230);
        doGLVertex2i(220, 270);
        doGLVertex2i(220, 310);
        doGLVertex2i(250, 340);
        doGLVertex2i(275, 360);
        doGLVertex2i(325, 360);
        doGLVertex2i(350, 340);
        doGLVertex2i(380, 310);
        doGLVertex2i(380, 270);
        doGLVertex2i(370, 230);
        doGLColor3f(0.42f, 0.27f, 0.11f);
        doGLVertex2i(330, 210);
        doGLVertex2i(330, 60);
        doGLVertex2i(350, 30);
        doGLEnd();
    }

    private void doGLDrawLineLoop() {
        doGLBegin(GL_LINE_LOOP);
        doGLColor3f(0.42f, 0.27f, 0.11f);
        doGLVertex2i(250, 30);
        doGLVertex2i(270, 60);
        doGLVertex2i(270, 210);
        doGLColor3f(0.04f, 0.70f, 0.02f);
        doGLVertex2i(230, 230);
        doGLVertex2i(220, 270);
        doGLVertex2i(220, 310);
        doGLVertex2i(250, 340);
        doGLVertex2i(275, 360);
        doGLVertex2i(325, 360);
        doGLVertex2i(350, 340);
        doGLVertex2i(380, 310);
        doGLVertex2i(380, 270);
        doGLVertex2i(370, 230);
        doGLColor3f(0.42f, 0.27f, 0.11f);
        doGLVertex2i(330, 210);
        doGLVertex2i(330, 60);
        doGLVertex2i(350, 30);
        doGLEnd();
    }

    private void doGLDrawTriangles() {
        doGLBegin(GL_TRIANGLES);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(300, 300);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(600, 300);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(450, 410);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(100, 400);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(70, 60);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(350, 100);
        doGLEnd();
    }

    private void doGLDrawTriangleStrip() {
        doGLBegin(GL_TRIANGLE_STRIP);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(40, 70);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(40, 390);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(130, 30);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(130, 350);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(330, 80);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(330, 400);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(480, 40);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(530, 330);
        doGLEnd();
    }

    private void doGLDrawTriangleFan() {
        doGLBegin(GL_TRIANGLE_FAN);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(250, 170);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(400, 140);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(300, 50);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(175, 55);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(100, 170);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(175, 285);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(300, 290);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(400, 200);
        doGLEnd();
    }

    private void doGLDrawQuads() {
        doGLBegin(GL_QUADS);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(40, 70);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(40, 390);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(130, 350);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(130, 30);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(330, 80);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(330, 400);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(530, 330);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(480, 40);
        doGLEnd();
    }

    private void doGLDrawQuadStrip() {
        doGLBegin(GL_QUAD_STRIP);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(40, 70);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(40, 390);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(130, 30);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(130, 350);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(330, 80);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(330, 400);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(480, 40);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(530, 330);
        doGLEnd();
    }

    private void doGLDrawPolygon() {
        doGLBegin(GL_POLYGON);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(250, 170);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(400, 140);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(300, 50);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(175, 55);
        doGLColor3f(1, 0, 1);
        doGLVertex2i(100, 170);
        doGLColor3f(0, 1, 1);
        doGLVertex2i(175, 285);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(300, 290);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(400, 200);
        doGLEnd();
    }

    private void doGLDrawBigPoints() {
        doGLPointSize(5);
        doGLBegin(GL_POINTS);
        doGLColor3f(1, 0, 0);
        doGLVertex2i(250, 170);
        doGLEnd();

        //doGLEnable(GL_BLEND);
        //doGLEnable(GL_POINT_SMOOTH);
        doGLBegin(GL_POINTS);
        doGLColor3f(1, 1, 0);
        doGLVertex2i(300, 170);
        doGLEnd();

        doGLPointSize(10);
        doGLBegin(GL_POINTS);
        doGLColor3f(0, 0, 1);
        doGLVertex2i(300, 140);
        doGLEnd();

        //doGLDisable(GL_POINT_SMOOTH);
        doGLBegin(GL_POINTS);
        doGLColor3f(0, 1, 0);
        doGLVertex2i(250, 140);
        doGLEnd();
    }
}