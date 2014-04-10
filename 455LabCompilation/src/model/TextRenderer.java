package model;

// http://ninjacave.com/taxonomy/term/3?page=1

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class TextRenderer {
    public static void drawString(String s, double x, double y, double z) {
        double startX = x;
        glBegin(GL_POINTS);
        for (char c : s.toLowerCase().toCharArray()) {
            if (c == 'a') {
                for (int i = 0; i < 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == 'b') {
                for (int i = 0; i < 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 4, z);
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 7, y + 5, z);
                glVertex3d(x + 7, y + 7, z);
                glVertex3d(x + 7, y + 6, z);
                glVertex3d(x + 7, y + 1, z);
                glVertex3d(x + 7, y + 2, z);
                glVertex3d(x + 7, y + 3, z);
                x += 8;
            } else if (c == 'c') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 6, y + 2, z);

                glVertex3d(x + 6, y + 6, z);
                glVertex3d(x + 6, y + 7, z);

                x += 8;
            } else if (c == 'd') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 6, y + 2, z);
                glVertex3d(x + 6, y + 3, z);
                glVertex3d(x + 6, y + 4, z);
                glVertex3d(x + 6, y + 5, z);
                glVertex3d(x + 6, y + 6, z);
                glVertex3d(x + 6, y + 7, z);

                x += 8;
            } else if (c == 'e') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == 'f') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == 'g') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 6, y + 2, z);
                glVertex3d(x + 6, y + 3, z);
                glVertex3d(x + 5, y + 3, z);
                glVertex3d(x + 7, y + 3, z);

                glVertex3d(x + 6, y + 6, z);
                glVertex3d(x + 6, y + 7, z);

                x += 8;
            } else if (c == 'h') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == 'i') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 3, y + i, z);
                }
                for (int i = 1; i <= 5; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                }
                x += 7;
            } else if (c == 'j') {
                for (int i = 1; i <= 8; i++) {
                    glVertex3d(x + 6, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y, z);
                }
                glVertex3d(x + 1, y + 3, z);
                glVertex3d(x + 1, y + 2, z);
                glVertex3d(x + 1, y + 1, z);
                x += 8;
            } else if (c == 'k') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                glVertex3d(x + 6, y + 8, z);
                glVertex3d(x + 5, y + 7, z);
                glVertex3d(x + 4, y + 6, z);
                glVertex3d(x + 3, y + 5, z);
                glVertex3d(x + 2, y + 4, z);
                glVertex3d(x + 2, y + 3, z);
                glVertex3d(x + 3, y + 4, z);
                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 5, y + 2, z);
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 7, y, z);
                x += 8;
            } else if (c == 'l') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                }
                x += 7;
            } else if (c == 'm') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                glVertex3d(x + 3, y + 6, z);
                glVertex3d(x + 2, y + 7, z);
                glVertex3d(x + 4, y + 5, z);

                glVertex3d(x + 5, y + 6, z);
                glVertex3d(x + 6, y + 7, z);
                glVertex3d(x + 4, y + 5, z);
                x += 8;
            } else if (c == 'n') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                glVertex3d(x + 2, y + 7, z);
                glVertex3d(x + 2, y + 6, z);
                glVertex3d(x + 3, y + 5, z);
                glVertex3d(x + 4, y + 4, z);
                glVertex3d(x + 5, y + 3, z);
                glVertex3d(x + 6, y + 2, z);
                glVertex3d(x + 6, y + 1, z);
                x += 8;
            } else if (c == 'o' || c == '0') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y, z);
                }
                x += 8;
            } else if (c == 'p') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y + 4, z);
                }
                glVertex3d(x + 6, y + 7, z);
                glVertex3d(x + 6, y + 5, z);
                glVertex3d(x + 6, y + 6, z);
                x += 8;
            } else if (c == 'q') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                    if (i != 1)
                        glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                    if (i != 6)
                        glVertex3d(x + i, y, z);
                }
                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 5, y + 2, z);
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 7, y, z);
                x += 8;
            } else if (c == 'r') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y + 4, z);
                }
                glVertex3d(x + 6, y + 7, z);
                glVertex3d(x + 6, y + 5, z);
                glVertex3d(x + 6, y + 6, z);

                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 5, y + 2, z);
                glVertex3d(x + 6, y + 1, z);
                glVertex3d(x + 7, y, z);
                x += 8;
            } else if (c == 's') {
                for (int i = 2; i <= 7; i++) {
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 1, y + 7, z);
                glVertex3d(x + 1, y + 6, z);
                glVertex3d(x + 1, y + 5, z);
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 4, z);
                    glVertex3d(x + i, y, z);
                }
                glVertex3d(x + 7, y + 3, z);
                glVertex3d(x + 7, y + 2, z);
                glVertex3d(x + 7, y + 1, z);
                glVertex3d(x + 1, y + 1, z);
                glVertex3d(x + 1, y + 2, z);
                x += 8;
            } else if (c == 't') {
                for (int i = 0; i <= 8; i++) {
                    glVertex3d(x + 4, y + i, z);
                }
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + i, y + 8, z);
                }
                x += 7;
            } else if (c == 'u') {
                for (int i = 1; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                }
                x += 8;
            } else if (c == 'v') {
                for (int i = 2; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 6, y + i, z);
                }
                glVertex3d(x + 2, y + 1, z);
                glVertex3d(x + 5, y + 1, z);
                glVertex3d(x + 3, y, z);
                glVertex3d(x + 4, y, z);
                x += 7;
            } else if (c == 'w') {
                for (int i = 1; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                glVertex3d(x + 2, y, z);
                glVertex3d(x + 3, y, z);
                glVertex3d(x + 5, y, z);
                glVertex3d(x + 6, y, z);
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + 4, y + i, z);
                }
                x += 8;
            } else if (c == 'x') {
                for (int i = 1; i <= 7; i++)
                    glVertex3d(x + i, y + i, z);
                for (int i = 7; i >= 1; i--)
                    glVertex3d(x + i, y + 8 - i, z);
                x += 8;
            } else if (c == 'y') {
                glVertex3d(x + 4, y, z);
                glVertex3d(x + 4, y + 1, z);
                glVertex3d(x + 4, y + 2, z);
                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 4, y + 4, z);

                glVertex3d(x + 3, y + 5, z);
                glVertex3d(x + 2, y + 6, z);
                glVertex3d(x + 1, y + 7, z);
                glVertex3d(x + 1, y + 8, z);

                glVertex3d(x + 5, y + 5, z);
                glVertex3d(x + 6, y + 6, z);
                glVertex3d(x + 7, y + 7, z);
                glVertex3d(x + 7, y + 8, z);
                x += 8;
            } else if (c == 'z') {
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y + i, z);
                }
                glVertex3d(x + 6, y + 7, z);
                x += 8;
            } else if (c == '1') {
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                }
                for (int i = 1; i <= 8; i++) {
                    glVertex3d(x + 4, y + i, z);
                }
                glVertex3d(x + 3, y + 7, z);
                x += 8;
            } else if (c == '2') {
                for (int i = 1; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 1, y + 7, z);
                glVertex3d(x + 1, y + 6, z);

                glVertex3d(x + 6, y + 7, z);
                glVertex3d(x + 6, y + 6, z);
                glVertex3d(x + 6, y + 5, z);
                glVertex3d(x + 5, y + 4, z);
                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 3, y + 2, z);
                glVertex3d(x + 2, y + 1, z);
                x += 8;
            } else if (c == '3') {
                for (int i = 1; i <= 5; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y, z);
                }
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 6, y + i, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == '4') {
                for (int i = 2; i <= 8; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 7; i++) {
                    glVertex3d(x + i, y + 1, z);
                }
                for (int i = 0; i <= 4; i++) {
                    glVertex3d(x + 4, y + i, z);
                }
                x += 8;
            } else if (c == '5') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + i, y + 8, z);
                }
                for (int i = 4; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                glVertex3d(x + 1, y + 1, z);
                glVertex3d(x + 2, y, z);
                glVertex3d(x + 3, y, z);
                glVertex3d(x + 4, y, z);
                glVertex3d(x + 5, y, z);
                glVertex3d(x + 6, y, z);

                glVertex3d(x + 7, y + 1, z);
                glVertex3d(x + 7, y + 2, z);
                glVertex3d(x + 7, y + 3, z);

                glVertex3d(x + 6, y + 4, z);
                glVertex3d(x + 5, y + 4, z);
                glVertex3d(x + 4, y + 4, z);
                glVertex3d(x + 3, y + 4, z);
                glVertex3d(x + 2, y + 4, z);
                x += 8;
            } else if (c == '6') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y, z);
                }
                for (int i = 2; i <= 5; i++) {
                    glVertex3d(x + i, y + 4, z);
                    glVertex3d(x + i, y + 8, z);
                }
                glVertex3d(x + 7, y + 1, z);
                glVertex3d(x + 7, y + 2, z);
                glVertex3d(x + 7, y + 3, z);
                glVertex3d(x + 6, y + 4, z);
                x += 8;
            } else if (c == '7') {
                for (int i = 0; i <= 7; i++)
                    glVertex3d(x + i, y + 8, z);
                glVertex3d(x + 7, y + 7, z);
                glVertex3d(x + 7, y + 6, z);

                glVertex3d(x + 6, y + 5, z);
                glVertex3d(x + 5, y + 4, z);
                glVertex3d(x + 4, y + 3, z);
                glVertex3d(x + 3, y + 2, z);
                glVertex3d(x + 2, y + 1, z);
                glVertex3d(x + 1, y, z);
                x += 8;
            } else if (c == '8') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                x += 8;
            } else if (c == '9') {
                for (int i = 1; i <= 7; i++) {
                    glVertex3d(x + 7, y + i, z);
                }
                for (int i = 5; i <= 7; i++) {
                    glVertex3d(x + 1, y + i, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 8, z);
                    glVertex3d(x + i, y, z);
                }
                for (int i = 2; i <= 6; i++) {
                    glVertex3d(x + i, y + 4, z);
                }
                glVertex3d(x + 1, y, z);
                x += 8;
            } else if (c == '.') {
                glVertex3d(x + 1, y, z);
                x += 2;
            } else if (c == ',') {
                glVertex3d(x + 1, y, z);
                glVertex3d(x + 1, y + 1, z);
                x += 2;
            } else if (c == '\n') {
                y -= 10;
                x = startX;
            } else if (c == ' ') {
                x += 8;
            }
        }
        glEnd();
    }

    // http://www.lwjgl.org/wiki/index.php?title=Slick-Util_Library_-_Part_3_-_TrueType_Fonts_for_LWJGL
    public static void drawString(String s) {
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        TrueTypeFont font = new TrueTypeFont(awtFont, false);

        font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.yellow);
    }
}
