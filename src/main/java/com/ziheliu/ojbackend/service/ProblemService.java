package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.model.dto.ProblemDto;
import java.util.List;

public interface ProblemService {
  public ProblemDto createProblem(ProblemDto problemDto);

  public List<ProblemDto> getProblemList();

  public ProblemDto getProblemById(int problemId);
}
