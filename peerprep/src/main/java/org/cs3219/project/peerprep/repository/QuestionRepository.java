package org.cs3219.project.peerprep.repository;

import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;

import java.util.List;

public interface QuestionRepository {
    InterviewQuestion fetchQuestionById(Long id);

    List<InterviewQuestion> fetchQuestionsByDifficulty(Integer difficulty);

    InterviewSolution fetchSolutionByQuestionId(Long questionId);
}
