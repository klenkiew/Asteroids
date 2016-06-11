package view;

import model.GameModel;

/**
 * Created by Kamil on 2016-05-20.
 */
public class InfoView
{
    private GameModel gameModel;
    private Display display;

    public InfoView(GameModel gameModel, Display simpleDisplay)
    {
        this.gameModel = gameModel;
        display = simpleDisplay;
    }

    public void setGameModel(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }

    private String getWeaponName()
    {
        String weapon;
        switch (gameModel.getPlayer().getCurrentWeapon())
        {
            case 0:
                weapon = "default";
                break;
            case 1:
                weapon = "shotgun";
                break;
            default:
                weapon = "unknown";
        }
        return weapon;
    }

    public void render()
    {
        String weapon = getWeaponName();
        display.setText("Score: " + gameModel.getPlayer().getScore() + " Lives: " + gameModel.getPlayer().getLives() + " Weapon: " + weapon);
    }
}
