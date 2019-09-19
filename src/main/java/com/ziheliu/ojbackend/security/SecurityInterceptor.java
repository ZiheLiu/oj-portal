package com.ziheliu.ojbackend.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class SecurityInterceptor implements HandlerInterceptor {

  public static final String SECURITY_USER = "SECURITY_USER";

  @Autowired
  private SecurityManager securityManager;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 没有注解HasRole
    String role = getHandlerRole(handler);
    if (role == null) {
      return true;
    }

    // 没有cookie
    String token = securityManager.getToken(request);
    if (token == null) {
      setResponse(response, "请先登录");
      return false;
    }

    // cookie不合法
    SecurityUser user = securityManager.getUserByToken(token);
    if (user == null) {
      securityManager.removeToken(request, response);
      setResponse(response, "请先登录");
      return false;
    }

    // 没有要求的role
    if (!user.containsRole(role)) {
      setResponse(response, "需要权限#" + role);
      return false;
    }

    request.setAttribute(SECURITY_USER, user);

    return true;
  }

  private String getHandlerRole(Object handler) {
    if (!(handler instanceof HandlerMethod)) {
      return null;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    HasRole role = handlerMethod.getMethod().getAnnotation(HasRole.class);
    if (role != null) {
      return role.value();
    }


    role = handlerMethod.getBean().getClass().getAnnotation(HasRole.class);
    if (role != null) {
      return role.value();
    }

    return null;
  }

  private static void setResponse(HttpServletResponse response, String message) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print("{\"message\":\"" + message + "\"}");
    response.getWriter().flush();
  }
}
