package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.InterviewDetailsRequest;
import org.cs3219.project.peerprep.model.dto.InterviewDetailsResponse;

public interface InterviewService {
    InterviewDetailsResponse getInterviewDetails(InterviewDetailsRequest interviewRequest);
}
