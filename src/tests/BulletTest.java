package tests;

import model.Bullet;
import model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 2016-05-17.
 */
public class BulletTest
{
    final double EPSILON = 0.001;
    private final long timeToLive = 2000000000L;
    Bullet bullet;
    Player owner;

    @org.junit.Before
    public void setUp() throws Exception
    {
        owner = new Player(500, 500);
    }

    @Test
    public void constructedBulletHasProperPosition()
    {
        bullet = new Bullet(owner, 500, 400, 0, 0, 0L);
        assertEquals(500, bullet.getxPosition(), EPSILON);
        assertEquals(400, bullet.getyPosition(), EPSILON);
    }

    @Test
    public void constructedBulletHasProperVelocity()
    {
        bullet = new Bullet(owner, 500, 500, 3, 4, 0L);
        assertEquals(3, bullet.getxVelocity(), EPSILON);
        assertEquals(4, bullet.getyVelocity(), EPSILON);
    }

    @Test
    public void bulletMovesProperly()
    {
        long createTime = 0L;
        bullet = new Bullet(owner, 500, 500, 3, 4, createTime);
        bullet.update(createTime + 1000L);
        assertEquals(bullet.getxPosition(), 503, EPSILON);
        assertEquals(bullet.getyPosition(), 504, EPSILON);
    }

    @Test
    public void bulletDoesntDisappearTooEarly()
    {
        long createTime = 0L;
        bullet = new Bullet(owner, 500, 500, 0, 0, createTime);
        owner.addBullet(bullet);
        bullet.update(createTime + timeToLive - 10L);
        assertEquals(1, owner.getBullets().size());
    }

    @Test
    public void bulletDisappearsWhenItsTimeEnds()
    {
        long createTime = 0L;
        bullet = new Bullet(owner, 500, 500, 0, 0, createTime);
        owner.addBullet(bullet);
        bullet.update(createTime + timeToLive + 10L);
        assertEquals(0, owner.getBullets().size());
    }

    @Test
    public void bulletMovesProperlyWhenOffScreen()
    {
        long createTime = 0L;
        // screen width is 1600, starting with 0, so correct coordinates are 0 - 1599
        bullet = new Bullet(owner, 1599, 899, 3, 3, createTime);
        bullet.update(createTime + 1000L);
        assertEquals(2, bullet.getxPosition(), EPSILON);
        assertEquals(2, bullet.getyPosition(), EPSILON);
    }
}