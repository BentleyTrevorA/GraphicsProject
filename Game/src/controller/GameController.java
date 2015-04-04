package controller;

import camera.Camera;
import driver.LWJGLSandbox;
import model.DepthBuffer;
import model.Lighting;
import model.Model;
import model.handlers.PlayerHandler;
import model.mapObjects.MapName;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GameController {
    private Model model;
    private Camera camera;
    private DepthBuffer depthBuffer;
    private PlayerHandler playerHandler;
    private Lighting light;
    private static MapName mapName = MapName.EXPERIMENT_MAP;
    private static int difficulty = 1;

    public static float a = 1f;
    public static float b = 1f;
    public static float c = 0f;
    public static float d = 0f;

    public GameController()
    {
        camera = new Camera();
        depthBuffer = new DepthBuffer();
        light = new Lighting();
    }

    public void setupAfterInitGL() {
        model = new Model(camera, mapName, difficulty);
        playerHandler = model.getPlayerHandler();
    }

    // This method is called to "resize" the viewport to match the screen.
    public void resizeGL() {
        setupPerspectiveMode();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
    }

    private void setupPerspectiveMode() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // IMPORTANT: DO NOT use 0 for near plane - it screws up depth_testing
        gluPerspective(80.0f, LWJGLSandbox.DISPLAY_WIDTH / (float) LWJGLSandbox.DISPLAY_HEIGHT, 1.0f, 700f);
    }

    private void setupOrthographicMode() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glRotated(-90, 1, 0, 0);

//        glOrtho(0, LWJGLSandbox.DISPLAY_WIDTH, 0, LWJGLSandbox.DISPLAY_HEIGHT, Model.MIN_MAP_COORDINATE, Model.MAX_MAP_COORDINATE);
        glOrtho(camera.xPos - 50, camera.xPos + 50, camera.zPos - 50, camera.zPos + 50, Model.MIN_MAP_COORDINATE, Model.MAX_MAP_COORDINATE);
//        glTranslated(875, 10, 190);
        glTranslated(a, 10, b);
        glScaled(c, 1, d);
    }

    public void update() {
        model.update();
    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    public void updateKeyboard() {
        if(!playerHandler.isAlive()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
                model.resetGame(1);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
                model.resetGame(1);
            }
        }
        else {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                playerHandler.moveForward();
            } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                playerHandler.moveBackward();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
                playerHandler.moveLeft();
            } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                playerHandler.moveRight();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                playerHandler.turnLeft();
            } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                playerHandler.turnRight();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                playerHandler.moveUp();
            } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                playerHandler.moveDown();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                playerHandler.resetPosition();
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                model.addShot();
            }

            //Switch to perspective
//            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
//                setupPerspectiveMode();
//            }
//            else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
//                setupOrthographicMode();
//            }

            // Debugging using a, b, c, and d variables
            if (Keyboard.isKeyDown(Keyboard.KEY_COMMA)) { d -= .01f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_PERIOD)) { d += .01f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)) { c += .01f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) { c -= .01f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_I)) { b += 1f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_K)) { b -= 1f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_U)) { a += 1f; printLightVariables(); }
            if (Keyboard.isKeyDown(Keyboard.KEY_J)) { a -= 1f; printLightVariables(); }
        }
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

//        if(playerHandler.isAlive()) {
            playerHandler.positionCamera();
            depthBuffer.enableDepthBuffering();
            light.enableLighting();
            model.drawMap();
            model.drawHud();
//        }
        // TODO: Else draw game-over and options to start again (#'s or something)
        glFlush();
    }

    private void printLightVariables() {
        System.out.println("A: " + a + " B: " + b + " C: " + c + " D: " + d);
    }
}
