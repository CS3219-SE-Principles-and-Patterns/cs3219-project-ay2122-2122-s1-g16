package org.cs3219.project.peerprep.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("interview_solutions")
public class InterviewSolution implements Serializable {
    public static final long serialVersionUID = 1L;

    private Long id;

    private Long questionId;

    private String content;
}
