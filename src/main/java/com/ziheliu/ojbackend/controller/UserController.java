package com.ziheliu.ojbackend.controller;

import com.ziheliu.ojbackend.model.dto.LoginUserDto;
import com.ziheliu.ojbackend.model.dto.MessageDto;
import com.ziheliu.ojbackend.model.dto.UserDto;
import com.ziheliu.ojbackend.security.HasRole;
import com.ziheliu.ojbackend.security.SecurityManager;
import com.ziheliu.ojbackend.security.SecurityUser;
import com.ziheliu.ojbackend.service.UserService;
import com.ziheliu.ojbackend.utils.CodecUtils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private static final int TOKEN_LENGTH = 32;

  private final UserService userService;
  private final SecurityManager securityManager;

  @Autowired
  public UserController(UserService userService, SecurityManager securityManager) {
    this.userService = userService;
    this.securityManager = securityManager;
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ResponseEntity<MessageDto> login(@Valid @RequestBody LoginUserDto userDto, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    UserDto user = userService.getUserByUsername(userDto.getUsername());
    if (user == null) {
      return new ResponseEntity<>(new MessageDto("用户名不存在"), HttpStatus.UNAUTHORIZED);
    }

    boolean valid = userService.authenticate(user, userDto.getPassword());
    if (!valid) {
      return new ResponseEntity<>(new MessageDto("密码错误"), HttpStatus.UNAUTHORIZED);
    }

    String token = CodecUtils.randomString(TOKEN_LENGTH);
    Instant validDate = Instant.now().plusSeconds(3600);
    securityManager.addToken(response,
      new SecurityUser(user.getUsername(), user.getRoleList()),
      token,
      validDate);

    return new ResponseEntity<>(new MessageDto(""), HttpStatus.OK);
  }

  @HasRole("ADMIN")
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    try {
      userService.createUser(userDto);
    } catch (DuplicateKeyException exception) {
      return new ResponseEntity<>(new MessageDto("用户名已经存在"), HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.ok(userDto);
  }

  @RequestMapping(path = "/logout",method = RequestMethod.GET)
  public ResponseEntity<MessageDto> logout(HttpServletRequest request, HttpServletResponse response){
    securityManager.removeToken(request, response);
    return new ResponseEntity<>(new MessageDto(""), HttpStatus.OK);
  }
}
