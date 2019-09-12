package com.ziheliu.ojbackend.security;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenManager implements TokenManager {

  private static class UserWrapper {
    SecurityUser user;
    Instant validDate;

    public UserWrapper(SecurityUser user, Instant validDate) {
      this.user = user;
      this.validDate = validDate;
    }
  }

  private Map<String, UserWrapper> token2userWrapper = new ConcurrentHashMap<>();

  @Override
  public void addToken(SecurityUser user, String token, Instant validDate) {
    token2userWrapper.put(token, new UserWrapper(user, validDate));
  }

  @Override
  public SecurityUser getUserByToken(String token) {
    UserWrapper wrapper = token2userWrapper.get(token);
    if (wrapper == null) {
      return null;
    }
    if (wrapper.validDate.compareTo(Instant.now()) <= 0) {
      token2userWrapper.remove(token, wrapper);
      return null;
    }
    return wrapper.user;
  }

  @Override
  public void removeToken(String token) {
    token2userWrapper.remove(token);
  }
}
