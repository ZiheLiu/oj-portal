package com.ziheliu.ojbackend.model.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

public class UserDto {
  private Integer id;
  @NotNull
  private String username;
  @NotNull
  private String password;

  private String salt;

  @NotNull
  private List<String> roleList;

  public UserDto() {

  }

  public UserDto(Integer id, String username, String password, String salt, List<String> roleList) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.salt = salt;
    this.roleList = roleList;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<String> roleList) {
    this.roleList = roleList;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}
