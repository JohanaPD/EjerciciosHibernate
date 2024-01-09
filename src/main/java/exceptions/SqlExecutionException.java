package exceptions;

public class SqlExecutionException extends RuntimeException {
    public SqlExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlExecutionException() {
    }
}