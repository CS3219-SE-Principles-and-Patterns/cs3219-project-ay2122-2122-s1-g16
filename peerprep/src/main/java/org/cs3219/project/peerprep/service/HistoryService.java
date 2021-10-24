package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.common.api.CommonPage;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptDetail;
import org.cs3219.project.peerprep.model.dto.interview.UserAttemptedQuestion;

public interface HistoryService {
    CommonPage<UserAttemptedQuestion> getUserAttemptedQuestions(Long userId, Integer pageNum, Integer pageSize);

    UserAttemptDetail getUserAttemptDetail(Long historyId, Long userId);
}
