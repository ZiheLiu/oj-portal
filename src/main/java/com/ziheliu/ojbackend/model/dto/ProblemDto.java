package com.ziheliu.ojbackend.model.dto;

import javax.validation.constraints.NotNull;

public class ProblemDto {
  private Integer id;

  @NotNull(message = "title is required")
  private String title;

  @NotNull(message = "description is required")
  private String description;

  @NotNull(message = "language is required")
  private String language;

  @NotNull(message = "memory is required")
  private int memory;

  @NotNull(message = "timeout is required")
  private int timeout;

  private int score;

  @NotNull(message = "enable is required")
  private boolean enable;

  public ProblemDto() {}

  public ProblemDto(Integer id, String title, String description, String language, int memory, int timeout, int score, boolean enable) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.language = language;
    this.memory = memory;
    this.timeout = timeout;
    this.score = score;
    this.enable = enable;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public int getMemory() {
    return memory;
  }

  public void setMemory(int memory) {
    this.memory = memory;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }
}
