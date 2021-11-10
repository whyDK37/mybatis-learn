package example;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import example.domain.User;
import example.domain.UserExample;
import example.domain.UserExample.Criteria;
import example.mapper.ExtUserMapper;
import example.mapper.UserMapper;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtUserMapperTest {


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
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    Map<String, Object> params = new HashMap<>();
    params.put("sql", "select id, name, address from user where id = #{id,jdbcType=INTEGER}");
    params.put("id", "1");
    List<User> users = mapper.getBySql(params);

    for (User user : users) {
      System.out.println(JSON.toJSON(user));
    }


  }


}
