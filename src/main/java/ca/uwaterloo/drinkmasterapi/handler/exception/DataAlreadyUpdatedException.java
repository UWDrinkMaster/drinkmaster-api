package ca.uwaterloo.drinkmasterapi.handler.exception;

public class DataAlreadyUpdatedException extends RuntimeException {
    public DataAlreadyUpdatedException(String message) {
        super(message);
    }
}
