package com.ziheliu.ojbackend.service.impl;

import com.google.gson.Gson;
import com.ziheliu.ojbackend.dao.mybatis.ProblemMapper;
import com.ziheliu.ojbackend.exception.TesterConfigException;
import com.ziheliu.ojbackend.model.dto.ProblemDto;
import com.ziheliu.ojbackend.model.dto.TesterConfig;
import com.ziheliu.ojbackend.model.entity.Problem;
import com.ziheliu.ojbackend.service.ProblemService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
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
    Problem problem = new Problem(
      problemDto.getTitle(),
      problemDto.getDescription(),
      problemDto.getLanguage(),
      problemDto.getMemory(),
      problemDto.getTimeout(),
      problemDto.getScore(),
      problemDto.isEnable(),
      new Timestamp(problemDto.getCreateTimestamp()),
      new Timestamp(problemDto.getUpdateTimestamp()));
    problemMapper.insertProblem(problem);
    problemDto.setId(problem.getId());

    return problemDto;
  }

  @Override
  public List<ProblemDto> getProblemList() {
    List<Problem> problemList = problemMapper.selectProblems();
    return problemList
      .stream()
      .map(problem -> new ProblemDto(
        problem.getId(),
        problem.getTitle(),
        problem.getDescription(),
        problem.getLanguage(),
        problem.getMemory(),
        problem.getTimeout(),
        problem.getScore(),
        problem.isEnable(),
        problem.getCreateDate().getTime(),
        problem.getUpdateDate().getTime()))
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

    return new ProblemDto(
      problem.getId(),
      problem.getTitle(),
      problem.getDescription(),
      problem.getLanguage(),
      problem.getMemory(),
      problem.getTimeout(),
      problem.getScore(),
      problem.isEnable(),
      problem.getCreateDate().getTime(),
      problem.getUpdateDate().getTime());
  }

  @Override
  public int validTesterZip(Map<String, byte[]> testerMap) throws TesterConfigException {
    byte[] bytes = testerMap.get("config.json");
    if (bytes == null) {
      throw new TesterConfigException("config.json not exists in zip");
    }
    Gson gson = new Gson();
    TesterConfig testerConfig = gson.fromJson(new String(bytes), TesterConfig.class);
    List<TesterConfig.TestCase> testCases = testerConfig.getTestCases();
    int score = 0;
    for (TesterConfig.TestCase testCase : testCases) {
      if (!testerMap.containsKey(testCase.getInputFile())) {
        throw new TesterConfigException(testCase.getInputFile() + " not exists in zip");
      } else if (!testerMap.containsKey(testCase.getOutputFile())) {
        throw new TesterConfigException(testCase.getOutputFile() + " not exists in zip");
      }
      score += testCase.getScore();
    }
    return score;
  }
}
