package mybatis.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.main.web-application-type=reactive")
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  void updateUser() {
    userService.updateUser();
  }
}