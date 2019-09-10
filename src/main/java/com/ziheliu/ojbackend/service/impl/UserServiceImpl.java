package com.ziheliu.ojbackend.service.impl;

import com.ziheliu.ojbackend.dao.mybatis.UserMapper;
import com.ziheliu.ojbackend.model.dto.RoleDto;
import com.ziheliu.ojbackend.model.dto.UserDto;
import com.ziheliu.ojbackend.model.entity.Role;
import com.ziheliu.ojbackend.model.entity.User;
import com.ziheliu.ojbackend.model.entity.UserRole;
import com.ziheliu.ojbackend.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  @Autowired
  public UserServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }


  @Override
  public UserDto getUserByUsername(String username) {
    User user = userMapper.selectUserByUsername(username);
    if (user == null) {
      return null;
    }

    List<Role> roleList = userMapper.selectRoleListByUsername(username);
    List<RoleDto> roleDtoList = roleList
      .stream()
      .map(role -> new RoleDto(role.getId(), role.getDescription()))
      .collect(Collectors.toList());

    return new UserDto(user.getId(), user.getUsername(), user.getPassword(), roleDtoList);
  }

  @Override
  @Transactional
  public UserDto createUser(UserDto userDto) {
    String encodePassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
    User user = new User(userDto.getUsername(), encodePassword);
    userMapper.insertUser(user);
    userDto.setId(user.getId());

    for (RoleDto roleDto : userDto.getRoleList()) {
      Role role = userMapper.selectRoleByDescription(roleDto.getDescription());
      roleDto.setId(role.getId());
      userMapper.insertUserRole(new UserRole(userDto.getId(), roleDto.getId()));
    }

    return userDto;
  }
}
