package exceptions;

// Custom exception for handling entity not found
class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
    }
}
