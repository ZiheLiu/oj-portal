package com.ziheliu.ojbackend.controller;

import com.ziheliu.ojbackend.model.dto.MessageDto;
import com.ziheliu.ojbackend.model.dto.ProblemDto;
import com.ziheliu.ojbackend.model.dto.SubmissionDto;
import com.ziheliu.ojbackend.security.HasRole;
import com.ziheliu.ojbackend.security.SecurityInterceptor;
import com.ziheliu.ojbackend.security.SecurityUser;
import com.ziheliu.ojbackend.service.ProblemService;
import com.ziheliu.ojbackend.service.SubmissionService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submissions")
@HasRole("BASIC")
public class SubmissionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionController.class);

  private final ProblemService problemService;
  private final SubmissionService submissionService;

  @Autowired
  public SubmissionController(ProblemService problemService, SubmissionService submissionService) {
    this.problemService = problemService;
    this.submissionService = submissionService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> create(@Valid @RequestBody SubmissionDto submissionDto, HttpServletRequest request) {
    SecurityUser user = (SecurityUser) request.getAttribute(SecurityInterceptor.SECURITY_USER);
    ProblemDto problemDto = problemService.getProblemById(submissionDto.getProblemId());
    if (problemDto == null) {
      return new ResponseEntity<>(new MessageDto("题目#" + submissionDto.getProblemId() + "不存在"), HttpStatus.NOT_FOUND);
    }
    if (!user.containsRole("ADMIN") && !problemDto.isEnable()) {
      return new ResponseEntity<>(new MessageDto("题目不允许提交"), HttpStatus.FORBIDDEN);
    }

    submissionDto.setUsername(user.getUsername());
    submissionDto.setStatus("PENDING");
    submissionDto = submissionService.createSubmission(submissionDto);
    if (submissionDto == null) {
      return new ResponseEntity<>(new MessageDto("现有的提交还没有完成"), HttpStatus.FORBIDDEN);
    }

    // TODO: send compile task to rocket mq

    return ResponseEntity.ok(submissionDto);
  }

  @HasRole("ADMIN")
  @RequestMapping(method = RequestMethod.GET)
  public List<SubmissionDto> getList() {
    return submissionService.getSubmissionList();
  }
}
