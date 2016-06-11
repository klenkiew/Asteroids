package model;

import exceptions.GameAlreadyEndedException;

/**
 * Created by Kamil on 2016-05-01.
 */
public class GameModel
{
    private static final int GAME_AREA_WIDTH = 1600;
    private static final int GAME_AREA_HEIGHT = 900;
    private final int immortalPlayerStateTimeInSeconds = 3;
    private Player player;
    private AsteroidsManager asteroidsManager;
    private long resetPlayerStateTime;
    private GameState gameState;

    public enum GameState
    {
        RUNNING, END
    }

    public GameModel(Player player, AsteroidsManager asteroidsManager)
    {
        this.asteroidsManager = asteroidsManager;
        this.player = player;
        this.resetPlayerStateTime = 0;
        this.gameState = GameState.RUNNING;
    }

    public static int getGameAreaWidth()
    {
        return GAME_AREA_WIDTH;
    }

    public static int getGameAreaHeight()
    {
        return GAME_AREA_HEIGHT;
    }

    public Player getPlayer()
    {
        return player;
    }

    public AsteroidsManager getAsteroidsManager()
    {
        return asteroidsManager;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    private boolean collisionWithPlayerDetected(Asteroid asteroid)
    {
        return asteroid.getShape().getShape().intersects(player.getShape().getShape().getBounds2D());
    }

    private void handlePlayerAsteroidCollision(Asteroid asteroid)
    {
        asteroidsManager.removeAsteroid(asteroid);
        player.playerShotDown();
        resetPlayerStateTime = System.nanoTime() + (long) immortalPlayerStateTimeInSeconds * 1000000000;
        player.becomeImmortal();
//        if (player.getLives() <= 0)
//            gameState = GameState.END;
    }

    private void detectPlayerAsteroidsCollision()
    {
        if (player.getState() != Player.State.IMMORTAL)
        {
            for (Asteroid asteroid : asteroidsManager.getAsteroids())
            {
                if (collisionWithPlayerDetected(asteroid))
                {
                    handlePlayerAsteroidCollision(asteroid);
                }
            }
        }
    }

    private boolean collisionDetected(Asteroid asteroid, Bullet bullet)
    {
        return asteroid.getShape().getShape().intersects(bullet.getShape().getBounds2D());
    }

    private void detectAsteroidsBulletsCollisions()
    {
        for (Asteroid asteroid : asteroidsManager.getAsteroids())
        {
            for (Bullet bullet : player.getBullets())
            {
                if (collisionDetected(asteroid, bullet))
                {
                    asteroidsManager.removeAsteroid(asteroid);
                    player.removeBullet(bullet, true);
                    break;
                }
            }
        }
    }

    public void detectCollisions()
    {
        detectPlayerAsteroidsCollision();
        detectAsteroidsBulletsCollisions();
    }

    private void changePlayerState(long currentTime)
    {
        if (player.getState() == Player.State.IMMORTAL && resetPlayerStateTime < currentTime)
        {
            player.becomeNormal();
        }
    }

    private void enterNextLevel(long currentTime)
    {
        resetPlayerStateTime = currentTime + (long) immortalPlayerStateTimeInSeconds * 1000000000;
        player.becomeImmortal();
        asteroidsManager.newAsteroidsWave();
    }

    private void handleGameLevels(long currentTime)
    {
        if (asteroidsManager.getAsteroidsNumber() == 0)
        {
            enterNextLevel(currentTime);
        }
    }

    private void handleGameState()
    {
        if (player.getLives() <= 0)
            gameState = GameState.END;
    }

    private void makeSureGameIsRunning()
    {
        if (gameState == GameState.END)
            throw new GameAlreadyEndedException();
    }

    public void update(long currentTime)
    {
        makeSureGameIsRunning();
        changePlayerState(currentTime);
        player.update(currentTime);
        asteroidsManager.update();
        detectCollisions();
        handleGameLevels(currentTime);
        handleGameState();
    }
}
