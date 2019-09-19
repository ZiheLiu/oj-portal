package com.ziheliu.ojbackend.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ziheliu.ojbackend.dao.mybatis.SubmissionMapper;
import com.ziheliu.ojbackend.model.dto.SubmissionDto;
import com.ziheliu.ojbackend.model.entity.Submission;
import com.ziheliu.ojbackend.service.SubmissionService;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl implements SubmissionService {
  private final SubmissionMapper submissionMapper;

  @Autowired
  public SubmissionServiceImpl(SubmissionMapper submissionMapper) {
    this.submissionMapper = submissionMapper;
  }

  @Override
  public SubmissionDto createSubmission(SubmissionDto submissionDto) {
    Submission submission = new Submission(
      submissionDto.getUsername(),
      submissionDto.getProblemId(),
      new Timestamp(submissionDto.getCreateTimestamp()),
      submissionDto.getStatus(),
      submissionDto.getCode());
    submissionMapper.insertSubmission(submission);
    submissionDto.setId(submission.getId());
    return submissionDto;
  }

  @Override
  public List<SubmissionDto> getSubmissionList() {
    return submissionMapper.selectSubmissions()
      .stream()
      .map(this::submission2dto)
      .collect(Collectors.toList());
  }

  @Override
  public SubmissionDto getSubmissionById(int submissionId) {
    return submission2dto(submissionMapper.selectSubmissionById(submissionId));
  }

  private SubmissionDto submission2dto(Submission submission) {
    Gson gson = new Gson();

    List<SubmissionDto.SubmissionResult> resultList;
    if (submission.getResult() != null) {
      Type listType = new TypeToken<ArrayList<SubmissionDto.SubmissionResult>>(){}.getType();
      resultList = gson.fromJson(submission.getResult(), listType);
    } else {
      resultList = null;
    }

    return new SubmissionDto(
      submission.getId(),
      submission.getUsername(),
      submission.getProblemId(),
      submission.getCreateDate().getTime(),
      submission.getCode(),
      submission.getStatus(),
      submission.getScore(),
      resultList);
  }
}
