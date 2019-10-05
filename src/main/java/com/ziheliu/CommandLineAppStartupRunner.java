package com.ziheliu;

import com.ziheliu.model.dto.UserDto;
import com.ziheliu.service.UserService;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

  private final UserService userService;

  @Autowired
  public CommandLineAppStartupRunner(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void run(String... args) throws Exception {
    UserDto userDto = new UserDto();
    userDto.setUsername("admin");
    userDto.setPassword("admin");
    userDto.setRoleList(Arrays.asList("BASIC", "ADMIN"));
    try {
      LOGGER.info("No admin user, so create admin user.");
      userService.createUser(userDto);
    } catch (DuplicateKeyException exception) {
      LOGGER.info("Admin user had already been created.");
    }
  }
}
