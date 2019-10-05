package com.ziheliu.model.entity;

import java.sql.Timestamp;

public class Submission {
  private Integer id;
  private String username;
  private Integer problemId;
  private Timestamp createDate;
  private String status;
  private String code;

  private int score;
  private String result;

  public Submission() {

  }

  public Submission(String username, Integer problemId, Timestamp createDate, String status, String code) {
    this.username = username;
    this.problemId = problemId;
    this.createDate = createDate;
    this.status = status;
    this.code = code;
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

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
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

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
