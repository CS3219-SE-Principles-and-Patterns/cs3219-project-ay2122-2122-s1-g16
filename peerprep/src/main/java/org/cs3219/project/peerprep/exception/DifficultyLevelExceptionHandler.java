package org.cs3219.project.peerprep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DifficultyLevelExceptionHandler {

    @ExceptionHandler(value = InvalidDifficultyLevelException.class)
    public ResponseEntity<Object> exception(InvalidDifficultyLevelException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
