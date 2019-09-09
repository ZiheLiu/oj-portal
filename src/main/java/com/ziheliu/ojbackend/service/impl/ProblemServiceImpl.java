package com.ziheliu.ojbackend.service.impl;

import com.ziheliu.ojbackend.dao.mybatis.ProblemMapper;
import com.ziheliu.ojbackend.model.dto.ProblemDto;
import com.ziheliu.ojbackend.model.entity.Problem;
import com.ziheliu.ojbackend.service.ProblemService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProblemServiceImpl implements ProblemService {

  private final ProblemMapper problemMapper;

  @Autowired
  public ProblemServiceImpl(ProblemMapper problemMapper) {
    this.problemMapper = problemMapper;
  }

  @Override
  public ProblemDto createProblem(ProblemDto problemDto) {
    Problem problem = new Problem(problemDto.getDesc());
    problemMapper.insertProblem(problem);
    problemDto.setId(problem.getId());

    return problemDto;
  }

  @Override
  public List<ProblemDto> getProblemList() {
    List<Problem> problemList = problemMapper.selectProblems();
    return problemList
      .stream()
      .map(problem -> new ProblemDto(problem.getId(), problem.getDesc()))
      .collect(Collectors.toList());
  }

  @Override
  public ProblemDto getProblemById(int problemId) {
    Problem problem = problemMapper.selectProblemById(problemId);

    if (problem == null) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "entity not found"
      );
    }

    return new ProblemDto(problem.getId(), problem.getDesc());
  }
}
