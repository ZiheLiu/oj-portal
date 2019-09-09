package com.ziheliu.ojbackend.model.entity;

public class Problem {
  private Integer id;

  private String desc;

  public Problem() {}

  public Problem(String desc) {
    this.desc = desc;
  }

  public Problem(int id, String desc) {
    this.id = id;
    this.desc = desc;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
