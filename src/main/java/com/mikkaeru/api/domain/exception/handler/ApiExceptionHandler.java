package com.mikkaeru.api.domain.exception.handler;

import com.mikkaeru.api.domain.exception.BusinessException;
import com.mikkaeru.api.domain.model.exception.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        return getHandleException(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return getHandleException(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<?> handleBusinessException(BusinessException exception, WebRequest request) {
        return getHandleException(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<?> handleExceptionDataIntegrity(Exception exception) {
        String message;

        if (exception instanceof DataIntegrityViolationException) {
            message = ((DataIntegrityViolationException) exception).getMessage();
        } else {
            message = exception.getMessage();
        }

        Problem problem = new Problem();

        problem.setTitle(message);
        problem.setDateTime(OffsetDateTime.now());
        problem.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var fields = new ArrayList<Problem.Field>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String msg = messageSource.getMessage(error, Locale.ENGLISH);

            fields.add(new Problem.Field(name, msg));
        }

        Problem problem = new Problem();

        problem.setFields(fields);
        problem.setStatus(status.value());
        problem.setTitle("Campos inválidos!");
        problem.setDateTime(OffsetDateTime.now());

        return super.handleExceptionInternal(exception, problem, headers, status, request);
    }

    private ResponseEntity<?> getHandleException(RuntimeException exception, HttpStatus status, WebRequest request) {
        Problem problem = new Problem();

        problem.setStatus(status.value());
        problem.setTitle(exception.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }
}
