package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import model.renderers.ShapeRenderer;
import org.lwjgl.util.vector.Vector3f;

public class CubeEnemy extends EnemyEntity {
    private static double cubeScale = 10;
    private int defaultTexture = 1;

    public CubeEnemy(double x, double z, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, cubeScale, x, z);
        texture = defaultTexture;
        this.shapeRenderer = shapeRenderer;
    }

    public CubeEnemy(double x, double z, int pointValue, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, cubeScale, x, z);
        this.pointValue = pointValue;
        texture = defaultTexture;
        this.shapeRenderer = shapeRenderer;
    }

    public CubeEnemy(double x, double z, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        texture = defaultTexture;
        this.shapeRenderer = shapeRenderer;
    }

    public CubeEnemy(double x, double z, int pointValue, Vector3f color, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, cubeScale, x, z, color);
        this.pointValue = pointValue;
        texture = defaultTexture;
        this.shapeRenderer = shapeRenderer;
    }

    public CubeEnemy(double x, double z, Vector3f color, boolean outline, ShapeRenderer shapeRenderer) {
        super(ShapeType.CUBE, cubeScale, x, z, color, outline);
        texture = defaultTexture;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    protected void updateTexture() {
        switch(hp) {
            case 6:
                texture = ShapeRenderer.ENEMY_HP_6;
                break;
            case 5:
                texture = ShapeRenderer.ENEMY_HP_5;
                break;
            case 4:
                texture = ShapeRenderer.ENEMY_HP_4;
                break;
            case 3:
                texture = ShapeRenderer.ENEMY_HP_3;
                break;
            case 2:
                texture = ShapeRenderer.ENEMY_HP_2;
                break;
            case 1:
                texture = ShapeRenderer.ENEMY_HP_1;
                break;
        }
    }
}
