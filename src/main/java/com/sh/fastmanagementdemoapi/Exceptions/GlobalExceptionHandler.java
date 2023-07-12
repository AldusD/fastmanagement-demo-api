package com.sh.fastmanagementdemoapi.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>("Recurso não encontrado", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBodyException.class)
    public ResponseEntity<String> handleInvalidBodyException(InvalidBodyException e) {
        return new ResponseEntity<>("Recurso não encontrado", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        return new ResponseEntity<>("Entidade já existente", HttpStatus.CONFLICT);
    }
}

