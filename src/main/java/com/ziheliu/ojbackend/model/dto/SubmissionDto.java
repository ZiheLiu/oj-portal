package com.ziheliu.ojbackend.model.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

public class SubmissionDto {
  public static class SubmissionResult {
    private int score;
    private String status;
    private int costTime;
    private int costMemory;

    public int getScore() {
      return score;
    }

    public void setScore(int score) {
      this.score = score;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public int getCostTime() {
      return costTime;
    }

    public void setCostTime(int costTime) {
      this.costTime = costTime;
    }

    public int getCostMemory() {
      return costMemory;
    }

    public void setCostMemory(int costMemory) {
      this.costMemory = costMemory;
    }
  }

  private Integer id;

  private String username;

  @NotNull
  private Integer problemId;

  @NotNull
  private Long createTimestamp;

  @NotNull
  private String code;

  private String status;

  private int score;

  private List<SubmissionResult> resultList;


  public SubmissionDto() {

  }

  public SubmissionDto(Integer id, String username, Integer problemId, long createTimestamp, String code, String status, int score, List<SubmissionResult> resultList) {
    this.id = id;
    this.username = username;
    this.problemId = problemId;
    this.createTimestamp = createTimestamp;
    this.code = code;
    this.status = status;
    this.score = score;
    this.resultList = resultList;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public long getCreateTimestamp() {
    return createTimestamp;
  }

  public void setCreateTimestamp(long createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public List<SubmissionResult> getResultList() {
    return resultList;
  }

  public void setResultList(List<SubmissionResult> resultList) {
    this.resultList = resultList;
  }
}
