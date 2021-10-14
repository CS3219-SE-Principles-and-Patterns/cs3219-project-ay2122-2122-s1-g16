package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.InterviewRequest;
import org.cs3219.project.peerprep.model.dto.InterviewResponse;

public interface InterviewService {
    InterviewResponse getInterviewInfo(InterviewRequest interviewRequest);
}
