package com.ziheliu.ojbackend.controller;

import com.ziheliu.ojbackend.model.dto.ProblemDto;
import com.ziheliu.ojbackend.security.HasRole;
import com.ziheliu.ojbackend.service.ProblemService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problems")
@HasRole("BASIC")
public class ProblemController {
  private final ProblemService problemService;

  @Autowired
  public ProblemController(ProblemService problemService) {
    this.problemService = problemService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ProblemDto create(@Valid @RequestBody ProblemDto problemDto) {
    return problemService.createProblem(problemDto);
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<ProblemDto> getList() {
    return problemService.getProblemList();
  }

  @RequestMapping(path = "/{problemId}", method = RequestMethod.GET)
  public ProblemDto get(@PathVariable("problemId") int problemId) {
    return problemService.getProblemById(problemId);
  }
}
