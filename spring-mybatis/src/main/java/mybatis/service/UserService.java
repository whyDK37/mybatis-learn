package mybatis.service;

import java.time.LocalDate;
import mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Transactional
  public void updateUser() {
    for (int i = 0; i < 4; i++) {
      userMapper.updateUser(i + 1, LocalDate.now().toString());
    }
  }
}
