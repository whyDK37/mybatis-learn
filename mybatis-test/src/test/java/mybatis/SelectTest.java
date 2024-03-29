package mybatis;

import example.domain.User;
import example.mapper.ExtUserMapper;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
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
class SelectTest {

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
  void getUserByID() {

    SqlSession sqlSession = sessionFactory.openSession(true);
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    User userByID = mapper.getUserByID(1);
    System.out.println(userByID);

  }


  @Test
  void getUserByID2() throws SQLException {
    SimpleExecutor simpleExecutor = new SimpleExecutor(sessionFactory.getConfiguration(),
        jdbcTransaction);

    final MappedStatement ms = sessionFactory.getConfiguration()
        .getMappedStatement("example.mapper.ExtUserMapper.getUserByID");
    final BoundSql boundSql = ms.getBoundSql(1);

    simpleExecutor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, boundSql);

  }

  @Test
  void getMapByID() {

    SqlSession sqlSession = sessionFactory.openSession(true);
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    Map<String, Object> userByID = mapper.getMapByID(1);
    System.out.println(userByID);
  }

  @Test
  void getUserByIDAndName() {

    SqlSession sqlSession = sessionFactory.openSession(true);
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    User userByID = mapper.getUserByIDAndName(1, "bat");
    System.out.println(userByID);

  }

  @Test
  void getAll() {

    SqlSession sqlSession = sessionFactory.openSession(true);
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    List<User> all = mapper.getAll();
    System.out.println(all);

  }

  @Test
  void getAllMap() {

    SqlSession sqlSession = sessionFactory.openSession(true);
    ExtUserMapper mapper = sqlSession.getMapper(ExtUserMapper.class);

    Map<Integer, User> all = mapper.getAllMap();
    for (Entry<Integer, User> integerUserEntry : all.entrySet()) {
      System.out.println(integerUserEntry.getKey());
      System.out.println(integerUserEntry.getValue());
    }
  }

}
