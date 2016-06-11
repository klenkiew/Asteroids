package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamil on 2016-04-16.
 */
public class GameFrame extends JFrame
{
    private static final String FRAME_TITLE = "Asteroids";

    public GameFrame()
    {
        //this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(this.FRAME_TITLE);
        setIcon();
    }

    private void setIcon()
    {
        java.net.URL url = ClassLoader.getSystemResource("resources/icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
    }
}
