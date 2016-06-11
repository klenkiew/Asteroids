package view;

import javax.swing.*;

/**
 * Created by Kamil on 2016-05-20.
 */
public class SimpleDisplay implements Display
{
    private JLabel label;

    public SimpleDisplay(JLabel label)
    {
        this.label = label;
    }

    @Override
    public void setText(String text)
    {
        label.setText(text);
    }
}
