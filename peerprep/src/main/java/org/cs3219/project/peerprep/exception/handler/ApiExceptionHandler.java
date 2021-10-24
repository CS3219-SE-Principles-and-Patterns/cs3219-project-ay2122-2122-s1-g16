package org.cs3219.project.peerprep.exception.handler;

import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.exception.InvalidDifficultyLevelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

// TODO: modify according to CommonResponse
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        CommonResponse<Object> resp = new CommonResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        CommonResponse<Object> resp = new CommonResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException e) {

        StringBuilder errorMsgBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errorMsgBuilder.append(errorMessage);
            errorMsgBuilder.append(";");
        });

        CommonResponse<Map<String, String>> resp = new CommonResponse<>(HttpStatus.BAD_REQUEST.value(), errorMsgBuilder.toString(), null);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        for (ConstraintViolation v: e.getConstraintViolations()) {
            errorMsgBuilder.append(v.getMessage());
            errorMsgBuilder.append(";");
        }
        CommonResponse<Object> resp = new CommonResponse<>(HttpStatus.BAD_REQUEST.value(), errorMsgBuilder.toString(), null);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDifficultyLevelException.class)
    public ResponseEntity<Object> handleInvalidDifficultyLevelException(InvalidDifficultyLevelException e) {
        CommonResponse<Object> resp = new CommonResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> handleMessagingException(MessagingException e) {
        String errorMsg = "fail to send email: " + e.getMessage();
        CommonResponse<Object> resp = new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg, null);
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
