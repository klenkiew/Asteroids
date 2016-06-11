package tests;

import model.NormalPlayerState;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 2016-05-21.
 */
public class NormalPlayerStateTest
{
    private static final int LIVES = 3;
    private Player player;
    private NormalPlayerState normalPlayerState;

    @Before
    public void setUp() throws Exception
    {
        player = new Player(500, 500);
        normalPlayerState = new NormalPlayerState(player);
    }

    @Test
    public void normalPlayerLosesLifeWhenShotDown()
    {
        normalPlayerState.playerShotDown();
        assertEquals(LIVES - 1, player.getLives());
    }

}