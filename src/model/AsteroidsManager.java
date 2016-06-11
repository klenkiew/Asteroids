package model;

import java.util.List;

/**
 * Created by Kamil on 2016-05-22.
 */
public interface AsteroidsManager
{
    int getAsteroidsNumber();

    void removeAsteroid(Asteroid asteroid);

    void createAsteroid(double x, double y, Asteroid.Type type);

    void newAsteroidsWave();

    void update();

    List<Asteroid> getAsteroids();
}
