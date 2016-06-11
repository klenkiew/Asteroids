package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Kamil on 2016-03-23.
 */
public class MenuFrame extends JFrame
{
    private static final String title = "Asteroids Menu";

    private final String[] menuOptions = {"Play", "Help", "About", "Exit"};
    private JPanel buttonsPanel;

    public MenuFrame()
    {
        buttonsPanel = new JPanel();
        for (String option : menuOptions)
            buttonsPanel.add(new JButton(option));
        this.setTitle(title);
        setIcon();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(buttonsPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void setIcon()
    {
        java.net.URL url = ClassLoader.getSystemResource("resources/icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
    }

    public void addButtonsListener(ActionListener listener)
    {
        for (Component button : buttonsPanel.getComponents())
        {
            try
            {
                JButton b = (JButton) button;
                b.addActionListener(listener);
            } catch (Exception e)
            {
                // continue
            }
        }
    }
}
