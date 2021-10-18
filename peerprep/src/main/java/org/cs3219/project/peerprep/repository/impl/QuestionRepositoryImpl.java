package org.cs3219.project.peerprep.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cs3219.project.peerprep.mapper.InterviewQuestionMapper;
import org.cs3219.project.peerprep.mapper.InterviewSolutionMapper;
import org.cs3219.project.peerprep.model.entity.InterviewQuestion;
import org.cs3219.project.peerprep.model.entity.InterviewSolution;
import org.cs3219.project.peerprep.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @Autowired
    private InterviewQuestionMapper interviewQuestionMapper;

    @Autowired
    private InterviewSolutionMapper interviewSolutionMapper;

    @Override
    public InterviewQuestion fetchQuestionById(Long id) {
        return interviewQuestionMapper.selectById(id);
    }

    @Override
    public List<InterviewQuestion> fetchQuestionsByDifficulty(Integer difficulty) {
        QueryWrapper<InterviewQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("difficulty", difficulty);
        return interviewQuestionMapper.selectList(wrapper);
    }

    @Override
    public InterviewSolution fetchSolutionByQuestionId(Long questionId) {
        QueryWrapper<InterviewSolution> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return interviewSolutionMapper.selectOne(wrapper);
    }
}
