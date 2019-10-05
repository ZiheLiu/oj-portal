package com.ziheliu.service.impl;

import com.google.gson.Gson;
import com.ziheliu.dao.mybatis.ProblemMapper;
import com.ziheliu.exception.TesterConfigException;
import com.ziheliu.model.dto.ProblemDto;
import com.ziheliu.model.dto.TesterConfig;
import com.ziheliu.model.entity.Problem;
import com.ziheliu.service.ProblemService;
import com.ziheliu.utils.ZipUtils;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProblemServiceImpl implements ProblemService {

  private final ProblemMapper problemMapper;
  private final String RUNTIME_ROOT;
  private final String PROBLEM_PATH;

  @Autowired
  public ProblemServiceImpl(ProblemMapper problemMapper, @Value("${runtime-path}") String runtime_root) {
    this.problemMapper = problemMapper;
    RUNTIME_ROOT = runtime_root;
    PROBLEM_PATH = RUNTIME_ROOT + "/problem";
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

  @Override
  public void writeProblem2Disk(Map<String, byte[]> files, ProblemDto problemDto) throws IOException {
    ZipUtils.write(files, PROBLEM_PATH + "/" + problemDto.getId());
  }
}
