package tests;

import exceptions.GameAlreadyEndedException;
import model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

/**
 * Created by Kamil on 2016-05-21.
 */
public class GameModelTest
{
    private Player player;
    private AsteroidsManager asteroidsManager;
    private GameModel gameModel;

    @Before
    public void setUp() throws Exception
    {
        player = new Player(500, 500);
        asteroidsManager = new FakeAsteroidsManager();
        gameModel = new GameModel(player, asteroidsManager);
    }

    @Test
    public void gameModelDetectsPlayerAsteroidCollisionProperly()
    {
        asteroidsManager.createAsteroid(500, 500, Asteroid.Type.SMALL);
        gameModel.update(0L);
        assertEquals(0, asteroidsManager.getAsteroidsNumber());
        assertEquals(2, player.getLives());
    }

    @Test
    public void gameModelDetectsBulletAsteroidCollisionProperly()
    {
        Player simplifiedPlayer = new SimplifiedPlayer(500, 500);
        asteroidsManager.createAsteroid(500, 500, Asteroid.Type.SMALL);
        GameModel gameModel = new GameModel(simplifiedPlayer, asteroidsManager);
        gameModel.update(0L);
        assertEquals(0, asteroidsManager.getAsteroidsNumber());
        assertEquals(0, player.getBullets().size());
    }

    @Test
    public void gameModelCreatesNewAsteroidsWaveWhenNeeded()
    {
        assumeThat(asteroidsManager.getAsteroidsNumber(), is(0));
        gameModel.update(10000000000L);
        FakeAsteroidsManager fakeAsteroidsManager = (FakeAsteroidsManager) asteroidsManager;
        assertEquals(true, fakeAsteroidsManager.isNewAsteroidsWaveGenerated());
    }

    @Test
    public void gameEndsIfPlayerHasNoLivesLeft()
    {
        Player deadPlayer = new Player(500, 500)
        {
            public int getLives()
            {
                return 0;
            }
        };
        GameModel gameModelWithDeadPlayer = new GameModel(deadPlayer, asteroidsManager);
        gameModelWithDeadPlayer.update(0L);
        assertTrue(gameModelWithDeadPlayer.getGameState() == GameModel.GameState.END);
    }

    @Ignore
    @Test(expected = GameAlreadyEndedException.class)
    public void gameModelThrowsExceptionOnUpdateIfGameIsOver2() throws NoSuchFieldException, IllegalAccessException
    {
        // [setting gameState to END with reflection]
        Field gameEndedField = GameModel.class.getDeclaredField("gameState");
        gameEndedField.setAccessible(true);
        gameEndedField.set(gameModel, GameModel.GameState.END);
        // [/setting gameState to END]

        assumeTrue(gameModel.getGameState() == GameModel.GameState.END);
        gameModel.update(0L);
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void gameModelThrowsExceptionOnUpdateIfGameIsOver()
    {
        Player deadPlayer = new Player(500, 500)
        {
            public int getLives()
            {
                return 0;
            }
        };
        GameModel gameModelWithDeadPlayer = new GameModel(deadPlayer, asteroidsManager);
        gameModelWithDeadPlayer.update(0L);
        assumeTrue(gameModelWithDeadPlayer.getGameState() == GameModel.GameState.END);
        gameModelWithDeadPlayer.update(1000L);
    }


    private class FakeAsteroidsManager implements AsteroidsManager
    {
        private boolean newAsteroidsWaveGenerated = false;

        private List<Asteroid> asteroids = new CopyOnWriteArrayList<>(new ArrayList<>());

        @Override
        public int getAsteroidsNumber()
        {
            return asteroids.size();
        }

        @Override
        public void removeAsteroid(Asteroid asteroid)
        {
            asteroids.remove(asteroid);
        }

        @Override
        public void createAsteroid(double x, double y, Asteroid.Type type)
        {
            Asteroid asteroid = new Asteroid();
            asteroid.setxPosition(x);
            asteroid.setyPosition(y);
            asteroid.setType(type);
            Rectangle2D.Double rectangle = new Rectangle2D.Double(0, 0, type.getRadius(), type.getRadius());
            asteroid.setShape(new MyShape(new Path2D.Double(rectangle), type.getRadius() / 2, type.getRadius() / 2));
            asteroids.add(asteroid);
        }

        @Override
        public void newAsteroidsWave()
        {
            newAsteroidsWaveGenerated = true;
        }

        @Override
        public void update()
        {

        }

        @Override
        public List<Asteroid> getAsteroids()
        {
            return asteroids;
        }

        public boolean isNewAsteroidsWaveGenerated()
        {
            return newAsteroidsWaveGenerated;
        }
    }


    private class SimplifiedPlayer extends Player
    {
        private boolean hasBullet = true;

        public SimplifiedPlayer(double xPosition, double yPosition)
        {
            super(xPosition, yPosition);
        }

        @Override
        public void update(long time)
        {
        }

        @Override
        public List<Bullet> getBullets()
        {
            CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>(new ArrayList<>());
            if (hasBullet)
            {
                Bullet bullet = new Bullet(this, 500, 500, 0, 0, 0L);
                bullets.add(bullet);
            }
            return bullets;
        }

        @Override
        public void removeBullet(Bullet bullet, boolean whatever)
        {
            hasBullet = false;
        }
    }

}