package game;


import camera.Camera;
import controllers.LabController;
import driver.LWJGLSandbox;
import model.*;
import model.renderers.ShapeRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GameController extends LabController {
    private Model model;
    private Camera camera;
    private DepthBuffer depthBuffer;
    private Lighting light;
    private static int map = 0; // TODO: Change to 1

    public static float a = 1f;
    public static float b = 1f;
    public static float c = 1f;
    public static float d = 0;

    protected void initBase() {
        camera = new Camera();
        depthBuffer = new DepthBuffer();
        light = new Lighting();
    }

    public void setupAfterInitGL() {
        model = new Model(camera, map);
    }

    // This method is called to "resize" the viewport to match the screen.
    public void resizeGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // IMPORTANT: DO NOT use 0 for near plane - it screws up depth_testing
        gluPerspective(80.0f, LWJGLSandbox.DISPLAY_WIDTH / (float) LWJGLSandbox.DISPLAY_HEIGHT, 1.0f, 700f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
    }

    public void update() {
        model.update();
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    public void updateKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.moveForward();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.moveBackward();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            camera.moveLeft();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            camera.moveRight();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.turnLeft();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.turnRight();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            camera.moveUp();
        } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            camera.moveDown();
        }

        // TODO: Remove in actual gameplay
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            camera.resetPosition();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            model.addShot();
        }

        // Debugging using a, b, c, and d variables
//        if (Keyboard.isKeyDown(Keyboard.KEY_P)) { d += (1.0/180.f); printLightVariables();}
//        if (Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)) { d -= (1.0/180.f); printLightVariables();}
//        if (Keyboard.isKeyDown(Keyboard.KEY_O)) { c += 1f; printLightVariables();}
//        if (Keyboard.isKeyDown(Keyboard.KEY_L)) { c -= 1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) { b += 1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) { b -= 1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_U)) { a += 1f; printLightVariables();}
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) { a -= 1f; printLightVariables();}
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        camera.positionCamera();
        depthBuffer.enableDepthBuffering();
        light.enableLighting();
        model.drawMap();
        glFlush();
    }

    private void printLightVariables() {
        System.out.println("A: " + a + " B: " + b + " C: " + c + " D: " + d);
    }
}
