package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.exception.TesterConfigException;
import com.ziheliu.ojbackend.model.dto.ProblemDto;
import java.util.List;
import java.util.Map;

public interface ProblemService {
  public ProblemDto createProblem(ProblemDto problemDto);

  public List<ProblemDto> getProblemList();

  public ProblemDto getProblemById(int problemId);

  public int validTesterZip(Map<String, byte[]> testerMap) throws TesterConfigException;
}
