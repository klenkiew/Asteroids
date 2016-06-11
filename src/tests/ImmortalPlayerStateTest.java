package tests;

import model.ImmortalPlayerState;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 2016-05-21.
 */
public class ImmortalPlayerStateTest
{
    private static final int LIVES = 3;
    private Player player;
    private ImmortalPlayerState immortalPlayerState;

    @Before
    public void setUp()
    {
        player = new Player(500, 500);
        immortalPlayerState = new ImmortalPlayerState(player);
    }

    @Test
    public void immortalPlayerIsActuallyImmortal()
    {
        immortalPlayerState.playerShotDown();
        assertEquals(LIVES, player.getLives());
    }

}