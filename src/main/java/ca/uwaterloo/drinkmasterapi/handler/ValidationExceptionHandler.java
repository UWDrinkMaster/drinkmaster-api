package ca.uwaterloo.drinkmasterapi.handler;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.info(ex.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.info(ex.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(DataAlreadyUpdatedException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataAlreadyUpdatedException(DataAlreadyUpdatedException ex) {
        log.info(ex.toString());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(OrderFailedException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderFailedException(OrderFailedException ex) {
        log.info(ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(InventoryShortageException.class)
    public ResponseEntity<ErrorResponseDTO> handleInventoryShortageException(InventoryShortageException ex) {
        log.info(ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }
}