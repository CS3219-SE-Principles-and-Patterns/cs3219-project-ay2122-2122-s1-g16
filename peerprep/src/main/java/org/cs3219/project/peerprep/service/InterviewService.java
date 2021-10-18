package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsRequest;
import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsResponse;
import org.cs3219.project.peerprep.model.dto.interview.SaveAnswerRequest;
import org.cs3219.project.peerprep.model.dto.interview.SaveAnswerResponse;

public interface InterviewService {
    InterviewDetailsResponse getInterviewDetails(InterviewDetailsRequest interviewRequest);
    SaveAnswerResponse saveUserAnswer(SaveAnswerRequest saveAnswerRequest);
}
