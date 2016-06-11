import controller.MenuController;

/**
 * Created by Kamil on 2016-03-23.
 */
public class Game implements Runnable
{
    public Game()
    {
    }

    public void run()
    {
        MenuController menuController = new MenuController();
        menuController.launch();
    }
}
