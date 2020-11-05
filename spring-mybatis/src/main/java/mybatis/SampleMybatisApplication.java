package mybatis;

import mybatis.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleMybatisApplication {


  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisApplication.class, args);
  }

}