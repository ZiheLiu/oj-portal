package com.ziheliu.security;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityManager {
  public void addToken(HttpServletResponse response, SecurityUser user, String token, Instant date);

  public void removeToken(HttpServletRequest request, HttpServletResponse response);

  public String getToken(HttpServletRequest request);

  public SecurityUser getUserByToken(String token);
}
