package com.ziheliu.security;

import com.ziheliu.utils.CookieUtils;
import java.time.Instant;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class SecurityManagerImpl implements SecurityManager {

  private static final String COOKIE_TOKEN_KEY = "oj-token";

  private final TokenManager tokenManager;

  @Autowired
  public SecurityManagerImpl(TokenManager tokenManager) {
    this.tokenManager = tokenManager;
  }


  @Override
  public void addToken(HttpServletResponse response, SecurityUser user, String token, Instant date) {
    Cookie cookie = new Cookie(COOKIE_TOKEN_KEY, token);
    cookie.setPath("/");
    cookie.setMaxAge(3600);
    response.addCookie(cookie);
    tokenManager.addToken(user, token, date);
  }

  @Override
  public void removeToken(HttpServletRequest request, HttpServletResponse response) {
    Cookie cookie = WebUtils.getCookie(request, COOKIE_TOKEN_KEY);
    if (cookie != null) {
      String token = cookie.getValue();
      tokenManager.removeToken(token);
      CookieUtils.deleteCookie(response, COOKIE_TOKEN_KEY);
    }
  }

  @Override
  public String getToken(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, COOKIE_TOKEN_KEY);
    if (cookie == null) {
      return null;
    }
    return cookie.getValue();
  }

  @Override
  public SecurityUser getUserByToken(String token) {
    return tokenManager.getUserByToken(token);
  }
}
