package com.ziheliu.ojbackend.controller;

import com.ziheliu.ojbackend.model.dto.MessageDto;
import com.ziheliu.ojbackend.model.dto.RoleDto;
import com.ziheliu.ojbackend.model.dto.UserDto;
import com.ziheliu.ojbackend.service.UserService;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(path = "/login", method = RequestMethod.GET)
  public ResponseEntity<MessageDto> login() {
    return new ResponseEntity<>(new MessageDto("还没有登录"), HttpStatus.UNAUTHORIZED);
  }

  @RequestMapping(path = "/login/fail")
  public ResponseEntity<MessageDto> loginFail(HttpServletRequest request) {
    Exception exception = (Exception) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    MessageDto messageDto = new MessageDto();
    if (exception == null) {
      messageDto.setMessage("");
    } else if (exception instanceof UsernameNotFoundException) {
      messageDto.setMessage("未找到用户名");
    } else if (exception instanceof BadCredentialsException) {
      messageDto.setMessage("密码错误");
    } else {
      messageDto.setMessage(exception.getMessage());
    }
    return new ResponseEntity<>(messageDto, HttpStatus.UNAUTHORIZED);
  }

  @RequestMapping(path = "/login/success")
  public ResponseEntity<MessageDto> loginSuccess() {
    return new ResponseEntity<>(new MessageDto(""), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public UserDto register(@Valid @RequestBody UserDto userDto) {
    userDto.setRoleList(Collections.singletonList(new RoleDto("BASIC")));
    userService.createUser(userDto);
    return userDto;
  }
}
