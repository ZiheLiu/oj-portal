package com.ziheliu.security;

import java.util.List;

public class SecurityUser {
  private String username;
  private List<String> roles;

  public SecurityUser(String username, List<String> roles) {
    this.username = username;
    this.roles = roles;
  }

  public boolean containsRole(String role) {
    for (String candidate : roles) {
      if (role.equals(candidate)) {
        return true;
      }
    }
    return false;
  }

  public String getUsername() {
    return username;
  }
}
