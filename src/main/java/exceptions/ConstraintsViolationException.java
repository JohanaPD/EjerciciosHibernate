package exceptions;
// Custom exception for handling constraints violations
public class ConstraintsViolationException extends RuntimeException {
    public ConstraintsViolationException(String message) {
        super(message);
    }

    public ConstraintsViolationException() {
    }
}
