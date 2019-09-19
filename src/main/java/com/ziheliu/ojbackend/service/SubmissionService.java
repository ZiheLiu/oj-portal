package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.model.dto.SubmissionDto;
import java.util.List;

public interface SubmissionService {
  public SubmissionDto createSubmission(SubmissionDto submissionDto);

  public List<SubmissionDto> getSubmissionList();

  public SubmissionDto getSubmissionById(int problemId);
}
