package cz.muni.fi.pb138.project.Exceptions;

/**
 * Created by xtomasch on 6/1/16.
 */
public class DbException extends Exception{
    public DbException() {
        super();
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    protected DbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
