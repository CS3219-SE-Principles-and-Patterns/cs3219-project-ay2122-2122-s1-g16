package org.cs3219.project.peerprep.exception;

public class InvalidDifficultyLevelException extends RuntimeException {
    public InvalidDifficultyLevelException(String difficultyLevel) {
        super(String.format("Invalid difficulty level: %s", difficultyLevel));
    }
}