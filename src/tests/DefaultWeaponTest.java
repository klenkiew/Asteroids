package tests;

import model.DefaultWeapon;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 2016-05-21.
 */
public class DefaultWeaponTest
{
    private final long now = 100000000000L;
    private final long delay = 100000000L;
    private Player player;
    private DefaultWeapon defaultWeapon;

    @Before
    public void setUp()
    {
        player = new Player(500, 500);
        defaultWeapon = new DefaultWeapon(player);
    }

    @Test
    public void weaponActuallyShoots()
    {
        defaultWeapon.shoot(now);
        assertEquals(1, player.getBullets().size());
    }

    @Test
    public void weaponCannotShootAgainImmediately()
    {
        defaultWeapon.shoot(now);
        defaultWeapon.shoot(now + delay - 1L);
        assertEquals(1, player.getBullets().size());
    }

    @Test
    public void weaponCanShootAgainIfEnoughAmountOfTimePassed()
    {
        defaultWeapon.shoot(now);
        defaultWeapon.shoot(now + delay + 1L);
        assertEquals(2, player.getBullets().size());
    }

}