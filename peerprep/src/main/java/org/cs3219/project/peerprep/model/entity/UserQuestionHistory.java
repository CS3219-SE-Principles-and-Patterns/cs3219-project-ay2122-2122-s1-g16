package org.cs3219.project.peerprep.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("users_questions_history")
public class UserQuestionHistory implements Serializable {
    public static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long questionId;

    private String userAnswer;

    @TableField(fill = FieldFill.INSERT)
    private Long createdAt;

    private Long deletedAt;
}
