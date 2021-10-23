package org.cs3219.project.peerprep.controller;

import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.exception.InvalidDifficultyLevelException;
import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.cs3219.project.peerprep.service.Pairing.PairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@ControllerAdvice
@RestController
@RequestMapping("/api/v1/match")
@Slf4j
public class PairingController {

    @Autowired
    private PairingService pairingService;

    private final String idProperty = "id";

    private final String difficultyProperty = "difficulty";

    private final int level = 3;

    private final String successMsg = "success";

    private final String errorMsg = "failure";

    @GetMapping("/queue")
    public ResponseEntity<Object> getPairingResponse(@RequestParam(name=idProperty) Long userId,
                                                     @RequestParam (name=difficultyProperty) int difficultyLevel) {
        final int difficulty;
        if (difficultyLevel >= 0 && difficultyLevel < level) {
            difficulty = difficultyLevel;
        } else {
            throw new InvalidDifficultyLevelException(String.valueOf(difficultyLevel));
        }
        final PairingRequest pairingRequest = PairingRequest.builder()
                .userId(userId)
                .difficulty(difficulty)
                .build();
        try {
            final PairingResponse pairingResponse = pairingService.getPairingResult(pairingRequest);
            final CommonResponse<PairingResponse> response = CommonResponse.success(successMsg, pairingResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InterruptedException ex) {
            final CommonResponse<PairingResponse> response = CommonResponse.fail(503, errorMsg);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/dequeue")
    public ResponseEntity<Object> getExitPairingResponse(@RequestParam(name=idProperty) Long userId,
                                                     @RequestParam (name=difficultyProperty) int difficultyLevel) {
        final int difficulty;
        if (difficultyLevel >= 0 && difficultyLevel < level) {
            difficulty = difficultyLevel;
        } else {
            throw new InvalidDifficultyLevelException(String.valueOf(difficultyLevel));
        }
        final CommonResponse<Object> response = CommonResponse.success(successMsg, new Object());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
