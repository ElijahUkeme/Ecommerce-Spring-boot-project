package com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.error;

import com.elijah.ecommerce.project.Ecommerce.Spring.boot.project.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus
@ControllerAdvice
public class RestResponseExceptionEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> dataNotFoundException(DataNotFoundException dataNotFoundException, WebRequest webRequest){

        ErrorMessage message = new ErrorMessage(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);

    }
}
