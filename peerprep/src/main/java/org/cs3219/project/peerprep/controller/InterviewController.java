package org.cs3219.project.peerprep.controller;

import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.interview.*;
import org.cs3219.project.peerprep.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interview")
@Slf4j
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/question")
    public ResponseEntity<CommonResponse<InterviewDetailsResponse>> getInterviewDetails(@RequestParam("id") Long userId,
                                                                                        @RequestParam("peer_id") Long peerId,
                                                                                        @RequestParam Integer difficulty) {
        InterviewDetailsRequest interviewRequest = InterviewDetailsRequest.builder()
                .userId(userId)
                .peerId(peerId)
                .difficulty(difficulty)
                .build();
        InterviewDetailsResponse interviewResponse = interviewService.getInterviewDetailsResponse(interviewRequest);
        return new ResponseEntity<>(CommonResponse.success(interviewResponse), HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<CommonResponse<SaveAnswerResponse>> saveUserAnswer(@RequestBody SaveAnswerRequest saveAnswerRequest) {
        SaveAnswerResponse saveAnswerResponse = interviewService.saveUserAnswer(saveAnswerRequest);
        return new ResponseEntity<>(CommonResponse.create(saveAnswerResponse), HttpStatus.CREATED);
    }
}
