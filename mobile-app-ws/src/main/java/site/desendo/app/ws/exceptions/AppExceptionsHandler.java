package site.desendo.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import site.desendo.app.ws.mobileappws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(
        value = {Exception.class}
    )
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();

        if(errorMessageDescription.equals(null)) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

        /*
        {
            "timestamp": "2022-02-15T13:46:41.441+0000",
            "message": "java.lang.NullPointerException"
        }
        */ 
        return new ResponseEntity<>(
            errorMessage,
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        // return new ResponseEntity<>(
        //     ex,
        //     new HttpHeaders(),
        //     HttpStatus.INTERNAL_SERVER_ERROR
        // );
    }

    @ExceptionHandler(
        value = {
            NullPointerException.class, 
            ArrayIndexOutOfBoundsException.class
        }
    )
    public ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();

        if(errorMessageDescription.equals(null)) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

        /*
        {
            "timestamp": "2022-02-15T13:46:41.441+0000",
            "message": "java.lang.NullPointerException"
        }
        */ 
        return new ResponseEntity<>(
            errorMessage,
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
