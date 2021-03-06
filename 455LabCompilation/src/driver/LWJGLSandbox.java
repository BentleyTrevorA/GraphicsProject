package driver;

import controllers.LabController;
import game.GameController;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.glClearColor;

public class LWJGLSandbox {
    public static int DISPLAY_WIDTH = 640;
    public static int DISPLAY_HEIGHT = 480;

    public LWJGLSandbox() {
    }

    public LabController c;

    public void create(LabController c) throws LWJGLException {
        this.c = c;

        //Display
        if (c.getClass() == GameController.class) {
            DISPLAY_WIDTH = 960;
            DISPLAY_HEIGHT = 720;
        }

        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));

        Display.setFullscreen(false);
        Display.setTitle("OpenGL Frame");
        Display.create();

        //Keyboard
        Keyboard.create();

        //OpenGL
        initGL();
        c.resizeGL();
        c.setupAfterInitGL();
    }

    public void destroy() {
        //Methods already check if created before destroying.
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    public void initGL() {
        //2D Initialization
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void processKeyboard() {
        c.updateKeyboard();
    }

    public void render() {
        c.render();
    }

    public void run() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.isVisible()) {
                processKeyboard();
                update();
                render();
            } else {
                if (Display.isDirty()) {
                    render();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            Display.update();
            Display.sync(60);
        }
    }

    public void update() {
        c.update();
    }
}
