package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.model.dto.UserDto;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.springframework.dao.DuplicateKeyException;

public interface UserService {
  public UserDto getUserByUsername(String username);

  public UserDto createUser(UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, DuplicateKeyException;

  public boolean authenticate(UserDto userDto, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
