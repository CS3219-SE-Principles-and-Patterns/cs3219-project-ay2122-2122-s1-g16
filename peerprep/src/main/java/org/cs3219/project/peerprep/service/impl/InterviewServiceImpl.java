package org.cs3219.project.peerprep.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.cs3219.project.peerprep.model.dto.InterviewRequest;
import org.cs3219.project.peerprep.model.dto.InterviewResponse;
import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.cs3219.project.peerprep.repository.InterviewRepository;
import org.cs3219.project.peerprep.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Override
    public InterviewResponse getInterviewInfo(InterviewRequest interviewRequest) {
        log.info("InterviewService.getInterviewInfo.query:{}", interviewRequest);
        // Retrieve user's unattempted questions
        List<InterviewQuestion> interviewQuestions = interviewRepository.fetchQuestionsByDifficulty(interviewRequest.getDifficulty());
        List<UserQuestionHistory> userQuestionHistories = interviewRepository.fetchAttemptedQuestionsByUserId(interviewRequest.getUserId());
        List<Long> attemptedQuestionIds = userQuestionHistories.stream().map(UserQuestionHistory::getQuestionId).collect(Collectors.toList());
        List<InterviewQuestion> unattemptedQuestions = interviewQuestions.stream()
                .filter(interviewQuestion -> !attemptedQuestionIds.contains(interviewQuestion.getId()))
                .collect(Collectors.toList());

        // Randomly choose from the question set
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        InterviewQuestion interviewQuestion;
        int randomNumber;
        if (unattemptedQuestions.isEmpty()) {
            randomNumber = randomDataGenerator.nextInt(0, interviewQuestions.size() - 1);
            interviewQuestion = interviewQuestions.get(randomNumber);
        } else {
            randomNumber = randomDataGenerator.nextInt(0, unattemptedQuestions.size() - 1);
            interviewQuestion = unattemptedQuestions.get(randomNumber);
        }

        InterviewResponse interviewResponse = InterviewResponse.builder()
                .questionId(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .question(interviewQuestion.getContent())
                .difficulty(interviewQuestion.getDifficulty())
                .role(interviewRequest.getRole())
                .build();
        // TODO: 14/10/21 Insert solutions into the DB
        // Retrieve and return solution if the user is interviewer
        if (interviewRequest.getRole().equals(1)) {
            Optional<InterviewSolution> interviewSolution = Optional.ofNullable(interviewRepository.fetchSolutionByQuestionId(interviewQuestion.getId()));
            interviewSolution.ifPresentOrElse(solution -> interviewResponse.setSolution(solution.getContent()),
                    () -> interviewResponse.setSolution("Unavailable"));
        }
        return interviewResponse;
    }
}
