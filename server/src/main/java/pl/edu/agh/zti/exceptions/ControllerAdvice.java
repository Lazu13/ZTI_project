package pl.edu.agh.zti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Exception handler for [[NotFoundException]]
     *
     * @param ex exception
     * @return exception message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Exception handler for [[BadCredentialsException]]
     *
     * @param ex exception
     * @return exception message
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public String handleBadCredentialsException(BadCredentialsException ex) {
        return ex.getMessage();
    }

    /**
     * Exception handler for [[IllegalUserGroupStatus]]
     *
     * @param ex exception
     * @return exception message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalUserGroupStatus.class)
    public String handleIllegalUserGroupStatus(IllegalUserGroupStatus ex) {
        return ex.getMessage();
    }


}
