package org.cs3219.project.peerprep.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.cs3219.project.peerprep.common.utils.StringUtils;
import org.cs3219.project.peerprep.model.dto.interview.*;
import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.cs3219.project.peerprep.repository.HistoryRepository;
import org.cs3219.project.peerprep.repository.QuestionRepository;
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
    private HistoryRepository historyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public InterviewDetailsResponse getInterviewDetails(InterviewDetailsRequest interviewDetailsRequest) {
        log.debug("InterviewService.getInterviewDetails.request:{}", interviewDetailsRequest);
        // Retrieve user's unattempted questions
        List<InterviewQuestion> interviewQuestions = questionRepository.fetchQuestionsByDifficulty(interviewDetailsRequest.getDifficulty());
        List<UserQuestionHistory> userQuestionHistories = historyRepository.fetchAttemptedQuestionsByUserId(interviewDetailsRequest.getUserId());
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

        String formatQuestionBody = StringUtils.convertSpecialCharFromJavaToHtml(interviewQuestion.getContent());
        InterviewDetailsResponse interviewResponse = InterviewDetailsResponse.builder()
                .questionId(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .question(formatQuestionBody)
                .difficulty(interviewQuestion.getDifficulty())
                .build();

        // TODO: 14/10/21 Insert solutions into the DB
        // Retrieve and return solution if the user is interviewer
        Optional<InterviewSolution> interviewSolution = Optional.ofNullable(questionRepository.fetchSolutionByQuestionId(interviewQuestion.getId()));
        interviewSolution.ifPresentOrElse(solution -> interviewResponse.setSolution(solution.getContent()),
                () -> interviewResponse.setSolution("Unavailable"));

        return interviewResponse;
    }

    @Override
    public SaveAnswerResponse saveUserAnswer(SaveAnswerRequest saveAnswerRequest) {
        log.debug("InterviewService.saveUserAnswer.request:{}", saveAnswerRequest);
        UserQuestionHistory userQuestionHistory = UserQuestionHistory.builder()
                .userId(saveAnswerRequest.getUserId())
                .questionId(saveAnswerRequest.getQuestionId())
                .userAnswer(saveAnswerRequest.getAnswer())
                .build();
        UserQuestionHistory result = historyRepository.saveUserAnswer(userQuestionHistory);
        return SaveAnswerResponse.builder()
                .userId(result.getUserId())
                .questionId(result.getQuestionId())
                .answer(result.getUserAnswer())
                .build();
    }
}
