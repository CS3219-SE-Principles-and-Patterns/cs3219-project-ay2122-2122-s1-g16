package org.cs3219.project.peerprep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cs3219.project.peerprep.model.entity.UserQuestionHistory;
import org.springframework.stereotype.Component;

@Component
public interface UserQuestionHistoryMapper extends BaseMapper<UserQuestionHistory> {
}
