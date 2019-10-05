package com.ziheliu.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtils {
  public static void deleteCookie(HttpServletResponse response, String key) {
    Cookie cookie = new Cookie(key, "");
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
