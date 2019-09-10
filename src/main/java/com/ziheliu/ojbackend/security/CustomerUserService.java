package com.ziheliu.ojbackend.security;

import com.ziheliu.ojbackend.model.dto.UserDto;
import com.ziheliu.ojbackend.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserService implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public CustomerUserService(UserService userService) {
    this.userService = userService;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto userDto = userService.getUserByUsername(username);
    if (userDto == null) {
      throw new UsernameNotFoundException("Username#" + username + " not found");
    }
    List<GrantedAuthority> auths = userDto.getRoleList()
      .stream()
      .map(roleDto -> new SimpleGrantedAuthority(roleDto.getDescription()))
      .collect(Collectors.toList());
    return new User(username, userDto.getPassword(), auths);
  }
}
