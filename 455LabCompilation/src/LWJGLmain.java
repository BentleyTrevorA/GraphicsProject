import Controllers.Lab1Controller;
import Controllers.Lab2Controller;
import Controllers.Lab3Controller;
import DataRepresentations.PointData;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class LWJGLmain
{
    private static int lab = 3;

    public static void main(String[] args) {
        LWJGLSandbox main = null;
        try {
            main = new LWJGLSandbox();
            switch(lab) {
                case 1:
                    main.create(new Lab1Controller());
                    break;
                case 2:
                    main.create(new Lab2Controller());
                    break;
                case 3:
                    main.create(new Lab3Controller());
                    break;
            }
            main.run();
        }
        catch (Exception ex) { ex.printStackTrace(); }
        finally { if (main != null) { main.destroy(); } }
    }
}
