package com.ziheliu.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ziheliu.model.dto.SubmissionDto;
import com.ziheliu.dao.mybatis.SubmissionMapper;
import com.ziheliu.model.entity.Submission;
import com.ziheliu.service.SubmissionService;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl implements SubmissionService {
  private final SubmissionMapper submissionMapper;
  private final RocketMQTemplate rocketMQTemplate;

  @Autowired
  public SubmissionServiceImpl(SubmissionMapper submissionMapper, RocketMQTemplate rocketMQTemplate) {
    this.submissionMapper = submissionMapper;
    this.rocketMQTemplate = rocketMQTemplate;
  }

  @Override
  public SubmissionDto createSubmission(SubmissionDto submissionDto) {
    Submission submission = new Submission(
      submissionDto.getUsername(),
      submissionDto.getProblemId(),
      new Timestamp(submissionDto.getCreateTimestamp()),
      submissionDto.getStatus(),
      submissionDto.getCode());
    int res = submissionMapper.insertSubmission(submission);
    if (res == 0) {
      return null;
    }
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

  @Override
  public boolean hasUnfinishedSubmissions(String username, int problemId) {
    return submissionMapper.hasUnfinishedSubmissions(username, problemId) > 0;
  }

  @Override
  public void requestCompile(SubmissionDto submissionDto) {
    rocketMQTemplate.convertAndSend("compile", submissionDto.decode());
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
