package movida.bassolidong.custom_exception;

public class ArrayOutOfSizeException extends Exception {
    public ArrayOutOfSizeException() {
    }

    // Constructor that accepts a message
    public ArrayOutOfSizeException(String message) {
        super(message);
    }
}