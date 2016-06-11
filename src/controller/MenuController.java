package controller;

import view.MenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kamil on 2016-04-15.
 */
public class MenuController
{
    private MenuFrame frame;

    public MenuController()
    {
        frame = new MenuFrame();
        frame.addButtonsListener(new ButtonsListener());
    }

    public void launch()
    {
        frame.setVisible(true);
    }

    private class ButtonsListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            switch (e.getActionCommand())
            {
                case "Play":
                    frame.setVisible(false);
                    new GameController().run();
                    break;
                case "Help":
                    String helpMessage = "Controls: \nMove - arrows \nShoot - space \nSwitch weapon - alt\nExit - escape";
                    JOptionPane.showMessageDialog(frame, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "About":
                    String aboutMessage = "Asteroids v0.1 \nAuthor: Kamil Lenkiewicz";
                    JOptionPane.showMessageDialog(frame, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Exit":
                    frame.dispose();
                    break;
            }
        }
    }
}
