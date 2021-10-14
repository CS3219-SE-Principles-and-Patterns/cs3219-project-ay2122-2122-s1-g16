package org.cs3219.project.peerprep.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("interview_questions")
public class InterviewQuestion implements Serializable {
    public static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String content;

    private Integer difficulty;
}
