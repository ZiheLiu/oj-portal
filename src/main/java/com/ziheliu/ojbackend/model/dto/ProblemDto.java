package com.ziheliu.ojbackend.model.dto;

import javax.validation.constraints.NotNull;

public class ProblemDto {
  private Integer id;

  @NotNull(message = "desc is required")
  private String desc;

  public ProblemDto() {}

  public ProblemDto(int id, String desc) {
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
