package model.handlers;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextureHandler {
    public static final int ENEMY_HP_6 = 0;
    public static final int NO_TEXTURE = -1;
    public static final int ENEMY_HP_5 = 1;
    public static final int ENEMY_HP_4 = 2;
    public static final int ENEMY_HP_3 = 3;
    public static final int ENEMY_HP_2 = 4;
    public static final int ENEMY_HP_1 = 5;
    public static final int STONE_1 = 6;
    public static final int STONE_2 = 7;
    public static final int STONE_3 = 8;
    public static final int STONE_4 = 9;
    private String imgLocation = "img/textures/";
    private Map<Integer, Texture> textures;

    public TextureHandler() {
        loadTextures();
    }

    public void loadTextures() {
        try {
            textures = new HashMap<Integer, Texture>();

            textures.put(ENEMY_HP_6, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar1.png")));
            textures.put(ENEMY_HP_5, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar2.png")));
            textures.put(ENEMY_HP_4, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar3.png")));
            textures.put(ENEMY_HP_3, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar4.png")));
            textures.put(ENEMY_HP_2, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar5.png")));
            textures.put(ENEMY_HP_1, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "Gengar6.png")));

            textures.put(STONE_1, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone1.png")));
            textures.put(STONE_2, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone2.png")));
            textures.put(STONE_3, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone3.png")));
            textures.put(STONE_4, TextureLoader.getTexture("PNG", new FileInputStream(imgLocation + "stone4.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Texture getTexture(int textureNum) {
        return textures.get(textureNum);
    }
}
