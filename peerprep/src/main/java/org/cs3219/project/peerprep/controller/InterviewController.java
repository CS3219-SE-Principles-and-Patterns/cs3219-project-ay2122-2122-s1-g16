package org.cs3219.project.peerprep.controller;

import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsRequest;
import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsResponse;
import org.cs3219.project.peerprep.model.dto.interview.SaveAnswerRequest;
import org.cs3219.project.peerprep.model.dto.interview.SaveAnswerResponse;
import org.cs3219.project.peerprep.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interview")
@Slf4j
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/question")
    public CommonResponse<InterviewDetailsResponse> getInterviewDetails(@RequestParam Long userId, @RequestParam Integer difficulty) {
        InterviewDetailsRequest interviewRequest = InterviewDetailsRequest.builder()
                .userId(userId)
                .difficulty(difficulty)
                .build();
        InterviewDetailsResponse interviewResponse = interviewService.getInterviewDetails(interviewRequest);
        return CommonResponse.success(interviewResponse);
    }

    @PostMapping("/answer")
    public CommonResponse<SaveAnswerResponse> saveUserAnswer(@RequestBody SaveAnswerRequest saveAnswerRequest) {
        SaveAnswerResponse saveAnswerResponse = interviewService.saveUserAnswer(saveAnswerRequest);
        return CommonResponse.create(saveAnswerResponse);
    }
}
