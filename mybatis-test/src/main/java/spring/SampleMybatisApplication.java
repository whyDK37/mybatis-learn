package spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleMybatisApplication implements CommandLineRunner {

  private final UserMapper cityMapper;

  public SampleMybatisApplication(UserMapper userMapper) {
    this.cityMapper = userMapper;
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(this.cityMapper.findById(1));
  }

}