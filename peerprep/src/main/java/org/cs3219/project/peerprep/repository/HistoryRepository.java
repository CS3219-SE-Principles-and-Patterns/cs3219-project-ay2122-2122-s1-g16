package org.cs3219.project.peerprep.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;

import java.util.List;

public interface HistoryRepository {
    List<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId);

    Page<UserQuestionHistory> fetchAttemptedQuestionsByUserId(Long userId, Integer pageNum, Integer pageSize);

    UserQuestionHistory fetchAttemptedQuestionById(Long id);

    UserQuestionHistory saveUserAnswer(UserQuestionHistory userQuestionHistory);
}
