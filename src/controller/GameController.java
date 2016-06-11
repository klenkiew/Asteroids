package controller;

import exceptions.GameAlreadyEndedException;
import model.GameModel;
import view.GameFrame;
import view.SimpleDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Kamil on 2016-03-23.
 */
public class GameController
{
    private JLabel scoreLabel;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private GameFrame gameFrame;
    private InputController inputController;
    private GameEngine gameEngine;
    private SimpleDisplay simpleDisplay;

    public GameController()
    {
        gameFrame = new GameFrame();
        initDisplay();
        gamePanel = new JPanel();
        gameFrame.add(gamePanel);
        gameFrame.add(infoPanel, BorderLayout.NORTH);
        inputController = new InputController();
        gameEngine = new GameEngine(simpleDisplay, inputController);
    }

    private void initDisplay()
    {
        infoPanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
        Font font = new Font("Arial", Font.BOLD, 20);
        scoreLabel.setFont(font);
        infoPanel.add(scoreLabel);
        simpleDisplay = new SimpleDisplay(scoreLabel);
    }

    private void initFrame()
    {
        gameFrame.addKeyListener(inputController);
        gameFrame.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                super.focusLost(e);
                inputController.reset();
            }
        });
        gameFrame.setVisible(true);
        gameFrame.setBackground(Color.DARK_GRAY);
        gameFrame.setResizable(false);
    }

    public void run()
    {
        initFrame();
        new Thread(new GameLoop()).start();
    }

    private class GameLoop implements Runnable
    {
        static final long NS_PER_UPDATE = 16000000L;
        private boolean gameReallyEnded = false;
        private boolean userExited = false;
        private Graphics bufferGraphics;
        private Graphics gamePanelGraphics;
        private BufferedImage buffer;

        private void displayGameOverFrame()
        {
            Object[] options = {"Play again", "Exit"};
            String message = "Game over. Your score: " + gameEngine.getScore();
            String title = "Game over!";
            int playAgainOrNot = JOptionPane.showOptionDialog(gameFrame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (playAgainOrNot == JOptionPane.NO_OPTION)
                gameReallyEnded = true;
        }

        private void handleGameEnd()
        {
            Frame[] applicationFrames = Frame.getFrames();
            for (Frame frame : applicationFrames)
                frame.dispose();
        }

        private void trySleep(long delta)
        {
            if (delta < NS_PER_UPDATE)
            {
                try
                {
                    Thread.sleep((NS_PER_UPDATE - delta) / 1000000L);
                } catch (InterruptedException e)
                {
                    // who cares
                }
            }
        }

        private void handleRendering()
        {
            gameEngine.render(bufferGraphics);
            gamePanelGraphics.drawImage(buffer, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
            bufferGraphics.clearRect(0, 0, GameModel.getGameAreaWidth(), GameModel.getGameAreaHeight());
        }

        private void initDoubleBuffering()
        {
            buffer = new BufferedImage(GameModel.getGameAreaWidth(), GameModel.getGameAreaHeight(), BufferedImage.TYPE_INT_RGB);
            bufferGraphics = buffer.getGraphics();
            gamePanelGraphics = gamePanel.getGraphics();
        }

        private void runGameLoop()
        {
            initDoubleBuffering();
            while (true)
            {
                long lastTime = System.nanoTime();
                handleEscapePressed();
                if (userExited)
                    return;
                gameEngine.handleInput();
                try
                {
                    gameEngine.update(System.nanoTime());
                    if (gameEngine.getGameState() == GameModel.GameState.END)
                        return;
                } catch (GameAlreadyEndedException e)
                {
                    JOptionPane.showMessageDialog(gameFrame, "Game ended with error. Please contact game support.", "Game error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                handleRendering();
                long delta = System.nanoTime() - lastTime;
                trySleep(delta);
            }
        }

        private void handleEscapePressed()
        {
            if (inputController.isEscapePressed())
            {
                String message = "Are you sure you wanna exit?";
                String title = "Exit game";
                int exitOrNot = JOptionPane.showOptionDialog(gameFrame, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (exitOrNot == JOptionPane.OK_OPTION)
                    userExited = true;
                inputController.reset();
            }
        }

        @Override
        public void run()
        {
            while (!gameReallyEnded && !userExited)
            {
                runGameLoop();
                if (!userExited)
                {
                    displayGameOverFrame();
                    if (!gameReallyEnded)
                        gameEngine.resetModel();
                }
            }
            handleGameEnd();
        }
    }
}
