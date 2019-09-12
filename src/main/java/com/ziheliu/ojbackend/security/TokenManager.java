package com.ziheliu.ojbackend.security;

import java.time.Instant;

public interface TokenManager {
  public void addToken(SecurityUser user, String token, Instant date);

  public SecurityUser getUserByToken(String token);

  public void removeToken(String token);
}
