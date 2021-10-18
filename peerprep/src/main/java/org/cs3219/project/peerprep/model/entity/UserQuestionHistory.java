package org.cs3219.project.peerprep.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@TableName("users_questions_history")
public class UserQuestionHistory implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long questionId;

    private String userAnswer;

//    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;

    private Timestamp deletedAt;
}
