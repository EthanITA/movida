package movida.bassolidong.custom_exception;

public class ArrayOutOfSizeException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ArrayOutOfSizeException() {
    }

    // Constructor that accepts a message
    public ArrayOutOfSizeException(String message) {
        super(message);
    }
}