package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Kamil on 2016-05-09.
 */
class InputController implements KeyListener
{
    private boolean downPressed;
    private boolean upPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean spacePressed;
    private boolean escapePressed;
    private boolean altPressed;

    public InputController()
    {
        downPressed = upPressed = rightPressed = leftPressed = spacePressed = escapePressed = altPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_ALT:
                altPressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
            case KeyEvent.VK_ALT:
                altPressed = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = false;
                break;
        }
    }

    public void reset()
    {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
        spacePressed = false;
        escapePressed = false;
        altPressed = false;
    }

    public boolean isLeftPressed()
    {
        return leftPressed;
    }

    public boolean isRightPressed()
    {
        return rightPressed;
    }

    public boolean isUpPressed()
    {
        return upPressed;
    }

    public boolean isDownPressed()
    {
        return downPressed;
    }

    public boolean isSpacePressed()
    {
        return spacePressed;
    }

    public boolean isEscapePressed()
    {
        return escapePressed;
    }

    public boolean isAltPressed()
    {
        return altPressed;
    }

    public void resetAltPressed()
    {
        altPressed = false;
    }
}
