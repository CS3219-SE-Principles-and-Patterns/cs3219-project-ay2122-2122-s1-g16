package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.interview.*;

import java.util.List;

public interface InterviewService {
    InterviewDetailsResponse getInterviewDetails(InterviewDetailsRequest interviewRequest);
    SaveAnswerResponse saveUserAnswer(SaveAnswerRequest saveAnswerRequest);
    List<UserAttemptedQuestion> getUserAttemptedQuestions(Long userId);
}
