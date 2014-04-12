package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;

public class CubeEnemy extends EnemyEntity {
    private static double cubeScale = 10;
    private String defaultTexture = "Gengar6.png";

    public CubeEnemy(double x, double z) {
        super(ShapeType.CUBE, cubeScale, x, z);
        textureFile = defaultTexture;
    }

    public CubeEnemy(double x, double z, int pointValue) {
        super(ShapeType.CUBE, cubeScale, x, z);
        this.pointValue = pointValue;
        textureFile = defaultTexture;
    }

    public CubeEnemy(double x, double z, Vector3f color) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        textureFile = defaultTexture;
    }

    public CubeEnemy(double x, double z, int pointValue, Vector3f color) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        this.pointValue = pointValue;
        textureFile = defaultTexture;
    }

    public CubeEnemy(double x, double z, Vector3f color, boolean outline) {
        super(ShapeType.CUBE, cubeScale, x, z, color, outline);
        textureFile = defaultTexture;
    }

    @Override
    protected void updateTexture() {
        switch(hp) {
            case 6:
                textureFile = "Gengar1.png";
                break;
            case 5:
                textureFile = "Gengar2.png";
                break;
            case 4:
                textureFile = "Gengar3.png";
                break;
            case 3:
                textureFile = "Gengar4.png";
                break;
            case 2:
                textureFile = "Gengar5.png";
                break;
            case 1:
                textureFile = "Gengar6.png";
                break;
        }
    }
}
