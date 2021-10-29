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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private static final Map<Long, InterviewDetail> userInterviewMap = new ConcurrentHashMap<>();

    @Override
    public InterviewDetailsResponse getInterviewDetailsResponse(InterviewDetailsRequest interviewDetailsRequest) {
        Long userId = interviewDetailsRequest.getUserId();
        Long peerId = interviewDetailsRequest.getPeerId();
        Integer difficulty = interviewDetailsRequest.getDifficulty();

        InterviewDetail userSet;
        InterviewDetail peerSet;
        // Check if the pair interview details are computed
        synchronized (this) {
            if (!userInterviewMap.containsKey(userId) || !userInterviewMap.containsKey(peerId)) {
                userSet = getInterviewDetail(userId, difficulty);
                peerSet = getInterviewDetail(peerId, difficulty);
                userInterviewMap.put(userId, userSet);
                userInterviewMap.put(peerId, peerSet);
            } else {
                userSet = userInterviewMap.get(userId);
                peerSet = userInterviewMap.get(peerId);
                userInterviewMap.remove(userId);
                userInterviewMap.remove(peerId);
            }
        }

        return InterviewDetailsResponse.builder().userSet(userSet).peerSet(peerSet).build();
    }

    private InterviewDetail getInterviewDetail(Long userId, Integer difficulty) {
        // Retrieve user's unattempted questions
        List<InterviewQuestion> interviewQuestions = questionRepository.fetchQuestionsByDifficulty(difficulty);
        List<UserQuestionHistory> userQuestionHistories = historyRepository.fetchAttemptedQuestionsByUserId(userId, true);
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

        InterviewSolution interviewSolution = questionRepository.fetchSolutionByQuestionId(interviewQuestion.getId());
        String formatQuestionBody = StringUtils.convertSpecialCharFromJavaToHtml(interviewQuestion.getContent());
        String formatSolutionBody = StringUtils.convertSpecialCharFromJavaToHtml(interviewSolution.getContent());
        return InterviewDetail.builder()
                .questionId(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .question(formatQuestionBody)
                .difficulty(interviewQuestion.getDifficulty())
                .solution(formatSolutionBody)
                .build();
    }

    @Override
    public SaveAnswerResponse saveUserAnswer(SaveAnswerRequest saveAnswerRequest) {
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
