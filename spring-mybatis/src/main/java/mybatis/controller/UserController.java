package mybatis.controller;

import mybatis.domain.User;
import mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @RequestMapping("user")
  public User getUser() {
    return this.userMapper.findById(1);
  }
}
