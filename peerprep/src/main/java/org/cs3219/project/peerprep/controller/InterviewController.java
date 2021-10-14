package org.cs3219.project.peerprep.controller;

import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.InterviewRequest;
import org.cs3219.project.peerprep.model.dto.InterviewResponse;
import org.cs3219.project.peerprep.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/interview")
@Slf4j
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/question")
    public CommonResponse<InterviewResponse> getInterviewResponse(@RequestParam Long userId, @RequestParam Integer difficulty,
                                                                  @RequestParam Integer role) {
        InterviewRequest interviewRequest = InterviewRequest.builder()
                .userId(userId)
                .difficulty(difficulty)
                .role(role)
                .build();
        InterviewResponse interviewResponse = interviewService.getInterviewInfo(interviewRequest);
        return CommonResponse.success(interviewResponse);
    }
}
