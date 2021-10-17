package org.cs3219.project.peerprep.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.cs3219.project.peerprep.mapper.InterviewQuestionMapper;
import org.cs3219.project.peerprep.mapper.InterviewSolutionMapper;
import org.cs3219.project.peerprep.mapper.UserQuestionHistoryMapper;
import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.cs3219.project.peerprep.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class InterviewRepositoryImpl implements InterviewRepository {

    @Autowired
    private InterviewQuestionMapper interviewQuestionMapper;

    @Autowired
    private UserQuestionHistoryMapper userQuestionHistoryMapper;

    @Autowired
    private InterviewSolutionMapper interviewSolutionMapper;

    @Value("${interview.dayDuration}")
    private int dayDuration;

    @Override
    public List<InterviewQuestion> fetchQuestionsByDifficulty(Integer difficulty) {
        QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("difficulty", difficulty);
        return interviewQuestionMapper.selectList(wrapper);
    }

    @Override
    public List<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId) {
        QueryWrapper<UserQuestionHistory> wrapper = new QueryWrapper<>();
        // Get user's attempted questions within dayDuration before current time
        Long startTime = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(dayDuration);
        wrapper.eq("user_id", userId);
        wrapper.ge("created_at", startTime);
        return userQuestionHistoryMapper.selectList(wrapper);
    }

    @Override
    public InterviewSolution fetchSolutionByQuestionId(Long questionId) {
        QueryWrapper<InterviewSolution> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return interviewSolutionMapper.selectOne(wrapper);
    }
}
