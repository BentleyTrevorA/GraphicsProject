import controllers.Lab1Controller;
import controllers.Lab2Controller;
import controllers.Lab3Controller;
import controllers.Lab4Controller;
import game.GameController;

public class LWJGLmain {
    private static int lab = 5;

    public static void main(String[] args) {
        LWJGLSandbox main = null;
        try {
            main = new LWJGLSandbox();
            switch (lab) {
                case 1:
                    main.create(new Lab1Controller());
                    break;
                case 2:
                    main.create(new Lab2Controller());
                    break;
                case 3:
                    main.create(new Lab3Controller());
                    break;
                case 4:
                    main.create(new Lab4Controller());
                    break;
                case 5:
                    main.create(new GameController());
                    break;
            }
            main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (main != null) {
                main.destroy();
            }
        }
    }
}
