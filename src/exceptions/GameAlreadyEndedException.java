package exceptions;

/**
 * Created by Kamil on 2016-05-20.
 */
public class GameAlreadyEndedException extends RuntimeException
{
    public GameAlreadyEndedException()
    {
        super();
    }

    public GameAlreadyEndedException(String message)
    {
        super(message);
    }

    public GameAlreadyEndedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GameAlreadyEndedException(Throwable cause)
    {
        super(cause);
    }
}
