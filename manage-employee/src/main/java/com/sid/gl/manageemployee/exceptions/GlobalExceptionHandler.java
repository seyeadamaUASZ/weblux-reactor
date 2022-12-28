package com.sid.gl.manageemployee.exceptions;


import com.sid.gl.manageemployee.tools.ValidationError;
import com.sid.gl.manageemployee.tools.response.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //exception par défaut
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicResponse> exceptionHandler(Exception exception) {
        return ResponseEntity.badRequest().body(new BasicResponse(500, exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BasicResponse> handleDepException(ResourceNotFoundException ex) {
        return ResponseEntity.badRequest().body(new BasicResponse(ex.getMessage()));
    }

    //pour les contraintes de validations
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BasicResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new BasicResponse(500, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BasicResponse> handleViolationException(ConstraintViolationException ex) {
        List<ValidationError> errors = ex.getConstraintViolations()
                .stream()
                .map(fieldError -> ValidationError.builder().field(fieldError.getPropertyPath().toString()).message(fieldError.getMessage()).build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new BasicResponse(500, errors));

    }

}
