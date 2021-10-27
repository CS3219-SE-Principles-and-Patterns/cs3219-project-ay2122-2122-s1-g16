package org.cs3219.project.peerprep.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cs3219.project.peerprep.mapper.UserQuestionHistoryMapper;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.cs3219.project.peerprep.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    @Autowired
    private UserQuestionHistoryMapper userQuestionHistoryMapper;

    @Value("${interview.dayDuration}")
    private int dayDuration;

    @Override
    public List<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId, boolean isInterview) {
        QueryWrapper<UserQuestionHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (isInterview) {
            // Get user's attempted questions within dayDuration before current time
            Timestamp startTime = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(dayDuration));
            wrapper.ge("created_at", startTime);
        } else {
            wrapper.orderByDesc("created_at");
        }
        return userQuestionHistoryMapper.selectList(wrapper);
    }

    @Override
    public Page<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId, Integer pageNum, Integer pageSize) {
        Page<UserQuestionHistory> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserQuestionHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("created_at");
        return userQuestionHistoryMapper.selectPage(page, wrapper);
    }

    @Override
    public UserQuestionHistory fetchAttemptedQuestionById(Long id) {
        return userQuestionHistoryMapper.selectById(id);
    }

    @Override
    public UserQuestionHistory saveUserAnswer(UserQuestionHistory userQuestionHistory) {
        userQuestionHistoryMapper.insert(userQuestionHistory);
        return userQuestionHistory;
    }
}
