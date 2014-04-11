package model;

import camera.Camera;
import game.Shot;
import model.mapObjects.MapCreator;
import model.mapObjects.destructible.CubeEnemy;
import model.mapObjects.destructible.EnemyEntity;
import model.mapObjects.MapObject;
import model.mapObjects.destructible.SphereEnemy;
import model.renderers.ShapeRenderer;
import model.renderers.TextHandler;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class Model {
    // Variables for creating the playing field
    private int tileSize = 25;
    private int numTilesInOneDirection = 10;
    private int lineWidth = 1;

    // Shots taken
    private ArrayList<Shot> shots;
    private int maxShots = 1;

    // Points
    private int points = 0;

    private MapCreator mapCreator;
    private TextHandler textHandler;

    // Map Obstacles
    private ArrayList<MapObject> obstacles;

    // Map Enemies
    private ArrayList<EnemyEntity> enemies;

    private Vector3f shotColor = Colors.PINK;

    public Model(int mapNumber) {
        mapCreator = new MapCreator();
        textHandler = new TextHandler();
        shots = new ArrayList<Shot>();
        obstacles = mapCreator.createMap(mapNumber);

        enemies = new ArrayList<EnemyEntity>();
        enemies.add(new CubeEnemy(0, 0));
        enemies.add(new CubeEnemy(0, -200, 50));
        enemies.add(new CubeEnemy(0, 200, 50));
        enemies.add(new SphereEnemy(150, 0));
    }

    public void update() {
        updateShotPositions();
    }

    public void drawMap() {
        glLineWidth(lineWidth);

        ShapeRenderer.drawFloor(tileSize, numTilesInOneDirection);
//        ShapeRenderer.drawFloorTiles(tileSize, numTilesInOneDirection); // Makes floor pink
        ShapeRenderer.drawWalls(500.0, 100.0, Colors.BLUE);

        drawObstacles();
        drawEnemies();
        drawShots();
        drawHud();
    }

    public void drawObstacles() {
        for(MapObject obstacle : obstacles) {
            obstacle.render();
        }
    }

    public void drawEnemies() {
        for(EnemyEntity enemy : enemies) {
            enemy.render();
        }
    }

    public void drawShots() {
        for (Shot shot : shots) {
            ShapeRenderer.drawSphere(shot.size, shot.x, shot.y, shot.z, shot.slices, shot.stacks, shotColor);
        }
    }

    public void drawHud() {
        textHandler.drawShotsRemaining(maxShots, shots.size());
        textHandler.drawPoints(points);
        textHandler.drawNumEnemies(enemies.size());
    }

    public void addShot(Camera camera) {
        if (shots.size() < maxShots) {
            shots.add(new Shot(camera));
        }
    }

    private void updateShotPositions() {
        Set<Shot> shotRemoval = new HashSet<Shot>();
        for (Shot shot : shots) {
            shot.updatePosition();
            // TODO: Don't do all these checks here
            if (shot.isOutsideGameField(-tileSize * numTilesInOneDirection, tileSize * numTilesInOneDirection)) {
                shotRemoval.add(shot);
            }
            else {
                EnemyEntity enemyHit = findEnemyHitByShot(shot);
                if (enemyHit != null)
                {
                    enemyHit.loseHp(shot.damage);
                    System.out.println("Hit an enemy!\nEnemy lost " + shot.damage + "hp!\n Enemy hp = " + enemyHit.getHp());
                    if(enemyHit.getHp() <= 0) {
                        enemies.remove(enemyHit);
                        points += enemyHit.getPointValue();
                    }
                    shotRemoval.add(shot);
                }
                else {
                    MapObject objectHit = findObjectHitByShot(shot);
                    if (objectHit != null)
                    {
                        System.out.println("I'm hit!\n" + objectHit);

//                        if(objectHit.getCollisionPlane() == MapObject.Z_PLANE)
                            shot.dz *= -1;
//                        else if(objectHit.getCollisionPlane() == MapObject.X_PLANE)
//                            shot.dx *= -1;

                        shot.loseHp();
                        if(shot.hp <= 0) {
                            System.out.println("Shot died");
                            shotRemoval.add(shot);
                        }
                    }
                }
            }
        }
        for (Shot shot : shotRemoval) {
            shots.remove(shot);
        }
    }

    private MapObject findObjectHitByShot(Shot shot) {
        for(MapObject obstacle : obstacles) {
            if(obstacle.isCollidingWith(shot.x, shot.y, shot.z)) {
                return obstacle;
            }
        }
        return null;
    }

    private EnemyEntity findEnemyHitByShot(Shot shot) {
        for(EnemyEntity enemy: enemies) {
            if(enemy.isCollidingWith(shot.x, shot.y, shot.z)) {
                return enemy;
            }
        }
        return null;
    }
}
