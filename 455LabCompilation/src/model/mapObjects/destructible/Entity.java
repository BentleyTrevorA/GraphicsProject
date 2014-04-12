package model.mapObjects.destructible;

import model.mapObjects.ShapeType;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity extends DestructibleObject {
    protected int hp;

    public Entity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline) {
        super(type, scale, x, y, z, color, outline);
        hp = 1;
    }

    public Entity(ShapeType type, double scale, double x, double y, double z, Vector3f color, boolean outline, int hp) {
        super(type, scale, x, y, z, color, outline);
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void loseHp(int amount) {
        System.out.println("Hit an enemy!\nEnemy lost " + amount + "hp!\n Enemy hp: " + hp);
        hp -= amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    // TODO: Display hp of enemy (in future for bosses / Mega Enemy)
    // TODO: Explosion when die
}
