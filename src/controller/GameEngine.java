package controller;

import model.DefaultAsteroidsManager;
import model.GameModel;
import model.Player;
import view.GameView;
import view.InfoView;
import view.SimpleDisplay;

import java.awt.*;

/**
 * Created by Kamil on 2016-05-09.
 */
public class GameEngine
{
    private static double DEFAULT_PLAYER_X_POSITION = GameModel.getGameAreaWidth() / 2;
    private static double DEFAULT_PLAYER_Y_POSITION = GameModel.getGameAreaHeight() / 2;
    private GameModel gameModel;
    private GameView gameView;
    private InputController inputController;
    private InfoView infoView;
    private Player player;

    public GameEngine(GameModel gameModel, GameView gameView, InputController inputController, InfoView infoView)
    {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.inputController = inputController;
        this.infoView = infoView;
        player = gameModel.getPlayer();
    }

    public GameEngine(SimpleDisplay simpleDisplay, InputController inputController)
    {
        initModel();
        this.inputController = inputController;
        this.infoView = new InfoView(gameModel, simpleDisplay);
    }

    private void initModel()
    {
        player = new Player(DEFAULT_PLAYER_X_POSITION, DEFAULT_PLAYER_Y_POSITION);
        this.gameModel = new GameModel(player, new DefaultAsteroidsManager());
        this.gameView = new GameView(gameModel);
    }

    public void resetModel()
    {
        initModel();
        infoView.setGameModel(gameModel);
        inputController.reset();
    }

    public void render(Graphics bufferGraphics)
    {
        gameView.render(bufferGraphics);
        infoView.render();
    }

    public void handleInput()
    {
        if (inputController.isUpPressed() && !inputController.isDownPressed())
            player.accelerate();
        else if (inputController.isDownPressed() && !inputController.isUpPressed())
            player.accelerateReverse();
        if (inputController.isRightPressed() && !inputController.isLeftPressed())
            player.rotateRight();
        else if (!inputController.isRightPressed() && inputController.isLeftPressed())
            player.rotateLeft();
        if (inputController.isSpacePressed())
            player.shoot();
        if (inputController.isAltPressed())
        {
            player.switchWeapon();
            inputController.resetAltPressed();
        }
    }

    public void update(long currentTime)
    {
        gameModel.update(currentTime);
    }

    public int getScore()
    {
        return gameModel.getPlayer().getScore();
    }

    public GameModel.GameState getGameState()
    {
        return gameModel.getGameState();
    }
}
