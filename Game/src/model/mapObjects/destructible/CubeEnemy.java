package model.mapObjects.destructible;

import model.handlers.TextureHandler;
import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public class CubeEnemy extends EnemyEntity {
    private static double cubeScale = 10;

    public CubeEnemy(double x, double z, TextureHandler textureHandler) {
        super(ShapeType.CUBE, cubeScale, x, z);
        updateTexture();
        this.textureHandler = textureHandler;
    }

    public CubeEnemy(double x, double z, int pointValue, TextureHandler textureHandler) {
        super(ShapeType.CUBE, cubeScale, x, z);
        this.pointValue = pointValue;
        updateTexture();
        this.textureHandler = textureHandler;
    }

    public CubeEnemy(double x, double z, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        updateTexture();
        this.textureHandler = textureHandler;
    }

    public CubeEnemy(double x, double z, int pointValue, Vector3f color, TextureHandler textureHandler) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        this.pointValue = pointValue;
        updateTexture();
        this.textureHandler = textureHandler;
    }

    public CubeEnemy(double x, double z, Vector3f color, boolean outline, TextureHandler textureHandler) {
        super(ShapeType.CUBE, cubeScale, x, z, color, outline);
        updateTexture();
        this.textureHandler = textureHandler;
    }

    @Override
    protected void updateTexture() {
        switch(hp) {
            case 6:
                textureNum = TextureHandler.ENEMY_HP_6;
                break;
            case 5:
                textureNum = TextureHandler.ENEMY_HP_5;
                break;
            case 4:
                textureNum = TextureHandler.ENEMY_HP_4;
                break;
            case 3:
                textureNum = TextureHandler.ENEMY_HP_3;
                break;
            case 2:
                textureNum = TextureHandler.ENEMY_HP_2;
                break;
            case 1:
                textureNum = TextureHandler.ENEMY_HP_1;
                break;
            default:
                textureNum = TextureHandler.ENEMY_HP_6;
                break;
        }
    }
}
