package example;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import example.domain.User;
import example.domain.UserExample;
import example.domain.UserExample.Criteria;
import example.mapper.UserMapper;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * https://mp.weixin.qq.com/s/sk0Kou9V727tRe5wddmDig
 * <p>
 * <p>3.2.4 版本之后，启用了  parameterType 参数，如果与参数类型不一致，会抛异常。
 */
class ExampleTest {

  SqlSessionFactory sessionFactory;

  JdbcTransaction jdbcTransaction;

  Configuration configuration;

  @BeforeEach
  void before() throws IOException {

    //使用MyBatis提供的Resources类加载mybatis的配置文件
    Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
    //构建sqlSession的工厂
    sessionFactory = new SqlSessionFactoryBuilder().build(reader);

    configuration = sessionFactory.getConfiguration();
    jdbcTransaction = new JdbcTransaction(sessionFactory.openSession().getConnection());
  }

  @Test
  public void example() throws SQLException {

    UserExample example = new UserExample();
    Criteria andCriteria = example.createCriteria();
    andCriteria.andIdIn(Lists.newArrayList(1, 2, 3));

    Criteria or = example.or();
    or.andIdEqualTo(1);
    or.andIdEqualTo(2);

    SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH, false);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    List<User> users = mapper.selectByExample(example);

    for (User user : users) {
      System.out.println(JSON.toJSON(user));
    }


  }


}
