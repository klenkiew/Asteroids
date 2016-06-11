package tests;

import model.Asteroid;
import model.DefaultAsteroidsManager;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

/**
 * Created by Kamil on 2016-05-18.
 */
public class AsteroidsManagerTest
{
    private static final int INITIAL_ASTEROIDS_NUMBER = 5;
    private static final int ADDITIONAL_ASTEROIDS_PER_STAGE = 2;
    private DefaultAsteroidsManager asteroidsManager;

    @org.junit.Before
    public void setUp()
    {
        asteroidsManager = new DefaultAsteroidsManager();
    }

    @Test
    public void constructedManagerHasNoAsteroids()
    {
        List<Asteroid> asteroids = asteroidsManager.getAsteroids();
        assertEquals(0, asteroids.size());
    }

    @Test
    public void managerActuallyCreatesAsteroid()
    {
        asteroidsManager.createAsteroid(500, 500, Asteroid.Type.BIG);
        assertEquals(1, asteroidsManager.getAsteroids().size());
    }

    @Test
    public void managerCreatesProperNumberOfAsteroids()
    {
        asteroidsManager.newAsteroidsWave();
        List<Asteroid> asteroids = asteroidsManager.getAsteroids();
        assertEquals(INITIAL_ASTEROIDS_NUMBER, asteroids.size());
    }

    @Test
    public void managerCountsAsteroidsCorrectly()
    {
        asteroidsManager.newAsteroidsWave();
        asteroidsManager.newAsteroidsWave();
        List<Asteroid> asteroids = asteroidsManager.getAsteroids();
        assertEquals(asteroids.size(), asteroidsManager.getAsteroidsNumber());
    }

    @Test
    public void managerIncreasesNumberOfAsteroidsCreatedProperly()
    {
        asteroidsManager.newAsteroidsWave();
        asteroidsManager.newAsteroidsWave();
        final int expectedAsteroidsNumber = INITIAL_ASTEROIDS_NUMBER + INITIAL_ASTEROIDS_NUMBER + ADDITIONAL_ASTEROIDS_PER_STAGE;
        assertEquals(expectedAsteroidsNumber, asteroidsManager.getAsteroidsNumber());
    }

    @Test
    public void managerRemovesSmallAsteroid()
    {
        asteroidsManager.createAsteroid(500, 500, Asteroid.Type.SMALL);
        assumeThat(asteroidsManager.getAsteroidsNumber(), is(1));
        Asteroid asteroid = asteroidsManager.getAsteroids().get(0);
        asteroidsManager.removeAsteroid(asteroid);
        assertEquals(0, asteroidsManager.getAsteroidsNumber());
    }

    @Test
    public void managerRemovesBigAsteroidProperly()
    {
        asteroidsManager.createAsteroid(500, 500, Asteroid.Type.BIG);
        assumeThat(asteroidsManager.getAsteroidsNumber(), is(1));
        Asteroid asteroid = asteroidsManager.getAsteroids().get(0);
        assumeNotNull(asteroid);
        asteroidsManager.removeAsteroid(asteroid);
        assertEquals(3, asteroidsManager.getAsteroidsNumber());
    }
}