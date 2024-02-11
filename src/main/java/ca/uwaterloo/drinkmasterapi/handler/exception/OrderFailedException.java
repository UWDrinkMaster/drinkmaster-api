package ca.uwaterloo.drinkmasterapi.handler.exception;

public class OrderFailedException extends RuntimeException {
    public OrderFailedException(String message) {
        super(message);
    }
}
