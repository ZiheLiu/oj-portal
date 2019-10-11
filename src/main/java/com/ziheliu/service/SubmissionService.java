package com.ziheliu.service;

import com.ziheliu.model.dto.SubmissionDto;
import java.util.List;

public interface SubmissionService {
  public SubmissionDto createSubmission(SubmissionDto submissionDto);

  public List<SubmissionDto> getSubmissionList();

  public SubmissionDto getSubmissionById(int problemId);

  public boolean hasUnfinishedSubmissions(String username, int problemId);

  public void requestCompile(SubmissionDto submissionDto);
}
