package model;

/**
 * Created by Kamil on 2016-05-18.
 */
public class ImmortalPlayerState implements PlayerState
{
    private Player player;
    private Player.State state;

    public ImmortalPlayerState(Player player)
    {
        this.player = player;
        this.state = Player.State.IMMORTAL;
    }

    @Override
    public void playerShotDown()
    {
        // player immortal, doesn't care
    }

    @Override
    public Player.State getState()
    {
        return state;
    }
}
