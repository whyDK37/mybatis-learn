package mybatis;

import static mybatis.TestUtils.printBatchResult;

import example.mapper.User;
import example.mapper.UserMapper;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
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
class InsertTest {

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
  void batchInsert() throws SQLException {

    BatchExecutor batchExecutor = new BatchExecutor(configuration, jdbcTransaction);

    final MappedStatement insert = configuration
        .getMappedStatement("example.mapper.UserMapper.insertUser");

    User user = new User();
    user.setName("" + new Date());
    batchExecutor.doUpdate(insert, user);
    batchExecutor.doUpdate(insert, user);

    //
    final List<BatchResult> batchResults = batchExecutor.flushStatements(false);
    jdbcTransaction.commit();
    printBatchResult(batchResults);
  }

  @Test
  void batchInsertUseMapper() {

    SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH, false);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    List<User> users = new ArrayList<>();
    users.add(new User().setName("batchInsert1" + new Date()));
    users.add(new User().setName("batchInsert2" + new Date()));

    mapper.insertUser(users.get(0));
    mapper.insertUser(users.get(1));

    sqlSession.commit();
    for (User user : users) {
      System.out.println(user.getId());
    }
  }


  @Test
  void insert() throws SQLException {

    SqlSession sqlSession = sessionFactory.openSession(true);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    User user = new User();
    user.setName("" + new Date());

    mapper.insertUser(user);
    System.out.println(user.getId());

  }

  @Test
  void insertUsers() throws SQLException {

    SqlSession sqlSession = sessionFactory.openSession(true);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    List<User> users = new ArrayList<>();
    users.add(new User().setName("a" + new Date()));
    users.add(new User().setName("b" + new Date()));

    mapper.insertUsers(users);
    for (User user : users) {
      System.out.println(user.getId());
    }

  }

  @Test
  void insertUserUseSelectKey() throws SQLException {

    SqlSession sqlSession = sessionFactory.openSession(true);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    User user = new User();
    user.setName("" + new Date());

    mapper.insertUserUseSelectKey(user);
    System.out.println(user.getId());

  }

  /**
   * 插入后获取不到id
   *
   * @throws SQLException
   */
  @Test
  void insertUsersUseSelectKey() throws SQLException {

    SqlSession sqlSession = sessionFactory.openSession(true);
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    List<User> users = new ArrayList<>();
    users.add(new User().setName("a" + new Date()));
    users.add(new User().setName("b" + new Date()));

    mapper.insertUsersUseSelectKey(users);
    for (User user : users) {
      System.out.println(user.getId());
    }

  }

}
