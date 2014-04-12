package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity extends DestructibleObject {
    protected static int defaultHp = 1;
    protected int hp;

    public Entity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
        hp = defaultHp;
        updatePointValue();
    }

    public Entity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline, int hp) {
        super(type, scale, x, y, z, color, outline);
        this.hp = hp;
        updatePointValue();
    }

    public void setHp(int hp) {
        this.hp = hp;
        updatePointValue();
        updateTexture();
    }

    public int getHp() {
        return hp;
    }

    public void loseHp(int amount) {
//        System.out.println("Hit an enemy!\nEnemy lost " + amount + "hp!\n Enemy hp: " + hp);
        hp -= amount;

        updateTexture();
    }

    protected void updatePointValue() {

    }

    protected void updateTexture() {

    }

    public boolean isDead() {
        return hp <= 0;
    }

    // TODO: Display hp of enemy (in future for bosses / Mega Enemy)
    // TODO: Explosion when die
}
