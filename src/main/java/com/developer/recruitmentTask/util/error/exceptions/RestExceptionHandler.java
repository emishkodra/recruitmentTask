package com.developer.recruitmentTask.util.error.exceptions;

import com.developer.recruitmentTask.util.error.BadRequestAlertException;
import com.developer.recruitmentTask.util.error.JsonError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({BadRequestAlertException.class})
    protected ResponseEntity<JsonError> handleBadRequest(BadRequestAlertException ex) {
        JsonError jsonError = new JsonError(ex.getErrorKey(), ex.getMessage());
        return new ResponseEntity<>(jsonError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ":" + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ":" + error.getDefaultMessage());
        }
        List<JsonError> jsonError = new ArrayList<>();
        for (String e : errors) {
            if (e.split(":").length > 0) {
                jsonError.add(new JsonError(e.split(":").length > 1 ? e.split(":")[1] : e,
                        e.split(":").length > 0 ? e.split(":")[0] : ex.getParameter().getParameterName()));
            }else{
                jsonError.add(new JsonError(e, ex.getParameter().getParameterName()));
            }
        }

        return new ResponseEntity<>(jsonError, status);
    }



}
