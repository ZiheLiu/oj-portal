package com.ziheliu.ojbackend.service;

import com.ziheliu.ojbackend.model.dto.UserDto;

public interface UserService {
  public UserDto getUserByUsername(String username);

  public UserDto createUser(UserDto userDto);
}
