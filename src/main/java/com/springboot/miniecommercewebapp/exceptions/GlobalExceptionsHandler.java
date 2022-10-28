package com.springboot.miniecommercewebapp.exceptions;

import com.springboot.miniecommercewebapp.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse(404, exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ItemExistException.class})
    protected ResponseEntity<ErrorResponse> handleItemExistExceptionException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse(302, exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.FOUND);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                           WebRequest request) {
        ErrorResponse error = new ErrorResponse(400, exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<ErrorResponse> handleSqlException(SQLException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(409, ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse error = new ErrorResponse(400, "Validation Error", errors);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

}