package antifraud.handler;

import antifraud.errors.CustomError;
import antifraud.exceptions.WrongAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(WrongAmountException.class)
    public ResponseEntity<HttpStatus> handleWrongAmount(WrongAmountException e){
        final CustomError customError = new CustomError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
