package com.ziheliu.dao.mybatis;

import com.ziheliu.model.entity.UserRole;
import com.ziheliu.model.entity.Role;
import com.ziheliu.model.entity.User;
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
