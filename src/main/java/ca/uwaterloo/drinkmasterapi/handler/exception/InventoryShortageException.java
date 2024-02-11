package ca.uwaterloo.drinkmasterapi.handler.exception;

public class InventoryShortageException extends RuntimeException {
    public InventoryShortageException(String message) {
        super(message);
    }
}
