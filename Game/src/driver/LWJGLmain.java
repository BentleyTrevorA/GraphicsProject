package driver;

import controller.GameController;

public class LWJGLmain {
    public static void main(String[] args) {
        LWJGLSandbox sandox = null;
        try {
            sandox = new LWJGLSandbox();
            sandox.create(new GameController());
            sandox.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sandox != null) {
                sandox.destroy();
            }
        }
    }
}
