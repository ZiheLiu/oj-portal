package com.ziheliu.service.impl;

import com.ziheliu.dao.mybatis.UserMapper;
import com.ziheliu.model.entity.Role;
import com.ziheliu.model.entity.UserRole;
import com.ziheliu.model.dto.UserDto;
import com.ziheliu.model.entity.User;
import com.ziheliu.service.UserService;
import com.ziheliu.utils.CodecUtils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    List<String> roleDtoList = roleList
      .stream()
      .map(Role::getDescription)
      .collect(Collectors.toList());

    return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getSalt(), roleDtoList);
  }

  @Override
  @Transactional
  public UserDto createUser(UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, DuplicateKeyException {
    String salt = CodecUtils.randomSalt();
    String encodePassword = CodecUtils.sha256Encode(userDto.getPassword(), salt);
    User user = new User(userDto.getUsername(), encodePassword, salt);
    userMapper.insertUser(user);
    userDto.setId(user.getId());

    for (String roleDesc : userDto.getRoleList()) {
      Role role = userMapper.selectRoleByDescription(roleDesc);
      userMapper.insertUserRole(new UserRole(userDto.getId(), role.getId()));
    }

    return userDto;
  }

  @Override
  public boolean authenticate(UserDto userDto, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    String encodePassword = CodecUtils.sha256Encode(password, userDto.getSalt());
    return encodePassword.equals(userDto.getPassword());
  }
}
