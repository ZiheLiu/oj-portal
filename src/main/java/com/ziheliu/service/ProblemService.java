package com.ziheliu.service;

import com.ziheliu.model.dto.ProblemDto;
import com.ziheliu.exception.TesterConfigException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProblemService {
  public ProblemDto createProblem(ProblemDto problemDto);

  public List<ProblemDto> getProblemList();

  public ProblemDto getProblemById(int problemId);

  public int validTesterZip(Map<String, byte[]> testerMap) throws TesterConfigException;

  public void writeProblem2Disk(Map<String, byte[]> files, ProblemDto problemDto) throws IOException;
}
