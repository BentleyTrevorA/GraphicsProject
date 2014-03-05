package Camera;

import static org.lwjgl.opengl.GL11.*;

public class Camera
{
    // moves camera in global coordinates
    public void translateWorldSpace(float x, float y, float z) {

    }

    // moves camera relative to its current orientation
    public void translateCameraSpace(float right, float up, float back) {

    }

    // rotates camera, without moving it; axis given in global coordinates
    public void rotateWorldSpace(float theta, float x, float y, float z) {

    }

    // rotates camera, without moving it; axis given in camera space coordinates
    public void rotateCameraSpace(float theta, float right, float up ,float back) {

    }

    public void use() {
        glMatrixMode(GL_PROJECTION);
        glMatrixMode(GL_MODELVIEW);
        // Orient camera here
    }
}
