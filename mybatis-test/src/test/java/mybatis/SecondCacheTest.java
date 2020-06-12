package mybatis;

import example.mapper.UserMapper;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

class SecondCacheTest {

  @Test
  void test() throws IOException {

    //使用MyBatis提供的Resources类加载mybatis的配置文件
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    //构建sqlSession的工厂
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    SqlSession session = sessionFactory.openSession();

    UserMapper userMapper = session.getMapper(UserMapper.class);

    System.out.println(userMapper.getUserByID(1));
    System.out.println(userMapper.getUserByID(1));

    session.commit();
    System.out.println(userMapper.getUserByID(1));
    System.out.println(userMapper.getUserByID(1));
    System.out.println(userMapper.getUserByID(1));
  }

}
