package com.sid.gl.managedepartment.exceptions;

import com.sid.gl.managedepartment.response.ApiResponse;
import com.sid.gl.managedepartment.response.DepartmentMessage;
import com.sid.gl.managedepartment.tools.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
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
    public ResponseEntity<ApiResponse> exceptionHandler(Exception exception) {
        return ResponseEntity.badRequest().body(new ApiResponse(DepartmentMessage.WS_ERROR, exception.getMessage()));
    }

    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<ApiResponse> handleDepException(DepartmentException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(ex.getDepartmentMessage(), ex.getMessage()));
    }

    //pour les contraintes de validations
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ValidationError.builder().field(fieldError.getField()).message(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ApiResponse(DepartmentMessage.WS_CONSTRAINT_VIOLATION, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleViolationException(ConstraintViolationException ex) {
        List<ValidationError> errors = ex.getConstraintViolations()
                .stream()
                .map(fieldError -> ValidationError.builder().field(fieldError.getPropertyPath().toString()).message(fieldError.getMessage()).build())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ApiResponse(DepartmentMessage.WS_CONSTRAINT_VIOLATION, errors));

    }

}
