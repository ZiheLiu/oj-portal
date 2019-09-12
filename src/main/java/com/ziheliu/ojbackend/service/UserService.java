package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.model.dto.UserDto;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
  public UserDto getUserByUsername(String username);

  public UserDto createUser(UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

  public boolean authenticate(UserDto userDto, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
