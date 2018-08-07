package com.siteminder.challenge.emailchallenge.exception;

import com.siteminder.challenge.emailchallenge.model.ExceptionResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponseModel emailException =
                new ExceptionResponseModel(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(emailException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        String details= "Validation Error";
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                details = "Invalid field: '" + fieldError.getField() +
                        "' - " + fieldError.getDefaultMessage();
            }
        }

        ExceptionResponseModel exceptionResponse =
                new ExceptionResponseModel(new Date(), "Validation Failed", details);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}