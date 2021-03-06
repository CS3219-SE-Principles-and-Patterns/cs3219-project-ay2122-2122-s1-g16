package org.cs3219.project.peerprep.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.common.api.CommonPage;
import org.cs3219.project.peerprep.common.utils.StringUtils;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptDetail;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptedQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.cs3219.project.peerprep.repository.HistoryRepository;
import org.cs3219.project.peerprep.repository.QuestionRepository;
import org.cs3219.project.peerprep.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public CommonPage<UserAttemptedQuestion> getUserAttemptedQuestions(Long userId, Integer pageNum, Integer pageSize) {
        List<UserAttemptedQuestion> userAttemptedQuestions = new ArrayList<>();
        Page<UserQuestionHistory> userQuestionHistoryPage = historyRepository.fetchAttemptedQuestionsByUserId(userId, pageNum, pageSize);
        for (UserQuestionHistory userQuestionHistory : userQuestionHistoryPage.getRecords()) {
            InterviewQuestion currentQuestion = questionRepository.fetchQuestionById(userQuestionHistory.getQuestionId());
            UserAttemptedQuestion userAttemptedQuestion = UserAttemptedQuestion.builder()
                    .historyId(userQuestionHistory.getId())
                    .questionId(userQuestionHistory.getQuestionId())
                    .title(currentQuestion.getTitle())
                    .difficulty(currentQuestion.getDifficulty())
                    .attemptedAt(userQuestionHistory.getCreatedAt())
                    .build();
            userAttemptedQuestions.add(userAttemptedQuestion);
        }

        Page<UserAttemptedQuestion> userAttemptedQuestionPage = new Page<>();
        userAttemptedQuestionPage.setCurrent(userQuestionHistoryPage.getCurrent());
        userAttemptedQuestionPage.setSize(userQuestionHistoryPage.getSize());
        userAttemptedQuestionPage.setRecords(userAttemptedQuestions);

        return CommonPage.getCommonPage(userAttemptedQuestionPage);
    }

    @Override
    public UserAttemptDetail getUserAttemptDetail(Long historyId, Long userId) {
        UserQuestionHistory userQuestionHistory = historyRepository.fetchAttemptedQuestionById(historyId);
        if (!userId.equals(userQuestionHistory.getUserId())) {
            return null;
        }

        Long questionId = userQuestionHistory.getQuestionId();
        InterviewQuestion interviewQuestion = questionRepository.fetchQuestionById(questionId);
        InterviewSolution interviewSolution = questionRepository.fetchSolutionByQuestionId(questionId);
        return UserAttemptDetail.builder()
                .userId(userId)
                .questionId(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .question(StringUtils.convertSpecialCharFromJavaToHtml(interviewQuestion.getContent()))
                .difficulty(interviewQuestion.getDifficulty())
                .solution(StringUtils.convertSpecialCharFromJavaToHtml(interviewSolution.getContent()))
                .attemptedAnswer(userQuestionHistory.getUserAnswer())
                .attemptAt(userQuestionHistory.getCreatedAt())
                .build();
    }

    @Override
    public List<UserAttemptDetail> getUserAttemptDetailList(Long userId) {
        List<UserQuestionHistory> userQuestionHistories = historyRepository.fetchAttemptedQuestionsByUserId(userId, false);
        List<UserAttemptDetail> userAttemptDetails = new ArrayList<>();
        for (UserQuestionHistory userQuestionHistory : userQuestionHistories) {
            Long questionId = userQuestionHistory.getQuestionId();
            InterviewQuestion interviewQuestion = questionRepository.fetchQuestionById(questionId);
            InterviewSolution interviewSolution = questionRepository.fetchSolutionByQuestionId(questionId);
            UserAttemptDetail userAttemptDetail = UserAttemptDetail.builder()
                    .userId(userId)
                    .questionId(questionId)
                    .historyId(userQuestionHistory.getId())
                    .title(interviewQuestion.getTitle())
                    .question(StringUtils.convertSpecialCharFromJavaToHtml(interviewQuestion.getContent()))
                    .difficulty(interviewQuestion.getDifficulty())
                    .solution(StringUtils.convertSpecialCharFromJavaToHtml(interviewSolution.getContent()))
                    .attemptedAnswer(userQuestionHistory.getUserAnswer())
                    .attemptAt(userQuestionHistory.getCreatedAt())
                    .build();
            userAttemptDetails.add(userAttemptDetail);
        }
        return userAttemptDetails;
    }
}
