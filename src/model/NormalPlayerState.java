package model;

/**
 * Created by Kamil on 2016-05-18.
 */
public class NormalPlayerState implements PlayerState
{
    private Player player;
    private Player.State state;

    public NormalPlayerState(Player player)
    {
        this.player = player;
        this.state = Player.State.NORMAL;
    }

    @Override
    public void playerShotDown()
    {
        player.die();
    }

    @Override
    public Player.State getState()
    {
        return state;
    }
}
