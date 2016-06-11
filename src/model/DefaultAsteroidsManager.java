package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kamil on 2016-05-04.
 */
public class DefaultAsteroidsManager implements AsteroidsManager
{
    private static final int SMALL_ASTEROIDS_NUMBER_FROM_BIG_ASTEROID = 3;
    private static final int POSITION_DEVIATION_OF_BIG_ASTEROID_PIECES = 50;
    private static final double BIG_ASTEROID_CHANCE = 0.35;
    private static final int ADDITIONAL_ASTEROIDS_PER_STAGE = 2;
    private final int initialAsteroidsNumber = 5;
    private volatile List<Asteroid> asteroids;
    private ShapeGenerator smallAsteroidShapeGenerator;
    private ShapeGenerator bigAsteroidShapeGenerator;
    private int stage;

    public DefaultAsteroidsManager()
    {
        this.asteroids = new CopyOnWriteArrayList<>(new ArrayList<>());
        this.smallAsteroidShapeGenerator = new AsteroidShapeGenerator(Asteroid.Type.SMALL.getRadius());
        this.bigAsteroidShapeGenerator = new AsteroidShapeGenerator(Asteroid.Type.BIG.getRadius());
        this.stage = 0;
    }

    public List<Asteroid> getAsteroids()
    {
        return asteroids;
    }

    private void generate(int number)
    {
        while (number-- > 0)
        {
            double random = Math.random();
            Asteroid.Type newAsteroidType;
            if (random < BIG_ASTEROID_CHANCE)
                newAsteroidType = Asteroid.Type.BIG;
            else
                newAsteroidType = Asteroid.Type.SMALL;
            double xPos = Math.random() * GameModel.getGameAreaWidth();
            double yPos = Math.random() * GameModel.getGameAreaHeight();
            createAsteroid(xPos, yPos, newAsteroidType);
        }
    }

    private void adjustPositionWhenOffScreen(Asteroid asteroid)
    {
        if (asteroid.getxPosition() >= GameModel.getGameAreaWidth())
            asteroid.setxPosition(asteroid.getxPosition() - GameModel.getGameAreaWidth());
        if (asteroid.getxPosition() < 0)
            asteroid.setxPosition(GameModel.getGameAreaWidth() - asteroid.getxPosition());
        if (asteroid.getyPosition() >= GameModel.getGameAreaHeight())
            asteroid.setyPosition(asteroid.getyPosition() - GameModel.getGameAreaHeight());
        if (asteroid.getyPosition() < 0)
            asteroid.setyPosition(GameModel.getGameAreaHeight() - asteroid.getyPosition());
    }

    private void updatePosition(Asteroid asteroid)
    {
        asteroid.setxPosition(asteroid.getxPosition() + asteroid.getxVelocity());
        asteroid.setyPosition(asteroid.getyPosition() + asteroid.getyVelocity());
    }

    public void update()
    {
        for (Asteroid asteroid : asteroids)
        {
            updatePosition(asteroid);
            adjustPositionWhenOffScreen(asteroid);
        }
    }

    public void newAsteroidsWave()
    {
        generate(initialAsteroidsNumber + ADDITIONAL_ASTEROIDS_PER_STAGE * stage);
        ++stage;
    }

    private double generateRandomVelocity(double min, double max)
    {
        double velocity = Math.random() * (max - min + 1) + min;
        if (Math.random() < 0.5)
            velocity = -velocity;
        return velocity;
    }

    public void createAsteroid(double x, double y, Asteroid.Type type)
    {
        double startVelocityX = generateRandomVelocity(1, 5);
        double startVelocityY = generateRandomVelocity(1, 5);
        Asteroid asteroid = new Asteroid();
        ShapeGenerator shapegenerator = type == Asteroid.Type.BIG ? bigAsteroidShapeGenerator : smallAsteroidShapeGenerator;
        asteroid.setShape(shapegenerator.generateShape());
        asteroid.setType(type);
        asteroid.setxAcceleration(0);
        asteroid.setyAcceleration(0);
        asteroid.setxPosition(x);
        asteroid.setyPosition(y);
        asteroid.setxVelocity(startVelocityX);
        asteroid.setyVelocity(startVelocityY);
        asteroids.add(asteroid);
    }

    private void breakIntoPieces(Asteroid asteroid)
    {
        for (int i = 0; i < SMALL_ASTEROIDS_NUMBER_FROM_BIG_ASTEROID; ++i)
        {
            double xPos = asteroid.getxPosition();
            double yPos = asteroid.getyPosition();
            double xDeviation = (Math.random() - 0.5) * POSITION_DEVIATION_OF_BIG_ASTEROID_PIECES * 2;
            double yDeviation = (Math.random() - 0.5) * POSITION_DEVIATION_OF_BIG_ASTEROID_PIECES * 2;
            createAsteroid(xPos + xDeviation, yPos + yDeviation, Asteroid.Type.SMALL);
        }
    }

    public void removeAsteroid(Asteroid asteroid)
    {
        if (asteroid.getType() == Asteroid.Type.BIG)
        {
            breakIntoPieces(asteroid);
        }
        asteroids.remove(asteroid);
    }

    public int getAsteroidsNumber()
    {
        return asteroids.size();
    }
}
