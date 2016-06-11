package tests;

import model.Bullet;
import model.Player;
import model.Spaceship;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

/**
 * Created by Kamil on 2016-05-16.
 */
public class PlayerTest
{
    final double EPSILON = 0.001;
    final double MAX_VELOCITY = 7;
    final double MAX_ACCELERATION = 0.5;
    final double SLOWING_SPEED = 0.1;
    final double ROTATION_STEP = 4.0;
    final int LIVES = 3;
    Player player;

    @org.junit.Before
    public void setUp() throws Exception
    {
        player = new Player(500, 500);
    }

    @Test
    public void constructedPlayerHasProperPosition()
    {
        double playerPositionX = player.getxPosition();
        double playerPositionY = player.getyPosition();

        assertEquals(500, playerPositionX, EPSILON);
        assertEquals(500, playerPositionY, EPSILON);
    }

    @Test
    public void constructedPlayerIsNotMoving()
    {
        assertEquals(0, player.getxVelocity(), EPSILON);
        assertEquals(0, player.getyVelocity(), EPSILON);
    }

    @Test
    public void constructedPlayerHasNoRotation()
    {
        assertEquals(0, player.getRotation(), EPSILON);
    }

    @Test
    public void playerRotatesWithProperRotationStep()
    {
        player.rotateRight();
        player.update(0L);
        assertEquals(4, player.getRotation(), EPSILON);
    }

    @Test
    public void playerRotates360Degrees()
    {
        Player rotatedPlayer = new Player(500, 500, 358);
        rotatedPlayer.rotateRight();
        rotatedPlayer.update(0L);
        assertEquals(2, rotatedPlayer.getRotation(), EPSILON);
    }

    @Test
    public void playerRotationCannotBeNegative()
    {
        Player rotatedPlayer = new Player(500, 500, 2);
        rotatedPlayer.rotateLeft();
        rotatedPlayer.update(0L);
        assertEquals(358, rotatedPlayer.getRotation(), EPSILON);
    }

    @Test
    public void playerActuallyShoots()
    {
        player.shoot();
        player.update(10000000000L);
        assertEquals(1, player.getBullets().size());
    }

    @Test
    public void playerMovesInRightDirection()
    {
        Player rotatedPlayer = new Player(500, 500, 45);
        rotatedPlayer.accelerate();
        rotatedPlayer.update(0L);
        /*
                  / |
        *     v /   |
        *     /     | y      sin45 = y/1 => y = sin45 = sqrt(2)/2 * v
        *   /45     |       x = -cos45 = -sqrt(2)/2 * v
        * /_________|
        *      x
         *  */
        assertEquals(500 + MAX_ACCELERATION * Math.sqrt(2) / 2, rotatedPlayer.getxPosition(), EPSILON);
        assertEquals(500 - MAX_ACCELERATION * Math.sqrt(2) / 2, rotatedPlayer.getyPosition(), EPSILON);
    }

    @Test
    public void playerAccelerationIncreasesVelocity()
    {
        Spaceship spaceship = new Spaceship();
        spaceship.setxPosition(500);
        spaceship.setyPosition(500);
        spaceship.setxVelocity(2);
        spaceship.setyVelocity(3);
        spaceship.setxAcceleration(1);
        spaceship.setyAcceleration(2);
        Player player = new Player(spaceship);
        player.update(0L);
        assertEquals(3, spaceship.getxVelocity(), EPSILON);
        assertEquals(5, spaceship.getyVelocity(), EPSILON);
    }

    @Test
    public void playerWithNoAccelerationSlowsDown()
    {
        Spaceship spaceship = new Spaceship();
        spaceship.setxPosition(500);
        spaceship.setyPosition(500);
        spaceship.setxVelocity(2);
        spaceship.setyVelocity(0);
        spaceship.setxAcceleration(0);
        spaceship.setyAcceleration(0);
        Player player = new Player(spaceship);
        player.update(0L);
        assertEquals(1.9, spaceship.getxVelocity(), EPSILON);
        assertEquals(0, spaceship.getyVelocity(), EPSILON);
    }

    @Test
    public void playerSlowsDownProperlyInBothAxes()
    {
        Spaceship spaceship = new Spaceship();
        spaceship.setxPosition(500);
        spaceship.setyPosition(500);
        spaceship.setxVelocity(3);
        spaceship.setyVelocity(4);
        spaceship.setxAcceleration(0);
        spaceship.setyAcceleration(0);
        Player player = new Player(spaceship);
        // linear velocity before - 5
        player.update(0L);
        double linearVelocity = Math.sqrt(spaceship.getxVelocity() * spaceship.getxVelocity() + spaceship.getyVelocity() * spaceship.getyVelocity());
        assertEquals(4.9, linearVelocity, EPSILON);
    }

    @Test
    public void playerCannotExceedMaxVelocity()
    {
        Spaceship spaceship = new Spaceship();
        spaceship.setxPosition(500);
        spaceship.setyPosition(500);
        spaceship.setxVelocity(7);
        spaceship.setyVelocity(0);
        spaceship.setxAcceleration(1);
        spaceship.setyAcceleration(0);
        Player player = new Player(spaceship);
        player.update(0L);
        assertEquals(7, spaceship.getxVelocity(), EPSILON);
    }

    @Test
    public void playerDies()
    {
        player.die();
        assertEquals(LIVES - 1, player.getLives());
    }

    @Test
    public void playerRemovesBullet()
    {
        final long now = 10000000000000L;
        player.shoot();
        player.update(now);
        assumeThat(player.getBullets().size(), is(1));
        Bullet bullet = player.getBullets().get(0);
        player.removeBullet(bullet, false);
        assertEquals(0, player.getBullets().size());
    }
}