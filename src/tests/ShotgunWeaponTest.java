package tests;

import model.Bullet;
import model.Player;
import model.ShotgunWeapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 2016-05-22.
 */
public class ShotgunWeaponTest
{
    private final int expectedNumberOfBullets = 7;

    private FakePlayer player;

    @Before
    public void setUp() throws Exception
    {
        player = new FakePlayer(500, 500);
    }

    @Test
    public void shotgunWeaponShootsProperNumberOfBullets()
    {
        ShotgunWeapon shotgun = new ShotgunWeapon(player);
        shotgun.shoot(10000000000L);
        assertEquals(expectedNumberOfBullets, player.bulletsNumber);
    }

    private class FakePlayer extends Player
    {
        public int bulletsNumber = 0;

        public FakePlayer(double xPosition, double yPosition)
        {
            super(xPosition, yPosition);
        }

        @Override
        public void addBullet(Bullet bullet)
        {
            ++bulletsNumber;
        }
    }

}