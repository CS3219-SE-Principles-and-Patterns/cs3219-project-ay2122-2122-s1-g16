package org.cs3219.project.peerprep.repository;

import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;

import java.util.List;

public interface InterviewRepository {
    List<InterviewQuestion> fetchQuestionsByDifficulty(Integer difficulty);
    List<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId);
    InterviewSolution fetchSolutionByQuestionId(Long questionId);
    UserQuestionHistory saveUserAnswer(UserQuestionHistory userQuestionHistory);
}
