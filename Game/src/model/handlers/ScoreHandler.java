package model.handlers;

import model.mapObjects.destructible.EnemyEntity;

public class ScoreHandler {
    // Points
    private int points = 0;

    public void addPoints(EnemyEntity enemyKilled) {
        points += enemyKilled.getPointValue();
    }

    public void addPoints(int pointsToAdd) {
        points += points;
    }

    public int getPoints() {
        return points;
    }
}
