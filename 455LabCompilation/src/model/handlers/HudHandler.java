package model.handlers;

public class HudHandler {
    private ShotsHandler shotsHandler;
    private ScoreHandler scoreHandler;
    private EnemyHandler enemyHandler;
    private HUDTextHandler hudTextHandler;

    public HudHandler(ScoreHandler scoreHandler, ShotsHandler shotsHandler, EnemyHandler enemyHandler) {
        hudTextHandler = new HUDTextHandler();
        this.scoreHandler = scoreHandler;
        this.shotsHandler = shotsHandler;
        this.enemyHandler = enemyHandler;
    }

    public void drawHud() {
        hudTextHandler.drawShotsRemaining(shotsHandler.getShotsRemaining());
        hudTextHandler.drawPoints(scoreHandler.getPoints());
        hudTextHandler.drawNumEnemies(enemyHandler.getEnemiesRemaining());
    }
}
