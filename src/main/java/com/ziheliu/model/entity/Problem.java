package com.ziheliu.model.entity;

import java.sql.Timestamp;

public class Problem {
  private Integer id;
  private String title;
  private String description;
  private String language;
  private int memory;
  private int timeout;
  private int score;
  private boolean enable;
  private boolean deleted;
  private Timestamp createDate;
  private Timestamp updateDate;


  public Problem() {}

  public Problem(String title, String description, String language, int memory, int timeout, int score, boolean enable, Timestamp createDate, Timestamp updateDate) {
    this.title = title;
    this.description = description;
    this.language = language;
    this.memory = memory;
    this.timeout = timeout;
    this.score = score;
    this.enable = enable;
    this.createDate = createDate;
    this.updateDate = updateDate;
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

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public Timestamp getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Timestamp updateDate) {
    this.updateDate = updateDate;
  }
}
