package model;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class Lighting {
    //----------- Variables added for Lighting Test -----------//
    private FloatBuffer matSpecular;
    private FloatBuffer lightPosition, lightPosition2;
    private FloatBuffer whiteLight, pinkLight;
    private FloatBuffer lModelAmbient;
    //----------- END: Variables added for Lighting Test -----------//

    public Lighting() {
        initLightArrays();
    }

    public void initLightArrays() {
        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();

        lightPosition2 = BufferUtils.createFloatBuffer(4);
        lightPosition2.put(-62).put(123).put(-47).put(1.0f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        pinkLight = BufferUtils.createFloatBuffer(4);
        pinkLight.put(.5f).put(0).put(.5f).put(1).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
//        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip(); // Grey
        lModelAmbient.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip(); // White
//        lModelAmbient.put(1.0f).put(0.0f).put(1.0f).put(1.0f).flip(); // Pink
    }

    // LIGHTING QUESTIONS: http://www.thejavahub.net/thejavahub/index.php?topic=2124.0
    public void enableLighting() {
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * 4);
        temp.order(ByteOrder.LITTLE_ENDIAN);

        glEnable(GL_NORMALIZE);
        glEnable(GL_LIGHTING);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_LIGHT0);
        float[] diffuse_color = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color = {0.1f, 0.1f, 0.1f, 1};
//        float[] ambient_color = {0.0f, 0.5f, 0.5f, 1};
//        float[] position = {0, 3, -10, 1};
        float[] position = {25, 38, -66, .1999f};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color).flip());
        glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color).flip());
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position).flip());

        glEnable(GL_LIGHT1);
        float[] diffuse_color1 = {1.0f, 1.0f, 1.0f, 1};
        float[] ambient_color1 = {0.5f, 0.0f, 0.5f, 1};
//        float[] ambient_color1 = {1f, 0.0f, 0.0f, 1};
        float[] position1 = {.1f, .1f, .1f, 0};

        // http://lwjgl.org/forum/index.php?action=printpage;topic=2233.0
        glLight(GL_LIGHT1, GL_DIFFUSE, (FloatBuffer) temp.asFloatBuffer().put(diffuse_color1).flip());
        glLight(GL_LIGHT1, GL_AMBIENT, (FloatBuffer) temp.asFloatBuffer().put(ambient_color1).flip());
        glLight(GL_LIGHT1, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT1, GL_POSITION, (FloatBuffer) temp.asFloatBuffer().put(position1).flip());
    }

    public void enableLighting2() {
//        initLightArrays();
        glPushMatrix();
//        glTranslated(50, 0, 0);
        glShadeModel(GL_SMOOTH);
        glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
        glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess

        glLight(GL_LIGHT0, GL_POSITION, lightPosition);				// sets light position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);					// sets diffuse light to white

        glLight(GL_LIGHT1, GL_POSITION, lightPosition2);			// sets light position
        glLight(GL_LIGHT1, GL_SPECULAR, whiteLight);				// sets specular light to white
        glLight(GL_LIGHT1, GL_DIFFUSE, pinkLight);					// sets diffuse light to pink

        glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light

//        glEnable(GL_NORMALIZE);
        glEnable(GL_LIGHTING);										// enables lighting
        glEnable(GL_LIGHT0);										// enables light0
        glEnable(GL_LIGHT1);										// enables light1

        glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
        glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
        glPopMatrix();
    }
}
