package org.cs3219.project.peerprep.controller;

import org.cs3219.project.peerprep.common.api.CommonPage;
import org.cs3219.project.peerprep.common.api.CommonResponse;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptDetail;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptedQuestion;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptedQuestionResponse;
import org.cs3219.project.peerprep.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/question")
    public ResponseEntity<CommonResponse<UserAttemptedQuestionResponse>> getUserAttemptedQuestions(@RequestParam Long userId,
                                                                                                   @RequestParam Integer pageNum,
                                                                                                   @RequestParam Integer pageSize) {
        CommonPage<UserAttemptedQuestion> userAttemptedQuestions = historyService.getUserAttemptedQuestions(userId, pageNum, pageSize);
        UserAttemptedQuestionResponse userAttemptedQuestionResponse = UserAttemptedQuestionResponse.builder()
                .userId(userId)
                .questions(userAttemptedQuestions)
                .build();
        return new ResponseEntity<>(CommonResponse.success(userAttemptedQuestionResponse), HttpStatus.OK);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<CommonResponse<UserAttemptDetail>> getUserAttemptDetail(@PathVariable("historyId") Long historyId,
                                                                                  @RequestParam Long userId) {
        UserAttemptDetail userAttemptDetail = historyService.getUserAttemptDetail(historyId, userId);
        return new ResponseEntity<>(CommonResponse.success(userAttemptDetail), HttpStatus.OK);
    }
}
