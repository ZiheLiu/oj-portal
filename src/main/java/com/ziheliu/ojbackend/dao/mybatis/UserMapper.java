package com.ziheliu.ojbackend.dao.mybatis;

import com.ziheliu.ojbackend.model.entity.Role;
import com.ziheliu.ojbackend.model.entity.User;
import com.ziheliu.ojbackend.model.entity.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

  public int insertUser(User user);

  public User selectUserByUsername(String username);

  public List<Role> selectRoleListByUsername(String username);

  public int insertUserRole(UserRole userRole);

  public Role selectRoleByDescription(String description);
}
