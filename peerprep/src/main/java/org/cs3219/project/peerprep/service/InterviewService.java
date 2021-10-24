package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.interview.*;


public interface InterviewService {
    InterviewDetailsResponse getInterviewDetails(InterviewDetailsRequest interviewRequest);

    SaveAnswerResponse saveUserAnswer(SaveAnswerRequest saveAnswerRequest);
}
