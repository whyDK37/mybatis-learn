package mybatis;

import example.mapper.UserMapper;
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

/**
 * https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig
 * <p>
 * <p>3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
 */
class ParameterTypeTest {

  @Test
  void test() throws IOException {

    //使用MyBatis提供的Resources类加载mybatis的配置文件
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    //构建sqlSession的工厂
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    SqlSession session = sessionFactory.openSession();

    UserMapper userMapper = session.getMapper(UserMapper.class);

    System.out.println(userMapper.getUserByID(1));
  }

}
