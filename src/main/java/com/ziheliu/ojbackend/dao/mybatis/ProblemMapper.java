package com.ziheliu.ojbackend.dao.mybatis;

import com.ziheliu.ojbackend.model.entity.Problem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProblemMapper {

  public int insertProblem(Problem problem);

  public Problem selectProblemById(int problemId);

  public List<Problem> selectProblems();
}
